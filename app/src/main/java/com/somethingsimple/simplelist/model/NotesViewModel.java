package com.somethingsimple.simplelist.model;

import android.app.Application;

import com.somethingsimple.simplelist.db.Note;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class NotesViewModel extends AndroidViewModel {

    private final NotesRepository notesRepo;
    private LiveData<List<Note>> liveData;

    private final MediatorLiveData<List<Note>> mediatorLiveData;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        notesRepo = new NotesRepository();
        mediatorLiveData = new MediatorLiveData<>();
    }

    public MediatorLiveData<List<Note>> getNotes(boolean ordered) {
        mediatorLiveData.removeSource(liveData);
        if (ordered) {
            liveData = notesRepo.getAllNotesReversed();
        } else {
            liveData = notesRepo.getAllNotes();
        }
        mediatorLiveData.addSource(liveData, mediatorLiveData::setValue);
        return mediatorLiveData;
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

    public void deleteAll() {
        notesRepo.deleteAll();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
