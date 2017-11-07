package com.example.emili.firstapp.ui.profilActivity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.emili.firstapp.R;
import com.example.emili.firstapp.app.ChatApplication;
import com.example.emili.firstapp.dagger.DaggerMainActivityComponent;
import com.example.emili.firstapp.dagger.DaggerProfilActivityComponent;
import com.example.emili.firstapp.dagger.MainActivityComponent;
import com.example.emili.firstapp.dagger.MainActivityModule;
import com.example.emili.firstapp.dagger.ProfilActivityComponent;
import com.example.emili.firstapp.dagger.ProfilActivityModule;
import com.example.emili.firstapp.data.FirebaseHelper;
import com.example.emili.firstapp.ui.chatActivity.ChatActivity;
import com.example.emili.firstapp.ui.preferencesActivity.PreferencesActivity;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.profil_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();
        switch (id){
            case R.id.action_setting:
                goToSettingActivity();
                break;

            case R.id.action_Chat:
                goToChatActivity();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void goToChatActivity() {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    private void goToSettingActivity() {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
    }
}
