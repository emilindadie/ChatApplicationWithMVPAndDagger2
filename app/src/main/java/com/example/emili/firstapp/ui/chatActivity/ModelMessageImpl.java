package com.example.emili.firstapp.ui.chatActivity;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.emili.firstapp.data.FirebaseHelper;
import com.example.emili.firstapp.model.ChatMessage;
import com.example.emili.firstapp.network.ModelMessageService;
import com.example.emili.firstapp.notification.NotificationUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by emili on 26/10/2017.
 */

public class ModelMessageImpl implements ModelMessageService {

    private Context context;
    private FirebaseHelper firebaseHelper;
    private DatabaseReference chatMessageReference;
    private ChildEventListener childEventListener;
    private List<ChatMessage> chatMessagesList;
    private ChatMessageModelCallBack chatMessageModelCallBack;
    private StorageReference messagePhoto;

    @Inject
    public ModelMessageImpl(Context context, FirebaseHelper firebaseHelper, ChatMessageModelCallBack chatMessageModelCallBack){
        this.context = context;
        this.firebaseHelper = firebaseHelper;
        chatMessageReference = firebaseHelper.getChatMessageDataReference();
        chatMessagesList = new ArrayList<>();
        this.chatMessageModelCallBack = chatMessageModelCallBack;
        messagePhoto = firebaseHelper.getMessagePicturesReference();
    }

    @Override
    public void sendMessage(ChatMessage chatMessage) {
        chatMessageReference.push().setValue(chatMessage);
        chatMessageModelCallBack.onSuccessSending();
    }

    @Override
    public void sendMessagePhoto(Context context, Uri uri) {
        StorageReference messagePhotoRef = messagePhoto.child(uri.getLastPathSegment());

        messagePhotoRef.putFile(uri).addOnSuccessListener((Activity) context, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            chatMessageModelCallBack.showSuccessUplaodingMassagePictures();
            }
        });

    }

    @Override
    public void detachDataBaseReadListener() {
        if (childEventListener != null) {
            chatMessageReference.removeEventListener(childEventListener);
            childEventListener = null;
        }
    }

    @Override
    public void attachDataBaseReadListerner() {

        if(childEventListener == null){

            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    startNotification(context, chatMessage.getAuteur(), chatMessage.getText());
                    chatMessagesList.add(chatMessage);
                    chatMessageModelCallBack.onSuccessLoading();
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    chatMessageModelCallBack.onErrorLoading();
                }
            };
            chatMessageModelCallBack.getMessage(chatMessagesList);
            chatMessageReference.addChildEventListener(childEventListener);
        }
    }

    private void startNotification(Context context, String title, String text) {
        NotificationUtils.remindUserBecauseCharging(context, title, text);
    }

}
