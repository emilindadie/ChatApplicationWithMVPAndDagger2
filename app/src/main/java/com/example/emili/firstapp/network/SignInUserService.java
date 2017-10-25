package com.example.emili.firstapp.network;

import android.content.Context;

import com.example.emili.firstapp.ui.signInActivity.SignInModelCallBack;

/**
 * Created by emili on 23/10/2017.
 */

public interface SignInUserService {
    void signInUser(Context context, String email, String password);
}
