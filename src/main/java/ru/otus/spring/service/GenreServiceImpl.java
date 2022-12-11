package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repositories.GenreRepository;
import ru.otus.spring.service.ioservice.ConsoleIOService;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final ConsoleIOService consoleIOService;

    public GenreServiceImpl(GenreRepository genreRepository, ConsoleIOService consoleIOService) {
        this.genreRepository = genreRepository;
        this.consoleIOService = consoleIOService;
    }

    @Override
    public long count() {
        return genreRepository.count();
//        consoleIOService.outputString("Amount genres: " + count);
    }

    @Transactional
    @Override
    public Genre insert(String genre) {
        Optional<Genre> optionalGenre = genreRepository.getByGenre(genre);
        Genre entity = optionalGenre.orElse(null);
        if (entity == null) {
            return genreRepository.insert(new Genre(genre));
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public Genre updateById(String genre, int id) {
        Optional<Genre> optionalGenre = genreRepository.getById(id);
        Genre entity = optionalGenre.orElse(null);
        if (entity != null) {
            entity.getBooks().forEach(book -> book.getId());
            return genreRepository.insert(new Genre(id, genre, entity.getBooks()));
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        genreRepository.deleteById(id);
    }


    @Override
    public List<Genre> getAll() {
        return genreRepository.getAll();

    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> getById(int id) {
        Optional<Genre> bookGenre = genreRepository.getById(id);
        bookGenre.ifPresent(genre -> genre.getBooks().forEach(book -> {

        }));
        return bookGenre;
    }

}
