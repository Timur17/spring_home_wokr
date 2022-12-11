package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repositories.BookRepositoryJpa;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepositoryJpa bookRepositoryJpa;

    public BookServiceImpl(BookRepositoryJpa bookRepositoryJpa) {
        this.bookRepositoryJpa = bookRepositoryJpa;
    }

    @Override
    public long count() {
        return bookRepositoryJpa.count();
    }

    @Transactional
    @Override
    public Book insert(String title, String author, String genre) {
        Book bookInStore = bookRepositoryJpa.getByTitle(title);
        if (bookInStore == null){
            return bookRepositoryJpa.insert(new Book(0, title, new Author(author), new Genre(genre)));
        }else {
            return null;
        }
    }

    @Transactional
    @Override
    public Book updateById(String title, long id) {
        Optional<Book> optionalBook = bookRepositoryJpa.getById(id);
        Book book = optionalBook.orElse(null);
        if (book !=null){
            book.setTitle(title);
            return bookRepositoryJpa.insert(book);
        }
        else {
            return null;
        }
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepositoryJpa.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        List<Book> books = bookRepositoryJpa.getAll();
        books.forEach(book -> {
            book.getAuthor().getId();
            book.getAuthor().getAuthorBook();
            book.getGenre().getId();
            book.getGenre().getGenreBook();
        });
        return books;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> getById(long id) {
        Optional<Book> optionalBook = bookRepositoryJpa.getById(id);
        optionalBook.ifPresent(book -> {
            book.getComments().forEach(comment -> comment.getCommentBook());
        });
        return optionalBook;
    }
}
