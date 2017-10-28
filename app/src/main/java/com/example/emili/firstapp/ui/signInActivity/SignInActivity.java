package com.example.emili.firstapp.ui.signInActivity;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emili.firstapp.R;
import com.example.emili.firstapp.app.ChatApplication;
import com.example.emili.firstapp.dagger.DaggerSignInActivityComponent;
import com.example.emili.firstapp.dagger.SignInActivityComponent;
import com.example.emili.firstapp.dagger.SignInActivityModule;
import com.example.emili.firstapp.ui.profilActivity.ProfilActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity implements SignInView{

    //@BindView(R.id.email)
    EditText email;

    //@BindView(R.id.password)
    EditText password;

    //@BindView(R.id.signInButton)
    Button signInButton;

    @Inject
    SignInPresenter signInPresenter;
    private SignInActivityComponent signInActivityComponent;

    public SignInActivityComponent getSignInActivityComponent(){
        if(signInActivityComponent == null){
            signInActivityComponent = DaggerSignInActivityComponent.builder()
                    .signInActivityModule(new SignInActivityModule(this))
                    .applicationComponent(ChatApplication.get(this).getApplicationComponent())
                    .build();
        }
        return signInActivityComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Bind view
        //ButterKnife.bind(this);

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signInButton = (Button) findViewById(R.id.signInButton);

        getSignInActivityComponent().inject(this);
        signInPresenter.setView(this);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUser();
            }
        });
    }

    private void signInUser(){
        if(email.getText().length() > 0 && password.getText().length() > 0){
            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();
            signInPresenter.signInUser(this, userEmail, userPassword);
        }
        else {
            Toast.makeText(this, "Tous les champs doivent etre remplis", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void goToProfilActivity() {
        Intent intent = new Intent(this, ProfilActivity.class);
        intent.putExtra("firstConnecting", false);
        startActivity(intent);
    }

    @Override
    public void goToFirstSignInActivity() {
        Intent intent = new Intent(this, ProfilActivity.class);
        intent.putExtra("firstConnecting", true);
        startActivity(intent);
    }

    @Override
    public void showSignInError() {
    Toast.makeText(this, "Adresse email ou mot de passe inconnu", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();
        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
