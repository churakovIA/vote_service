package ru.churakov.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.churakov.graduation.model.Dish;
import ru.churakov.graduation.model.Menu;
import ru.churakov.graduation.model.Restaurant;
import ru.churakov.graduation.repository.MenuRepository;
import ru.churakov.graduation.util.exception.IllegalRequestDataException;
import ru.churakov.graduation.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.churakov.graduation.util.Util.orElse;
import static ru.churakov.graduation.util.ValidationUtil.checkNotFound;
import static ru.churakov.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuServiceImpl implements MenuService {

    private static final LocalTime EXPIRED_TIME = LocalTime.of(11, 0);

    @Autowired
    MenuRepository repository;

    @Autowired
    VoteService voteService;

    @Override
    public Menu get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findById(id), id);
    }

    @Cacheable("menu")
    @Override
    public List<Menu> getWithRestaurantAndDishes(LocalDate date) {
        return repository.getWithRestaurantAndDishesByDate(orElse(date, LocalDate.now()));
    }

    @Override
    public Menu getByDateAndRestaurant(LocalDate date, int restaurantId) {
        LocalDate menuDate = orElse(date, LocalDate.now());
        return checkNotFound(repository.getByDateAndRestaurant(menuDate, restaurantId),
                "restaurant id=" + restaurantId + ", date=" + menuDate);
    }

    @CacheEvict(value = "menu", allEntries = true)
    @Transactional
    @Override
    public void updateDishes(LocalDate date, Restaurant restaurant, List<Dish> dishes) {
        Assert.notNull(dishes, "list dishes must not be null");
        LocalDate menuDate = orElse(date, LocalDate.now());
        if (LocalDate.now().isAfter(menuDate)) {
            throw new IllegalRequestDataException("нельзя редактировать меню прошлого периода");
        }
        Menu menu = repository.getByDateAndRestaurant(menuDate, restaurant.getId())
                .orElseGet(() -> repository.save(
                        new Menu(menuDate, restaurant)));
        dishes.forEach(dish -> dish.setMenu(menu));
        //menu.setDishes(dishes); // not work https://stackoverflow.com/questions/5587482
        menu.getDishes().clear();
        menu.getDishes().addAll(dishes);
        repository.save(menu);
    }

    @Transactional
    @Override
    public void vote(int id, int userId) {
        Menu menu = get(id);
        if (!LocalDate.now().equals(menu.getDate())) {
            throw new IllegalRequestDataException("меню устарело");
        }
        if (LocalTime.now().isAfter(EXPIRED_TIME) || voteService.updateIfPresent(menu, userId) == null) {
            voteService.save(menu, userId);
        }
    }
}
