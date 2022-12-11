package ru.otus.spring.service;


import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    long count();

    Genre insert(String genre);

    Genre updateById(String genre, int id);

    void deleteById(int id);

    List<Genre> getAll();

    Optional<Genre> getById(int id);

}
