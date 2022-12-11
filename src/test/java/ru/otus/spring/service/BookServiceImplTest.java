package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Book;
import ru.otus.spring.repositories.BookRepositoryJpa;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Сервис для работы с книгами")
@DataJpaTest
@Import({BookServiceImpl.class, BookRepositoryJpa.class})
class BookServiceImplTest {
    private static final String EXISTING_BOOK_AUTHOR = "Tolstoy";
    private static final int EXPECTED_BOOK_COUNT = 1;
    private static final long EXPECTED_BOOK_ID = 1;
    private static final String EXISTING_BOOK_GENRE = "Historical novel";
    private static final String EXISTING_BOOK_TITLE = "war and peace";


    @Autowired
    BookServiceImpl service;

    @Test
    void count() {
        assertEquals(EXPECTED_BOOK_COUNT, service.count());
    }

    @Test
    void insert() {
        String newBook = "newBook";
        String newAuthor = "newAuthor";
        String newGenre = "newGenre";

        Book book = service.insert(newBook, newAuthor, newGenre);
        assertEquals(newBook, book.getTitle());
        assertEquals(newAuthor, book.getAuthor().getAuthorBook());
        assertEquals(newGenre, book.getGenre().getGenreBook());

        Book bookAddRepeat = service.insert(newBook, newAuthor, newGenre);
        assertNull(bookAddRepeat);
    }

    @Test
    void updateById() {
        String newBook = "updBook";
        Book book = service.updateById(newBook, EXPECTED_BOOK_ID);
        assertEquals(newBook, book.getTitle());

        Book bookNotExistId = service.updateById(newBook, 3);
        assertNull(bookNotExistId);

    }

    @Test
    void deleteById() {
        Optional<Book> optionalBook = service.getById(EXPECTED_BOOK_ID);
        Book book = optionalBook.orElse(null);
        assertNotNull(book);

        service.deleteById(EXPECTED_BOOK_ID);

        Optional<Book> optionalBookAfterDelete = service.getById(EXPECTED_BOOK_ID);
        Book bookAfterDelete = optionalBookAfterDelete.orElse(null);
        assertNull(bookAfterDelete);
    }

    @Test
    void getAll() {
        List<Book> books = service.getAll();
        books.forEach(book -> {
            assertNotNull(book);
            assertNotNull(book.getTitle());
            assertNotNull(book.getAuthor());
            assertNotNull(book.getGenre());
            assertNotNull(book.getComments());
        });
    }

    @Test
    void getById() {
        Optional<Book> optionalBook = service.getById(EXPECTED_BOOK_ID);
        Book book = optionalBook.orElse(null);
        assertNotNull(book);
        assertNotNull(book.getTitle());
        assertNotNull(book.getAuthor().getAuthorBook());
        assertNotNull(book.getGenre().getGenreBook());
        book.getComments().forEach(Assertions::assertNotNull);

        book.getComments().forEach(comment -> assertNotNull(comment.getCommentBook()));

        Optional<Book> optionalBookNotExistId = service.getById(3);
        assertFalse(optionalBookNotExistId.isPresent());


    }
}