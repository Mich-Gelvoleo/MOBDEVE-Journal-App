package com.mobdeve.s11.gelvoleo.galura.journal.controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.mobdeve.s11.gelvoleo.galura.journal.model.Entry;
import com.mobdeve.s11.gelvoleo.galura.journal.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EntriesAdapter extends RecyclerView.Adapter<EntriesViewHolder> implements Filterable {
    private List<Entry> entries;
    private List<Entry> entriesAll;

    public EntriesAdapter(List<Entry> entries){
        this.entries = entries;
        this.entriesAll = new ArrayList<>(entries);
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

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Entry> filteredList = new ArrayList<>();

            if(constraint.toString().isEmpty()){
                filteredList.addAll(entriesAll);
            } else{
                for(Entry entry : entriesAll){
                    if(entry.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(entry);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            entries.clear();
            entries.addAll((Collection<? extends Entry>)results.values);
            notifyDataSetChanged();
        }
    };
}
