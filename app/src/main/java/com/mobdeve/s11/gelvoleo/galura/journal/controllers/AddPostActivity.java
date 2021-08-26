package com.mobdeve.s11.gelvoleo.galura.journal.controllers;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobdeve.s11.gelvoleo.galura.journal.utils.CustomDate;
import com.mobdeve.s11.gelvoleo.galura.journal.utils.DataHelper;
import com.mobdeve.s11.gelvoleo.galura.journal.model.Entry;
import com.mobdeve.s11.gelvoleo.galura.journal.R;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddPostActivity extends AppCompatActivity {
    DatePickerDialog picker;
    private EditText etDate;
    private EditText etTitle;
    private EditText etCaption;
    private FloatingActionButton fabSave;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        this.etDate = findViewById(R.id.et_add_date);
        this.etTitle = findViewById(R.id.et_add_title);
        this.etCaption = findViewById(R.id.et_add_caption);
        this.fabSave = findViewById(R.id.fab_add_save);

        this.fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(AddPostActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayh) {
                                etDate.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();

                if(etTitle.getText().toString().isEmpty()){
                    Toast.makeText(AddPostActivity.this, "Your Journal Title", Toast.LENGTH_SHORT).show();
                }

                if(etCaption.getText().toString().isEmpty()){
                    Toast.makeText(AddPostActivity.this, "Let's get typing!", Toast.LENGTH_SHORT).show();
                }

                Entry entry = new Entry(
                        new CustomDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                        etTitle.getText().toString(),
                        etCaption.getText().toString());

                assert  DataHelper.getData() != null;
                DataHelper.getData().add(0, entry);
                finish();
            }
        });
    }
}
