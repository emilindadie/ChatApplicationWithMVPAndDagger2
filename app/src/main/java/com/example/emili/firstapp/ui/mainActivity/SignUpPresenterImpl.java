package com.example.emili.firstapp.ui.mainActivity;

import android.content.Context;

import com.example.emili.firstapp.app.ChatApplication;
import com.example.emili.firstapp.network.CreateUserService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by emili on 15/10/2017.
 */

public class SignUpPresenterImpl implements SignUpPresenter , ModelCallBack{


    @Inject
    CreateUserService createUserService;

    @Inject
    Context context;
    private SignUpView signUpView;

    public SignUpPresenterImpl(@Named("application.context") Context context){
        ChatApplication.app().getAppComponent().inject(this);
        this.context = context;
    }

    @Override
    public void setView(SignUpView signUpView) {
        this.signUpView = signUpView;
    }

    @Override
    public void createUser(String fistName, String lastName, String email, String password) {

        createUserService.createNewUser(context, fistName, lastName, email, password);
    }

    @Override
    public void successSignUp() {

        signUpView.showSuccessSinUp();
    }

    @Override
    public void errorSignUp() {
        signUpView.showErrorSignUp();
    }
}
