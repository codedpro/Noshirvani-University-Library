package com.noshirvani;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private static final String BOOKS_FILE = "data/books.json";
    private static final String USERS_FILE = "data/users.json";
    private static final String RENTALS_FILE = "data/rentals.json";

    private String name;
    private int capacity;
    private String operatingHours;
    private List<Book> bookRepository;
    private List<User> userRegistry;
    private List<Rent> rentalRegistry;

    public Library(String name, int capacity, String operatingHours) {
        this.name = name;
        this.capacity = capacity;
        this.operatingHours = operatingHours;
        this.bookRepository = new ArrayList<>();
        this.userRegistry = new ArrayList<>();
        this.rentalRegistry = new ArrayList<>();

        loadBooks();
        loadUsers();
        loadRentals();
    }
    public String getName() {
        return name;
    }
    public int getCapacity() {return capacity;}
    public String getOperatingHours() {return operatingHours;}
    public void addBook(Book book) {
        bookRepository.add(book);
        saveBooks();
    }

    public List<Book> getBooks() {
        return bookRepository;
    }

    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : bookRepository) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    public void addUser(User user) {
        userRegistry.add(user);
        saveUsers();
        loadUsers();
    }


    public List<User> getUsers() {
        return userRegistry;
    }

    public void rentBook(Book book, User user) {
        if (book.isAvailable()) {
            String rentalDate = LocalDate.now().toString();
            Rent rent = new Rent(book, user, rentalDate);
            rentalRegistry.add(rent);
            book.setAvailability(false);
            System.out.println("Book rented successfully!");
            saveRentals();
            saveBooks();
        } else {
            System.out.println("Sorry, the book is currently not available for rental.");
        }
    }


    public void returnBook(Book book) {
        for (Rent rent : rentalRegistry) {
            if (rent.getBook().equals(book)) {
                rentalRegistry.remove(rent);
                book.setAvailability(true);
                System.out.println("Book returned successfully!");
                saveRentals();
                saveBooks();
                return;
            }
        }
        System.out.println("Book not found in rental records.");
    }
    private void saveBooks() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(BOOKS_FILE), bookRepository);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveUsers() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(USERS_FILE), userRegistry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveRentals() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(RENTALS_FILE), rentalRegistry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadBooks() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(BOOKS_FILE);
            if (!file.exists() || file.length() == 0) {
                bookRepository.add(new Book("Default Book", "Default Author", "Default Description"));
                saveBooks();
            } else {
                List<Book> loadedBooks = mapper.readValue(file, new TypeReference<List<Book>>() {});
                bookRepository.addAll(loadedBooks);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadUsers() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(USERS_FILE);
            if (!file.exists() || file.length() == 0) {
                userRegistry.add(new AdminUser("Admin", "Admin_1", "1234567890", LocalDate.now(), "1234"));
                saveUsers();
            } else {
                List<User> loadedUsers = mapper.readValue(file, new TypeReference<List<User>>() {});
                for (User loadedUser : loadedUsers) {
                    boolean exists = false;
                    for (User user : userRegistry) {
                        if (user.getUserID().equals(loadedUser.getUserID())) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        userRegistry.add(loadedUser);

                    }
                }
                saveUsers();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadRentals() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            File file = new File(RENTALS_FILE);
            if (!file.exists() || file.length() == 0) {
                saveRentals();
            } else {
                List<Rent> loadedRentals = mapper.readValue(file, new TypeReference<List<Rent>>() {});

                rentalRegistry.addAll(loadedRentals);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}