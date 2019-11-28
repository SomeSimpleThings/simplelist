package com.somethingsimple.simplelist.di.module;

import com.somethingsimple.simplelist.db.FolderRepository;
import com.somethingsimple.simplelist.db.NotesRepository;
import com.somethingsimple.simplelist.db.dao.FolderDao;
import com.somethingsimple.simplelist.db.dao.NoteDao;

import dagger.Module;
import dagger.Provides;

@Module(includes = DatabaseModule.class)
public class RepositoryModule {

    @Provides
    FolderRepository provideFolderRepositoty(FolderDao folderDao) {
        return new FolderRepository(folderDao);
    }

    @Provides
    NotesRepository provideNoteRepositoty(NoteDao noteDao) {
        return new NotesRepository(noteDao);
    }
}
