package com.somethingsimple.simplelist.di.module;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.somethingsimple.simplelist.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    GoogleSignInOptions provideGso(Application application) {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(application.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    @Provides
    SharedPreferences providePreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    GoogleSignInClient provideGSignInClient(Application application, GoogleSignInOptions gso) {
        return GoogleSignIn.getClient(application, gso);
    }

    @Provides
    @Singleton
    GoogleSignInAccount provideGoogleAccount(Application application) {
        return GoogleSignIn.getLastSignedInAccount(application);
    }

    @Provides
    @Singleton
    FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }
}
