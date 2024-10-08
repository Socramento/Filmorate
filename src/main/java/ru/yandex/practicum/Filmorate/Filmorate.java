package ru.yandex.practicum.Filmorate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Filmorate {
    private static final Logger log = LoggerFactory.getLogger(Filmorate.class);

    public static void main(final String[] args) {
        SpringApplication.run(Filmorate.class, args);

        ((ch.qos.logback.classic.Logger) LoggerFactory
                .getLogger(Logger.ROOT_LOGGER_NAME))
                .setLevel(Level.WARN);
    }
}
