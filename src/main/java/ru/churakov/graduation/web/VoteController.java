package ru.churakov.graduation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.churakov.graduation.model.Vote;
import ru.churakov.graduation.service.VoteService;

import java.time.LocalDate;
import java.util.List;

import static ru.churakov.graduation.util.DateTimeUtil.MAX_DATE;
import static ru.churakov.graduation.util.DateTimeUtil.MIN_DATE;
import static ru.churakov.graduation.util.Util.orElse;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String REST_URL = "/rest/votes";

    @Autowired
    VoteService service;

    @GetMapping
    public List<Vote> getAll() {
        return service.getAll(SecurityUtil.authUserId());
    }

    @GetMapping(value = "/filter")
    public List<Vote> getBetween(
            @RequestParam(value = "start", required = false) LocalDate startDate,
            @RequestParam(value = "end", required = false) LocalDate endDate) {
        return service.getBetweenDates(
                SecurityUtil.authUserId(),
                orElse(startDate, MIN_DATE),
                orElse(endDate, MAX_DATE)
        );
    }

    @GetMapping(value = "/{id}")
    public Vote get(@PathVariable("id") int id) {
        return service.get(id, SecurityUtil.authUserId());
    }
}
