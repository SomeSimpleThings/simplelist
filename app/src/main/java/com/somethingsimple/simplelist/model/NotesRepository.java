package com.somethingsimple.simplelist.model;

import com.somethingsimple.simplelist.NoteApplication;
import com.somethingsimple.simplelist.db.Note;
import com.somethingsimple.simplelist.db.NoteDao;
import com.somethingsimple.simplelist.db.NotesDatabase;
import com.somethingsimple.simplelist.network.NetworkService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class NotesRepository {
    private final NoteDao mNoteDao;
    private final ExecutorService executorService;
    private final NetworkService networkService;
    private final NoteApplication app;

    NotesRepository() {
        app = NoteApplication.getInstanced();
        NotesDatabase dbNotes = app.getNotesDatabase();
        networkService = app.getNetworkService();

        mNoteDao = dbNotes.noteDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    LiveData<List<Note>> getAllNotes() {
        if (app.isFirstLaunch()) {
            refreshFromNetwork();
            app.setFirstLaunch(false);
        }
        return mNoteDao.getNotes();
    }

    private void refreshFromNetwork() {
        networkService.getJSONApi().getAllNotes().enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                insert(response.body().toArray(new Note[0]));
            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {

            }
        });
    }

    void insert(Note... note) {
        executorService.execute(() -> mNoteDao.insert(note));
    }

    void update(Note note) {
        executorService.execute(() -> mNoteDao.update(note));
    }

    void delete(Note note) {
        executorService.execute(() -> mNoteDao.delete(note));
    }

    void delete(long noteId) {
        executorService.execute(() -> mNoteDao.delete(noteId));
    }

    void deleteAll() {
        executorService.execute(mNoteDao::deleteAll);
    }

}
