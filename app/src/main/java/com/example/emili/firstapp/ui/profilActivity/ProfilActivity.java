package com.example.emili.firstapp.ui.profilActivity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.emili.firstapp.R;
import com.example.emili.firstapp.app.ChatApplication;
import com.example.emili.firstapp.dagger.DaggerMainActivityComponent;
import com.example.emili.firstapp.dagger.DaggerProfilActivityComponent;
import com.example.emili.firstapp.dagger.MainActivityComponent;
import com.example.emili.firstapp.dagger.MainActivityModule;
import com.example.emili.firstapp.dagger.ProfilActivityComponent;
import com.example.emili.firstapp.dagger.ProfilActivityModule;
import com.example.emili.firstapp.data.FirebaseHelper;
import com.example.emili.firstapp.ui.profilActivity.firstConnectingFragment.FirstConnectingFragment;
import com.example.emili.firstapp.ui.profilActivity.profilActivityFragment.ProfilActivityFragment;

import javax.inject.Inject;

public class ProfilActivity extends AppCompatActivity implements FirstConnectingFragment.OnFragmentInteractionListener, ProfilActivityFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Intent intent = getIntent();
        boolean extraResult = intent.getBooleanExtra("firstConnecting", true);

        if(extraResult){
            getSupportFragmentManager().beginTransaction().add(R.id.frameLayoutProfilActivity, new FirstConnectingFragment()).commit();

        }else {
            getSupportFragmentManager().beginTransaction().add(R.id.frameLayoutProfilActivity, new ProfilActivityFragment()).commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
