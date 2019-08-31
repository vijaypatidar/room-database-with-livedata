package com.example.roomdatabase.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insertNote(Note note);

    @Query("SELECT * FROM Note ORDER BY created_at desc")
    LiveData<List<Note>> fetchAllNotes();

    @Query("SELECT * FROM Note WHERE id =:noteId")
    LiveData<Note> getNote(long noteId);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);
}
