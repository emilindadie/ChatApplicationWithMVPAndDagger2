package com.example.emili.firstapp.ui.profilActivity.profilActivityFragment;

import android.content.Context;

import com.example.emili.firstapp.data.FirebaseHelper;
import com.example.emili.firstapp.model.User;
import com.example.emili.firstapp.network.GetUserProfilInformationService;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by emili on 23/10/2017.
 */

public class GetUserProfilInformationImpl implements GetUserProfilInformationService {

    private FirebaseHelper firebaseHelper;
    private DatabaseReference userData;
    private FirebaseUser firebaseUser;
    private Context context;
    private UserProfilModelCallBack userProfilModelCallBack;

    public GetUserProfilInformationImpl(Context context, UserProfilModelCallBack userProfilModelCallBack, FirebaseHelper firebaseHelper){
        this.context = context;
        this.firebaseHelper = firebaseHelper;
        this.firebaseUser = firebaseHelper.getFirebaseUser();
        this.userProfilModelCallBack = userProfilModelCallBack;
        this.userData = firebaseHelper.getUserDataReference(firebaseUser);
    }

    @Override
    public void loadUserData() {

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                userData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        assert user != null;

                        userProfilModelCallBack.onSuccessFirstName(user.getFirstName());
                        userProfilModelCallBack.onSuccessLastName(user.getLastName());
                        userProfilModelCallBack.onSuccessEmail(user.getEmail());
                        userProfilModelCallBack.onSuccessUrlProfilPicture(user.getProfilPicture());
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        thread.start();
    }
}
