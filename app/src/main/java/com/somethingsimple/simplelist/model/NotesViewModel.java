package com.somethingsimple.simplelist.model;

import android.app.Application;

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
    private LiveData<Note> note;

    private final MediatorLiveData<List<Note>> mediatorLiveData;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        notesRepo = new NotesRepository();
        mediatorLiveData = new MediatorLiveData<>();
    }

    public MediatorLiveData<List<Note>> getNotes(boolean ordered, long folderId) {
        mediatorLiveData.removeSource(liveData);
        if (ordered) {
            liveData = notesRepo.getAllNotesReversed(folderId);
        } else {
            liveData = notesRepo.getAllNotes(folderId);
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

//    public void init(long id, long folderId) {
//        if (this.note != null) {
//            // ViewModel is created on a per-Fragment basis, so the userId
//            // doesn't change.
//            return;
//        }
//        if (id == -1) note = new MutableLiveData<>(new Note("", folderId, false));
//        else note = notesRepo.getNote(id);
//    }

    public LiveData<Note> getNote(long folderId) {
        return note;
    }

    public void insert(String note, long folderId) {
        notesRepo.insert(new Note(note, folderId, false));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
