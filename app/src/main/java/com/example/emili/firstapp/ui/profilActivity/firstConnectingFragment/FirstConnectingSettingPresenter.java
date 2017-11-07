package com.example.emili.firstapp.ui.profilActivity.firstConnectingFragment;

import android.net.Uri;

import com.example.emili.firstapp.ui.profilActivity.profilActivityFragment.UserProfilView;

/**
 * Created by emili on 24/10/2017.
 */

public interface FirstConnectingSettingPresenter {
    void loadUserDefaultProfilPicture();
    void setFirstConnectingSettingView(FirstConnectingSettingView firstConnectingSettingView);
    void updateProfilPicture(Uri uri);
    void updateBooleanFirstConnecting();
}
