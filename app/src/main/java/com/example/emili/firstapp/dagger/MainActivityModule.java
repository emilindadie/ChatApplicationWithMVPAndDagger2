package com.example.emili.firstapp.dagger;

import android.app.Activity;
import android.content.Context;

import com.example.emili.firstapp.data.FirebaseHelper;
import com.example.emili.firstapp.network.SignUpUserService;
import com.example.emili.firstapp.ui.mainActivity.SignUpModelCallBack;
import com.example.emili.firstapp.ui.mainActivity.SignUpUserImpl;
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
    SignUpPresenter provideSignUpPresenter(@ActivityContext Context context, SignUpUserService signUpUserService){
        return new SignUpPresenterImpl(context, signUpUserService);
    }

    @Provides
    SignUpUserService provideCreateUserService(@ActivityContext Context context, SignUpModelCallBack signUpModelCallBack, FirebaseHelper firebaseHelper){
        return new SignUpUserImpl(context, signUpModelCallBack, firebaseHelper);
    }

    @Provides
    SignUpModelCallBack provideModelCallBack(@ActivityContext Context context){
        return new SignUpPresenterImpl(context, null);
    }
}
