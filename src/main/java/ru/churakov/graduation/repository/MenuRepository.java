package ru.churakov.graduation.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.churakov.graduation.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @EntityGraph(attributePaths = {"restaurant", "dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Menu m WHERE m.date=:date")
    List<Menu> getWithRestaurantAndDishesByDate(@Param("date") LocalDate date);

    @EntityGraph(attributePaths = {"restaurant", "dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Menu m WHERE m.date=:date and m.restaurant.id=:id")
    Optional<Menu> getByDateAndRestaurant(@Param("date") LocalDate date, @Param("id") int id);
}