package com.somethingsimple.simplelist.db.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.somethingsimple.simplelist.db.entity.Note;

@Dao
public interface NoteDao extends BaseDao<Note> {

    @Query("SELECT * FROM Note WHERE folderId = :folderId ORDER by position")
    LiveData<List<Note>> getNotes(long folderId);

    @Query("SELECT * from Note WHERE noteId = :id")
    LiveData<Note> getNoteById(long id);

    @Query("DELETE from Note WHERE noteId = :noteId")
    void delete(long noteId);

    @Query("DELETE FROM Note")
    void deleteAll();
}
