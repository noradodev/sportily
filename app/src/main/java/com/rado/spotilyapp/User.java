package com.rado.spotilyapp;
public class User {
    private String full_name;
    private String phone_number;
    private String email;
    private String gender;

    public User(String full_name, String phone_number, String email, String gender) {
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.email = email;
        this.gender = gender;
    }

    public String getName() {
        return full_name;
    }

    public void setName(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone_number;
    }

    public void setPhone(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
