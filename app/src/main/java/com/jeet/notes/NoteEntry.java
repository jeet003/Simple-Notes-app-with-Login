package com.jeet.notes;

import java.util.ArrayList;


public class NoteEntry {
    public static ArrayList<NoteEntry> notesList;


    public static final int PLAIN_TEXT =1;

    private String title;
    private String text;
    private String username;
    private int type;
    private int id;


    public NoteEntry() {
        this.id =-1;
        type =0;
        title = "Enter Title here...";
        username="";
        text = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
