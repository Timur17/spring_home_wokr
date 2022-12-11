package ru.otus.spring.repositories;

import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    long count();

    Genre insert(Genre genre);

    void deleteById(long id);

    Optional<Genre> getById(long id);

    Optional<Genre> getByGenre(String genre);

    List<Genre> getAll();
}
