package com.example.roomdatabase.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> listLiveData;

    public NoteRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        noteDao = appDatabase.noteDao();
        listLiveData = noteDao.fetchAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return listLiveData;
    }


    public void insert(Note note) {
        new insertAsyncTask(noteDao).execute(note);
    }

    public void deleteNote(Note note) {
        new deleteAsyncTask(noteDao).execute(note);
    }

    private static class insertAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao mAsyncTaskDao;

        insertAsyncTask(NoteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... params) {
            mAsyncTaskDao.insertNote(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao mAsyncTaskDao;

        deleteAsyncTask(NoteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... params) {
            mAsyncTaskDao.deleteNote(params[0]);
            return null;
        }
    }
}
