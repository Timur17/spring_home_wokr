package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Comments")
public class Comment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private long id;

    @Column(name = "comment_book")
    private String commentBook;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "book_id")
    private Book book;

    public Comment(String commentBook) {
        this.commentBook = commentBook;
    }

    public Comment(long id, String commentBook) {
        this.id = id;
        this.commentBook = commentBook;
    }

    public Comment(String commentBook, Book book) {
        this.commentBook = commentBook;
        this.book = book;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", comment='" + commentBook + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getCommentBook() {
        return commentBook;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCommentBook(String commentBook) {
        this.commentBook = commentBook;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
