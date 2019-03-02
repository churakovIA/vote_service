package ru.churakov.graduation;

import ru.churakov.graduation.model.Restaurant;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {

    public final static Restaurant RESTAURANT = new Restaurant(100002, "Петрушка");
    public final static Restaurant RESTAURANT_100003 = new Restaurant(100003, "Мясо");
    public final static Restaurant RESTAURANT_100004 = new Restaurant(100004, "Рыба");
    public final static Restaurant RESTAURANT_100005 = new Restaurant(100005, "Ананас");

    public final static List<Restaurant> RESTAURANTS = List.of(RESTAURANT_100005, RESTAURANT_100003, RESTAURANT, RESTAURANT_100004);

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "voteCount");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("voteCount").isEqualTo(expected);
    }


}
