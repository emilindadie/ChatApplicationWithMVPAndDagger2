package com.example.emili.firstapp.ui.profilActivity.profilActivityFragment;

import android.app.Activity;
import android.content.Context;

import com.example.emili.firstapp.data.FirebaseHelper;
import com.example.emili.firstapp.model.User;
import com.example.emili.firstapp.network.GetUserProfilInformationService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;

/**
 * Created by emili on 23/10/2017.
 */

public class GetUserProfilInformationImpl implements GetUserProfilInformationService {

    private FirebaseHelper firebaseHelper;
    private DatabaseReference userData;
    private Context context;
    private UserProfilModelCallBack userProfilModelCallBack;
    Handler handler;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    StorageReference profilPictureReference;

    public GetUserProfilInformationImpl(Context context, UserProfilModelCallBack userProfilModelCallBack, FirebaseHelper firebaseHelper){
        this.context = context;
        this.firebaseHelper = firebaseHelper;
        this.firebaseAuth = firebaseHelper.getFirebaseAuth();
        this.userProfilModelCallBack = userProfilModelCallBack;
        profilPictureReference = firebaseHelper.getProfilPicturesReference();
        this.userData = firebaseHelper.getUserDataReference(firebaseAuth.getCurrentUser());
        handler = new Handler();
    }

    @Override
    public void loadUserData() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                userData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final User user = dataSnapshot.getValue(User.class);
                        assert user != null;

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                userProfilModelCallBack.onSuccessFirstName(user.getFirstName());
                                userProfilModelCallBack.onSuccessLastName(user.getLastName());
                                userProfilModelCallBack.onSuccessEmail(user.getEmail());
                                userProfilModelCallBack.onSuccessUrlProfilPicture(user.getProfilPicture());
                            }
                        });
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        thread.start();
    }

    @Override
    public void updateProfilPicture(Uri uri) {

        StorageReference messagePhotoRef = profilPictureReference.child(uri.getLastPathSegment());

        messagePhotoRef.putFile(uri).addOnSuccessListener((Activity) context, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                userProfilModelCallBack.onSuccessUrlProfilPicture(taskSnapshot.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                userProfilModelCallBack.onErrorUrlProfilPicture();
            }
        });
    }
}
