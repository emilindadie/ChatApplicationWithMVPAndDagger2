package com.example.emili.firstapp.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

/**
 * Created by emili on 21/10/2017.
 */

@Singleton
public class FirebaseHelper {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    public FirebaseHelper(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getFirebaseAuth(){
        return firebaseAuth;
    }
    public DatabaseReference getDatabaseReference(){
        return databaseReference;
    }
}
