package com.example.roomdatabase.adapter;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabase.R;
import com.example.roomdatabase.model.Note;
import com.example.roomdatabase.model.NoteRepository;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    private ArrayList<Note> arrayList;
    private NoteRepository noteRepository;

    public NotesAdapter(ArrayList<Note> arrayList, Application application) {
        this.arrayList = arrayList;
        this.noteRepository = new NoteRepository(application);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        final Note note = arrayList.get(position);
        String title = note.getTitle();
        String content = note.getContent();
        holder.title.setText(title);
        holder.content.setText(content);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog.Builder ab = new AlertDialog.Builder(view.getContext());
                ab.setTitle("Remove note");
                ab.setMessage("Do you really want to delete this note?");
                ab.setNegativeButton("No", null);
                ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        noteRepository.deleteNote(note);
                        Snackbar.make(view, "Note deleted", Snackbar.LENGTH_LONG).show();
                    }
                });
                ab.create().show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView title, content;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            content = itemView.findViewById(R.id.textViewContent);
        }
    }
}
