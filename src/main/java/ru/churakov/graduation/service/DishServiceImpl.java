package ru.churakov.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.churakov.graduation.model.Dish;
import ru.churakov.graduation.model.Menu;
import ru.churakov.graduation.repository.DishRepository;
import ru.churakov.graduation.repository.RestaurantRepository;
import ru.churakov.graduation.util.exception.NotFoundException;

import java.time.LocalDate;

import static ru.churakov.graduation.util.ValidationUtil.*;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    DishRepository repository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    MenuService menuService;

    @Override
    public Dish get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findById(id), id);
    }

    @CacheEvict(value = "menu", allEntries = true)
    @Transactional
    @Override
    public void update(Dish dish, int id) {
        Assert.notNull(dish, "dish must not be null");
        Menu menu = checkNotFoundWithId(repository.findById(id), id).getMenu();
        checkMenuDateBeforeUpdate(menu.getDate());
        assureIdConsistent(dish, id);
        dish.setMenu(menu);
        repository.save(dish);
    }

    @CacheEvict(value = "menu", allEntries = true)
    @Transactional
    @Override
    public void delete(int id) throws NotFoundException {
        checkMenuDateBeforeUpdate(checkNotFoundWithId(repository.findById(id), id).getMenu().getDate());
        repository.deleteById(id);
    }

    @CacheEvict(value = "menu", allEntries = true)
    @Transactional
    @Override
    public Dish create(Dish dish, int restaurantId, LocalDate date) {
        Assert.notNull(dish, "dish must not be null");
        Assert.notNull(date, "date must not be null");
        checkNew(dish);
        checkMenuDateBeforeUpdate(date);
        dish.setMenu(menuService.get(restaurantRepository.getOne(restaurantId), date));
        return repository.save(dish);
    }
}
