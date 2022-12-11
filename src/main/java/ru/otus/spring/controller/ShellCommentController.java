package ru.otus.spring.controller;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Controller;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;
import ru.otus.spring.service.ioservice.ConsoleIOService;

import java.util.List;
import java.util.Set;

@ShellComponent
@Controller
public class ShellCommentController {

    private final CommentService commentService;
    private final BookService bookService;
    private final ConsoleIOService consoleIOService;

    public ShellCommentController(CommentService commentService, BookService bookService,
                                  ConsoleIOService consoleIOService) {
        this.commentService = commentService;
        this.bookService = bookService;
        this.consoleIOService = consoleIOService;
    }

    @ShellMethod(value = "Count comments", key = {"countComments", "cc"})
    public void count() {
        consoleIOService.outputString("Amount comments: " + commentService.count());
    }

    @ShellMethod(value = "Insert comment", key = {"insertComment", "ic"})
    public void insert(String comment, long bookId) {
        Comment commentEntity = commentService.insert(comment, bookId);
        if (commentEntity != null) {
            consoleIOService.outputString("Comment was added to book: " + commentEntity.getBook().getTitle());
        } else {
            consoleIOService.outputString("Book with id - " + bookId + " not exist");
        }

    }

    @ShellMethod(value = "Delete comment  by id", key = {"deleteComment", "dc"})
    public void deleteById(int id) {
        commentService.deleteById(id);
    }

    @ShellMethod(value = "Show all comments", key = {"getAllComments", "gac"})
    public void ShowAllCommentsByBookIdAll(long id) {
        Set<Comment> comments = commentService.getAllByBookId(id);
        comments.forEach(comment -> {
            System.out.println("Comment: " + comment);
        });
    }

}
