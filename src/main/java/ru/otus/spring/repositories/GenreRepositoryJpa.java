package ru.otus.spring.repositories;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Genre;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Component
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    public GenreRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public long count() {
        Query query = em.createQuery("select count(a) from Genre a");
        return (long) query.getSingleResult();
    }

    @Override
    public Genre insert(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public void deleteById(long id) {
        getById(id).ifPresent(em::remove);
    }

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public Optional<Genre> getByGenre(String genre) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.genreBook = :genre", Genre.class);
        query.setParameter("genre", genre);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Genre> getAll() {
        EntityGraph<?> graph = em.getEntityGraph("genres-books-entity-graph");
        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        query.setHint(FETCH.getKey(), graph);
        return query.getResultList();
    }
}
