package ru.churakov.graduation.service;

import ru.churakov.graduation.model.Menu;
import ru.churakov.graduation.model.Vote;

public interface VoteService {

    Vote save(Menu menu, int userId);

    Vote updateIfPresent(Menu menu, int userId);

}
