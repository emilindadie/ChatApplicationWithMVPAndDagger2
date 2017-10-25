package com.example.emili.firstapp.ui.signInActivity;

import android.content.Context;

/**
 * Created by emili on 23/10/2017.
 */

public interface SignInPresenter {
    void setView(SignInView signInView);
    void signInUser(Context context, String email, String password);
}
