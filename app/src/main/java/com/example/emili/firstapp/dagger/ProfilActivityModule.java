package com.example.emili.firstapp.dagger;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.example.emili.firstapp.data.FirebaseHelper;
import com.example.emili.firstapp.network.GetUserDefaultPictureService;
import com.example.emili.firstapp.network.GetUserProfilInformationService;
import com.example.emili.firstapp.ui.profilActivity.firstConnectingFragment.FirstConnectingSettingModelcallBack;
import com.example.emili.firstapp.ui.profilActivity.firstConnectingFragment.FirstConnectingSettingPresenter;
import com.example.emili.firstapp.ui.profilActivity.firstConnectingFragment.FirstConnectingSettingPresenterImpl;
import com.example.emili.firstapp.ui.profilActivity.firstConnectingFragment.GetUserDefaultPictureImpl;
import com.example.emili.firstapp.ui.profilActivity.profilActivityFragment.GetUserProfilInformationImpl;
import com.example.emili.firstapp.ui.profilActivity.profilActivityFragment.UserProfilModelCallBack;
import com.example.emili.firstapp.ui.profilActivity.profilActivityFragment.UserProfilPresenter;
import com.example.emili.firstapp.ui.profilActivity.profilActivityFragment.UserProfilPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by emili on 24/10/2017.
 */

@Module
public class ProfilActivityModule {

    private Activity mActivity;

    public ProfilActivityModule(Activity activity){
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    FirebaseHelper firebaseHelper(){
        return new FirebaseHelper();
    }

    @Provides
    UserProfilPresenter provideUserProfilPresenter(@ActivityContext Context context, GetUserProfilInformationService getUserProfilInformationService){
        return new UserProfilPresenterImpl(context, getUserProfilInformationService);
    }

    @Provides
    GetUserProfilInformationService provideGetUserProfilInformationService(@ActivityContext Context context, UserProfilModelCallBack userProfilModelCallBack, FirebaseHelper firebaseHelper){
        return new GetUserProfilInformationImpl(context, userProfilModelCallBack, firebaseHelper);
    }

    @Provides
    FirstConnectingSettingPresenter provideFirstConnectingSettingPresenter(@ActivityContext Context context,  GetUserDefaultPictureService getUserDefaultPictureService){
        return new FirstConnectingSettingPresenterImpl(context, getUserDefaultPictureService);
    }

    @Provides
    GetUserDefaultPictureService provideGetUserDefaultPictureService(@ActivityContext Context context, FirstConnectingSettingModelcallBack firstConnectingSettingModelcallBack, FirebaseHelper firebaseHelper){
        return new GetUserDefaultPictureImpl(context, firstConnectingSettingModelcallBack, firebaseHelper);
    }

    @Provides
    UserProfilModelCallBack provideUserProfilModelCallBack(@ActivityContext Context context){
        return new UserProfilPresenterImpl(context, null);
    }

    @Provides
    FirstConnectingSettingModelcallBack provideFirstConnectingSettingModelcallBack(@ActivityContext Context context){
        return new FirstConnectingSettingPresenterImpl(context, null);
    }
}
