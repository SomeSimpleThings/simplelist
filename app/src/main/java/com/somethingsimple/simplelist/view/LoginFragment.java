package com.somethingsimple.simplelist.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.somethingsimple.simplelist.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment
        implements GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener,
        OnCompleteListener<AuthResult> {

    private static final String TAG = "SignInFragment";
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mFirebaseAuth;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();

        SignInButton mSignInButton = view.findViewById(R.id.sign_in_google);
        mSignInButton.setOnClickListener(this);
        Button anonSignButton = view.findViewById(R.id.sign_in_anon);
        anonSignButton.setOnClickListener(this);
        FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_google:
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(
                        ((MainActivity) getActivity()).mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
            case R.id.sign_in_anon:
                signInAnonymously();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Google sign in
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign-In was successful, authenticate with Firebase
                firebaseAuthWithGoogle(result.getSignInAccount());
            } else {
                // Google Sign-In failed
                Log.e(TAG, "Google Sign-In failed.");
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this);
    }

    private void signInAnonymously() {
        mFirebaseAuth.signInAnonymously()
                .addOnCompleteListener(this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Toast.makeText(this,
//                getString(R.string.google_ps_error),
//                Toast.LENGTH_SHORT)
//                .show();
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            Navigation.findNavController(getView()).navigate(
                    R.id.action_loginFragment_to_noteListFragment);
        } else {
            Log.w(TAG, "signInWithCredential", task.getException());
//            Toast.makeText(SignInActivity.this, "Authentication failed.",
//                    Toast.LENGTH_SHORT).show();
        }
    }

}
