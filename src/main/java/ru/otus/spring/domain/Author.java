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
@Table(name = "authors")
@NamedEntityGraph(name = "authors-books-entity-graph", attributeNodes = {@NamedAttributeNode("books")})
public class Author {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private long id;

    @Column(name = "author_book")
    private String authorBook;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "author_id")
    private List<Book> books;

    public Author(String authorBook) {
        this.authorBook = authorBook;
    }

    public Author(long id, String authorBook) {
        this.id = id;
        this.authorBook = authorBook;
    }
}
