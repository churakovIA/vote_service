package ru.churakov.graduation.util;

import ru.churakov.graduation.model.User;
import ru.churakov.graduation.to.UserTo;

public class UserUtil {
    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }
}