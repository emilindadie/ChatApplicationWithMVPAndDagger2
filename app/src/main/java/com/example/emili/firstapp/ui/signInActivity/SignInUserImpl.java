package com.example.emili.firstapp.ui.signInActivity;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.emili.firstapp.data.FirebaseHelper;
import com.example.emili.firstapp.model.User;
import com.example.emili.firstapp.network.SignInUserService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

/**
 * Created by emili on 23/10/2017.
 */

public class SignInUserImpl implements SignInUserService {

    FirebaseHelper firebaseHelper;
    FirebaseAuth firebaseAuth;
    DatabaseReference userData;
    SignInModelCallBack signInModelCallBack;
    Context context;

    @Inject
    public SignInUserImpl(SignInModelCallBack signInModelCallBack, Context context, FirebaseHelper firebaseHelper){
        this.context = context;
        this.firebaseHelper = firebaseHelper;
        firebaseAuth = firebaseHelper.getFirebaseAuth();
        this.signInModelCallBack = signInModelCallBack;
    }

    @Override
    public void signInUser(Context context,String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    userData = firebaseHelper.getUserDataReference(user);
                    userData.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            assert user != null;
                            if (user.getFirstConnecting()) {
                                signInModelCallBack.firstConnectingSignIn();
                            } else {
                                signInModelCallBack.successSignIp();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }else {
                    signInModelCallBack.errorSignIp();
                }
            }
        });
    }
}
