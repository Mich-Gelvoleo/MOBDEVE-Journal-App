package com.mobdeve.s11.gelvoleo.galura.journal.controllers;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobdeve.s11.gelvoleo.galura.journal.utils.DataHelper;
import com.mobdeve.s11.gelvoleo.galura.journal.model.Entry;
import com.mobdeve.s11.gelvoleo.galura.journal.R;

import androidx.appcompat.app.AppCompatActivity;

public class AddPostActivity extends AppCompatActivity {
    private EditText etTitle;
    private EditText etCaption;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        this.etTitle = findViewById(R.id.et_add_title);
        this.etCaption = findViewById(R.id.et_add_caption);
        this.btnSave = findViewById(R.id.btn_add_save);

        this.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etTitle.getText().toString().isEmpty()){
                    Toast.makeText(AddPostActivity.this, "Your Journal Title", Toast.LENGTH_SHORT).show();
                }

                if(etCaption.getText().toString().isEmpty()){
                    Toast.makeText(AddPostActivity.this, "Let's get typing!", Toast.LENGTH_SHORT).show();
                }

                Entry entry = new Entry(
                        etTitle.getText().toString(),
                        etCaption.getText().toString());

                assert  DataHelper.getData() != null;
                DataHelper.getData().add(0, entry);
                finish();
            }
        });
    }
}
