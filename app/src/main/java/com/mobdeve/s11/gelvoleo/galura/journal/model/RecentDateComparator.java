package com.mobdeve.s11.gelvoleo.galura.journal.model;

import java.util.Comparator;

public class RecentDateComparator implements Comparator<Entry> {
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
}
