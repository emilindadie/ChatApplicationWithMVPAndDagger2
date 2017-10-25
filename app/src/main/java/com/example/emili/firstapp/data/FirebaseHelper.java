package com.example.emili.firstapp.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.inject.Singleton;

/**
 * Created by emili on 21/10/2017.
 */

@Singleton
public class FirebaseHelper {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseStorage firebaseStorage;
    private StorageReference profilPictures;

    public FirebaseHelper(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        profilPictures = firebaseStorage.getReference().child("profil_pictures");
    }

    public FirebaseAuth getFirebaseAuth(){
        return firebaseAuth;
    }
    public DatabaseReference getDatabaseReference(){
        return databaseReference;
    }
    public FirebaseUser getFirebaseUser(){
        return firebaseUser;
    }

    public DatabaseReference getUserDataReference(FirebaseUser firebaseUser){
        return  databaseReference.child("users").child(firebaseUser.getUid());
    }

    //get profil picture references
    public StorageReference getProfilPicturesReference(){
        return profilPictures;
    }
}
