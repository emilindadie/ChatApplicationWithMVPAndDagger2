package com.example.emili.firstapp.ui.mainActivity;

import android.content.Context;


import com.example.emili.firstapp.network.SignUpUserService;

import javax.inject.Inject;

/**
 * Created by emili on 15/10/2017.
 */

public class SignUpPresenterImpl implements SignUpPresenter , SignUpModelCallBack {

    SignUpUserService signUpUserService;
    static SignUpView signUpView;
    Context context;

    @Inject
    public SignUpPresenterImpl(Context context, SignUpUserService signUpUserService){
        //voir si probleme
        this.context = context;
        this.signUpUserService = signUpUserService;
    }

    @Override
    public void setView(SignUpView signUpView) {
        SignUpPresenterImpl.signUpView = signUpView;
    }

    @Override
    public void createUser(Context context, String fistName, String lastName, String email, String password) {
        signUpUserService.createNewUser(context, fistName, lastName, email, password);
    }

    @Override
    public void successSignUp() {
        signUpView.showSuccessSignUp();
    }

    @Override
    public void errorSignUp() {
        signUpView.showErrorSignUp();
    }
}
