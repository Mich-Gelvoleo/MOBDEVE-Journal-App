package com.mobdeve.s11.gelvoleo.galura.journal.model;

import java.util.Date;
import java.util.UUID;

public class Entry {

    private UUID id;
    private String title;
    private String caption;
    private Date date;
    private int archived;
    private String filename;

    public Entry(String title, String caption) {
        this.title = title;
        this.caption = caption;
    }

    public Entry(String title, String caption, Date date){
        this.id = UUID.randomUUID();
        this.title = title;
        this.caption = caption;
        this.date = date;
        this.archived = 0;
    }

    public Entry(UUID uuid) {
        this.id = uuid;
    }

    public UUID getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int isArchived() {
        return archived;
    }

    public void setArchived(int archived) {
        this.archived = archived;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTitle(){
        return this.title;
    }

    public String getCaption(){
        return this.caption;
    }

}
