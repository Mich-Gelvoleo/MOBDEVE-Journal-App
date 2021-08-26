package com.mobdeve.s11.gelvoleo.galura.journal.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Entry implements Serializable {

    private UUID id;
    private String title;
    private String caption;
    private Date date;
    private boolean archived;
    private String filename;

    public Entry(String title, String caption) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.caption = caption;
        this.date = new Date();
        this.archived = false;
        this.filename = null;
    }

    public Entry(String title, String caption, Date date){
        this.id = UUID.randomUUID();
        this.title = title;
        this.caption = caption;
        this.date = date;
        this.archived = false;
    }

    public Entry(UUID uuid) {
        this.id = uuid;
        this.date = new Date();
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

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
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
