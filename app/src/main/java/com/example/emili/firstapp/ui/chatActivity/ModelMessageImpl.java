package com.example.emili.firstapp.ui.chatActivity;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.emili.firstapp.data.FirebaseHelper;
import com.example.emili.firstapp.model.ChatMessage;
import com.example.emili.firstapp.model.User;
import com.example.emili.firstapp.network.ModelMessageService;
import com.example.emili.firstapp.notification.NotificationUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by emili on 26/10/2017.
 */

public class ModelMessageImpl implements ModelMessageService {

    private Context context;
    private FirebaseHelper firebaseHelper;
    private DatabaseReference chatMessageReference;
    private ChildEventListener childEventListener;
    private List<ChatMessage> chatMessagesList, notifacationMessage;
    private ChatMessageModelCallBack chatMessageModelCallBack;
    private StorageReference messagePhoto;
    private DatabaseReference userData, urlMessages;
    android.os.Handler handler;
    private FirebaseAuth firebaseAuth;
    private List<String> urlList;
    boolean newUrl;


    @Inject
    public ModelMessageImpl(Context context, FirebaseHelper firebaseHelper, ChatMessageModelCallBack chatMessageModelCallBack){
        this.context = context;
        this.firebaseHelper = firebaseHelper;
        this.firebaseAuth = firebaseHelper.getFirebaseAuth();
        chatMessageReference = firebaseHelper.getChatMessageDataReference();
        chatMessagesList = new ArrayList<>();
        notifacationMessage = new ArrayList<>();
        this.chatMessageModelCallBack = chatMessageModelCallBack;
        messagePhoto = firebaseHelper.getMessagePicturesReference();
        this.userData = firebaseHelper.getUserDataReference(firebaseAuth.getCurrentUser());
        handler = new android.os.Handler();
        urlMessages = firebaseHelper.getUrlMessageDataReference();
        urlList = new ArrayList<>();
        newUrl = false;
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
                            chatMessageModelCallBack.getUserData(user);
                        }
                    });
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //chatMessageModelCallBack.onErrorLoadingUserData();
                }
            });
            }
        });
        thread.start();
    }

    @Override
    public void sendMessagePhoto(Context context, final ChatMessage chatMessage, Uri uri) {

        if(childEventListener == null){
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    String message = dataSnapshot.getValue(String.class);
                    urlList.add(message);
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

                }
            };
            urlMessages.addChildEventListener(childEventListener);
        }

        for(String message :urlList) {
            if (!message.trim().equals(uri.toString())) {
                newUrl = true;
            } else {
                newUrl = false;
            }
        }

        if(newUrl){
            StorageReference messagePhotoRef = messagePhoto.child(uri.getLastPathSegment());
            messagePhotoRef.putFile(uri).addOnSuccessListener((Activity) context, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    urlMessages.push().setValue(chatMessage.getPhotoUrl());
                    chatMessageModelCallBack.showSuccessUploadingMessagePictures();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    chatMessageModelCallBack.showErrorUploadingMessagePictures();
                }
            });
        }
        chatMessageReference.push().setValue(chatMessage);
        chatMessageModelCallBack.onSuccessSending();
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
                    //startNotification(context, chatMessage.getAuteur(), chatMessage.getText());
                    //chatMessagesList.add(chatMessage);
                    chatMessageModelCallBack.getMessage(chatMessage);
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
            /*
            if(chatMessagesList.size() == 0){
                chatMessageModelCallBack.onLoadingEmptyData();
            }else {
                chatMessageModelCallBack.getMessage(chatMessagesList);
            }*/
            //chatMessageModelCallBack.getMessage(chatMessagesList);
            chatMessageModelCallBack.onSuccessLoading();
            chatMessageReference.addChildEventListener(childEventListener);
        }
    }

    @Override
    public void activedNotification() {
        if(childEventListener == null){
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    notifacationMessage.add(chatMessage);
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
                }
            };
            chatMessageReference.addChildEventListener(childEventListener);
        }

        for(ChatMessage chatMessage: chatMessagesList){
            if(chatMessage.isNotified()){
                startNotification(context, chatMessage.getAuteur(), chatMessage.getText());
            }
        }
    }

    @Override
    public void markedAllNotification() {

        final Map<String, Object> changeBooleanNotified = new HashMap<String, Object>();
        chatMessageReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    changeBooleanNotified.put("notified", true);
                }
                userData.updateChildren(changeBooleanNotified);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void startNotification(Context context, String title, String text) {
        NotificationUtils.remindUserBecauseCharging(context, title, text);
    }
}
