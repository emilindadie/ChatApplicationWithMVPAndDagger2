package com.example.emili.firstapp.ui.mainActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.emili.firstapp.app.ChatApplication;
import com.example.emili.firstapp.model.User;
import com.example.emili.firstapp.network.CreateUserService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by emili on 15/10/2017.
 */

public class CreateUserImpl implements CreateUserService {


    private static final String TAG = "MainActivity";
    @Inject
    FirebaseAuth firebaseAuth;

    @Inject
    FirebaseDatabase firebaseDatabase;

    @Inject
    DatabaseReference databaseReference;

    @Inject ModelCallBack modelCallBack;


    Handler handler;

    @Inject
    Context context;


    public CreateUserImpl(@Named("application.context")Context context){
        ChatApplication.app().getAppComponent().inject(this);
        this.context = context;
    }

    @Override
    public void createNewUser(final Context context, final String firstName, final String lastName, final String email, String password) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            onAuthSuccess(task.getResult().getUser(), firstName, lastName, email);

                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            modelCallBack.errorSignUp();

                        }
                    }
                });
    }

    private void onAuthSuccess(final FirebaseUser user, final String firstName, final String lastName, String email) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                writeNewUser(user.getUid(), firstName, lastName, user.getEmail(), false);
                handler.post(new Runnable() {
                    @Override
                    public void run() {


                    }
                });
            }
        });
        thread.start();

    }

    private void writeNewUser(String userId, String nom, String prenom, String email, boolean firstConnecting) {
        User user = new User(userId, nom, prenom, email, firstConnecting);
        databaseReference.child("users").child(userId).setValue(user);
        modelCallBack.successSignUp();
    }

}
