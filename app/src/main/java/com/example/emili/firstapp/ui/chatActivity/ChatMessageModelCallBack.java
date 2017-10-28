package com.example.emili.firstapp.ui.chatActivity;

import com.example.emili.firstapp.model.ChatMessage;

import java.util.List;

/**
 * Created by emili on 27/10/2017.
 */

public interface ChatMessageModelCallBack{
        void getMessage(List<ChatMessage> chatMessageList);
        void onSuccessSending();
        void onErrorSending();
        void onSuccessLoading();
        void onErrorLoading();
        void showSuccessUplaodingMassagePictures();
}
