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

public class EntriesAdapter extends RecyclerView.Adapter<EntriesViewHolder> {
    private ArrayList<Entry> entries;

    public EntriesAdapter(ArrayList<Entry> entries){
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
        holder.setTvTitle(entries.get(position).getTitle());
        holder.setTvCaption(entries.get(position).getCaption());
    }

    public int getItemCount(){
        return this.entries.size();
    }
}
