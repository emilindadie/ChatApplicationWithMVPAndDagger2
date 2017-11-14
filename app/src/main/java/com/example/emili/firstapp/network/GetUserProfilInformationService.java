package com.example.emili.firstapp.network;

import android.net.Uri;

import com.example.emili.firstapp.ui.profilActivity.profilActivityFragment.UserProfilModelCallBack;

/**
 * Created by emili on 24/10/2017.
 */

public interface GetUserProfilInformationService {
    void loadUserData();
    void updateProfilPicture(Uri uri);
}
