package com.somethingsimple.simplelist.di.builder;

import com.somethingsimple.simplelist.view.MainActivity;
import com.somethingsimple.simplelist.view.settings.SettingsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @SuppressWarnings("unused")
    @ContributesAndroidInjector(modules = FragmentBuilderModule.class)
    abstract MainActivity mainActivity();

    @SuppressWarnings("unused")
    abstract SettingsActivity settingsActivity();
}
