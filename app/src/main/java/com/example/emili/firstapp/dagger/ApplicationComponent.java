package com.example.emili.firstapp.dagger;

import android.app.Application;
import android.content.Context;

import com.example.emili.firstapp.app.ChatApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by emili on 21/10/2017.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(ChatApplication chatApplication);
    @ApplicationContext
    Context getContext();
    Application getApplication();
}