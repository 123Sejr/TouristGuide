package com.example.touristguideapi.model;

import java.util.ArrayList;
import java.util.EnumSet;

public class TouristAttraction {
    private int id;
    private String name;
    private String description;
    private int city_id;
    private ArrayList<Integer> tags;
    // en liste af tag_id. I sql'en peger de på tag tabellen, hvor et tag har både id og navn
    // kunne omskrives til en liste af TagObject? Usikker på hvilke, der er brug for

    public TouristAttraction(int id, String name, String description, int city_id, ArrayList<Integer> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city_id = city_id;
        this.tags = tags;
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

    public ArrayList<Integer> getTags() {
        return tags;
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

    public void setTags(ArrayList<Integer> tags) {
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
