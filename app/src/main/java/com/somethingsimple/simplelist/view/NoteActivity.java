package com.somethingsimple.simplelist.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.db.Note;
import com.somethingsimple.simplelist.view.MainActivity;

public class NoteActivity extends AppCompatActivity {

    private EditText mEditNoteView;
    private Note mNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mEditNoteView = findViewById(R.id.edit_word);
        Button buttonSave = findViewById(R.id.button_save);
        Button buttonDel = findViewById(R.id.button_delete);
        mNote = getIntent().getParcelableExtra(MainActivity.NOTE_EXTRA);
        if (mNote == null) {
            mNote = new Note("");
        }
        mEditNoteView.setText(mNote.getNote());
        buttonSave.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            mNote.setNote(mEditNoteView.getText().toString());
            replyIntent.putExtra(MainActivity.NOTE_EXTRA, mNote);
            setResult(RESULT_OK, replyIntent);
            finish();
        });

        buttonDel.setOnClickListener(v -> {
            Intent replyIntent = new Intent();
            replyIntent.putExtra(MainActivity.NOTE_EXTRA, mNote);
            setResult(MainActivity.DELETE_NOTE_REQUEST, replyIntent);
            finish();
        });
    }
}
