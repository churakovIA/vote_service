package ru.churakov.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.churakov.graduation.model.User;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {
    User getByEmail(String email);
}
