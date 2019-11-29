package com.somethingsimple.simplelist.di.builder;

import com.somethingsimple.simplelist.view.bottomDrawer.BottomDrawerFragment;
import com.somethingsimple.simplelist.view.folder.FolderListFragment;
import com.somethingsimple.simplelist.view.note.NoteDetailsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract FolderListFragment contributeFolderListFragment();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract NoteDetailsFragment contributeNoteDetailFragment();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract BottomDrawerFragment contributeBottomDrawerFragment();

}