package ru.churakov.graduation;

import ru.churakov.graduation.model.*;
import ru.churakov.graduation.to.MenuTo;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.time.LocalDate.of;
import static ru.churakov.graduation.model.AbstractBaseEntity.START_SEQ;

public class TestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static final Restaurant RESTAURANT_100002 = new Restaurant(100002, "Нихао");
    public static final Restaurant RESTAURANT_100003 = new Restaurant(100003, "Mama Roma");
    public static final Restaurant RESTAURANT_100004 = new Restaurant(100004, "Корюшка");
    public static final Restaurant RESTAURANT_100005 = new Restaurant(100005, "Тако");

    public static final Dish DISH_100010 = new Dish(100010, "БАРАНЬИ РЁБРЫШКИ ПО-ХУНАНЬСКИ", 78000, null);
    public static final Dish DISH_100011 = new Dish(100011, "Паста Делла Мамма", 39500, null);
    public static final Dish DISH_100012 = new Dish(100012, "ЦЫПЛЕНОК, ТОМЛЕННЫЙ В ИМБИРНОМ СОУСЕ", 68000, null);
    public static final Dish DISH_100013 = new Dish(100013, "Судак Портофино с соусом песто", 42500, null);

    public static final Menu MENU_100006 = new Menu(100006, of(2018, Month.DECEMBER, 11), RESTAURANT_100002, List.of(DISH_100010));
    public static final Menu MENU_100007 = new Menu(100007, of(2018, Month.DECEMBER, 11), RESTAURANT_100003, List.of(DISH_100011));
    public static final Menu MENU_100008 = new Menu(100008, LocalDate.now(), RESTAURANT_100002, List.of(DISH_100012));
    public static final Menu MENU_100009 = new Menu(100009, LocalDate.now(), RESTAURANT_100003, List.of(DISH_100013));

    public static final int VOTE100014_ID = START_SEQ + 14;
    public static final Vote VOTE_100014 = new Vote(VOTE100014_ID, of(2018, Month.DECEMBER, 11), MENU_100006, USER);

    public static Vote getCreatedVote() {
        return new Vote(null, LocalDate.now(), MENU_100008, USER);
    }

    public static Restaurant getCreatedRestaurant() {
        return new Restaurant(null, "New");
    }

    public static Restaurant getUpdatedRestaurant() {
        return new Restaurant(RESTAURANT_100003.getId(), RESTAURANT_100003.getName() + " Updated");
    }

    public static MenuTo getUpdatedMenuTo() {
        return new MenuTo(List.of(
                new Dish(null, "Пельмени", 55500, null),
                new Dish(null, "Борщ", 22200, null),
                new Dish(null, "Яичница", 33300, null)));
    }

    public static Dish getCreatedDish() {
        return new Dish(null, "new", 100, null);
    }

    public static Dish getUpdatedDish() {
        return new Dish(DISH_100013.getId(), "UPDATED " + DISH_100013.getName(), 77777, null);
    }

}
