package com.example.emili.firstapp.ui.chatActivity;

import android.content.Context;
import android.net.Uri;

import com.example.emili.firstapp.model.ChatMessage;
import com.example.emili.firstapp.network.ModelMessageService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by emili on 26/10/2017.
 */

public class ChatPresenterImpl implements ChatPresenter, ChatMessageModelCallBack {

    private Context context;
    private ModelMessageService modelMessageService;
    private static ChatView chatView;


    @Inject
    public ChatPresenterImpl(Context context, ModelMessageService modelMessageService){
        this.context = context;
        this.modelMessageService = modelMessageService;
    }

    @Override
    public void sendMessage(ChatMessage chatMessage) {
        modelMessageService.sendMessage(chatMessage);
    }

    @Override
    public void sendMessagePhoto(Context context, Uri uri) {
    modelMessageService.sendMessagePhoto(context, uri);
    }

    @Override
    public void setView(ChatView chatView) {
        ChatPresenterImpl.chatView = chatView;
    }

    @Override
    public void detachDataBaseReadListener() {
        modelMessageService.detachDataBaseReadListener();
    }

    @Override
    public void attachDataBaseReadListerner() {
        modelMessageService.attachDataBaseReadListerner();
        chatView.startLoading();
    }

    @Override
    public void getMessage(List<ChatMessage> chatMessageList) {
        chatView.showMessageList(chatMessageList);
        chatView.stopLoading();
    }

    @Override
    public void onSuccessSending() {
        chatView.showSuccessSending();
    }

    @Override
    public void onErrorSending() {
        chatView.showErrorSending();
    }

    @Override
    public void onSuccessLoading() {
    chatView.showSuccessLoading();
    }

    @Override
    public void onErrorLoading() {
    chatView.showErrorLoading();
    }

    @Override
    public void showSuccessUplaodingMassagePictures() {
    chatView.showSuccessUploadingMessagePictures();
    }
}
