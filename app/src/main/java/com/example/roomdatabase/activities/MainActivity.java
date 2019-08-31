package com.example.roomdatabase.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabase.R;
import com.example.roomdatabase.adapter.NotesAdapter;
import com.example.roomdatabase.model.Note;
import com.example.roomdatabase.model.NoteRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NotesAdapter notesAdapter;
    private ArrayList<Note> noteArrayList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        noteArrayList = new ArrayList<>();
        notesAdapter = new NotesAdapter(noteArrayList, getApplication());
        NoteRepository noteRepository = new NoteRepository(getApplication());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);

        setLayoutManager(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notesAdapter);

        noteRepository.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteArrayList.clear();
                noteArrayList.addAll(notes);
                notesAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_as_list:
                item.setChecked(true);
                setLayoutManager(true);
                break;
            case R.id.action_show_as_grid:
                item.setChecked(true);
                setLayoutManager(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setLayoutManager(boolean isListView) {
        if (isListView) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            int orientation = getResources().getConfiguration().orientation;
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            if (Configuration.ORIENTATION_LANDSCAPE == orientation) {
                gridLayoutManager.setSpanCount(3);
            }
            gridLayoutManager.setSmoothScrollbarEnabled(true);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
    }
}
