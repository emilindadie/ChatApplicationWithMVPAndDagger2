package com.example.emili.firstapp.network;

import android.content.Context;

import com.example.emili.firstapp.model.User;
import com.example.emili.firstapp.ui.mainActivity.SignUpModelCallBack;

import java.util.Observable;

/**
 * Created by emili on 15/10/2017.
 */

public interface SignUpUserService {

    void createNewUser(Context context, String firstName, String lastName, String email, String password);
}
