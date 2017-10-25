package com.example.emili.firstapp.ui.profilActivity.firstConnectingFragment;

/**
 * Created by emili on 24/10/2017.
 */

public interface FirstConnectingSettingModelcallBack {
    void getUserDefaultPicture(String url);
    void getUserFirstname(String firstName);
    void getUserLastName(String lastName);
    void onSuccessUpdateProfilPicture();
    void onErrorUpdateProfilPicture();
}
