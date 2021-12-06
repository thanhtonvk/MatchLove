package com.tondz.matchlove.Model;

public class Video {
    private String id,name;
    private int views;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Video(String id, String name, int views) {
        this.id = id;
        this.name = name;
        this.views = views;
    }
    public Video(){

    }

    @Override
    public String toString() {
        return name;
    }
}
