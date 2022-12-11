package ru.otus.spring.repositories;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    long count();

    Comment insert(Comment comment);

    void deleteById(long id);

    Optional<Comment> getById(long id);

}
