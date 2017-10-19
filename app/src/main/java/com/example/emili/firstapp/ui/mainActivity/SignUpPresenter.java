package com.example.emili.firstapp.ui.mainActivity;

/**
 * Created by emili on 15/10/2017.
 */

public interface SignUpPresenter {
    void setView(SignUpView signUpView);
    void createUser(String fistName, String lastName, String email, String password);
}
