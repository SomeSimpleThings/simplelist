package com.somethingsimple.simplelist.model;

import android.app.Application;

import com.somethingsimple.simplelist.SingleLiveEvent;
import com.somethingsimple.simplelist.db.entity.Note;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

public class NotesViewModel extends AndroidViewModel {

    private final NotesRepository notesRepo;
    private LiveData<List<Note>> liveData;
    private long currentFolderId;

    private final SingleLiveEvent<Long> mSaveNoteEvent = new SingleLiveEvent<>();


    private final MediatorLiveData<List<Note>> mediatorLiveData;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        notesRepo = new NotesRepository();
        mediatorLiveData = new MediatorLiveData<>();
    }

    public MediatorLiveData<List<Note>> getNotes() {
        boolean ordered = true;
        mediatorLiveData.removeSource(liveData);
        if (ordered) {
            liveData = notesRepo.getAllNotesReversed(getCurrentFolderId());
        } else {
            liveData = notesRepo.getAllNotes(getCurrentFolderId());
        }
        mediatorLiveData.addSource(liveData, mediatorLiveData::setValue);
        return mediatorLiveData;
    }

    public void addNote() {
        notesRepo.insert(new Note(getCurrentFolderId(), false));
    }

    public void addNoteCheckable() {
        notesRepo.insert(new Note(getCurrentFolderId(), true));
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

    public void insert(String note, long folderId) {
        notesRepo.insert(new Note(note, folderId, false));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public SingleLiveEvent<Long> getSaveNoteEvent() {
        return mSaveNoteEvent;
    }

    public long getCurrentFolderId() {
        return currentFolderId;
    }

    public void setCurrentFolderId(long currentFolderId) {
        this.currentFolderId = currentFolderId;
    }
}
