package com.mobdeve.s11.gelvoleo.galura.journal.model;

import com.mobdeve.s11.gelvoleo.galura.journal.utils.CustomDate;

public class Entry {
    private String title, caption;
    private CustomDate createdAt;

    // For DateHelper Class
    public Entry(CustomDate createdAt, String title, String caption){
        this.createdAt = createdAt;
        this.title = title;
        this.caption = caption;
    }

    public CustomDate getCreatedAt(){ return this.createdAt; }

    public String getTitle(){
        return this.title;
    }

    public String getCaption(){
        return this.caption;
    }

}
