package ru.yandex.practicum.Filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.Filmorate.exceptions.ValidationException;
import ru.yandex.practicum.Filmorate.model.Film;
import ru.yandex.practicum.Filmorate.model.User;

import java.lang.annotation.RetentionPolicy;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final Map<Long, User> users = new HashMap<>();


    @GetMapping
    public Collection<User> findAll() {
        log.info("Список всех пользователей представлен");
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (user.getLogin() == null || user.getLogin().contains(" ")) {
            throw new ValidationException("Не заполнен логин или в нем присутствую пробелы");
        }

        if (user.getUserName() == null || user.getUserName().isBlank()) {

             user.setUserName(user.getLogin());
            log.info("Имя заполнено.");
        }

        user.setId(getNextId());

        users.put(user.getId(), user);
        log.info("Пользователь добавлен");
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());


            oldUser.setUserName(newUser.getUserName());
            log.info("Имя изменено");
            oldUser.setLogin(newUser.getLogin());
            log.info("Логин изменен");
            oldUser.setBirthday(newUser.getBirthday());
            log.info("День рождения изменен ");
            oldUser.setEmail(newUser.getEmail());
            log.info("Email изменен");

            return oldUser;
        }
        log.warn("Пользователь с id -  {}  отсутствует!", newUser.getId());
        throw new ValidationException("Пользователь с id " + newUser.getId() + " отсутствует!");

    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
