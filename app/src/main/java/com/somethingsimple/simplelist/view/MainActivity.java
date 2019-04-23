package com.somethingsimple.simplelist.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.SignInActivity;
import com.somethingsimple.simplelist.db.Note;
import com.somethingsimple.simplelist.model.NoteViewModel;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener {


    NavController navController;

    public static final int NEW_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    public static final int DELETE_NOTE_REQUEST = 3;
    public static final String NOTE_EXTRA = "com.somethingsimple.simplelist.note";
    private static final String ANONYMOUS = "anon";
    private SharedPreferences mSharedPreferences;
    private NoteViewModel noteViewModel;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private GoogleApiClient mGoogleApiClient;
    private String mUsername;
    private String mPhotoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        mUsername = ANONYMOUS;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            navController.navigate(R.id.noteListFragment);
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete_all:
                noteViewModel.deleteAll();
                return true;
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mFirebaseUser = null;
                mUsername = ANONYMOUS;
                mPhotoUrl = null;
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Note note;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case NEW_NOTE_REQUEST:
                    note = data.getParcelableExtra(NOTE_EXTRA);
                    noteViewModel.insert(note);
                    break;
                case EDIT_NOTE_REQUEST:
                    note = data.getParcelableExtra(NOTE_EXTRA);
                    noteViewModel.update(note);
                    break;
            }
        } else if (resultCode == DELETE_NOTE_REQUEST) {
            note = data.getParcelableExtra(NOTE_EXTRA);
            if (note.getNoteId() != 0) {
                noteViewModel.delete(note);
            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
