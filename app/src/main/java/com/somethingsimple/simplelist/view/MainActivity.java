package com.somethingsimple.simplelist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.view.folder.FolderViewModel;
import com.somethingsimple.simplelist.view.note.NotesViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private NavController navController;
    private NotesViewModel mNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidInjection.inject(this);

        setContentView(R.layout.activity_main);
        setupBottomBar();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        FolderViewModel mFolderViewModel = new ViewModelProvider(this, viewModelFactory).get(FolderViewModel.class);
        mNoteViewModel = new ViewModelProvider(this, viewModelFactory).get(NotesViewModel.class);

        mFolderViewModel.getOpenNoteEvent().observe(this, folder -> {
            mNoteViewModel.setCurrentFolder(folder);
            navController.navigate(
                    R.id.action_folderListFragment_to_noteDetailsFragment);
        });
    }

    private void setupBottomBar() {
        BottomAppBar bar = findViewById(R.id.bar);
        setSupportActionBar(bar);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showBottomDrawer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showBottomDrawer() {
        BottomDrawerFragment bottomNavDrawerFragment = new BottomDrawerFragment();
        bottomNavDrawerFragment.show(getSupportFragmentManager(), "tag");
    }
}
