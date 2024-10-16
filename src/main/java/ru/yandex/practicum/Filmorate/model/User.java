package ru.yandex.practicum.Filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;


@Data
@Getter
@Setter
public class User {

    private Long id;
    @Email
    @NotNull
    private String email;
    @NotNull
    private String login;

    private String userName;
    @NotNull
    @PastOrPresent (message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;


}
