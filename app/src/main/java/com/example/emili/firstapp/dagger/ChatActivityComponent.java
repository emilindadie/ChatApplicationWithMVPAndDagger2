package com.example.emili.firstapp.dagger;

import com.example.emili.firstapp.adapter.ChatMessageAdapter;
import com.example.emili.firstapp.ui.chatActivity.ChatActivity;
import com.example.emili.firstapp.ui.chatActivity.ChatPresenterImpl;
import com.example.emili.firstapp.ui.chatActivity.ModelMessageImpl;
import dagger.Component;

/**
 * Created by emili on 28/10/2017.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ChatActivityModule.class)
public interface ChatActivityComponent {

        void inject(ChatActivity chatActivity);
        void inject(ChatPresenterImpl chatPresenter);
        void inject(ModelMessageImpl modelMessage);
        void inject(ChatMessageAdapter chatMessageAdapter);
}
