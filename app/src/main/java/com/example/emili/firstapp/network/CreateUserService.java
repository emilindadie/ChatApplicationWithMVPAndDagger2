package com.example.emili.firstapp.network;

import android.content.Context;

import com.example.emili.firstapp.ui.mainActivity.ModelCallBack;

/**
 * Created by emili on 15/10/2017.
 */

public interface CreateUserService {
    void createNewUser(ModelCallBack modelCallBack, Context context, String firstName, String lastName, String email, String password);
}
