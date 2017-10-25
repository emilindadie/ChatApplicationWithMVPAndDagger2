package com.example.emili.firstapp.ui.signInActivity;

import android.content.Context;

import com.example.emili.firstapp.network.SignInUserService;
import com.example.emili.firstapp.ui.mainActivity.SignUpPresenterImpl;

import javax.inject.Inject;

/**
 * Created by emili on 23/10/2017.
 */

public class SignInPresenterImpl implements SignInPresenter , SignInModelCallBack{

    //Static for create a global variable
    static SignInView signInView;
    SignInUserService signInUserService;
    Context context;
    SignInModelCallBack signInModelCallBack;

    @Inject
    public SignInPresenterImpl(Context context, SignInUserService signInUserService){
        this.context = context;
        this.signInUserService = signInUserService;
    }

    @Override
    public void setView(SignInView signInView) {
        SignInPresenterImpl.signInView = signInView;
    }

    @Override
    public void signInUser(Context context, String email, String password) {
        signInUserService.signInUser(context,email, password);
    }

    @Override
    public void successSignIp() {
        signInView.goToProfilActivity();
    }

    @Override
    public void firstConnectingSignIn() {
        signInView.goToFirstSignInActivity();
    }

    @Override
    public void errorSignIp() {
        signInView.showSignInError();
    }
}
