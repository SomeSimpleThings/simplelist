package com.somethingsimple.simplelist.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.somethingsimple.simplelist.db.Note;

public class NoteViewModel extends AndroidViewModel {

    private LiveData<Note> note;
    private final NoteRepository noteRepository;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository();
    }

    public void init(long id) {
        if (this.note != null) {
            // ViewModel is created on a per-Fragment basis, so the userId
            // doesn't change.
            return;
        }
        if (id == -1) note = new MutableLiveData<>(new Note(""));
        else note = noteRepository.getNote(id);
    }

    public LiveData<Note> getNote() {
        return note;
    }

    public void insert(Note note) {
        noteRepository.insert(note);
    }

    public void insert(String note) {
        noteRepository.insert(new Note(note));
    }
}
