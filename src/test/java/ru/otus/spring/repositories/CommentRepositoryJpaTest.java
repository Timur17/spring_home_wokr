package ru.otus.spring.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Comment;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {

    private static final int EXPECTED_COMMENT_COUNT = 1;
    private static final int EXISTING_COMMENT_ID = 1;
    private static final String EXISTING_BOOK_COMMENT = "The best book";

    @Autowired
    private CommentRepositoryJpa jpa;


    @Test
    public void countTest() {
        long count = jpa.count();
        assertEquals(EXPECTED_COMMENT_COUNT, count);
    }

    @Test
    public void saveTest() {

        Comment expected = new Comment("newComment");
        jpa.insert(expected);
        long count = jpa.count();
        assertEquals(EXPECTED_COMMENT_COUNT + 1, count);

        expected.setId(EXISTING_COMMENT_ID + 1);

        Optional<Comment> actual = jpa.getById(EXISTING_COMMENT_ID + 1);

        assertThat(actual.get()).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("Обновить книгу в БД")
    @Test
    void updateTest() {
        Comment expected = new Comment(EXISTING_COMMENT_ID, "The best one");
        jpa.insert(expected);
        Optional<Comment> actual = jpa.getById(EXISTING_COMMENT_ID);
        assertEquals(expected.getId(), actual.orElseThrow().getId());
        assertEquals(expected.getCommentBook(), actual.orElseThrow().getCommentBook());
    }

    @DisplayName("возвращать ожидаемую книгу по id")
    @Test
    void getByIdTest() {
        Optional<Comment> actual = jpa.getById(EXISTING_COMMENT_ID);
        assertEquals(EXISTING_BOOK_COMMENT, actual.get().getCommentBook());
    }

    @DisplayName("удалять заданного книгу по ее id")
    @Test
    void deleteById() {
        assertTrue(jpa.getById(EXISTING_COMMENT_ID).isPresent());
        jpa.deleteById(EXISTING_COMMENT_ID);
        assertFalse(jpa.getById(EXISTING_COMMENT_ID).isPresent());
    }

}
