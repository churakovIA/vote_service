package ru.churakov.graduation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.churakov.graduation.model.Menu;
import ru.churakov.graduation.model.Restaurant;
import ru.churakov.graduation.service.MenuService;
import ru.churakov.graduation.service.RestaurantService;
import ru.churakov.graduation.to.MenuTo;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    static final String REST_URL = "/rest/restaurants";

    @Autowired
    RestaurantService service;

    @Autowired
    MenuService menuService;

    @GetMapping
    public List<Restaurant> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public Restaurant get(@PathVariable("id") int id){
        return service.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant){
        Restaurant created = service.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable("id") int id){
        service.update(restaurant, id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id){
        service.delete(id);
    }

    @GetMapping(value = "/{id}/menu")
    public Menu getMenu(@PathVariable("id") int id,
                        @RequestParam(value = "date", required = false) LocalDate date) {
        return menuService.getByDateAndRestaurant(date, id);
    }

    @PutMapping(value = "/{id}/menu")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateMenu(@PathVariable("id") int id,
                           @RequestParam(value = "date", required = false) LocalDate date,
                           @Valid @RequestBody MenuTo menuTo) {
        menuService.updateDishes(date, service.get(id), menuTo.getDishes());
    }
}
