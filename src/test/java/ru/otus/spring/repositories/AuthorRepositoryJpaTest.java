package ru.otus.spring.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Jpa для работы с авторами ")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    private static final long EXPECTED_AUTHORS_COUNT = 1;
    private static final long EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_BOOK_AUTHOR = "Tolstoy";

    @Autowired
    private AuthorRepositoryJpa jpa;


    @Test
    public void countTest() {
        long count = jpa.count();
        assertEquals(EXPECTED_AUTHORS_COUNT, count);
    }


    @Test
    public void save() {
        Author expected = new Author(0, "newAuthor",
                new ArrayList<>(List.of(new Book(0, "ewBook"))));
        jpa.insert(expected);
        long count = jpa.count();
        assertEquals(EXPECTED_AUTHORS_COUNT + 1, count);

        expected.setId(EXPECTED_AUTHORS_COUNT + 1);

        Optional<Author> actualAuthor = jpa.getById(EXISTING_AUTHOR_ID + 1);

        actualAuthor.ifPresent(author -> assertThat(author).usingRecursiveComparison().isEqualTo(expected));

    }

    @DisplayName("Обновить книгу в БД")
    @Test
    void updateTest() {
        String newAuthor = "newAuthor";
        Author expected = new Author(EXISTING_AUTHOR_ID, "newAuthor");
        jpa.insert(expected);
        Optional<Author> actualAuthor = jpa.getById(EXISTING_AUTHOR_ID);
        assertFalse(actualAuthor.isEmpty());
        actualAuthor.ifPresent(author -> {
            assertEquals(EXISTING_AUTHOR_ID, author.getId());
            assertEquals(newAuthor, author.getAuthorBook());
        });
    }

    @DisplayName("возвращать ожидаемую книгу по id")
    @Test
    void getByIdTest() {
        Optional<Author> actualAuthor = jpa.getById(EXISTING_AUTHOR_ID);
        assertFalse(actualAuthor.isEmpty());
        actualAuthor.ifPresent(author -> {
            assertEquals(EXISTING_AUTHOR_ID, author.getId());
            assertEquals(EXISTING_BOOK_AUTHOR, author.getAuthorBook());
        });
    }

    @DisplayName("возвращать ожидаемую книгу по title")
    @Test
    void getByAuthorTest() {
        Optional<Author> actualAuthor = jpa.getByAuthor(EXISTING_BOOK_AUTHOR);
        assertFalse(actualAuthor.isEmpty());
        actualAuthor.ifPresent(author -> {
            assertEquals(EXISTING_AUTHOR_ID, author.getId());
            assertEquals(EXISTING_BOOK_AUTHOR, author.getAuthorBook());
        });
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void getAllTest() {
        List<Author> authors = jpa.getAll();
        assertEquals(EXPECTED_AUTHORS_COUNT, authors.size());
    }


    @DisplayName("удалять заданного книгу по ее id")
    @Test
    void deleteById() {
        assertTrue(jpa.getById(EXISTING_AUTHOR_ID).isPresent());
        jpa.deleteById(EXISTING_AUTHOR_ID);
        assertFalse(jpa.getById(EXISTING_AUTHOR_ID).isPresent());
    }

}
