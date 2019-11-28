package com.somethingsimple.simplelist.di.module;

import android.app.Application;

import androidx.room.Room;

import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.db.NotesDatabase;
import com.somethingsimple.simplelist.db.dao.FolderDao;
import com.somethingsimple.simplelist.db.dao.NoteDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    NotesDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                NotesDatabase.class,
                application.getString(R.string.dbname))
                .build();
    }

    @Provides
    @Singleton
    NoteDao provideNoteDao(NotesDatabase notesDatabase) {
        return notesDatabase.noteDao();
    }

    @Provides
    @Singleton
    FolderDao provideFolderDao(NotesDatabase notesDatabase) {
        return notesDatabase.folderDao();
    }
}
