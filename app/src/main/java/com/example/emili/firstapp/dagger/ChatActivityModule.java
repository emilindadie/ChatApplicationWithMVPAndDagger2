package com.example.emili.firstapp.dagger;

import android.app.Activity;
import android.content.Context;

import com.example.emili.firstapp.data.FirebaseHelper;
import com.example.emili.firstapp.data.GPSHelper;
import com.example.emili.firstapp.network.ModelMessageService;
import com.example.emili.firstapp.ui.chatActivity.ChatMessageModelCallBack;
import com.example.emili.firstapp.ui.chatActivity.ChatPresenter;
import com.example.emili.firstapp.ui.chatActivity.ChatPresenterImpl;
import com.example.emili.firstapp.ui.chatActivity.ModelMessageImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by emili on 27/10/2017.
 */

@Module
public class ChatActivityModule {

    private Activity mActivity;

    public ChatActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    FirebaseHelper firebaseHelper(){
        return new FirebaseHelper();
    }


    @Provides
    GPSHelper gpsHelper(){
        return new GPSHelper();
    }

    @Provides
    ChatPresenter provideChatPresenter(@ActivityContext Context context, ModelMessageService modelMessageService){
        return new ChatPresenterImpl(context, modelMessageService);
    }

    @Provides
    ModelMessageService provideModelMessageService(@ActivityContext Context context, FirebaseHelper firebaseHelper, ChatMessageModelCallBack chatMessageModelCallBack){
        return new ModelMessageImpl(context, firebaseHelper, chatMessageModelCallBack);
    }

    @Provides
    ChatMessageModelCallBack provideChatMessageModelCallBack(@ActivityContext Context context){
        return new ChatPresenterImpl(context, null);
    }

}
