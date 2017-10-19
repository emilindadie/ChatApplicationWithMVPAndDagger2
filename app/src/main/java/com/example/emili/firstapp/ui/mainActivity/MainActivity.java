package com.example.emili.firstapp.ui.mainActivity;

import android.app.Application;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emili.firstapp.R;
import com.example.emili.firstapp.app.ChatApplication;
import com.example.emili.firstapp.dagger.PresenterModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SignUpView{

    @BindView(R.id.firstName)
    EditText firstName;

    @BindView(R.id.lastName)
    EditText lastName;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.password1)
    EditText password1;

    @BindView(R.id.password2)
    EditText password2;

    @BindView(R.id.inscriptionButton)
    Button inscription;

    @Inject
    SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ChatApplication.app().getAppComponent().inject(this);

        signUpPresenter.setView(this);

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUser();
            }
        });
    }

    private void signUser(){
        if(firstName.getText().length() > 0 && lastName.getText().length() > 0 && email.getText().length() > 0 &&
                password1.getText().length() > 0 && password2.getText().length() >0){

            if(password1.getText().equals(password2.getText())){
                String userFirstName = firstName.getText().toString();
                String userLastName = lastName.getText().toString();
                String userEmail = email.getText().toString();
                String password = password1.getText().toString();
                signUpPresenter.createUser(userFirstName, userLastName, userEmail, password);
            }
            else {
                Toast.makeText(this, "Les deux mot de passes ne correspondent pas !", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this, "Tous les champs doivent etre remplis", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showErrorSignUp() {
        Toast.makeText(this, "inscription échoué", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccessSinUp() {
        Toast.makeText(this, "inscription réussite", Toast.LENGTH_LONG).show();
    }
}

