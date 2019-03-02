package ru.churakov.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.churakov.graduation.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Override
    @Transactional
    Vote save(Vote vote);

    @Query("SELECT m FROM Vote m WHERE m.user.id=:userId and m.date=:date")
    Optional<Vote> findByUserAndDate(@Param("userId") int userId, @Param("date") LocalDate date);
}
