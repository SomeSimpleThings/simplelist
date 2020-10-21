package com.somethingsimple.simplelist.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.view.bottomdrawer.BottomDrawerFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements Preference.OnPreferenceChangeListener {

    private static final String TAG = "tag";
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    BottomDrawerFragment bottomDrawerFragment;
    @Inject
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        applyTheme(preferences.getBoolean(getString(R.string.pref_dark_theme), false));
        setContentView(R.layout.activity_main);
        setupBottomBar();
    }

    private void setupBottomBar() {
        BottomAppBar bar = findViewById(R.id.bar);
        setSupportActionBar(bar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            bottomDrawerFragment.show(getSupportFragmentManager(), TAG);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference.getKey().equals(getString(R.string.pref_dark_theme)))
            applyTheme((Boolean) newValue);
        return true;
    }

    private void applyTheme(boolean dark) {
        int nightMode = dark ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(nightMode);

    }
}
