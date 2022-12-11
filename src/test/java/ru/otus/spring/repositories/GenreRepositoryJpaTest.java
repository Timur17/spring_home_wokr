package ru.otus.spring.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами ")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {
    private static final int EXPECTED_GENRES_COUNT = 1;
    private static final int EXISTING_GENRE_ID = 1;
    private static final String EXISTING_BOOK_GENRE = "Historical novel";

    @Autowired
    private GenreRepositoryJpa jpa;


    @Test
    public void countTest() {
        long count = jpa.count();
        assertEquals(EXPECTED_GENRES_COUNT, count);
    }

    @DisplayName("Сохранить жанр")
    @Test
    public void save() {
        Genre expected = new Genre(0, "newGenre",
                new ArrayList<>(List.of(new Book(0, "newBook"))));
        jpa.insert(expected);
        long count = jpa.count();
        assertEquals(EXPECTED_GENRES_COUNT + 1, count);

        expected.setId(EXPECTED_GENRES_COUNT + 1);
        Optional<Genre> actualGenre = jpa.getById(EXISTING_GENRE_ID + 1);
        actualGenre.ifPresent(genre -> assertThat(genre).usingRecursiveComparison().isEqualTo(expected));
    }

    @DisplayName("Обновить жанр")
    @Test
    void updateTest() {
        Genre expected = new Genre(EXISTING_GENRE_ID, "newGenre");
        jpa.insert(expected);
        Optional<Genre> actualGenre = jpa.getById(EXISTING_GENRE_ID);
        assertFalse(actualGenre.isEmpty());
        actualGenre.ifPresent(genre -> {
            assertEquals(EXISTING_GENRE_ID, genre.getId());
            assertEquals(expected.getGenreBook(), genre.getGenreBook());
        });
    }


    @DisplayName("возвращать ожидаемый жанр по id")
    @Test
    void getByIdTest() {
        Genre expected = new Genre(EXISTING_GENRE_ID, EXISTING_BOOK_GENRE);
        Optional<Genre> actualGenre = jpa.getById(EXISTING_GENRE_ID);
        assertFalse(actualGenre.isEmpty());
        actualGenre.ifPresent(genre -> {
            assertEquals(EXISTING_GENRE_ID, genre.getId());
            assertEquals(expected.getGenreBook(), genre.getGenreBook());
        });
    }


    @DisplayName("возвращать  ожидаемый жанр по title")
    @Test
    void getByGenreTest() {
        Genre expected = new Genre(EXISTING_GENRE_ID, EXISTING_BOOK_GENRE);
        Optional<Genre> actualGenre = jpa.getByGenre(EXISTING_BOOK_GENRE);
        assertFalse(actualGenre.isEmpty());
        actualGenre.ifPresent(genre -> {
            assertEquals(genre.getId(), EXISTING_GENRE_ID);
            assertEquals(genre.getGenreBook(), expected.getGenreBook());
        });
    }

    @DisplayName("возвращать ожидаемый список жанров")
    @Test
    void getAllTest() {
        List<Genre> genres = jpa.getAll();
        assertEquals(EXPECTED_GENRES_COUNT, genres.size());
    }


    @DisplayName("удалять заданный жанр по id")
    @Test
    void deleteById() {
        Optional<Genre> beforeGenre = jpa.getById(EXISTING_GENRE_ID);
        assertTrue(beforeGenre.isPresent());

        jpa.deleteById(EXISTING_GENRE_ID);
        Optional<Genre> afterGenre = jpa.getById(EXISTING_GENRE_ID);
        assertFalse(afterGenre.isPresent());
    }

}