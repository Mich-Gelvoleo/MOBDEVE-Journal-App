package com.mobdeve.s11.gelvoleo.galura.journal.controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobdeve.s11.gelvoleo.galura.journal.model.Entry;
import com.mobdeve.s11.gelvoleo.galura.journal.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EntriesAdapter extends RecyclerView.Adapter<EntriesViewHolder> {
    private List<Entry> entries;

    public EntriesAdapter(List<Entry> entries){
        this.entries = entries;
    }

    @NonNull
    @NotNull
    @Override

    public EntriesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_entry, parent, false);

        EntriesViewHolder entriesViewHolder = new EntriesViewHolder(itemView);
        return entriesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EntriesViewHolder holder, int position){
        Entry entry = entries.get(position);
        holder.bind(entry);
    }

    public int getItemCount(){
        return this.entries.size();
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}
