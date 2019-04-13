package com.somethingsimple.simplelist.db;

import android.content.Context;

import com.somethingsimple.simplelist.R;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

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
