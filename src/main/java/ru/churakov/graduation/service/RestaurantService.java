package ru.churakov.graduation.service;

import ru.churakov.graduation.model.Restaurant;
import ru.churakov.graduation.util.exception.NotFoundException;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> getAll();

    Restaurant get(int id) throws NotFoundException;

    Restaurant create(Restaurant restaurant);

    void update(Restaurant restaurant, int id);

    void delete(int id) throws NotFoundException;
}
