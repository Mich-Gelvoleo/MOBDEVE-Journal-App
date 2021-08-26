package com.mobdeve.s11.gelvoleo.galura.journal.controllers;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.mobdeve.s11.gelvoleo.galura.journal.R;
import com.mobdeve.s11.gelvoleo.galura.journal.utils.CustomDate;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class EntriesViewHolder extends RecyclerView.ViewHolder {
    private TextView tvDate;
    private TextView tvTitle;
    private TextView tvCaption;

    public EntriesViewHolder(@NonNull @NotNull View itemView){
        super(itemView);

        this.tvDate = itemView.findViewById(R.id.tv_entry_date);
        this.tvTitle = itemView.findViewById(R.id.tv_entry_title);
        this.tvCaption = itemView.findViewById(R.id.tv_entry_caption);

        itemView.setOnClickListener(view ->{
            Intent intent = new Intent(itemView.getContext(), EntryDetailsActivity.class);
            intent.putExtra("Date", tvDate.getText());
            intent.putExtra("Title", tvTitle.getText());
            intent.putExtra("Caption", tvCaption.getText());

            itemView.getContext().startActivity(intent);
        });

    }

    public void setTvDate(String date){ this.tvDate.setText(date); }

    public void setTvTitle(String title){
        this.tvTitle.setText(title);
    }

    public void setTvCaption(String caption){
        this.tvCaption.setText(caption);
    }
}
