package com.example.emili.firstapp.ui.chatActivity;

import android.content.Context;
import android.net.Uri;

import com.example.emili.firstapp.model.ChatMessage;
import com.example.emili.firstapp.model.User;
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
    public void sendMessage(Context context,ChatMessage chatMessage, Uri uri) {
    modelMessageService.sendMessagePhoto(context, chatMessage, uri);
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
        chatView.startLoading();
        modelMessageService.attachDataBaseReadListerner();
    }

    @Override
    public void loadUserData() {
        modelMessageService.loadUserData();
    }

    @Override
    public void activedNotification() {
    modelMessageService.activedNotification();
    }

    @Override
    public void markedAllNotification() {
    modelMessageService.markedAllNotification();
    }

    @Override
    public void getMessage(List<ChatMessage> chatMessageList) {
        chatView.showMessageList(chatMessageList);
    }

    @Override
    public void getMessage(ChatMessage chatMessage) {
    chatView.showMessageList(chatMessage);
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
    chatView.stopLoading();
    }

    @Override
    public void onErrorLoading() {
    chatView.showErrorLoading();
    chatView.stopLoading();
    }

    @Override
    public void onErrorLoadingUserData() {
    chatView.showErrorLoadingUserData();
    }

    @Override
    public void showSuccessUploadingMessagePictures() {
    chatView.showSuccessUploadingMessagePictures();
    }

    @Override
    public void showErrorUploadingMessagePictures() {
        chatView.showErrorUploadingMessagePictures();
    }

    @Override
    public void getUserData(User user) {
        chatView.showUserData(user);
    }

    @Override
    public void onLoadingEmptyData() {
        chatView.stopLoading();
        chatView.showLoadingEmptyData();
    }
}
