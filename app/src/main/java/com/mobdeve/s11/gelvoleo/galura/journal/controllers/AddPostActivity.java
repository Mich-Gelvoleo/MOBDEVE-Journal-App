package com.mobdeve.s11.gelvoleo.galura.journal.controllers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.FormatException;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
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
    private ImageView ivImage;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private AutoCompleteTextView actvLocation;
    private PlaceAutoSuggestAdapter placeAdapter;

    private TextView tvArchive;
    private TextView tvAddPhoto;
    private TextView tvDeletePhoto;
    private FloatingActionButton fabSave;

/*  private FloatingActionButton fabArchive;
    private FloatingActionButton fabRemovePhoto;
    private FloatingActionButton fabCamera;*/

    private Date mDatePicked = new Date();
    private String mSelectedImagePath = null;
    private String locationChosen = null;
    private File photoFile = null;

    private boolean forEdit = false;
    private boolean forDelete = false;
    private Entry mEntry = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        this.tvDate = findViewById(R.id.tv_add_date);
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy");
        tvDate.setText(sdf.format(mDatePicked));
        this.etTitle = findViewById(R.id.et_add_title);
        this.etCaption = findViewById(R.id.et_add_caption);
        this.ivImage = findViewById(R.id.iv_add_photo);
        this.etTags = findViewById(R.id.et_add_tags);
        this.actvLocation = findViewById(R.id.actv_add_location);

        this.tvArchive = findViewById(R.id.tv_add_archive);
        this.tvAddPhoto = findViewById(R.id.tv_add_photo);
        this.tvDeletePhoto = findViewById(R.id.tv_add_remove_photo);
        this.fabSave = findViewById(R.id.fab_add_save);

/*        this.fabCamera = findViewById(R.id.fab_add_camera);
        this.fabRemovePhoto = findViewById(R.id.fab_remove_photo);
        this.fabArchive = findViewById(R.id.fab_archive);*/

        forEdit = getIntent().getBooleanExtra("FOR_EDIT", false);
        if (forEdit) {
            initializeEntryDetails();
        }

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

        placeAdapter = new PlaceAutoSuggestAdapter(this, android.R.layout.simple_list_item_1);
        actvLocation.setAdapter(placeAdapter);
        actvLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locationChosen = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + locationChosen, Toast.LENGTH_SHORT).show();
                actvLocation.setText(locationChosen);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.fabSave.setOnClickListener(view -> {

            if(etTitle.getText().toString().isEmpty()){
                Toast.makeText(AddPostActivity.this, "Title Required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (forDelete) {
                File fdelete = new File(photoFile.getAbsolutePath());

                if (fdelete.exists()) {
                    if (!fdelete.delete()) {
                        Toast.makeText(AddPostActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddPostActivity.this, "No image to delete.", Toast.LENGTH_SHORT).show();
                }

                mSelectedImagePath = null;
                photoFile = null;
                forDelete = false;
            }

            if (photoFile != null) {
                galleryAddPic();
            }

            mSelectedImagePath = (photoFile == null) ? null : photoFile.getAbsolutePath();


            if (forEdit) {
                mEntry.setTitle(etTitle.getText().toString());
                mEntry.setCaption(etCaption.getText().toString());
                mEntry.setDate(mDatePicked);
                mEntry.setFilename(mSelectedImagePath);
                mEntry.setTags(etTags.getText().toString());
                mEntry.setLocation(locationChosen);

                EntryLab.get(this).updateEntry(mEntry);

                Intent returnIntent = getIntent();
                returnIntent.putExtra("RETURN_ENTRY", mEntry);
                setResult(RESULT_OK, returnIntent);
            } else {
                Entry entry = new Entry(
                        etTitle.getText().toString(),
                        etCaption.getText().toString(),
                        mDatePicked,
                        mSelectedImagePath,
                        etTags.getText().toString(),

                        //Delete if flops
                        actvLocation.getText().toString()
                );

                EntryLab.get(this).addEntry(entry);
            }

            finish();
        });

        tvAddPhoto.setOnClickListener(view -> {
            dispatchTakePictureIntent();
        });

        tvDeletePhoto.setOnClickListener(view -> {
            if (photoFile == null) {
                Toast.makeText(AddPostActivity.this, "No image to delete.", Toast.LENGTH_SHORT).show();
            }

            ivImage.setImageBitmap(null);
            forDelete = true;
        });

        tvArchive.setOnClickListener(view -> {
            mEntry.setArchived(true);
            EntryLab.get(this).updateEntry(mEntry);

            Intent returnIntent = getIntent();
            returnIntent.putExtra("RETURN_ENTRY", mEntry);
            setResult(RESULT_OK, returnIntent);
            finish();
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_bottom_photo:
                dispatchTakePictureIntent();
                return true;
            case R.id.menu_bottom_remove:
                if (photoFile == null) {
                    Toast.makeText(AddPostActivity.this, "No image to delete.", Toast.LENGTH_SHORT).show();
                    return true;
                }


                ivImage.setImageBitmap(null);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                ivImage.setImageBitmap(takenImage);

                forDelete = false;
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

    private void initializeEntryDetails() {
        mEntry = (Entry) getIntent().getSerializableExtra("ENTRY");
        etTitle.setText(mEntry.getTitle());

        Date date = mEntry.getDate();
        String formattedDate = new SimpleDateFormat("dd MMM yyyy").format(date);
        mDatePicked = mEntry.getDate();
        tvDate.setText(formattedDate);

        etCaption.setText(mEntry.getCaption());
        etTags.setText(mEntry.getTags());

        if(mEntry.getFilename() != null) {
            photoFile = new File(mEntry.getFilename());
        }

        Bitmap takenImage = BitmapFactory.decodeFile(mEntry.getFilename());
        ivImage.setImageBitmap(takenImage);

        /*fabArchive.setVisibility(View.VISIBLE);*/
        tvArchive.setVisibility(View.VISIBLE);
    }
}
