package com.somethingsimple.simplelist.db;

import androidx.lifecycle.LiveData;

import com.somethingsimple.simplelist.db.entity.Folder;
import com.somethingsimple.simplelist.db.dao.FolderDao;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class FolderRepository {

    private final FolderDao folderDao;
    private final ExecutorService executorService;

    @Inject
    public FolderRepository(FolderDao Dao) {
        folderDao = Dao;
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Folder>> getAllFolders() {
        return folderDao.getFolders();
    }

    LiveData<List<Folder>> getAllFoldersReversed() {
        return folderDao.getFoldersOrdered();
    }

    LiveData<Folder> getFolder(long id) {
        return folderDao.getFolder(id);
    }

    public void insert(Folder... folders) {
        executorService.execute(() -> folderDao.insert(folders));
    }

    public long insert(Folder folder) {
        long id = -1;
        Future<Long> future = executorService.submit(() -> folderDao.insert(folder));
        try {
            id = future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void update(Folder folder) {
        executorService.execute(() -> folderDao.update(folder));
    }

    public void delete(Folder folder) {
        executorService.execute(() -> folderDao.delete(folder));
    }

    public void delete(long folderId) {
        executorService.execute(() -> folderDao.delete(folderId));
    }

    public void deleteAll() {
        executorService.execute(folderDao::deleteAll);
    }

}
