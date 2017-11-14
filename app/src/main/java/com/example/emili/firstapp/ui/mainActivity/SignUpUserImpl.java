package com.example.emili.firstapp.ui.mainActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.emili.firstapp.R;
import com.example.emili.firstapp.data.FirebaseHelper;
import com.example.emili.firstapp.model.User;
import com.example.emili.firstapp.network.SignUpUserService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    Handler handler;

    @Inject
    public SignUpUserImpl(Context context, SignUpModelCallBack signUpModelCallBack, FirebaseHelper firebaseHelper){
        this.context = context;
        this.signUpModelCallBack = signUpModelCallBack;
        this.firebaseHelper = firebaseHelper;
        firebaseAuth = firebaseHelper.getFirebaseAuth();
        this.profilPicutre = firebaseHelper.getProfilPicturesReference();
        defaultProfilPicture = R.drawable.anonyme;
        handler = new Handler();
    }

    @Override
    public void createNewUser(final Context context, final String firstName, final String lastName, final String email, final String password) {
        /*
        setupCreateUser(firstName, lastName, password, email).subscribeOn(Schedulers.io())
            // Be notified on the main thread
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getFirebaseUserObserver());*/


        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser(), firstName, lastName, email);
                }
                else {
                    signUpModelCallBack.errorSignUp();
                }
            }
        });
    }



    /*
    private Observable<AuthResult> setupCreateUser(final String firstName, final String lastName, final String email, final String password){


        return Observable.create(new ObservableOnSubscribe<AuthResult>() {
            @Override
            public void subscribe(final ObservableEmitter<AuthResult> e) throws Exception {

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            onAuthSuccess(firebaseAuth.getCurrentUser(), firstName, lastName, password);
                            e.onNext(task.getResult());
                            e.onComplete();
                        }
                        else{
                            e.onError(new Throwable("error"));
                        }
                    }
                });
            }
        });
    }

    private Observer<AuthResult> getFirebaseUserObserver() {
        return new Observer<AuthResult>() {

            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(AuthResult value) {

                if(value.getUser() != null){
                    signUpModelCallBack.successSignUp();
                }
                //System.out.println(value);
            }

            @Override
            public void onError(Throwable e) {
                signUpModelCallBack.errorSignUp();
            }

            @Override
            public void onComplete() {
                //System.out.println("onComplete");
            }
        };
    }
    */
    private void onAuthSuccess(final FirebaseUser user, final String firstName, final String lastName, final String email) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                writeNewUser(user, user.getUid(), firstName, lastName, email, true);
               handler.post(new Runnable() {
                    @Override
                    public void run() {
                        signUpModelCallBack.successSignUp();
                    }
                });
            }
        });
        thread.start();
    }

    private void writeNewUser(FirebaseUser firebaseUser, final String userId, final String nom, final String prenom, final String email, final boolean firstConnecting) {
        userDatabaseReference = firebaseHelper.getUserDataReference(firebaseUser);
        User user = new User(userId, nom, prenom, email, firstConnecting, "https://firebasestorage.googleapis.com/v0/b/freechatappli.appspot.com/o/anonyme.png?alt=media&token=80e29409-5e91-4dda-979f-07c2ccedccd8");
        userDatabaseReference.setValue(user);
    }
}
