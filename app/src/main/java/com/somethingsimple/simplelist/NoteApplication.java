package com.somethingsimple.simplelist;

import android.app.Application;

import com.somethingsimple.simplelist.db.NotesDatabase;


public class NoteApplication extends Application {

    private static NoteApplication instanced;
    private NotesDatabase notesDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instanced = this;
        notesDatabase = NotesDatabase.getDatabase(this);
    }

    public static NoteApplication getInstanced() {
        return instanced;
    }

    public NotesDatabase getNotesDatabase() {
        return notesDatabase;
    }
}
