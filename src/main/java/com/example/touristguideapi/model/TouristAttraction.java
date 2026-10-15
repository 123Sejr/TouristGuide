package com.example.touristguideapi.model;

import java.util.EnumSet;

public class TouristAttraction {
    private String name;
    private String description;
    private String city;
    private EnumSet<Tag> tags;
    //private List<Tags> tags;

    public TouristAttraction() {

    }

    public TouristAttraction(String name, String description, String city) {
        this.name = name;
        this.description = description;
        this.city = city;
    }

    public TouristAttraction(String name, String description, String city, EnumSet<Tag> tags) {
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }

    public EnumSet<Tag> getTags() {
        return tags;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTags(EnumSet<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                "\nDescription: " + description +
                "\nTags: " + tags +
                "\nCity: " + city;
    }
}
