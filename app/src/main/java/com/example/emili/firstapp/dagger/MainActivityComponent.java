package com.example.emili.firstapp.dagger;

import com.example.emili.firstapp.ui.mainActivity.CreateUserImpl;
import com.example.emili.firstapp.ui.mainActivity.MainActivity;
import com.example.emili.firstapp.ui.mainActivity.SignUpPresenterImpl;

import dagger.Component;

/**
 * Created by emili on 21/10/2017.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(SignUpPresenterImpl signUpPresenter);
    void inject(CreateUserImpl createUser);
}