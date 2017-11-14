package com.example.emili.firstapp.ui.profilActivity.profilActivityFragment;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;

import com.example.emili.firstapp.network.GetUserProfilInformationService;

import javax.inject.Inject;

/**
 * Created by emili on 23/10/2017.
 */

    public class UserProfilPresenterImpl implements UserProfilPresenter, UserProfilModelCallBack {


    private static UserProfilView userProfilView;
    GetUserProfilInformationService getUserProfilInformationService;
    Context context;

    @Inject
    public UserProfilPresenterImpl(Context context, GetUserProfilInformationService getUserProfilInformationService){
        this.context = context;
        this.getUserProfilInformationService = getUserProfilInformationService;
    }

    @Override
    public void loadUserData() {
        getUserProfilInformationService.loadUserData();
    }

    @Override
    public void setUserProfilView(UserProfilView userProfilView) {
    UserProfilPresenterImpl.userProfilView = userProfilView;
    }

    @Override
    public void updateProfilPicture(Uri uri) {
    getUserProfilInformationService.updateProfilPicture(uri);
    }

    @Override
    public void onSuccessFirstName(String firstName) {
    userProfilView.showFirstName(firstName);
    }

    @Override
    public void onSuccessLastName(String lastName) {
    userProfilView.showLastName(lastName);
    }

    @Override
    public void onSuccessEmail(String email) {
    userProfilView.showEmail(email);
    }

    @Override
    public void onSuccessUrlProfilPicture(String url) {
    userProfilView.showUrlProfilPicture(url);
    }

    @Override
    public void onErrorUrlProfilPicture() {
    userProfilView.showErrorUploadingProfilPicture();
    }

}
