package com.mobdeve.s11.gelvoleo.galura.journal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Entry> entries;
    private RecyclerView rvEntries;
    private FloatingActionButton fabAdd;
    private EntriesAdapter entriesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    @Override
    protected void onResume(){
        super.onResume();
        entriesAdapter.notifyDataSetChanged();
    }

    public void initComponents(){
        DataHelper helper = DataHelper.getInstance();
        this.entries = DataHelper.getData();
        entriesAdapter = new EntriesAdapter(this.entries);

        this.fabAdd = findViewById(R.id.fab_main_add);

        this.rvEntries = findViewById(R.id.rvEntries);
        this.rvEntries.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.rvEntries.setAdapter(entriesAdapter);

    }
}