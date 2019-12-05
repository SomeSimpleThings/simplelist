package com.somethingsimple.simplelist.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.somethingsimple.simplelist.db.dao.FolderDao;
import com.somethingsimple.simplelist.db.dao.NoteDao;
import com.somethingsimple.simplelist.db.entity.Folder;
import com.somethingsimple.simplelist.db.entity.Note;

@Database(entities = {Note.class, Folder.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
    public abstract FolderDao folderDao();
}
