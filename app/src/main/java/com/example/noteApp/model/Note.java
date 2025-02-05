package com.example.noteApp.model;

import java.io.Serializable;

public class Note implements Serializable {
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }



    public String getTitle() {
        return title;
    }



    public Note(String title) {
        this.title = title;
    }
}
