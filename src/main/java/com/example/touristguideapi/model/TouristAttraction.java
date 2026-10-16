package com.example.touristguideapi.model;

public class TouristAttraction {
    private int id;
    private String name;
    private String description;
    private int city_id;
    private String[] tags;

    public TouristAttraction(int id, String name, String description, int city_id, String[] tags) {
        this.id = id;
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

    public String[] getTags() {
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

    public void setTags(String[] tags) {
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
