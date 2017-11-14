package com.example.emili.firstapp.ui.chatActivity;

import com.example.emili.firstapp.model.ChatMessage;
import com.example.emili.firstapp.model.User;
import com.example.emili.firstapp.notification.MessageUtils;

import java.util.List;

/**
 * Created by emili on 27/10/2017.
 */

public interface ChatMessageModelCallBack{
        void getMessage(List<ChatMessage> chatMessageList);
        void getMessage(ChatMessage chatMessage);
        void onSuccessSending();
        void onErrorSending();
        void onSuccessLoading();
        void onErrorLoading();
        void onErrorLoadingUserData();
        void showSuccessUploadingMessagePictures();
        void showErrorUploadingMessagePictures();
        void getUserData(User user);
        void onLoadingEmptyData();
}
