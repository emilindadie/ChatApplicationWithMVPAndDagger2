package com.example.emili.firstapp.ui.mainActivity;

import android.content.Context;


import com.example.emili.firstapp.network.CreateUserService;

import javax.inject.Inject;

/**
 * Created by emili on 15/10/2017.
 */

public class SignUpPresenterImpl implements SignUpPresenter , ModelCallBack{

    CreateUserService createUserService;
    static SignUpView signUpView;
    ModelCallBack modelCallBack;

    Context context;

    @Inject
    public SignUpPresenterImpl(Context context, CreateUserService createUserService, ModelCallBack modelCallBack){
        //voir si probleme
        this.context = context;
        this.createUserService = createUserService;
        this.modelCallBack = modelCallBack;
        //((MainActivityComponent)context).inject(this);
    }


    @Override
    public void setView(SignUpView signUpView) {
        this.signUpView = signUpView;
    }

    @Override
    public void createUser(Context context, String fistName, String lastName, String email, String password) {
        createUserService.createNewUser(modelCallBack, context, fistName, lastName, email, password);
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
