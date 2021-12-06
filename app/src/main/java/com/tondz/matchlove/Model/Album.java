package com.tondz.matchlove.Model;

import java.util.List;

public class Album {
    private String name,image;
    private List<Music>musicList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Music> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }

    public Album(String name, String image, List<Music> musicList) {
        this.name = name;
        this.image = image;
        this.musicList = musicList;
    }
}
