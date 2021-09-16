package com.mobdeve.s11.gelvoleo.galura.journal.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobdeve.s11.gelvoleo.galura.journal.model.EntryLab;
import com.mobdeve.s11.gelvoleo.galura.journal.model.Entry;
import com.mobdeve.s11.gelvoleo.galura.journal.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Entry> entries;
    private RecyclerView rvEntries;
    private FloatingActionButton fabAdd;
    private EntriesAdapter entriesAdapter;
    private SearchView searchView;
    private ImageButton filter;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                entriesAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onResume(){
        super.onResume();
        updateUI();
    }

    private void initComponents(){
        entries = EntryLab.get(this).getEntries();
        entriesAdapter = new EntriesAdapter(entries);

        this.fabAdd = findViewById(R.id.fab_main_add);

        fabAdd.setOnClickListener(view ->{
            Intent intent = new Intent(getBaseContext(), AddPostActivity.class);
            startActivity(intent);
        });

        this.rvEntries = findViewById(R.id.rvEntries);
        this.rvEntries.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.rvEntries.setAdapter(entriesAdapter);

    }

    private void updateUI() {
        EntryLab entryLab = EntryLab.get(this);
        entries = entryLab.getEntries();

        if (entriesAdapter == null) {
            entriesAdapter = new EntriesAdapter(entries);
            rvEntries.setAdapter(entriesAdapter);
        } else {
            entriesAdapter.setEntries(entries);
            entriesAdapter.notifyDataSetChanged();
        }
    }
}