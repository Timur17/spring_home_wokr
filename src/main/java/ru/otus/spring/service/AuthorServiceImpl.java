package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repositories.AuthorRepositoryJpa;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepositoryJpa repositoryJpa;

    public AuthorServiceImpl(AuthorRepositoryJpa repositoryJpa) {
        this.repositoryJpa = repositoryJpa;
    }

    @Override
    public long count() {
        return repositoryJpa.count();
    }

    @Transactional
    @Override
    public Author insert(String author) {
        Optional<Author> optionalAuthor = repositoryJpa.getByAuthor(author);
        Author entity = optionalAuthor.orElse(null);
        if (entity == null) {
            return repositoryJpa.insert(new Author(author));
        }
        return null;
    }

    @Transactional
    @Override
    public Author updateById(String newAuthorName, long id) {
        Optional<Author> optionalAuthor = repositoryJpa.getById(id);
        Author entity = optionalAuthor.orElse(null);
        if (entity != null) {
            return repositoryJpa.insert(new Author(id, newAuthorName, entity.getBooks()));
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        repositoryJpa.deleteById(id);
    }


    @Override
    public List<Author> getAll() {
        return repositoryJpa.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> getById(long id) {
        Optional<Author> optionalAuthor = repositoryJpa.getById(id);
        optionalAuthor.ifPresent(author -> author.getBooks().forEach(book -> {
        }));
        return repositoryJpa.getById(id);
    }
}
