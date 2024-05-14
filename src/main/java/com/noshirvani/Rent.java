package com.noshirvani;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Rent {
    private Book book;
    private User user;
    private int rentalID;
    private String rentalDate;

    private static int nextRentalID = 1;

    public Rent(@JsonProperty("book") Book book,
                @JsonProperty("user") User user,
                @JsonProperty("rentalDate") String rentalDate) {
        this.book = book;
        this.user = user;
        this.rentalID = nextRentalID++;
        this.rentalDate = rentalDate;
    }




    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRentalID() {
        return rentalID;
    }

    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }
}
