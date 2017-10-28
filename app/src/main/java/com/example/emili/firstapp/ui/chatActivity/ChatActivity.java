package com.example.emili.firstapp.ui.chatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emili.firstapp.R;
import com.example.emili.firstapp.adapter.ChatMessageAdapter;
import com.example.emili.firstapp.app.ChatApplication;
import com.example.emili.firstapp.dagger.ChatActivityComponent;
import com.example.emili.firstapp.dagger.ChatActivityModule;
import com.example.emili.firstapp.dagger.DaggerChatActivityComponent;
import com.example.emili.firstapp.data.FirebaseHelper;
import com.example.emili.firstapp.data.GPSHelper;
import com.example.emili.firstapp.model.ChatMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener, ChatView{

    private static final String TAG = ChatActivity.class.getSimpleName();

    private ChatActivityComponent chatActivityComponent;
    @Inject
    GPSHelper gpsHelper;
    @Inject
    FirebaseHelper firebaseHelper;
    @Inject
    ChatPresenter chatPresenter;

    private FirebaseUser user;
    private String auteur;
    EditText message;
    Button envoyer;
    ImageView messagePicture;
    TextView fileIndicator;
    boolean cliquer = false;
    boolean privateMode = true;
    private static final int RC_PHOTO_PICKER = 2;
    private FirebaseAuth.AuthStateListener authStateListener;
    StorageReference messagePhotoReferences;
    FirebaseAuth firebaseAuth;
    ProgressBar loadingBar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ChatMessageAdapter chatMessageAdapter;

    public ChatActivityComponent getChatActivityComponent(){
        if(chatActivityComponent == null){
            chatActivityComponent = DaggerChatActivityComponent.builder()
                    .chatActivityModule(new ChatActivityModule(this))
                    .applicationComponent(ChatApplication.get(this).getApplicationComponent())
                    .build();
        }
        return chatActivityComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getChatActivityComponent().inject(this);

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        message = (EditText) findViewById(R.id.editMessage);
        envoyer = (Button) findViewById(R.id.btnSendMessage);
        messagePicture = (ImageView) findViewById(R.id.messagePicture);
        fileIndicator = (TextView) findViewById(R.id.messagePictureIndicator);
        fileIndicator.setVisibility(View.INVISIBLE);
        envoyer.setEnabled(false);
        setupPreferences();
        messagePhotoReferences = firebaseHelper.getMessagePicturesReference();
        firebaseAuth = firebaseHelper.getFirebaseAuth();
        user = firebaseHelper.getFirebaseUser();
        auteur = user.getDisplayName();
        loadingBar = (ProgressBar) findViewById(R.id.loadingBar);


        recyclerView = (RecyclerView) findViewById(R.id.chatRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        chatMessageAdapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>(), gpsHelper);

        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0){
                    envoyer.setEnabled(true);
                }
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //Load Message
                    chatPresenter.attachDataBaseReadListerner();
                }
                else {
                    chatPresenter.detachDataBaseReadListener();
                    //Clear adapter
                }
            }
        };
    }

    private void startInterneGalleryPicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id) {
            case R.id.messagePicture:
                startInterneGalleryPicture();
                break;

            case R.id.btnSendMessage:
                cliquer = true;
                sendMessage();
                break;
        }
    }

    private void sendMessage() {
        if(message.getText().length() > 0){
            //Mode privée
            if(privateMode){
                String text = message.getText().toString();
                ChatMessage chatMessage = new ChatMessage(auteur, text, null);

                //envoyer au presenter
                chatPresenter.sendMessage(chatMessage);
            }else {
            Location position = gpsHelper.getMyPosition(this);
            double longitude = position.getLongitude();
            double latitude = position.getLatitude();
            String text = message.getText().toString();
            ChatMessage chatMessage = new ChatMessage(auteur, text,null,longitude, latitude);

            //envoyer au presenter
            chatPresenter.sendMessage(chatMessage);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            // Sign in was canceled by the user, finish the activity
            Uri selectImageUri = data.getData();
            message.setEnabled(false);
            envoyer.setEnabled(true);

            fileIndicator.setVisibility(View.VISIBLE);
            fileIndicator.setText(selectImageUri.toString().substring(0, 10));
            if(cliquer){
                if(privateMode){
                    //envoyer le message aux presenter
                    ChatMessage chatMessage = new ChatMessage(auteur, null, selectImageUri.toString());
                    chatPresenter.sendMessage(chatMessage);
                    chatPresenter.sendMessagePhoto(this, selectImageUri);
                }else {
                    Location position = gpsHelper.getMyPosition(this);
                    double longitude = position.getLongitude();
                    double latitude = position.getLatitude();

                    //envoyer le message aux presenter
                    ChatMessage chatMessage = new ChatMessage(auteur, null, selectImageUri.toString(), longitude, latitude);
                    chatPresenter.sendMessage(chatMessage);
                    chatPresenter.sendMessagePhoto(this, selectImageUri);
                }
            }
        }
    }

    private void setupPreferences() {
        //get default preference
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getBoolean(getString(R.string.pref_private_mode_key), getResources().getBoolean(R.bool.pref_private_mode_default))){
            privateMode = true;
        }
        else {
            privateMode = false;
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(sharedPreferences.getBoolean(getString(R.string.pref_private_mode_key), getResources().getBoolean(R.bool.pref_private_mode_default))){
            privateMode = true;
        }
        else {
            privateMode = false;
        }
    }

    @Override
    public void showMessageList(List<ChatMessage> chatMessageList) {
    chatMessageAdapter.ajouterTous(chatMessageList);
    recyclerView.setAdapter(chatMessageAdapter);
    }

    @Override
    public void startLoading() {
        loadingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {
        loadingBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showSuccessSending() {
    Toast.makeText(this, "Le message a été envoyé", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorSending() {
        Toast.makeText(this, "Le message n'a pas été envoyé", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccessLoading() {
        Toast.makeText(this, "Chargement terminé", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorLoading() {
        Toast.makeText(this, "Erreur de chargement", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccessUploadingMessagePictures() {
        Toast.makeText(this, "Le message a bien été envoyé", Toast.LENGTH_LONG).show();
    }

    protected void onPause(){
        super.onPause();
        if(authStateListener != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
        chatPresenter.detachDataBaseReadListener();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.v(TAG, "OnResume failed");
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();
        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
