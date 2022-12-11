package ru.otus.spring.controller;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Controller;
import ru.otus.spring.domain.Author;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.ioservice.ConsoleIOService;

import java.util.List;
import java.util.Optional;

@ShellComponent
@Controller
public class ShellAuthorController {

    private final AuthorService authorService;
    private final ConsoleIOService consoleIOService;

    public ShellAuthorController(AuthorService authorService, ConsoleIOService consoleIOService) {
        this.authorService = authorService;
        this.consoleIOService = consoleIOService;
    }


    @ShellMethod(value = "Count authors", key = {"countAuthors", "ca"})
    public void count() {
        consoleIOService.outputString("Amount authors: " + authorService.count());
    }


    @ShellMethod(value = "Insert author", key = {"insertAuthors", "ia"})
    public void insert(String author) {
        Author authorEntity = authorService.insert(author);
        if (authorEntity == null) {
            consoleIOService.outputString("Store already has author - " + author);
        } else {
            consoleIOService.outputString("Author - " + author + " was added with id: " + authorEntity.getId());
        }
    }


    @ShellMethod(value = "Update author by id", key = {"updateAuthor", "ua"})
    public void update(String author, int id) {
        Author authorEntity = authorService.updateById(author, id);
        if (authorEntity == null) {
            consoleIOService.outputString("Author was not found with id: " + id);
            ;
        } else {
            consoleIOService.outputString("Updated author: " + authorEntity.getAuthorBook() + ", id: " + authorEntity.getId());
        }

    }


    @ShellMethod(value = "Delete author", key = {"deleteAuthor", "da"})
    public void deleteById(int id) {
        authorService.deleteById(id);
    }

    @ShellMethod(value = "Get all authors", key = {"getAllAuthors", "gaa"})
    public void getAll() {
        List<Author> authors = authorService.getAll();
        consoleIOService.outputString("Amount authors: " + authors.size());
        authors.forEach(author -> author.getBooks().forEach(book -> book.getId()));
        authors.forEach(author -> consoleIOService.outputString("Author: " + author.getAuthorBook()
                        + ", authorId: " + author.getId()
                        + ", books: " + author.getBooks()
                )
        );
    }

    @ShellMethod(value = "Get author by id", key = {"getAuthor", "ga"})
    public void getById(int id) {
        Optional<Author> author = authorService.getById(id);
        author.ifPresent(author1 -> consoleIOService.outputString("author: " + author1));
    }
}
