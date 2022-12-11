package ru.otus.spring.controller;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Controller;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.GenreService;
import ru.otus.spring.service.ioservice.ConsoleIOService;

import java.util.List;
import java.util.Optional;


@ShellComponent
@Controller
public class ShellGenreController {

    private final GenreService genreService;
    private final ConsoleIOService consoleIOService;

    public ShellGenreController(GenreService genreService, ConsoleIOService consoleIOService) {
        this.genreService = genreService;
        this.consoleIOService = consoleIOService;
    }


    @ShellMethod(value = "Count genres", key = {"countGenres", "cg"})
    public void count() {
        consoleIOService.outputString("Amount genres: " + genreService.count());
    }


    @ShellMethod(value = "Insert genre", key = {"insertGenres", "ig"})
    public void insert(String genre) {
        Genre genreEntity = genreService.insert(genre);
        if (genreEntity != null) {
            consoleIOService.outputString("Genre " + genreEntity.getGenreBook() + " was added with id: " + genreEntity.getId());
        } else {
            consoleIOService.outputString("Store already has genre - " + genre + ", with id: " + genreEntity.getId());
        }
    }


    @ShellMethod(value = "Update genre by id", key = {"updateGenre", "ug"})
    public void update(String genre, int id) {
        Genre genreEntity = genreService.updateById(genre, id);
        if (genreEntity != null) {
            consoleIOService.outputString("Genre with id:  " + genreEntity.getId() + ", was updated: " + genreEntity.getGenreBook());
        } else {
            consoleIOService.outputString("Genre was not found with id: " + id);
        }

    }


    @ShellMethod(value = "Delete genre", key = {"deleteGenre", "dg"})
    public void deleteById(int id) {
        genreService.deleteById(id);
    }

    @ShellMethod(value = "Get all genres", key = {"getAllGenres", "gag"})
    public void getAll() {
        List<Genre> genres = genreService.getAll();
        System.out.println("All Genres in library:");
        consoleIOService.outputString("Amount genres: " + genres.size());
        genres.forEach(genre -> consoleIOService.outputString("Genre: " + genre.getGenreBook() +
                ", genreId: " + genre.getId() +
                ", books: " + genre.getBooks()));
    }


    @ShellMethod(value = "Get genre by id", key = {"getGenre", "gg"})
    public void getById(int id) {
        Optional<Genre> bookGenre = genreService.getById(id);
        bookGenre.ifPresent(genre -> {
            consoleIOService.outputString("genre with id : " + id + " is " + genre);
        });
    }
}
