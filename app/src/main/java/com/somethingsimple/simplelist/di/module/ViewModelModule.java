package com.somethingsimple.simplelist.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.somethingsimple.simplelist.view.ViewModelFactory;
import com.somethingsimple.simplelist.view.folder.FolderViewModel;
import com.somethingsimple.simplelist.view.note.NotesViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FolderViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsFolderViewModel(FolderViewModel folderViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NotesViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsNoteViewModel(NotesViewModel notesViewModel);

    @Binds
    @SuppressWarnings("unused")
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);

}
