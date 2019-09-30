package com.somethingsimple.simplelist.model;

import android.app.Application;

import com.somethingsimple.simplelist.db.entity.Folder;
import com.somethingsimple.simplelist.db.entity.Note;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

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
        mediatorLiveData.removeSource(liveData);
        liveData = notesRepo.getAllNotes(getCurrentFolder().getId());
        mediatorLiveData.addSource(liveData, mediatorLiveData::setValue);
        return mediatorLiveData;
    }

    public void update(List<Note> notesToChange, List<Note> notesToDelete) {
        notesRepo.delete(notesToDelete.toArray(new Note[0]));
        for (int i = 0; i < notesToChange.size(); i++) {
            notesToChange.get(i).setPosition(i);
        }
        notesRepo.insert(notesToChange.toArray(new Note[0]));
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

    public Folder getCurrentFolder() {
        return currentFolder;
    }

    public void setCurrentFolder(Folder currentFolder) {
        this.currentFolder = currentFolder;
    }
}
