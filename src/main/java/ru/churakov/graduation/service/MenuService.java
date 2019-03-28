package ru.churakov.graduation.service;

import ru.churakov.graduation.model.Dish;
import ru.churakov.graduation.model.Menu;
import ru.churakov.graduation.model.Restaurant;
import ru.churakov.graduation.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface MenuService {

    List<Menu> getAllWithRestaurantAndDishes(LocalDate date);

    Menu getWithRestaurantAndDishes(LocalDate date, int restaurantId) throws NotFoundException;

    void updateDishes(LocalDate date, int restaurantId, List<Dish> dishes);

    Menu get(Restaurant restaurant, LocalDate date) throws NotFoundException;

    Menu get(Restaurant restaurant) throws NotFoundException;

}
