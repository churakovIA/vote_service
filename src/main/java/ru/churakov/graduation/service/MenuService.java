package ru.churakov.graduation.service;

import ru.churakov.graduation.model.Dish;
import ru.churakov.graduation.model.Menu;
import ru.churakov.graduation.model.Restaurant;
import ru.churakov.graduation.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface MenuService {

    Menu get(int id) throws NotFoundException;

    List<Menu> getWithRestaurantAndDishes(LocalDate date);

    Menu getByDateAndRestaurant(LocalDate date, int restaurantId) throws NotFoundException;

    void updateDishes(LocalDate date, Restaurant restaurant, List<Dish> dishes);

    void vote(int id, int userId);

}
