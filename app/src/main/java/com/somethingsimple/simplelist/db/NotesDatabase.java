package com.somethingsimple.simplelist.db;

import android.content.Context;

import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.db.dao.FolderDao;
import com.somethingsimple.simplelist.db.dao.NoteDao;
import com.somethingsimple.simplelist.db.entity.Folder;
import com.somethingsimple.simplelist.db.entity.Note;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class, Folder.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();
    public abstract FolderDao folderDao();

    private static volatile NotesDatabase instance;

    public static NotesDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (NotesDatabase.class) {
                if (instance == null) {
                   instance = Room.databaseBuilder(context,
                           NotesDatabase.class,
                           context.getString(R.string.dbname))
                           .build();
                }
            }
        }
        return instance;
    }
}
