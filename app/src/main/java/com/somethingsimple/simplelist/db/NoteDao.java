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

    @Query("SELECT * FROM Note")
    LiveData<List<Note>> getNotes();

    @Query("SELECT * from Note WHERE noteId = :id")
    LiveData<Note> getNoteById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note... note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}
