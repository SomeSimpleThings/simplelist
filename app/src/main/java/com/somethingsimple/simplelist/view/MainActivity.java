package com.somethingsimple.simplelist.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.db.Note;
import com.somethingsimple.simplelist.model.NoteViewModel;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    public static final int DELETE_NOTE_REQUEST = 3;
    public static final String NOTE_EXTRA = "com.somethingsimple.simplelist.note";
    private NoteViewModel noteViewModel;

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            startActivityForResult(intent, NEW_NOTE_REQUEST);
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        NotesListAdapter adapter = new NotesListAdapter(note -> {
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            intent.putExtra(NOTE_EXTRA, note);
            startActivityForResult(intent, EDIT_NOTE_REQUEST);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getNotes().observe(this, adapter::submitList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Note note;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case NEW_NOTE_REQUEST:
                    note = data.getParcelableExtra(NOTE_EXTRA);
                    noteViewModel.insert(note);
                    break;
                case EDIT_NOTE_REQUEST:
                    note = data.getParcelableExtra(NOTE_EXTRA);
                    noteViewModel.update(note);
                    break;
            }
        } else if (resultCode == DELETE_NOTE_REQUEST) {
            note = data.getParcelableExtra(NOTE_EXTRA);
            if (note.getNoteId() != 0) {
                noteViewModel.delete(note);
            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
