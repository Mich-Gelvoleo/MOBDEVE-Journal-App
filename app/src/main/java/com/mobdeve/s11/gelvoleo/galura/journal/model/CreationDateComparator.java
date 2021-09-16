package com.mobdeve.s11.gelvoleo.galura.journal.model;

import java.util.Comparator;

public class CreationDateComparator implements Comparator<Entry> {
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
}
