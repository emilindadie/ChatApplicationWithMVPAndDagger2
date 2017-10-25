package com.example.emili.firstapp.dagger;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.example.emili.firstapp.data.FirebaseHelper;
import com.example.emili.firstapp.network.SignInUserService;
import com.example.emili.firstapp.ui.signInActivity.SignInModelCallBack;
import com.example.emili.firstapp.ui.signInActivity.SignInPresenter;
import com.example.emili.firstapp.ui.signInActivity.SignInPresenterImpl;
import com.example.emili.firstapp.ui.signInActivity.SignInUserImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by emili on 23/10/2017.
 */

@Module
public class SignInActivityModule {

    private Activity mActivity;

    public SignInActivityModule(Activity activity) {
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
    SignInPresenter provideSignInPresenter(@ActivityContext Context context, SignInUserService signInUserService){
        return new SignInPresenterImpl(context, signInUserService);
    }

    @Provides
    SignInUserService provideSignInUserService(SignInModelCallBack signInModelCallBack, @ActivityContext Context context,FirebaseHelper firebaseHelper){
        return new SignInUserImpl(signInModelCallBack, context, firebaseHelper);
    }


    @Provides
    SignInModelCallBack provideSignInModelCallBack(@ActivityContext Context context){
        return new SignInPresenterImpl(context, null);
    }

}
