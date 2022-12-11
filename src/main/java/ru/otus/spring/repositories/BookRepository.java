package ru.otus.spring.repositories;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    long count();

    Book insert(Book book);

    void deleteById(long id);

    Optional<Book> getById(long id);

    Book getByTitle(String title);

    List<Book> getAll();
}
