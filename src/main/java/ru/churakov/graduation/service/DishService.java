package ru.churakov.graduation.service;

import ru.churakov.graduation.model.Dish;
import ru.churakov.graduation.util.exception.NotFoundException;

import java.time.LocalDate;

public interface DishService {

    Dish get(int id) throws NotFoundException;

    void update(Dish dish, int id);

    void delete(int id) throws NotFoundException;

    Dish create(Dish dish, int restaurantId, LocalDate date);
}
