package com.mobdeve.s11.gelvoleo.galura.journal.controllers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EntryDetailsActivity extends AppCompatActivity {

    private final static int EDIT_ENTRY = 10;

    private TextView tvTitle;
    private TextView tvCaption;
    private TextView tvDate;
    private TextView tvTags;
    private ImageView ivEntryImage;
    private FloatingActionButton fabEdit;
    private FloatingActionButton fabDeletePermanently;
    private FloatingActionButton fabRestore;

    private Entry mEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);

        mEntry = (Entry) getIntent().getSerializableExtra("ENTRY");

        initView();

        fabEdit.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), AddPostActivity.class);
            intent.putExtra("FOR_EDIT", true);
            intent.putExtra("ENTRY", mEntry);
            startActivityForResult(intent, EDIT_ENTRY);
        });

        fabDeletePermanently.setOnClickListener(view -> {
            File fdelete = new File(mEntry.getFilename());
            if (fdelete.exists()) {
                boolean deleted = fdelete.delete();
                Log.d("DELETED", "is deleted: " + deleted);
            }

            EntryLab.get(this).deleteEntry(mEntry);

            finish();
        });

        fabRestore.setOnClickListener(view -> {
            mEntry.setArchived(false);
            EntryLab.get(this).updateEntry(mEntry);
            finish();
        });
    }

    private void initView() {
        this.tvTitle = findViewById(R.id.tv_view_title);
        tvTitle.setText(mEntry.getTitle());

        this.tvCaption = findViewById(R.id.tv_view_caption);
        tvCaption.setText(mEntry.getCaption());

        this.tvDate = findViewById(R.id.tv_view_date);
        Date date = mEntry.getDate();
        String formattedDate = new SimpleDateFormat("dd MMM yyyy").format(date);
        tvDate.setText(formattedDate);

        this.tvTags = findViewById(R.id.tv_view_tags);
        tvTags.setText(mEntry.getTags());

        this.ivEntryImage = findViewById(R.id.iv_view_photo);
        Bitmap takenImage = BitmapFactory.decodeFile(mEntry.getFilename());
        ivEntryImage.setImageBitmap(takenImage);

        this.fabEdit = findViewById(R.id.fab_view_edit);
        if (mEntry.isArchived()) {
            fabEdit.setVisibility(View.GONE);
        }

        this.fabDeletePermanently = findViewById(R.id.fab_delete_permanent);
        if (mEntry.isArchived()) {
            fabDeletePermanently.setVisibility(View.VISIBLE);
        }

        this.fabRestore = findViewById(R.id.fab_restore);
        if (mEntry.isArchived()) {
            fabRestore.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == EDIT_ENTRY) {
                mEntry = (Entry) data.getSerializableExtra("RETURN_ENTRY");

                initView();
            }

        }
    }
}
