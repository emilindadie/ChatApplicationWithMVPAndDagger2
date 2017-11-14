package com.example.emili.firstapp.ui.chatActivity;

import com.example.emili.firstapp.model.ChatMessage;
import com.example.emili.firstapp.model.User;

import java.util.List;

/**
 * Created by emili on 26/10/2017.
 */

public interface ChatView {

    void showMessageList(List<ChatMessage> chatMessageList);
    void showMessageList(ChatMessage chatMessageList);
    void startLoading();
    void stopLoading();
    void showSuccessSending();
    void showErrorSending();
    void showSuccessLoading();
    void showErrorLoading();
    void showErrorLoadingUserData();
    void showSuccessUploadingMessagePictures();
    void showErrorUploadingMessagePictures();
    void showUserData(User user);
    void showLoadingEmptyData();
}
