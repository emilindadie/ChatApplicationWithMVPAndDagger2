package com.example.emili.firstapp.dagger;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.example.emili.firstapp.app.ChatApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by emili on 15/10/2017.
 */

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application){
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }
}