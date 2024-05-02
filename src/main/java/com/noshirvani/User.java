package com.noshirvani;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String name;
    private String userID;
    private String phoneNumber;
    private LocalDate registrationDate;
    private String role;
    private String password;

    public User() {
    }

    public User(String name, String userID, String phoneNumber, String role, String password) {
        this.name = name;
        this.userID = userID;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.password = password;
    }


    public User(String name, String userID, String phoneNumber, LocalDate registrationDate, String role, String password) {
        this(name, userID, phoneNumber, role, password);
        this.registrationDate = registrationDate;
    }

    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }
    public User(String name, String userID, String phoneNumber) {
    }

    public User(String name, String userID, String phoneNumber, LocalDate registrationDate, String admin) {
    this.name = name;
    this.userID = userID;
    this.phoneNumber = phoneNumber;
    this.registrationDate = registrationDate;
    this.password = "";
    this.role = admin;
    }

    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
