package com.mobdeve.s11.gelvoleo.galura.journal.controllers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobdeve.s11.gelvoleo.galura.journal.model.EntryLab;
import com.mobdeve.s11.gelvoleo.galura.journal.utils.CustomDate;
import com.mobdeve.s11.gelvoleo.galura.journal.utils.DataHelper;
import com.mobdeve.s11.gelvoleo.galura.journal.model.Entry;
import com.mobdeve.s11.gelvoleo.galura.journal.R;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddPostActivity extends AppCompatActivity {
    private static final String TAG = "AddPostActivity";

    DatePickerDialog picker;
    private TextView tvDate;
    private EditText etTitle;
    private EditText etCaption;
    private FloatingActionButton fabSave;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        this.tvDate = findViewById(R.id.tv_add_date);
        this.etTitle = findViewById(R.id.et_add_title);
        this.etCaption = findViewById(R.id.et_add_caption);
        this.fabSave = findViewById(R.id.fab_add_save);

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
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy");
                calendar.set(year, month, day);
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

            /*if(etCaption.getText().toString().isEmpty()){
                Toast.makeText(AddPostActivity.this, "Let's get typing!", Toast.LENGTH_SHORT).show();
            }*/

            Entry entry = new Entry(
                    etTitle.getText().toString(),
                    etCaption.getText().toString()
            );

            EntryLab.get(this).addEntry(entry);
            finish();
        });
    }
}
