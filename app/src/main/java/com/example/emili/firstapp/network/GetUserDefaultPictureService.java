package com.example.emili.firstapp.network;

import android.net.Uri;

import com.example.emili.firstapp.ui.profilActivity.firstConnectingFragment.FirstConnectingSettingModelcallBack;
import com.example.emili.firstapp.ui.profilActivity.profilActivityFragment.UserProfilModelCallBack;

/**
 * Created by emili on 24/10/2017.
 */

public interface GetUserDefaultPictureService {
    void loadUserDefaultPicture();
    void sendUserNewProfilPicture(Uri uri);
    void updateBoleanFirstConnecting();
}
