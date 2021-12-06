package com.tondz.matchlove.Model;

public class Music {
    private String id,link,name;
    private int view;

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

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public Music(String id, String name, int view) {
        this.id = id;
        this.name = name;
        this.view = view;
    }
    public Music(){

    }

    public Music(String id, String link, String name, int view) {
        this.id = id;
        this.link = link;
        this.name = name;
        this.view = view;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "♫"+name;
    }
}
