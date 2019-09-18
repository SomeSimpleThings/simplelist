package com.somethingsimple.simplelist.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.somethingsimple.simplelist.db.entity.Folder;

import java.util.List;

@Dao
public interface FolderDao {

    @Query("SELECT * FROM Folder")
    LiveData<List<Folder>> getFolders();

    @Query("SELECT * FROM Folder WHERE id = :folderId")
    LiveData<Folder> getFolder(long folderId);

    @Query("SELECT * FROM Folder  ORDER by id DESC")
    LiveData<List<Folder>> getFoldersOrdered();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Folder... folders);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Folder folder);

    @Update
    void update(Folder folder);

    @Query("UPDATE Folder SET folderName = :foldername WHERE id = :id; ")
    void update(long id, String foldername);

    @Delete
    void delete(Folder folder);

    @Query("DELETE from Folder WHERE id = :folderId")
    void delete(long folderId);

    @Query("DELETE FROM Folder")
    void deleteAll();
}
