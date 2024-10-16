package ru.yandex.practicum.Filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.Filmorate.exceptions.ValidationException;
import ru.yandex.practicum.Filmorate.model.Film;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Validated
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final Map<Long, Film> films = new HashMap<>();
    public static final LocalDate MOST_EARLE_DATE_RELEASE = LocalDate.of(1895, 12, 28);


    @GetMapping
    public Collection<Film> findAll() {
        log.info("Список всех фильмов представлен");
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        if (film.getNameFilm() == null || film.getNameFilm().isBlank()) {
            log.warn("Пустое название фильма.");
            throw new ValidationException("Название фильма не может быть пусты!");
        }

        if (film.getDescription().length() > 200) {
            log.warn("Превышена максимальная длина (200 символов) - {}", film.getDescription().length());
            throw new ValidationException("Длина описания не должна превышать 200 символов.");
        }

        if (film.getReleaseDate().isBefore(MOST_EARLE_DATE_RELEASE)) {
            log.warn("Дата релиза ранее {}", MOST_EARLE_DATE_RELEASE);
            throw new ValidationException("Дата релиза ранее " + MOST_EARLE_DATE_RELEASE);
        }

        if (film.getNameFilm() == null
                || film.getDurationFilm() == null
                || film.getDescription() == null
                || film.getReleaseDate() == null) {
            log.warn("Какое-то из полей не заполнено!");
            throw new ValidationException("Какое-то из полей не заполнено!");
        }

        film.setId(getNextId());

        films.put(film.getId(), film);
        log.info("Фильм добавлен");
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());


            oldFilm.setNameFilm(newFilm.getNameFilm());
            log.info("Называние изменено");
            oldFilm.setDurationFilm(newFilm.getDurationFilm());
            log.info("Продолжительность изменена");
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
            log.info("Дата релиза изменена");
            oldFilm.setDescription(newFilm.getDescription());
            log.info("Описание изменено");

            return oldFilm;
        }
        log.warn("Фильм с id - {}  отсутствует!", newFilm.getId());
        throw new ValidationException("Фильм с " + newFilm.getId() + "отсутствует!");

    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}

