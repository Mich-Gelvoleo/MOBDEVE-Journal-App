package com.mobdeve.s11.gelvoleo.galura.journal.model;

public class Entry {
    private String title, caption;

    public Entry(String title, String caption){
        this.title = title;
        this.caption = caption;
    }

    public String getTitle(){
        return this.title;
    }

    public String getCaption(){
        return this.caption;
    }

}
