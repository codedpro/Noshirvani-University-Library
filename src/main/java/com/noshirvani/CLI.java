package com.noshirvani;

import java.util.Scanner;
import java.time.LocalDate;

public class CLI {
    private Library library;
    private Scanner scanner;
    private User currentUser;

    public CLI(Library library) {
        this.library = library;
        this.scanner = new Scanner(System.in);
        this.currentUser = null;
    }
    private void printBoxBorder() {
        System.out.println("--------------------------------------------------");
    }
    private void displayUserInfo(User user) {
        printBoxBorder();
        System.out.println("Welcome, " + user.getName() + "!");
        System.out.println("UserID: " + user.getUserID());
        System.out.println("Phone Number: " + user.getPhoneNumber());
        if (user instanceof AdminUser) {
            System.out.println("Role: Admin");
        } else {
            System.out.println("Role: Normal User");
        }
        printBoxBorder();
    }
    public void start() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║         Welcome to " + library.getName() + "!           ║");
        System.out.println("║         We are open " + library.getOperatingHours() + "!           ║");
        System.out.println("║          We have Capacity up to " + library.getCapacity() + "!             ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        while (true) {
            if (currentUser == null) {
                System.out.println("Please log in to access the library features.");
                System.out.print("Enter your userID: ");
                String userID = scanner.nextLine().trim();
                if (userID.equals("quit")) {
                    break;
                }
                currentUser = findUserByID(userID);
                if (currentUser == null) {
                    System.out.println("User not found. Please try again or type 'quit' to exit.");
                } else {
                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine().trim();
                    if (!currentUser.authenticate(password)) {
                        System.out.println("Incorrect password. Please try again or type 'quit' to exit.");
                        currentUser = null;
                    } else {
                        if (currentUser.getRole().equalsIgnoreCase("admin")) {
                            currentUser = new AdminUser(currentUser.getName(), currentUser.getUserID(), currentUser.getPhoneNumber(), LocalDate.now(), password);
                        } else {
                            currentUser = new NormalUser(currentUser.getName(), currentUser.getUserID(), currentUser.getPhoneNumber(), LocalDate.now(), password);
                        }
                        displayUserInfo(currentUser);
                        System.out.println("Type 'help' to see available commands.");
                    }
                }
            } else {
                System.out.print("> ");
                String input = scanner.nextLine().trim();

                if (input.equals("help")) {
                    displayHelp();
                } else if (input.equals("quit")) {
                    break;
                } else if (input.equals("logout")) {
                    currentUser = null;
                    System.out.println("Logged out successfully.");
                } else if (input.startsWith("add book") && isAdminUser(currentUser)) {
                    addBook(input);
                } else if (input.equals("get books")) {
                    displayBooks();
                } else if (input.startsWith("add user") && isAdminUser(currentUser)) {
                    addUser(input);
                } else if (input.equals("get users") && isAdminUser(currentUser)) {
                    displayUsers();
                } else if (input.startsWith("rent")) {
                    rentBook(input);
                } else if (input.startsWith("return")) {
                    returnBook(input);
                } else {
                    System.out.println("Invalid command. Type 'help' for available commands.");
                }
            }
        }

        scanner.close();
    }


    private void displayHelp() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                 Available Commands               ║");

        if (isAdminUser(currentUser)) {
            System.out.println("╠═════════════════ Admin Commands ═════════════════╣");
            System.out.println("║  - add book <name> <author> <description>        ║");
            System.out.println("║  - get books                                     ║");
            System.out.println("║  - add user <name> <ID> <phone> <role> <password>║");
            System.out.println("║  - get users                                     ║");
        }

        System.out.println("╠═════════════════ Users Commands ═════════════════╣");
        System.out.println("║  - get books                                     ║");
        System.out.println("║  - rent <bookID> <userID>                        ║");
        System.out.println("║  - return <bookID>                               ║");
        System.out.println("║  - logout                                        ║");
        System.out.println("║  - quit                                          ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }



    private boolean isAdminUser(User user) {
        return user instanceof AdminUser;
    }

    private void addBook(String input) {
        String[] tokens = input.split(" ");
        if (tokens.length < 5) {
            System.out.println("Invalid command format. Please specify book details.");
            return;
        }
        String name = tokens[2];
        String author = tokens[3];
        String description = tokens[4];
        Book book = new Book(name, author, description);
        library.addBook(book);
        System.out.println("Book added successfully.");
    }

    private void displayBooks() {
        System.out.println("Available books in the library:");
        for (Book book : library.getBooks()) {
            System.out.println("Book ID: " + book.getBookID() + ", Title: " + book.getTitle() +
                    ", Author: " + book.getAuthor() + ", Description: " + book.getDescription() +
                    ", Availability: " + (book.isAvailable() ? "Available" : "Not Available"));
        }
    }

    private void addUser(String input) {
        String[] tokens = input.split(" ");
        if (tokens.length < 7) {
            System.out.println("Invalid command format. Please specify name, userID, phoneNumber, role, and password.");
            return;
        }
        String name = tokens[2];
        String userID = tokens[3];
        String phoneNumber = tokens[4];
        String role = tokens[5];
        String password = tokens[6];
        User user;
        if (role.equalsIgnoreCase("admin")) {
            user = new AdminUser(name, userID, phoneNumber, LocalDate.now(), password);
        } else {
            user = new NormalUser(name, userID, phoneNumber, LocalDate.now(), password);
        }
        System.out.println(user.getName());
        library.addUser(user);
        System.out.println("User added successfully.");
    }



    private void displayUsers() {
        System.out.println("Registered users in the library:");
        for (User user : library.getUsers()) {
            System.out.println("User ID: " + user.getUserID() + ", Name: " + user.getName() +
                    ", Phone Number: " + user.getPhoneNumber());
        }
    }

    private void rentBook(String input) {
        String[] tokens = input.split(" ");
        if (tokens.length < 3) {
            System.out.println("Invalid command format. Please specify book ID and user ID.");
            return;
        }
        String bookID = tokens[1];
        String userID = tokens[2];
        Book book = findBookByID(bookID);
        User user = findUserByID(userID);
        if (book == null || user == null) {
            System.out.println("Book or user not found. Please check the IDs.");
            return;
        }
        library.rentBook(book, user);
    }

    private void returnBook(String input) {
        String[] tokens = input.split(" ");
        if (tokens.length < 2) {
            System.out.println("Invalid command format. Please specify book ID.");
            return;
        }
        String bookID = tokens[1];
        Book book = findBookByID(bookID);
        if (book == null) {
            System.out.println("Book not found. Please check the ID.");
            return;
        }
        library.returnBook(book);
    }

    private Book findBookByID(String bookID) {
        for (Book book : library.getBooks()) {
            if (book.getBookID().equals(bookID)) {
                return book;
            }
        }
        return null;
    }
    private User findUserByID(String userID) {
        for (User user : library.getUsers()) {
            if (user.getUserID().equals(userID)) {
                return user;
            }
        }
        System.out.println("User not found.");
        return null;
    }



}