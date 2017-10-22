package com.example.emili.firstapp.dagger;

import android.app.Application;
import android.content.Context;

import com.example.emili.firstapp.data.FirebaseHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by emili on 15/10/2017.
 */

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application){
        this.application = application;
    }

    @Provides
    Application providesApplication() {
        return application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    FirebaseHelper firebaseHelper(){
        return new FirebaseHelper();
    }
}