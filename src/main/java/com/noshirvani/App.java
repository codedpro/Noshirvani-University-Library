package com.noshirvani;

public class App {
    public static void main(String[] args) {
        Library library = new Library("Noshirvani Library", 100, "9:00 AM - 5:00 PM");

        CLI cli = new CLI(library);

        cli.start();
    }
}
