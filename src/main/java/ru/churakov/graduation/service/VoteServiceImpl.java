package ru.churakov.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.churakov.graduation.model.Vote;
import ru.churakov.graduation.repository.RestaurantRepository;
import ru.churakov.graduation.repository.UserRepository;
import ru.churakov.graduation.repository.VoteRepository;
import ru.churakov.graduation.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.churakov.graduation.util.Util.EXPIRED_TIME;
import static ru.churakov.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    VoteRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MenuService menuService;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public Vote get(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.findById(id).filter(v -> v.getUser().getId() == userId), id);
    }

    /**
     * After EXPIRED_TIME create a new voice, if the user has already voted, then a DataIntegrityViolationException
     * occurs because of the uniqueness of the base index.
     * Before EXPIRED_TIME we are looking for a voice in the database. If we find something we update,
     * otherwise we create a new voice.
     */
    @Transactional
    @Override
    public UpdatedVote createOrUpdate(int restaurantId, int userId) {
        return LocalTime.now().isAfter(EXPIRED_TIME) ? UpdatedVote.getCreated(create(restaurantId, userId)) :
                repository.findByUserIdAndDate(userId, LocalDate.now())
                        .map(v -> {
                            v.setMenu(menuService.get(restaurantRepository.getOne(restaurantId)));
                            return UpdatedVote.getUpdated(v);
                        })
                        .orElseGet(() -> UpdatedVote.getCreated(create(restaurantId, userId)));
    }

    @Override
    public List<Vote> getAll(int userId) {
        return repository.findByUserIdOrderByDateDesc(userId);
    }

    @Override
    public List<Vote> getBetweenDates(int userId, LocalDate startDate, LocalDate endDate) {
        Assert.notNull(startDate, "startDate must not be null");
        Assert.notNull(endDate, "endDate  must not be null");
        return repository.findByUserIdAndDateBetweenOrderByDateDesc(userId, startDate, endDate);
    }

    private Vote create(int restaurantId, int userId) {
        return repository.save(
                new Vote(LocalDate.now(),
                        menuService.get(restaurantRepository.getOne(restaurantId)), userRepository.getOne(userId)));
    }
}
