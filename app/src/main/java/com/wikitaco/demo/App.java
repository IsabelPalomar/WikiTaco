package com.wikitaco.demo;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ykro.
 */

public class App extends Application {
  private FirebaseAuth auth;

  @Override
  public void onCreate() {
    super.onCreate();
    auth = FirebaseAuth.getInstance();
  }

  public FirebaseAuth getAuth() {
    return auth;
  }

  public String getSharedPrefsName(){
    return getPackageName() + "_internal";
  }
}
