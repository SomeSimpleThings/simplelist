package com.somethingsimple.simplelist.db;

import com.somethingsimple.simplelist.db.entity.Note;
import com.somethingsimple.simplelist.db.dao.NoteDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;

import javax.inject.Inject;

public class NotesRepository {
    private final NoteDao mNoteDao;
    private final ExecutorService executorService;

    @Inject
    public NotesRepository(NoteDao dao) {
        mNoteDao = dao;
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Note>> getAllNotes(long folderId) {
        return mNoteDao.getNotes(folderId);
    }

    public void insert(Note... note) {
        executorService.execute(() -> mNoteDao.insert(note));
    }

    public void update(Note... note) {
        executorService.execute(() -> mNoteDao.update(note));
    }

    public void delete(Note... note) {
        executorService.execute(() -> mNoteDao.delete(note));
    }

    public void delete(long noteId) {
        executorService.execute(() -> mNoteDao.delete(noteId));
    }

    public void deleteAll() {
        executorService.execute(mNoteDao::deleteAll);
    }

}
