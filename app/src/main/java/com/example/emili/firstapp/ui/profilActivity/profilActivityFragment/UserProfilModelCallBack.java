package com.example.emili.firstapp.ui.profilActivity.profilActivityFragment;

/**
 * Created by emili on 23/10/2017.
 */

public interface UserProfilModelCallBack {
    void onSuccessFirstName(String firstName);
    void onSuccessLastName(String lastName);
    void onSuccessEmail(String email);
    void onSuccessUrlProfilPicture(String url);
    void onErrorUrlProfilPicture();

}
