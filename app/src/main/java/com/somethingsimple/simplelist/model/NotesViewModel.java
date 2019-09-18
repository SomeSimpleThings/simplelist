package com.somethingsimple.simplelist.model;

import android.app.Application;

import com.somethingsimple.simplelist.SingleLiveEvent;
import com.somethingsimple.simplelist.db.entity.Folder;
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
    private Folder currentFolder;

    private final MediatorLiveData<List<Note>> mediatorLiveData;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        notesRepo = new NotesRepository();
        mediatorLiveData = new MediatorLiveData<>();
    }

    public MediatorLiveData<List<Note>> getNotes() {
        boolean ordered = false;
        mediatorLiveData.removeSource(liveData);
        if (ordered) {
            liveData = notesRepo.getAllNotesReversed(getCurrentFolder().getId());
        } else {
            liveData = notesRepo.getAllNotes(getCurrentFolder().getId());
        }
        mediatorLiveData.addSource(liveData, mediatorLiveData::setValue);
        return mediatorLiveData;
    }

    public void addNote() {
        notesRepo.insert(new Note(getCurrentFolder().getId(), false));
    }

    public void addNoteCheckable() {
        notesRepo.insert(new Note(getCurrentFolder().getId(), true));
    }

    public void insert(Note note) {
        notesRepo.insert(note);
    }

    public void update(List<Note> notes) {
        notesRepo.update(notes.toArray(new Note[0]));
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

    public Folder getCurrentFolder() {
        return currentFolder;
    }

    public void setCurrentFolder(Folder currentFolder) {
        this.currentFolder = currentFolder;
    }
}
