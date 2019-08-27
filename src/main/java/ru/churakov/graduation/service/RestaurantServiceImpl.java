package ru.churakov.graduation.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.churakov.graduation.model.Restaurant;
import ru.churakov.graduation.repository.RestaurantRepository;
import ru.churakov.graduation.util.exception.NotFoundException;

import java.util.List;

import static ru.churakov.graduation.util.ValidationUtil.*;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private static final Sort SORT_NAME = new Sort(Sort.Direction.ASC, "name");

    private RestaurantRepository repository;

    public RestaurantServiceImpl(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Restaurant> getAll() {
        return repository.findAll(SORT_NAME);
    }

    @Override
    public Restaurant get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findById(id), id);
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNew(restaurant);
        return repository.save(restaurant);
    }

    @CacheEvict(value = "menu", allEntries = true)
    @Transactional
    @Override
    public void update(Restaurant restaurant, int id) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.findById(id), id);
        assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }

    @CacheEvict(value = "menu", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }
}
