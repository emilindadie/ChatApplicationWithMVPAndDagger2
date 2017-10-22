package com.example.emili.firstapp.app;

import android.app.Application;
import android.content.Context;

import com.example.emili.firstapp.dagger.ApplicationComponent;
import com.example.emili.firstapp.dagger.ApplicationModule;
import com.example.emili.firstapp.dagger.DaggerApplicationComponent;
import com.example.emili.firstapp.data.FirebaseHelper;

import javax.inject.Inject;

/**
 * Created by emili on 15/10/2017.
 */

public class ChatApplication extends Application {

    private ApplicationComponent applicationComponent;


    @Inject
    FirebaseHelper firebaseHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);

    }


    public static ChatApplication get(Context context) {
        return (ChatApplication) context.getApplicationContext();
    }

    public ApplicationComponent getApplicationComponent(){
        return this.applicationComponent;
    }

}
