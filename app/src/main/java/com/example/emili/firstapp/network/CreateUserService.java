package com.example.emili.firstapp.network;

import android.content.Context;

/**
 * Created by emili on 15/10/2017.
 */

public interface CreateUserService {
    void createNewUser(Context context, String firstName, String lastName, String email, String password);
}
