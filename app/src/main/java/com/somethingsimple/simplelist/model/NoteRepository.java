package com.somethingsimple.simplelist.model;

import androidx.lifecycle.LiveData;

import com.somethingsimple.simplelist.NoteApplication;
import com.somethingsimple.simplelist.db.Note;
import com.somethingsimple.simplelist.db.NoteDao;
import com.somethingsimple.simplelist.db.NotesDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class NoteRepository {

    private final NoteDao mNoteDao;
    private final ExecutorService executorService;

    NoteRepository() {
        NotesDatabase dbNotes = NoteApplication.getInstanced().getNotesDatabase();
        mNoteDao = dbNotes.noteDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    LiveData<Note> getNote(long id) {
        return mNoteDao.getNoteById(id);
    }

    void insert(Note note) {
        executorService.execute(() -> mNoteDao.insert(note));
    }

}
