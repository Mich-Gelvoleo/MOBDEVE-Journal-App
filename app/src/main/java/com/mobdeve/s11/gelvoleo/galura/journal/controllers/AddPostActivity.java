package com.mobdeve.s11.gelvoleo.galura.journal.controllers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobdeve.s11.gelvoleo.galura.journal.model.EntryLab;
import com.mobdeve.s11.gelvoleo.galura.journal.model.Entry;
import com.mobdeve.s11.gelvoleo.galura.journal.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddPostActivity extends AppCompatActivity {
    private static final String TAG = "AddPostActivity";
    private static final int SELECT_PICTURE = 22;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    DatePickerDialog picker;
    private TextView tvDate;
    private EditText etTitle;
    private EditText etCaption;
    private EditText etTags;
    private FloatingActionButton fabSave;
    private FloatingActionButton fabRemovePhoto;
    private FloatingActionButton fabCamera;
    private ImageView ivImage;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private Date mDatePicked = new Date();
    private String mSelectedImagePath = null;
    private File photoFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        this.tvDate = findViewById(R.id.tv_add_date);
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy");
        tvDate.setText(sdf.format(mDatePicked));
        this.etTitle = findViewById(R.id.et_add_title);
        this.etCaption = findViewById(R.id.et_add_caption);
        this.fabSave = findViewById(R.id.fab_add_save);
        this.fabRemovePhoto = findViewById(R.id.fab_remove_photo);
        this.ivImage = findViewById(R.id.iv_add_photo);
        this.fabCamera = findViewById(R.id.fab_add_camera);
        this.etTags = findViewById(R.id.et_add_tags);

        final Calendar calendar = Calendar.getInstance();

        Context context = this;

        tvDate.setOnClickListener(v -> {

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatePickerDialog dialog = new DatePickerDialog(
                    AddPostActivity.this,
                    //android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    mDateSetListener,
                    year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(year, month, day);
                mDatePicked = calendar.getTime();
                String date = sdf.format(calendar.getTime());

                tvDate.setText(date);
            }
        };

        this.fabSave.setOnClickListener(view -> {

            if(etTitle.getText().toString().isEmpty()){
                Toast.makeText(AddPostActivity.this, "Title Required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (photoFile != null) {
                galleryAddPic();
            }

            mSelectedImagePath = (photoFile == null) ? null : photoFile.getAbsolutePath();
            Entry entry = new Entry(
                    etTitle.getText().toString(),
                    etCaption.getText().toString(),
                    mDatePicked,
                    mSelectedImagePath,
                    etTags.getText().toString()
            );

            EntryLab.get(this).addEntry(entry);
            finish();
        });

        fabCamera.setOnClickListener(view -> {
            dispatchTakePictureIntent();
        });

        fabRemovePhoto.setOnClickListener(view -> {
            if (photoFile == null) {
                Toast.makeText(AddPostActivity.this, "No image to delete.", Toast.LENGTH_SHORT).show();
                return;
            }
            
            File fdelete = new File(photoFile.getAbsolutePath());
            ivImage.setImageBitmap(null);
            if (fdelete.exists()) {
                if (!fdelete.delete()) {
                    Toast.makeText(AddPostActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AddPostActivity.this, "No image to delete.", Toast.LENGTH_SHORT).show();
            }

            mSelectedImagePath = null;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a previe
                ivImage.setImageBitmap(takenImage);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(photoFile.getAbsolutePath());
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(AddPostActivity.this, "An error occurred in creating the file.", Toast.LENGTH_LONG).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.mobdeve.s11.gelvoleo.galura.journal",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mSelectedImagePath = image.getAbsolutePath();
        return image;
    }
}
