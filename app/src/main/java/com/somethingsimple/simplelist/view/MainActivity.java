package com.somethingsimple.simplelist.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.ViewModelFactory;
import com.somethingsimple.simplelist.model.FolderViewModel;
import com.somethingsimple.simplelist.model.NotesViewModel;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener {

    private NavController navController;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    public GoogleApiClient mGoogleApiClient;
    private FolderViewModel mFolderViewModel;
    private NotesViewModel mNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setupBottomBar();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        checkUserSigned();
        setupGoogleSign();

        mFolderViewModel = obtainFolderViewModel(this);
        mNoteViewModel = obtainNoteViewModel(this);

        mFolderViewModel.getNewNoteEvent().observe(this,
                action -> navController.navigate(
                        R.id.action_folderListFragment_to_noteDetailsFragment));

        mFolderViewModel.getOpenNoteEvent().observe(this, id -> {
            mNoteViewModel.setCurrentFolderId(id);
            navController.navigate(
                    R.id.action_folderListFragment_to_noteDetailsFragment);
        });
    }

    public static FolderViewModel obtainFolderViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(FolderViewModel.class);
    }

    public static NotesViewModel obtainNoteViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(NotesViewModel.class);
    }

    private void setupBottomBar() {
        BottomAppBar bar = findViewById(R.id.bar);
        setSupportActionBar(bar);
    }

    private void checkUserSigned() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            navController.navigate(R.id.action_noteListFragment_to_loginFragment);
        }
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
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.user_bundle_key), mFirebaseUser);
        bottomNavDrawerFragment.setArguments(bundle);
        bottomNavDrawerFragment.show(getSupportFragmentManager(), "tag");
    }

    public void processLogout() {
        mFirebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        mFirebaseUser = null;
        navController.navigate(R.id.loginFragment);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    // Configure Google Sign In
    private void setupGoogleSign() {
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

}
