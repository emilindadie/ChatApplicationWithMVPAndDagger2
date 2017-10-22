package com.example.emili.firstapp.ui.mainActivity;


import android.content.Context;

/**
 * Created by emili on 15/10/2017.
 */


public interface SignUpPresenter {
    void setView(SignUpView signUpView);
    void createUser(Context context, String fistName, String lastName, String email, String password);
}
