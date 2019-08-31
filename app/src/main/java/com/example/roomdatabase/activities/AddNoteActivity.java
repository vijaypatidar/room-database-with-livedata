package com.example.roomdatabase.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.roomdatabase.R;
import com.example.roomdatabase.model.Note;
import com.example.roomdatabase.model.NoteRepository;

import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTitle, editContent;
    private boolean flag_auto_save = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        initUiComponents();
    }

    private void initUiComponents() {
        editTitle = findViewById(R.id.title);
        editContent = findViewById(R.id.content);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuDiscard:
                flag_auto_save = false;
                finish();
                break;
            case R.id.menuSave:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveNote();
    }

    private void saveNote() {
        String title = editTitle.getText().toString();
        String content = editContent.getText().toString();
        Note note = new Note(title, content, new Date(), new Date());
        NoteRepository noteRepository = new NoteRepository(getApplication());
        noteRepository.insert(note);

    }

}
