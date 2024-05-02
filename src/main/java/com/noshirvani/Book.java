package com.noshirvani;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    private String bookID;
    private String title;
    private String author;
    private boolean availability;
    private String description;
    public Book() {
    }
    public Book(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.availability = true;
        this.description = description;
        this.bookID = generateBookID();
    }

    public String getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String generateBookID() {

        return "B" + (int) (Math.random() * 1000);
    }
}
