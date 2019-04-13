package com.somethingsimple.simplelist.model;

import com.somethingsimple.simplelist.NoteApplication;
import com.somethingsimple.simplelist.db.Note;
import com.somethingsimple.simplelist.db.NoteDao;
import com.somethingsimple.simplelist.db.NotesDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;

class NotesRepository {
    private final NoteDao mNoteDao;
    private final LiveData<List<Note>> mAllNotes;
    private final ExecutorService executorService;

    NotesRepository() {
        NotesDatabase dbNotes = NoteApplication
                .getInstanced()
                .getNotesDatabase();

        mNoteDao = dbNotes.noteDao();
        mAllNotes = mNoteDao.getNotes();
        executorService = Executors.newSingleThreadExecutor();
    }

    LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    LiveData<Note> getNote(long id)  {
        try {
            return executorService.submit(() -> mNoteDao.getNoteById(id)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    void insert(Note note) {
        executorService.execute(() -> mNoteDao.insert(note));
    }

    void update(Note note) {
        executorService.execute(() -> mNoteDao.update(note));
    }

    void delete(Note note) {
        executorService.execute(() -> mNoteDao.delete(note));
    }

}
