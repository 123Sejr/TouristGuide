package com.example.touristguideapi.model;

import java.util.EnumSet;

// skal evt slettes

public class TagObject {
    private int tag_id;
    private Tag tag;


    public TagObject (int tag_id, Tag tag) {
        this.tag_id = tag_id;
        this.tag = tag;
    }

    //Getters
    public int getTag_id() {
        return tag_id;
    }

    public Tag getTag() {
        return tag;
    }

    //Setters
    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Tag id: " + tag_id + ", tags: " + tag;
    }
}
