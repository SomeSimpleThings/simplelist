package com.somethingsimple.simplelist.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.somethingsimple.simplelist.db.entity.Folder;

import java.util.List;

@Dao
public abstract class FolderDao implements BaseDao<Folder> {

    @Query("SELECT * FROM Folder ORDER by position")
    public abstract LiveData<List<Folder>> getFolders();

    @Query("SELECT * FROM Folder WHERE id = :folderId")
    public abstract LiveData<Folder> getFolder(long folderId);

//    @Query("SELECT * FROM Folder  ORDER by position DESC")
//    public abstract LiveData<List<Folder>> getFoldersOrdered();

    @Query("SELECT noteText " +
            "FROM note " +
            "WHERE folderId = :folderId " +
            "ORDER by checked, position " +
            "LIMIT 4")
    public abstract List<String> selectNames(long folderId);

    @Transaction
    public void updateWithNoteText(Folder folder) {
        List<String> names = selectNames(folder.getId());
        StringBuilder stringBuilder = new StringBuilder();
        for (String name : names) {
            stringBuilder.append(name).append("\n");
        }
        folder.setFolderText(stringBuilder.toString());
        update(folder);
    }

    @Query("DELETE from Folder WHERE id = :folderId")
    public abstract void delete(long folderId);

    @Query("DELETE FROM Folder")
    public abstract void deleteAll();
}
