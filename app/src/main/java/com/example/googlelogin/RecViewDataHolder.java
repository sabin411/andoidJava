package com.example.googlelogin;

public class RecViewDataHolder {
    String date, title, description, location, image;

    RecViewDataHolder()
    {
    }

    public RecViewDataHolder(String date, String title, String description, String location, String image) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.location = location;
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
