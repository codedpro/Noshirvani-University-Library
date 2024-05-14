package com.noshirvani;

import java.time.LocalDateTime;

public class AdminUser extends User {
    private String password;

    public AdminUser(String name, String userID, String phoneNumber, LocalDateTime registrationDate, String password) {
        super(name, userID, phoneNumber, registrationDate, "admin");
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public boolean authenticate(String inputPassword) {
        return password.equals(inputPassword);
    }
}
