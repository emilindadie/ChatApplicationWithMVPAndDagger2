package com.example.emili.firstapp.dagger;

import android.content.Context;

import com.example.emili.firstapp.ui.mainActivity.ModelCallBack;
import com.example.emili.firstapp.ui.mainActivity.SignUpPresenter;
import com.example.emili.firstapp.ui.mainActivity.SignUpPresenterImpl;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by emili on 15/10/2017.
 */

@Module
public class PresenterModule {

    private Context context;

    @Provides
    @Singleton
    SignUpPresenter provideSignUpPresenter(Context context) {
        return new SignUpPresenterImpl(context);
    }

    @Provides
    @Named("context")
    Context provideContext(){
        return this.context;
    }

    @Provides
    @Singleton
    ModelCallBack provideModelCallBack(Context context) {
        return new SignUpPresenterImpl(context);
    }
}