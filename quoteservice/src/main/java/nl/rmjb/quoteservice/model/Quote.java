package nl.rmjb.quoteservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Quote {

    private static final int DEFAULT_LIKES = 0;
    private static final int DEFAULT_DISLIKES = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private String author;
    private int likes;
    private int dislikes;

    public Quote() {
        this.likes = DEFAULT_LIKES;
        this.dislikes = DEFAULT_DISLIKES;
    }

    public Quote(String text, String author) {
        this();
        this.text = text;
        this.author = author;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    // Business logic
    public void incrementLikes() {
        this.likes++;
    }

    public void incrementDislikes() {
        this.dislikes++;
    }
}