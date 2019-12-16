package com.somethingsimple.simplelist.view.bottomDrawer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.databinding.DrawerBottomLayoutBinding;
import com.somethingsimple.simplelist.view.settings.SettingsActivity;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class BottomDrawerFragment extends BottomSheetDialogFragment
        implements OnCompleteListener<AuthResult> {

    @Inject
    GoogleSignInClient mGoogleSignInClient;
    @Inject
    FirebaseAuth mFirebaseAuth;

    private static final String TAG = "SignInFragment";
    private static final int RC_SIGN_IN = 9001;

    @Inject
    public BottomDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        DrawerBottomLayoutBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.drawer_bottom_layout,
                container,
                false);
        binding.setUser(mFirebaseAuth.getCurrentUser());
        binding.setHandler(this);
        return binding.getRoot();
    }

    @BindingAdapter("userPhotoUrl")
    public static void loadImage(ImageView view, Uri imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NavigationView drawerNavigation = getView().findViewById(R.id.navigation_view);
        String title = mFirebaseAuth.getCurrentUser() == null ?
                getString(R.string.sign_in) :
                getString(R.string.sign_out);
        drawerNavigation.getMenu()
                .findItem(R.id.sign_out_menu_drawer)
                .setTitle(title);

        drawerNavigation.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.sign_out_menu_drawer:
                    if (drawerNavigation.getMenu()
                            .findItem(R.id.sign_out_menu_drawer)
                            .getTitle().equals(getString(R.string.sign_in))) {
                        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                        startActivityForResult(signInIntent, RC_SIGN_IN);
                    } else {
                        processLogout();
                        this.dismiss();
                    }
                    return true;
                case R.id.settings_menu_drawer:
                    Intent intent = new Intent(getContext(), SettingsActivity.class);
                    startActivity(intent);
                    return true;
                default:
                    return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Google sign in
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            // Signed in successfully, show authenticated UI.
            firebaseAuthWithGoogle(completedTask.getResult(ApiException.class));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (!task.isSuccessful()) {
            Log.w(TAG, "signInWithCredential", task.getException());
        } else this.dismiss();
    }

    private void processLogout() {
        mFirebaseAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
            // ...
        });
    }
}
