package com.example.emili.firstapp.dagger;

import com.example.emili.firstapp.ui.signInActivity.SignInActivity;
import com.example.emili.firstapp.ui.signInActivity.SignInPresenterImpl;
import com.example.emili.firstapp.ui.signInActivity.SignInUserImpl;

import dagger.Component;

/**
 * Created by emili on 23/10/2017.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = SignInActivityModule.class)
public interface SignInActivityComponent {
    void inject(SignInActivity signInActivity);
    void inject(SignInPresenterImpl signInPresenter);
    void inject(SignInUserImpl signInUser);
}