package com.example.emili.firstapp.ui.profilActivity.firstConnectingFragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.example.emili.firstapp.data.FirebaseHelper;
import com.example.emili.firstapp.model.User;
import com.example.emili.firstapp.network.GetUserDefaultPictureService;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by emili on 24/10/2017.
 */

public class GetUserDefaultPictureImpl implements GetUserDefaultPictureService{

   Context context;
   FirebaseHelper firebaseHelper;
   private DatabaseReference userData;
   private FirebaseUser firebaseUser;
   FirstConnectingSettingModelcallBack firstConnectingSettingModelcallBack;

   @Inject
   public GetUserDefaultPictureImpl(Context context, FirstConnectingSettingModelcallBack firstConnectingSettingModelcallBack, FirebaseHelper firebaseHelper){
      this.context = context;
      this.firebaseHelper = firebaseHelper;
      this.firebaseUser = firebaseHelper.getFirebaseUser();
      this.firstConnectingSettingModelcallBack = firstConnectingSettingModelcallBack;
      this.userData = firebaseHelper.getUserDataReference(firebaseUser);
   }

   @Override
   public void loadUserDefaultPicture() {

      Thread thread = new Thread(new Runnable() {
         @Override
         public void run() {
            userData.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                  User user = dataSnapshot.getValue(User.class);
                  assert user != null;
                  firstConnectingSettingModelcallBack.getUserFirstname(user.getFirstName());
                  firstConnectingSettingModelcallBack.getUserLastName(user.getLastName());
                  firstConnectingSettingModelcallBack.getUserDefaultPicture(user.getProfilPicture());
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
   public void sendUserNewProfilPicture(Uri uri) {
      //Obtenir la reference pour stocker le fichier dans profil_pictures/nomDuFichier
      StorageReference photoRef = firebaseHelper.getProfilPicturesReference().child(uri.getLastPathSegment());
      photoRef.putFile(uri).addOnSuccessListener((Activity) context, new OnSuccessListener<UploadTask.TaskSnapshot>() {
         @Override
         public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            Uri downloadUrl = taskSnapshot.getDownloadUrl();

            FirebaseUser firebaseUser = firebaseHelper.getFirebaseUser();
            DatabaseReference userDataReference = firebaseHelper.getUserDataReference(firebaseUser);
            Map<String, Object> changeProfilUrl = new HashMap<String, Object>();
            changeProfilUrl.put("profilPicture", downloadUrl.toString());
            userDataReference.updateChildren(changeProfilUrl);
            firstConnectingSettingModelcallBack.onSuccessUpdateProfilPicture();
         }
      });
   }
}
