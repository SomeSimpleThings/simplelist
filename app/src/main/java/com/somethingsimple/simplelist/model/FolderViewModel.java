package com.somethingsimple.simplelist.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.somethingsimple.simplelist.db.Folder;

import java.util.List;

public class FolderViewModel extends AndroidViewModel {

    private final FolderRepository folderRepository;
    private LiveData<List<Folder>> liveData;
    private LiveData<Folder> folderLiveData;

    private final MediatorLiveData<List<Folder>> mediatorLiveData;

    public FolderViewModel(@NonNull Application application) {
        super(application);
        folderRepository = new FolderRepository();
        mediatorLiveData = new MediatorLiveData<>();
    }

    public MediatorLiveData<List<Folder>> getFolders(boolean ordered) {
        mediatorLiveData.removeSource(liveData);
        if (ordered) {
            liveData = folderRepository.getAllFolders();
        } else {
            liveData = folderRepository.getAllFoldersReversed();
        }
        mediatorLiveData.addSource(liveData, mediatorLiveData::setValue);
        return mediatorLiveData;
    }

    public void insert(Folder folder) {
        folderRepository.insert(folder);
    }

    public void update(Folder folder) {
        folderRepository.update(folder);
    }

    public void delete(Folder folder) {
        folderRepository.delete(folder);
    }

    public void deleteAll() {
        folderRepository.deleteAll();
    }

    public void init(long id) {
        if (this.folderLiveData != null) {
            // ViewModel is created on a per-Fragment basis, so the userId
            // doesn't change.
            return;
        }
        if (id == -1) folderLiveData = new MutableLiveData<>(new Folder(""));
        else folderLiveData = folderRepository.getFolder(id);
    }

    public LiveData<Folder> getFolderLiveData() {
        return folderLiveData;
    }

    public void insert(String name) {
        folderRepository.insert(new Folder(name));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
