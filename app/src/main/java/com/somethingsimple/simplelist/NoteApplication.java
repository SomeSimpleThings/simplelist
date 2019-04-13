package com.somethingsimple.simplelist;

import android.app.Application;

import com.somethingsimple.simplelist.db.NotesDatabase;
import com.somethingsimple.simplelist.network.NetworkService;


public class NoteApplication extends Application {

    private static NoteApplication instanced;
    private NotesDatabase notesDatabase;
    private NetworkService networkService;
    private boolean firstLaunch;


    @Override
    public void onCreate() {
        super.onCreate();
        instanced = this;
        notesDatabase = NotesDatabase.getDatabase(this);
        networkService = NetworkService.getInstance();
        firstLaunch = true;
    }

    public static NoteApplication getInstanced() {
        return instanced;
    }

    public NotesDatabase getNotesDatabase() {
        return notesDatabase;
    }

    public NetworkService getNetworkService() {
        return networkService;
    }

    public boolean isFirstLaunch() {
        return firstLaunch;
    }

    public void setFirstLaunch(boolean firstLaunch) {
        this.firstLaunch = false;
    }
}
