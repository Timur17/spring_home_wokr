package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repositories.CommentRepositoryJpa;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepositoryJpa commentRepositoryJpa;
    private final BookServiceImpl bookService;

    public CommentServiceImpl(CommentRepositoryJpa commentRepositoryJpa, BookServiceImpl bookService) {
        this.commentRepositoryJpa = commentRepositoryJpa;
        this.bookService = bookService;
    }


    @Override
    public long count() {
        return commentRepositoryJpa.count();
    }

    @Transactional
    @Override
    public Comment insert(String comment, long bookId) {
        Optional<Book> optionalBook = bookService.getById(bookId);
        Book book = optionalBook.orElse(null);
        if (book != null) {
            return commentRepositoryJpa.insert(new Comment(comment, book));
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepositoryJpa.deleteById(id);
    }

    @Override
    public Set<Comment> getAllByBookId(long bookId) {
        Optional<Book> optionalBook = bookService.getById(bookId);
        Book book = optionalBook.orElse(null);
        if (book == null)
            return null;
        return book.getComments();
    }
}
