package ru.yandex.practicum.Filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.Filmorate.exceptions.ValidationException;
import ru.yandex.practicum.Filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    FilmController filmController = new FilmController();
    Film film = new Film();




    @Test
    public void testMostEarleDateRelease() {

        film.setName("Film 1");
        film.setDescription("About Film 1");
        film.setReleaseDate(LocalDate.of(1895, 12, 28));
        Assertions.assertEquals(FilmController.MOST_EARLE_DATE_RELEASE, film.getReleaseDate());
    }

    @Test
    public void testDateReleaseValidation() {

        film.setName("Film 1");
        film.setDescription("About Film 1");
        film.setReleaseDate(LocalDate.of(1700, 12, 29));
        film.setDuration(100L);
        try {
            filmController.create(film);
            fail("Ожидалось исключение ValidationException для даты релиза ранее допустимой");
        } catch (ValidationException e) {
            Assertions.assertEquals(("Дата релиза ранее " + FilmController.MOST_EARLE_DATE_RELEASE), e.getMessage());
        }
    }

    @Test
    public void testLengthDescription() {
        film.setName("Film 1");
        film.setDescription("33333333333333333333333333333333333333333333333333333333333333333" +
                "33333333333333333333333333333333333333333333333333333333333333333333333333333" +
                "33333333333333333333333333333333333333333333333333333333333333333333333333333");
        film.setReleaseDate(LocalDate.of(2000, 12, 29));
        film.setDuration(100L);

        try {
            filmController.create(film);
            fail("Ожидалось исключение ValidationException для длины описания фильма более 200 символов");
        } catch (ValidationException e) {
            Assertions.assertEquals("Длина описания не должна превышать 200 символов.", e.getMessage());
        }

    }

}


