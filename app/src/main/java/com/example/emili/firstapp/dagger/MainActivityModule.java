package com.example.emili.firstapp.dagger;

import android.app.Activity;
import android.content.Context;

import com.example.emili.firstapp.data.FirebaseHelper;
import com.example.emili.firstapp.network.CreateUserService;
import com.example.emili.firstapp.ui.mainActivity.CreateUserImpl;
import com.example.emili.firstapp.ui.mainActivity.ModelCallBack;
import com.example.emili.firstapp.ui.mainActivity.SignUpPresenter;
import com.example.emili.firstapp.ui.mainActivity.SignUpPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by emili on 21/10/2017.
 */

@Module
public class MainActivityModule {

    private Activity mActivity;

    public MainActivityModule(Activity activity) {
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
    SignUpPresenter provideSignUpPresenter(@ActivityContext Context context, CreateUserService createUserService, ModelCallBack modelCallBack){
        return new SignUpPresenterImpl(context, createUserService, modelCallBack);
    }

    @Provides
    CreateUserService provideCreateUserService(@ActivityContext Context context, FirebaseHelper firebaseHelper){
        return new CreateUserImpl(context, firebaseHelper);
    }

    @Provides
    ModelCallBack provideModelCallBack(@ActivityContext Context context){
        return new SignUpPresenterImpl(context, null, null);
    }
}
