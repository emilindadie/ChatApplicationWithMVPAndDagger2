package com.example.emili.firstapp.ui.profilActivity.profilActivityFragment;

import android.net.Uri;

/**
 * Created by emili on 23/10/2017.
 */

public interface UserProfilView {
    void showFirstName(String firstName);
    void showLastName(String lastName);
    void showEmail(String email);
    void showUrlProfilPicture(String url);
    void showErrorUploadingProfilPicture();

}
