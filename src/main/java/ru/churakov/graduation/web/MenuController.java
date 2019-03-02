package ru.churakov.graduation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.churakov.graduation.model.Menu;
import ru.churakov.graduation.service.MenuService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = MenuController. REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {
    static final String REST_URL = "/rest/menu";

    @Autowired
    MenuService service;

    @GetMapping
    public List<Menu> getAll(@RequestParam(name = "date", required = false) LocalDate date) {
        return service.getWithRestaurantAndDishes(date);
    }

    @PutMapping("/{id}/vote")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void vote(@PathVariable("id") int id) {
        service.vote(id, SecurityUtil.authUserId());
    }
}
