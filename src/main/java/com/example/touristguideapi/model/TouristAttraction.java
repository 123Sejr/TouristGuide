package com.example.touristguideapi.model;

import java.util.ArrayList;

public class TouristAttraction {
    private int id;
    private String name;
    private String description;
    private int city_id;
    private ArrayList<String> tags;

    public TouristAttraction(int id, String name, String description, int city_id, ArrayList<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city_id = city_id;
        this.tags = tags;
    }

    public TouristAttraction(String name, String description, int city_id, ArrayList<String> tags) {
        this.name = name;
        this.description = description;
        this.city_id = city_id;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCityID() {
        return city_id;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCityID(int city_id) {
        this.city_id = city_id;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                "\nDescription: " + description +
                "\nTags: " + tags +
                "\nCity: " + city_id;
    }
}
