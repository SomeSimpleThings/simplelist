package com.somethingsimple.simplelist.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM Note WHERE folderId = :folderId")
    LiveData<List<Note>> getNotes(long folderId);

    @Query("SELECT * FROM Note WHERE folderId = :folderId ORDER by noteId DESC")
    LiveData<List<Note>> getNotesOrdered(long folderId);

    @Query("SELECT * from Note WHERE noteId = :id")
    LiveData<Note> getNoteById(long id);

//    @Query("SELECT * from Note WHERE folderId = :id")
//    LiveData<List<Note>> getNoteByFolderId(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note... note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE from Note WHERE noteId = :noteId")
    void delete(long noteId);

    @Query("DELETE FROM Note")
    void deleteAll();
}
