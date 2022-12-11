package ru.otus.spring.repositories;

import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    long count();

    Author insert(Author author);

    void deleteById(long id);

    Optional<Author> getById(long id);

    Optional<Author> getByAuthor(String author);

    List<Author> getAll();
}
