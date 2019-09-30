package com.somethingsimple.simplelist.model;

import com.somethingsimple.simplelist.NoteApplication;
import com.somethingsimple.simplelist.db.entity.Note;
import com.somethingsimple.simplelist.db.dao.NoteDao;
import com.somethingsimple.simplelist.db.NotesDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;

class NotesRepository {
    private final NoteDao mNoteDao;
    private final ExecutorService executorService;
    private final NoteApplication app;

    NotesRepository() {
        app = NoteApplication.getInstanced();
        NotesDatabase dbNotes = app.getNotesDatabase();
        mNoteDao = dbNotes.noteDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    LiveData<List<Note>> getAllNotes(long folderId) {
        return mNoteDao.getNotes(folderId);
    }

    void insert(Note... note) {
        executorService.execute(() -> mNoteDao.insert(note));
    }

    void update(Note... note) {
        executorService.execute(() -> mNoteDao.update(note));
    }

    void delete(Note... note) {
        executorService.execute(() -> mNoteDao.delete(note));
    }

    void delete(long noteId) {
        executorService.execute(() -> mNoteDao.delete(noteId));
    }

    void deleteAll() {
        executorService.execute(mNoteDao::deleteAll);
    }

}
