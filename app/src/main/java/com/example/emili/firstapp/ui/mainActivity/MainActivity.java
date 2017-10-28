package com.example.emili.firstapp.ui.mainActivity;

import android.content.Intent;
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
import com.example.emili.firstapp.dagger.MainActivityModule;
import com.example.emili.firstapp.dagger.DaggerMainActivityComponent;
import com.example.emili.firstapp.data.FirebaseHelper;
import com.example.emili.firstapp.dagger.MainActivityComponent;
import com.example.emili.firstapp.ui.signInActivity.SignInActivity;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements SignUpView{

    //@BindView(R.id.firstName)
    EditText firstName;

    //@BindView(R.id.lastName)
    EditText lastName;

    //@BindView(R.id.email)
    EditText email;

   //@BindView(R.id.password1)
    EditText password1;

    //@BindView(R.id.password2)
    EditText password2;

   // @BindView(R.id.signUpButton)
    Button signUpButton;

    private MainActivityComponent mainActivityComponent;

    @Inject
    FirebaseHelper firebaseHelper;

    @Inject
    SignUpPresenter signUpPresenter;

    public  MainActivityComponent getMainActivityComponent(){
        if (mainActivityComponent == null){

            mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this))
                .applicationComponent(ChatApplication.get(this).getApplicationComponent())
                .build();
        }
        return mainActivityComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        password1 = (EditText) findViewById(R.id.password1);
        password2 = (EditText) findViewById(R.id.password2);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        //Bind view
       // ButterKnife.bind(this);
        getMainActivityComponent().inject(this);
        signUpPresenter.setView(this);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUser();
            }
        });
    }

    private void signUser(){
        if(firstName.getText().length() > 0 && lastName.getText().length() > 0 && email.getText().length() > 0 &&
                password1.getText().length() > 0 && password2.getText().length() >0){

            if(password1.getText().toString().equals(password2.getText().toString())){
                String userFirstName = firstName.getText().toString();
                String userLastName = lastName.getText().toString();
                String userEmail = email.getText().toString();
                String password = password1.getText().toString();
                signUpPresenter.createUser(this,userFirstName, userLastName, userEmail, password);
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
    public void showSuccessSignUp() {
        Toast.makeText(this, "inscription réussite", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();
        switch (id){
            case R.id.action_signIn:
                goToSignInActivity();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void goToSignInActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}

