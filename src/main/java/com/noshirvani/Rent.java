package com.noshirvani;


public class Rent {
    private Book book;
    private User user;
    private int rentalID;
    private String rentalDate;

    private static int nextRentalID = 1;

    public Rent(Book book, User user, String rentalDate) {
        this.book = book;
        this.user = user;
        this.rentalID = nextRentalID++;
        this.rentalDate = rentalDate;
    }


    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public int getRentalID() {
        return rentalID;
    }

    public String getRentalDate() {
        return rentalDate;
    }
}
