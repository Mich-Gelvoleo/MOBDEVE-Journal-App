package com.mobdeve.s11.gelvoleo.galura.journal;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EntryDetailsActivity extends AppCompatActivity {
    private TextView tvTitle;
    private TextView tvCaption;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);

        this.tvTitle = findViewById(R.id.tv_view_title);
        tvTitle.setText(getIntent().getStringExtra("Title"));

        this.tvCaption = findViewById(R.id.tv_view_caption);
        tvCaption.setText(getIntent().getStringExtra("Caption"));

    }

}
