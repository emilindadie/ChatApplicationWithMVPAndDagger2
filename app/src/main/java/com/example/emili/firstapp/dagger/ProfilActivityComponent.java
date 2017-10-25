package com.example.emili.firstapp.dagger;

import com.example.emili.firstapp.ui.profilActivity.ProfilActivity;
import com.example.emili.firstapp.ui.profilActivity.firstConnectingFragment.FirstConnectingFragment;
import com.example.emili.firstapp.ui.profilActivity.firstConnectingFragment.FirstConnectingSettingPresenterImpl;
import com.example.emili.firstapp.ui.profilActivity.firstConnectingFragment.GetUserDefaultPictureImpl;
import com.example.emili.firstapp.ui.profilActivity.profilActivityFragment.GetUserProfilInformationImpl;
import com.example.emili.firstapp.ui.profilActivity.profilActivityFragment.ProfilActivityFragment;
import com.example.emili.firstapp.ui.profilActivity.profilActivityFragment.UserProfilPresenterImpl;


import dagger.Component;

/**
 * Created by emili on 24/10/2017.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ProfilActivityModule.class)
public interface ProfilActivityComponent {

    void inject(ProfilActivityFragment profilActivityFragment);
    void inject(UserProfilPresenterImpl userProfilPresenter);
    void inject(GetUserProfilInformationImpl getUserProfilInformation);
    void inject(FirstConnectingFragment firstConnectingFragment);
    void inject(FirstConnectingSettingPresenterImpl firstConnectingSettingPresenter);
    void inject(GetUserDefaultPictureImpl getUserDefaultPicture);
}