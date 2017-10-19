package com.example.emili.firstapp.dagger;

import android.content.Context;

import com.example.emili.firstapp.app.ChatApplication;
import com.example.emili.firstapp.ui.mainActivity.CreateUserImpl;
import com.example.emili.firstapp.ui.mainActivity.MainActivity;
import com.example.emili.firstapp.ui.mainActivity.SignUpPresenterImpl;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by emili on 15/10/2017.
 */

@Singleton
@Component( modules = {AppModule.class, NetworkModule.class, PresenterModule.class})
public interface AppComponent {

    void inject(MainActivity target);
    void inject(SignUpPresenterImpl target);
    void inject(CreateUserImpl target);
}
