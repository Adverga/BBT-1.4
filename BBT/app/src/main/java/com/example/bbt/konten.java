package com.example.bbt;

public class konten {
    public String title;
    public int thumbnail;

    public konten (){

    }
    public konten(String title, int thumbnail){
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }
}
