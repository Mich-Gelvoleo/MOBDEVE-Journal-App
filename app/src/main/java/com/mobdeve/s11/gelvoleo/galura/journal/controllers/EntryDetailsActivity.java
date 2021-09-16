package com.mobdeve.s11.gelvoleo.galura.journal.controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobdeve.s11.gelvoleo.galura.journal.R;
import com.mobdeve.s11.gelvoleo.galura.journal.model.Entry;
import com.mobdeve.s11.gelvoleo.galura.journal.model.EntryLab;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;

public class EntryDetailsActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvCaption;
    private TextView tvDate;
    private TextView tvTags;
    private ImageView ivEntryImage;
    private FloatingActionButton fabEdit;

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
        tvTags.setText(mEntry.getTags());

        this.ivEntryImage = findViewById(R.id.iv_view_photo);
        Bitmap takenImage = BitmapFactory.decodeFile(mEntry.getFilename());
        // RESIZE BITMAP, see section below
        // Load the taken image into a previe
        ivEntryImage.setImageBitmap(takenImage);

        this.fabEdit = findViewById(R.id.fab_view_edit);
        fabEdit.setOnClickListener(view -> {
//            File fdelete = new File(mEntry.getFilename());
//            if (fdelete.exists()) {
//                if (fdelete.delete()) {
//                    Toast.makeText(EntryDetailsActivity.this, "deleted", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(EntryDetailsActivity.this, "not deleted", Toast.LENGTH_LONG).show();
//                }
//            }
        });
    }

}
