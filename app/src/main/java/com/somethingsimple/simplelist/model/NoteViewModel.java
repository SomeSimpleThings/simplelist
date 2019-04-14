package com.somethingsimple.simplelist.model;

import android.app.Application;

import com.somethingsimple.simplelist.db.Note;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class NoteViewModel extends AndroidViewModel {

    private NotesRepository notesRepo;
    private LiveData<List<Note>> liveData;

    private MediatorLiveData<List<Note>> mediatorLiveData;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        notesRepo = new NotesRepository();
        liveData = notesRepo.getAllNotes();
        mediatorLiveData = new MediatorLiveData<>();
        mediatorLiveData.addSource(liveData, notes -> mediatorLiveData.setValue(notes));
    }

    public MediatorLiveData<List<Note>> getNotes() {
        return mediatorLiveData;
    }

    public Note getNote(long id) {
        return notesRepo.getNote(id);
    }

    public void insert(Note note) {
        notesRepo.insert(note);
    }

    public void update(Note note) {
        notesRepo.update(note);
    }

    public void delete(Note note) {
        notesRepo.delete(note);
    }

    public void delete(long noteId) {
        notesRepo.delete(noteId);
    }

    public void deleteAll() {
        notesRepo.deleteAll();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
