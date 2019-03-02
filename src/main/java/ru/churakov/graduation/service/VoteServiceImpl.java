package ru.churakov.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.churakov.graduation.model.Menu;
import ru.churakov.graduation.model.Vote;
import ru.churakov.graduation.repository.UserRepository;
import ru.churakov.graduation.repository.VoteRepository;

import java.time.LocalDate;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    VoteRepository repository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Vote save(Menu menu, int userId) {
        Assert.notNull(menu, "menu must not be null");
        return repository.save(new Vote(LocalDate.now(), menu, userRepository.getOne(userId)));
    }

    @Transactional
    @Override
    public Vote updateIfPresent(Menu menu, int userId) {
        Assert.notNull(menu, "menu must not be null");
        return repository.findByUserAndDate(userId, LocalDate.now())
                .map(v -> {
                    v.setMenu(menu);
                    return v;
                })
                .orElse(null);
    }

}
