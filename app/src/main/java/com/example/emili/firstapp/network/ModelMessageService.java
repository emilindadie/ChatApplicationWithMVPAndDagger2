package com.example.emili.firstapp.network;

import android.content.Context;
import android.net.Uri;

import com.example.emili.firstapp.model.ChatMessage;

import java.util.List;

/**
 * Created by emili on 26/10/2017.
 */

public interface ModelMessageService {

    void sendMessage(ChatMessage chatMessage);
    void sendMessagePhoto(Context context, Uri uri);
    void detachDataBaseReadListener();
    void attachDataBaseReadListerner();
}
