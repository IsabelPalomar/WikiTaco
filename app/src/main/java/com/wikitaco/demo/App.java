package com.wikitaco.demo;

import android.app.Application;
import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by ykro.
 */

public class App extends Application {
  private FirebaseAuth auth;
  private DatabaseReference dbRef;
  private StorageReference storageRef;

  private final static String TACO_LIST_CHILD = "tacos";

  @Override
  public void onCreate() {
    super.onCreate();

    auth = FirebaseAuth.getInstance();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    dbRef = db.getReference();
    storageRef = storage.getReference();
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

  public DatabaseReference getTacoListReference(){
    return dbRef.child(TACO_LIST_CHILD).getRef();
  }

  public StorageReference getTacoStorageReference(String key){
    return storageRef.child(TACO_LIST_CHILD).child(key + ".jpg");
  }
}
