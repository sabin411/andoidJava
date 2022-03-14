package com.example.googlelogin.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName ="user")
public class User {

    private String name;
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String email;
    private String phone;
    private String password;

    public User(String name, int id, String email, String phone, String password) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
}