package com.example.googlelogin.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName ="user")
public class User {

    private String name;
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int phone;
    private String address;
    private int gender;

    public User(String name, int id, int phone, String address, int gender) {
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getGender() {
        return gender;
    }
}
