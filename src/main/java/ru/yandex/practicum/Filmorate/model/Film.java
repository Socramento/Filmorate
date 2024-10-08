package ru.yandex.practicum.Filmorate.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Data
@Getter
@Setter
public class Film {
    @NotNull
    private Long id;
    @NotNull(message = "Название не должно быть пустым")
    private String nameFilm;
    @NotNull(message = "Описание не должно быть пустым")
    private String description;
    private LocalDate releaseDate;
    @Positive(message = "Значение должно быть положительным")
    private Long durationFilm;


}
