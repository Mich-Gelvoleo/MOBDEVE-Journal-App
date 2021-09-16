package com.mobdeve.s11.gelvoleo.galura.journal.controllers;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobdeve.s11.gelvoleo.galura.journal.model.EntryLab;
import com.mobdeve.s11.gelvoleo.galura.journal.utils.CustomDate;
import com.mobdeve.s11.gelvoleo.galura.journal.utils.DataHelper;
import com.mobdeve.s11.gelvoleo.galura.journal.model.Entry;
import com.mobdeve.s11.gelvoleo.galura.journal.R;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
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
    private FloatingActionButton fabSave;
    private FloatingActionButton fabChooseImage;
    private FloatingActionButton fabCamera;
    private ImageView ivImage;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private Date mDatePicked = new Date();
    private String mSelectedImagePath = null;
    private File photoFile;

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
        this.fabChooseImage = findViewById(R.id.fab_add_file);
        this.ivImage = findViewById(R.id.iv_add_photo);
        this.fabCamera = findViewById(R.id.fab_add_camera);

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

                //month = month + 1;
                //Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                //String date = month + "/" + day + "/" + year;

                tvDate.setText(date);
            }
        };

        this.fabSave.setOnClickListener(view -> {

            if(etTitle.getText().toString().isEmpty()){
                Toast.makeText(AddPostActivity.this, "Title Required", Toast.LENGTH_SHORT).show();
                return;
            }

            galleryAddPic();

            /*if(etCaption.getText().toString().isEmpty()){
                Toast.makeText(AddPostActivity.this, "Let's get typing!", Toast.LENGTH_SHORT).show();
            }*/

            Entry entry = new Entry(
                    etTitle.getText().toString(),
                    etCaption.getText().toString(),
                    mDatePicked,
                    photoFile.getAbsolutePath()
            );

            EntryLab.get(this).addEntry(entry);
            finish();
        });

        fabCamera.setOnClickListener(view -> {
            dispatchTakePictureIntent();
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
