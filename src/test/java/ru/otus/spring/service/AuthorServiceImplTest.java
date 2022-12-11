package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repositories.AuthorRepositoryJpa;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Сервис для работы с авторами ")
@DataJpaTest
@Import({AuthorServiceImpl.class, AuthorRepositoryJpa.class})
class AuthorServiceImplTest {

    private static final long EXPECTED_AUTHORS_COUNT = 1;
    private static final long EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_BOOK_AUTHOR = "Tolstoy";

    private static final int EXPECTED_BOOK_COUNT = 1;
    private static final String EXISTING_BOOK_GENRE = "Historical novel";
    private static final String EXISTING_BOOK_TITLE = "war and peace";

    @Autowired
    private AuthorServiceImpl service;

    @Test
    void count() {
        assertEquals(EXPECTED_AUTHORS_COUNT, service.count());
    }

    @Test
    void insert() {
        String newAuthor = "newAuthor";
        Author author = service.insert(newAuthor);
        assertEquals(newAuthor, author.getAuthorBook());
        Author authorAddRepeat = service.insert(newAuthor);
        assertNull(authorAddRepeat);
    }

    @Test
    void updateById() {
        String newAuthor = "updAuthor";
        Author author = service.updateById(newAuthor, EXISTING_AUTHOR_ID);
        assertEquals(newAuthor, author.getAuthorBook());

        Author authorNotExist = service.updateById("authorNotExist", 2);
        assertNull(authorNotExist);
    }

    @Test
    void deleteById() {
        Optional<Author> optionalAuthor = service.getById(EXISTING_AUTHOR_ID);
        Author author = optionalAuthor.orElse(null);
        assertNotNull(author);

        service.deleteById(EXISTING_AUTHOR_ID);

        Optional<Author> optionalAuthorAfterDelete = service.getById(EXISTING_AUTHOR_ID);
        Author authorAfterDelete = optionalAuthorAfterDelete.orElse(null);
        assertNull(authorAfterDelete);
    }

    @Test
    void getAll() {
        List<Author> authors = service.getAll();
        authors.stream().forEach(author -> {
            assertNotNull(author);
            assertEquals(EXISTING_AUTHOR_ID, author.getId());
            assertEquals(EXISTING_BOOK_AUTHOR, author.getAuthorBook());
            assertEquals(EXISTING_BOOK_TITLE, author.getBooks().get(EXPECTED_BOOK_COUNT - 1).getTitle());
            assertEquals(EXISTING_BOOK_GENRE, author.getBooks().get(EXPECTED_BOOK_COUNT - 1).getGenre().getGenreBook());
            assertNotNull(author.getBooks().get(EXPECTED_BOOK_COUNT - 1).getComments());
        });
    }

    @Test
    void getById() {
        Optional<Author> optionalAuthor = service.getById(EXISTING_AUTHOR_ID);
        Author author = optionalAuthor.orElse(null);
        assertNotNull(author);
        assertEquals(EXISTING_AUTHOR_ID, author.getId());
        assertEquals(EXISTING_BOOK_AUTHOR, author.getAuthorBook());
        assertEquals(EXISTING_BOOK_TITLE, author.getBooks().get(EXPECTED_BOOK_COUNT - 1).getTitle());
        assertEquals(EXISTING_BOOK_GENRE, author.getBooks().get(EXPECTED_BOOK_COUNT - 1).getGenre().getGenreBook());
        assertNotNull(author.getBooks().get(EXPECTED_BOOK_COUNT - 1).getComments());

        Optional<Author> optionalAuthorWithUnknownId = service.getById(3);
        assertTrue(optionalAuthorWithUnknownId.isEmpty());
    }
}