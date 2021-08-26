package com.mobdeve.s11.gelvoleo.galura.journal.controllers;

import android.os.Bundle;
import android.widget.TextView;

import com.mobdeve.s11.gelvoleo.galura.journal.R;
import com.mobdeve.s11.gelvoleo.galura.journal.model.Entry;
import com.mobdeve.s11.gelvoleo.galura.journal.model.EntryLab;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;

public class EntryDetailsActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvCaption;
    private TextView tvDate;
    private TextView tvTags;

    private Entry mEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);

        mEntry = (Entry) getIntent().getSerializableExtra("ENTRY");

        this.tvTitle = findViewById(R.id.tv_view_title);
        tvTitle.setText(mEntry.getTitle());

        this.tvCaption = findViewById(R.id.tv_view_caption);
        tvCaption.setText(mEntry.getCaption());

        this.tvDate = findViewById(R.id.tv_view_date);
        Date date = mEntry.getDate();
        String formattedDate = new SimpleDateFormat("dd MMM yyyy").format(date).toUpperCase();
        tvDate.setText(formattedDate);

        this.tvTags = findViewById(R.id.tv_view_tags);
        //TODO: set tags

    }

}
