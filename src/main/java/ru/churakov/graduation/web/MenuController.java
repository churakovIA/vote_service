package ru.churakov.graduation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.churakov.graduation.model.Menu;
import ru.churakov.graduation.service.MenuService;

import java.time.LocalDate;
import java.util.List;

import static ru.churakov.graduation.util.Util.orElse;

@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {
    static final String REST_URL = "/rest/menu";

    @Autowired
    MenuService service;

    @GetMapping
    public List<Menu> getAll(@RequestParam(name = "date", required = false) LocalDate date) {
        return service.getAllWithRestaurantAndDishes(orElse(date, LocalDate.now()));
    }
}
