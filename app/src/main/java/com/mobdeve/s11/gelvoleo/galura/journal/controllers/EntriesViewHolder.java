package com.mobdeve.s11.gelvoleo.galura.journal.controllers;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.mobdeve.s11.gelvoleo.galura.journal.R;
import com.mobdeve.s11.gelvoleo.galura.journal.model.Entry;
import com.mobdeve.s11.gelvoleo.galura.journal.utils.CustomDate;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EntriesViewHolder extends RecyclerView.ViewHolder {

    private Entry mEntry;

    private TextView tvDate;
    private TextView tvTitle;
    private TextView tvCaption;
    private TextView tvLocation;

    public EntriesViewHolder(@NonNull @NotNull View itemView){
        super(itemView);

        this.tvDate = itemView.findViewById(R.id.tv_entry_date);
        this.tvTitle = itemView.findViewById(R.id.tv_entry_title);
        this.tvCaption = itemView.findViewById(R.id.tv_entry_caption);
        this.tvLocation = itemView.findViewById(R.id.tv_entry_location);

        itemView.setOnClickListener(view ->{
            Intent intent = new Intent(itemView.getContext(), EntryDetailsActivity.class);
            intent.putExtra("ENTRY", mEntry);

            itemView.getContext().startActivity(intent);
        });

    }

    public void bind(Entry entry) {
        mEntry = entry;
        tvTitle.setText(mEntry.getTitle());
        tvCaption.setText(mEntry.getCaption());
        tvLocation.setText(mEntry.getLocation());

        Date date = mEntry.getDate();
        String formattedDate = new SimpleDateFormat("dd MMM yyyy").format(date).toUpperCase();
        tvDate.setText(formattedDate);
    }
}
