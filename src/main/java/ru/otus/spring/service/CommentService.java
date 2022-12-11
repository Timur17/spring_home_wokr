package ru.otus.spring.service;

import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Set;

public interface CommentService {
    long count();

    Comment insert(String comment, long bookId);

    void deleteById(long id);

    Set<Comment> getAllByBookId(long id);
}
