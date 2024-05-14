package com.noshirvani;

import java.time.LocalDateTime;

public class NormalUser extends User {
    private String password;

    public NormalUser(String name, String userID, String phoneNumber, LocalDateTime registrationDate, String password) {
        super(name, userID, phoneNumber, registrationDate, "normal");
        this.password = password;

    }

    public String getPassword() {
        return password;
    }
}
