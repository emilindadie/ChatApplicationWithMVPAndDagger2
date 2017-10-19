package com.example.emili.firstapp.app;

import android.app.Application;
import android.content.Context;

import com.example.emili.firstapp.dagger.AppComponent;
import com.example.emili.firstapp.dagger.AppModule;
import com.example.emili.firstapp.dagger.DaggerAppComponent;
import com.example.emili.firstapp.dagger.NetworkModule;
import com.example.emili.firstapp.dagger.PresenterModule;

import javax.inject.Inject;

/**
 * Created by emili on 15/10/2017.
 */

public class ChatApplication extends Application {

    private static ChatApplication app;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .presenterModule(new PresenterModule()).networkModule(new NetworkModule())
                .build();    }

    public static ChatApplication app(){
        return app;
    }

    public AppComponent getAppComponent(){
        return this.appComponent;
    }

}
