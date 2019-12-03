package com.somethingsimple.simplelist.view.note;

import android.app.Application;
import android.util.Log;

import com.somethingsimple.simplelist.db.FolderRepository;
import com.somethingsimple.simplelist.db.entity.Folder;
import com.somethingsimple.simplelist.db.entity.Note;
import com.somethingsimple.simplelist.db.NotesRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import javax.inject.Inject;

public class NotesViewModel extends AndroidViewModel {

    private final NotesRepository notesRepo;
    private final FolderRepository folderRepository;
    private LiveData<List<Note>> liveData;

    private final MediatorLiveData<List<Note>> mediatorLiveData;

    @Inject
    public NotesViewModel(@NonNull Application application,
                          NotesRepository notesRepository,
                          FolderRepository folderRepo) {
        super(application);
        notesRepo = notesRepository;
        folderRepository = folderRepo;
        mediatorLiveData = new MediatorLiveData<>();
    }

    public MediatorLiveData<List<Note>> getNotes(Long folderid) {
        mediatorLiveData.removeSource(liveData);
        liveData = notesRepo.getAllNotes(folderid);
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

    public void updateFolder(Folder folder) {
        folderRepository.update(folder);
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

    public LiveData<Folder> getCurrentFolder(Long id) {
        return folderRepository.getFolder(id);
    }
}
