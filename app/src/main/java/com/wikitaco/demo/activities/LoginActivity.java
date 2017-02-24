package com.wikitaco.demo.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wikitaco.demo.App;
import com.wikitaco.demo.R;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        doLogin();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == ResultCodes.OK) {
                doLogin();
                finish();
            }
        }
    }

    private void doLogin() {
        App app = (App) getApplicationContext();
        FirebaseAuth auth = app.getAuth();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {

            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            String provider = currentUser.getProviders().get(0);

            SharedPreferences pref = getSharedPreferences(app.getSharedPrefsName(), MODE_PRIVATE);
            pref.edit().putString("provider", provider).commit();
            pref.edit().putString("name", name).commit();

            if (email != null) {
                pref.edit().putString("email", email).commit();
            }

            Intent i = new Intent(this, TacosListActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);

        } else {
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setProviders(Arrays.asList(
                        new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                        new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                        new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()))
                    .setIsSmartLockEnabled(false)
                    .build(),
                RC_SIGN_IN);
        }
    }
}