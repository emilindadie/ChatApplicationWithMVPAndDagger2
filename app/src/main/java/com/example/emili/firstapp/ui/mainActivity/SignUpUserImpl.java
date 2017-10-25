package com.example.emili.firstapp.ui.mainActivity;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.emili.firstapp.R;
import com.example.emili.firstapp.data.FirebaseHelper;
import com.example.emili.firstapp.model.User;
import com.example.emili.firstapp.network.SignUpUserService;
import com.example.emili.firstapp.ui.signInActivity.SignInModelCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import javax.inject.Inject;

/**
 * Created by emili on 15/10/2017.
 */

public class SignUpUserImpl implements SignUpUserService {

    private static final String TAG = "MainActivity";

    private FirebaseHelper firebaseHelper;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private Context context;
    private DatabaseReference userDatabaseReference;
    private StorageReference profilPicutre;
    private int defaultProfilPicture;
    SignUpModelCallBack signUpModelCallBack;
    //Handler handler;

    @Inject
    public SignUpUserImpl(Context context, SignUpModelCallBack signUpModelCallBack, FirebaseHelper firebaseHelper){
        this.context = context;
        this.signUpModelCallBack = signUpModelCallBack;
        this.firebaseHelper = firebaseHelper;
        firebaseAuth = firebaseHelper.getFirebaseAuth();
        firebaseUser = firebaseHelper.getFirebaseUser();
        userDatabaseReference = firebaseHelper.getUserDataReference(firebaseUser);
        this.profilPicutre = firebaseHelper.getProfilPicturesReference();
        defaultProfilPicture = R.drawable.anonyme;
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
                            signUpModelCallBack.successSignUp();
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            signUpModelCallBack.errorSignUp();
                        }
                    }
                });
    }

    private void onAuthSuccess(final FirebaseUser user, final String firstName, final String lastName, final String email) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                writeNewUser(user.getUid(), firstName, lastName, email, true);
                /*
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });*/
            }
        });
        thread.start();
    }

    private void writeNewUser(final String userId, final String nom, final String prenom, final String email, final boolean firstConnecting) {
        User user = new User(userId, nom, prenom, email, firstConnecting, "https://firebasestorage.googleapis.com/v0/b/freechatappli.appspot.com/o/anonyme.png?alt=media&token=80e29409-5e91-4dda-979f-07c2ccedccd8");
        userDatabaseReference.setValue(user);
    }
}
