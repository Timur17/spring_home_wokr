package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "genres")
@NamedEntityGraph(name = "genres-books-entity-graph", attributeNodes = {@NamedAttributeNode("books")})
public class Genre {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(name = "genre_book")
    private String genreBook;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "genre_id")
    private List<Book> books;

    public Genre(String genreBook) {
        this.genreBook = genreBook;
    }

    public Genre(long id, String genreBook) {
        this.id = id;
        this.genreBook = genreBook;
    }
}
