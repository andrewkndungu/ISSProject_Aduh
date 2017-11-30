package com.example.sonnie.issproject;

/**
 * Created by Sonnie on 11/13/2017.
 */

public class User {
    private String Email, Password, Phone;

    public User() {
    }

    public User(String email, String password) {
        Email = email;
        Password = password;
    }

    public User(String email, String password, String phone) {
        Email = email;
        Password = password;
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
