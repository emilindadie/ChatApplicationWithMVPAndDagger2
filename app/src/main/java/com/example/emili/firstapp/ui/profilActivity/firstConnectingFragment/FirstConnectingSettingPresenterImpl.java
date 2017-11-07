package com.example.emili.firstapp.ui.profilActivity.firstConnectingFragment;

import android.content.Context;
import android.net.Uri;

import com.example.emili.firstapp.network.GetUserDefaultPictureService;
import com.example.emili.firstapp.ui.profilActivity.profilActivityFragment.UserProfilView;

import javax.inject.Inject;

/**
 * Created by emili on 24/10/2017.
 */

public class FirstConnectingSettingPresenterImpl implements FirstConnectingSettingPresenter , FirstConnectingSettingModelcallBack{


    static FirstConnectingSettingView firstConnectingSettingView;
    GetUserDefaultPictureService getUserDefaultPictureService;
    Context context;

    @Inject
    public FirstConnectingSettingPresenterImpl(Context context, GetUserDefaultPictureService getUserDefaultPictureService){
        this.context = context;
        this.getUserDefaultPictureService = getUserDefaultPictureService;
    }

    @Override
    public void loadUserDefaultProfilPicture() {
        getUserDefaultPictureService.loadUserDefaultPicture();
    }

    @Override
    public void setFirstConnectingSettingView(FirstConnectingSettingView firstConnectingSettingView) {
        FirstConnectingSettingPresenterImpl.firstConnectingSettingView = firstConnectingSettingView;
    }

    @Override
    public void updateProfilPicture(Uri uri) {
    getUserDefaultPictureService.sendUserNewProfilPicture(uri);
    }

    @Override
    public void updateBooleanFirstConnecting() {
        getUserDefaultPictureService.updateBoleanFirstConnecting();
    }

    @Override
    public void getUserDefaultPicture(String url) {
    firstConnectingSettingView.showUrlProfilPicture(url);
    }

    @Override
    public void getUserFirstname(String firstName) {
    firstConnectingSettingView.showUserFirstName(firstName);
    }

    @Override
    public void getUserLastName(String lastName) {
        firstConnectingSettingView.showUserLastName(lastName);
    }

    @Override
    public void onSuccessUpdateProfilPicture() {
    firstConnectingSettingView.showSuccessUpdateProfilPicture();
    }

    @Override
    public void onErrorUpdateProfilPicture() {

    }
}
