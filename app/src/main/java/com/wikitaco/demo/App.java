package com.wikitaco.demo;

import android.app.Application;
import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

  public String getName(){
    FirebaseUser currentUser = auth.getCurrentUser();
    String name = "";
    if (currentUser != null) {
      return currentUser.getDisplayName();
    }
    return name;
  }

  public String getEmail(){
    FirebaseUser currentUser = auth.getCurrentUser();
    String email = "";
    if (currentUser != null && currentUser.getEmail() != null) {
      return currentUser.getEmail();
    }
    return email;
  }

  public Uri getPhotoUrl(){
    FirebaseUser currentUser = auth.getCurrentUser();
    Uri photoUrl = null;
    if (currentUser != null) {
      return currentUser.getPhotoUrl();
    }
    return photoUrl;
  }
}
