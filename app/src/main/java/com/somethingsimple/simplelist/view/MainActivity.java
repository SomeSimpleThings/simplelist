package com.somethingsimple.simplelist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.view.bottomDrawer.BottomDrawerFragment;
import com.somethingsimple.simplelist.view.settings.SettingsActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    BottomDrawerFragment bottomDrawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_main);
        setupBottomBar();
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
                bottomDrawerFragment.show(getSupportFragmentManager(), "tag");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
