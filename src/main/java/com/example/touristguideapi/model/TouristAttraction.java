package com.example.touristguideapi.model;

import java.util.ArrayList;
import java.util.EnumSet;

public class TouristAttraction {
    private int id;
    private String name;
    private String description;
    private int city_id;
    private int tags_id;

    public TouristAttraction(int id, String name, String description, int city_id, int tags_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city_id = city_id;
        this.tags_id = tags_id;
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

    public int getTags() {
        return tags_id;
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

    public void setTags(int tags_id) {
        this.tags_id = tags_id;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                "\nDescription: " + description +
                "\nTags: " + tags_id +
                "\nCity: " + city_id;
    }
}
