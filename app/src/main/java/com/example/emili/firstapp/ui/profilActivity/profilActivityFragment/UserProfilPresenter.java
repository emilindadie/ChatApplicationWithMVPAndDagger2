package com.example.emili.firstapp.ui.profilActivity.profilActivityFragment;

import android.net.Uri;

/**
 * Created by emili on 23/10/2017.
 */

public interface UserProfilPresenter {
    void loadUserData();
    void setUserProfilView(UserProfilView userProfilView);
    void updateProfilPicture(Uri uri);
}
