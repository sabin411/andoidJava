package com.example.googlelogin;

public class userModel {
    String name,email,phone,password,uid;

    public userModel(String name, String email, String phone, String password, String uid) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.uid = uid;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
