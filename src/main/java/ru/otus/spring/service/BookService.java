package ru.otus.spring.service;


import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    long count();

    Book insert(String title, String author, String genre);

    Book updateById(String title, long id);

    void deleteById(long id);

    List<Book> getAll();

    Optional<Book> getById(long id);

}
