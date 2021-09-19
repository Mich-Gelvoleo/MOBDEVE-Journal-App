package com.mobdeve.s11.gelvoleo.galura.journal.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

public class Entry implements Serializable, Comparable<Entry> {

    private UUID id;
    private String title;
    private String caption;
    private Date date;
    private boolean archived;
    private String filename;

    public Date getArchivedDate() {
        return archivedDate;
    }

    public void setArchivedDate(Date archivedDate) {
        this.archivedDate = archivedDate;
    }

    private Date archivedDate = new Date();

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    private String tags = "";

    private String location = "";

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

    public Entry(String title, String caption, Date date, String filename){
        this.id = UUID.randomUUID();
        this.title = title;
        this.caption = caption;
        this.date = date;
        this.archived = false;
        this.filename = filename;
    }

    public Entry(String title, String caption, Date date, String filename, String tags, String location){
        this.id = UUID.randomUUID();
        this.title = title;
        this.caption = caption;
        this.date = date;
        this.archived = false;
        this.filename = filename;
        this.tags = tags;

        //Delete if it flops
        this.location = location;
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

    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return this.location;
    }

    @Override
    public int compareTo(Entry e) {
        if(this.getDate().before(e.getDate())){
            return -1;
        }else if(this.getDate().after(e.getDate())){
            return 1;
        }else{
            return 0;
        }
    }

    public static class Comparators{
        public static Comparator<Entry> creationDate = new Comparator<Entry>() {
            @Override
            public int compare(Entry e1, Entry e2) {
                if(e1.getDate().before(e2.getDate())){
                    return -1;
                }else if(e1.getDate().after(e2.getDate())){
                    return 1;
                }else{
                    return 0;
                }
            }
        };

        public static Comparator<Entry> recentDate = new Comparator<Entry>() {
            @Override
            public int compare(Entry e1, Entry e2) {
                if(e2.getDate().before(e1.getDate())){
                    return -1;
                }else if(e2.getDate().after(e1.getDate())){
                    return 1;
                }else{
                    return 0;
                }
            }
        };

        public static Comparator<Entry> locationAZ = new Comparator<Entry>() {
            @Override
            public int compare(Entry e1, Entry e2) {
                return e1.getLocation().compareTo(e2.getLocation());
            }
        };

        public static Comparator<Entry> locationZA = new Comparator<Entry>() {
            @Override
            public int compare(Entry e1, Entry e2) {
                return e2.getLocation().compareTo(e1.getLocation());
            }
        };
    }
}
