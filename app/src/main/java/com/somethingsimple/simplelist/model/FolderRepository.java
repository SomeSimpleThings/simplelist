package com.somethingsimple.simplelist.model;

import androidx.lifecycle.LiveData;

import com.somethingsimple.simplelist.NoteApplication;
import com.somethingsimple.simplelist.db.Folder;
import com.somethingsimple.simplelist.db.FolderDao;
import com.somethingsimple.simplelist.db.NotesDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FolderRepository {

    private final FolderDao folderDao;
    private final ExecutorService executorService;
    //    private final NetworkService networkService;
    private final NoteApplication app;

    FolderRepository() {
        app = NoteApplication.getInstanced();
        NotesDatabase dbNotes = app.getNotesDatabase();
        folderDao = dbNotes.folderDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    LiveData<List<Folder>> getAllFolders() {
        return folderDao.getFolders();
    }

    LiveData<List<Folder>> getAllFoldersReversed() {
        return folderDao.getFoldersOrdered();
    }

    LiveData<Folder> getFolder(long id) {
        return folderDao.getFolder(id);
    }

    void insert(Folder... folders) {
        executorService.execute(() -> folderDao.insert(folders));
    }

    void update(Folder folder) {
        executorService.execute(() -> folderDao.update(folder));
    }

    void delete(Folder folder) {
        executorService.execute(() -> folderDao.delete(folder));
    }

    void delete(long folderId) {
        executorService.execute(() -> folderDao.delete(folderId));
    }

    void deleteAll() {
        executorService.execute(folderDao::deleteAll);
    }

}
