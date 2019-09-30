package com.somethingsimple.simplelist.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.somethingsimple.simplelist.swipeInteractions.SingleLiveEvent;
import com.somethingsimple.simplelist.db.entity.Folder;

import java.util.List;

public class FolderViewModel extends AndroidViewModel {

    private final FolderRepository folderRepository;

    private final SingleLiveEvent<Folder> mOpenNoteEvent = new SingleLiveEvent<>();
    private Folder currentFolder;

    private LiveData<List<Folder>> liveData;

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

    public long insert(Folder folder) {
        return folderRepository.insert(folder);
    }

    public void update(Folder folder) {
        folderRepository.update(folder);
    }

    public void update(String foldername) {
        currentFolder.setFolderName(foldername);
        folderRepository.update(currentFolder);
    }

    public void delete(Folder folder) {
        folderRepository.delete(folder);
    }

    public void deleteAll() {
        folderRepository.deleteAll();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public SingleLiveEvent<Folder> getOpenNoteEvent() {
        return mOpenNoteEvent;
    }

    public void addFolder() {
        currentFolder = new Folder("");
        currentFolder.setId(insert(currentFolder));
        mOpenNoteEvent.setValue(currentFolder);
    }

    public void openFolder(Folder folder){
        currentFolder = folder;
        mOpenNoteEvent.setValue(currentFolder);
    }

    public void setFolder(Folder folder) {
        currentFolder = folder;
    }
}
