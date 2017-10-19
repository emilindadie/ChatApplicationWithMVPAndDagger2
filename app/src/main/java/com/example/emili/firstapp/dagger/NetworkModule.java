package com.example.emili.firstapp.dagger;

import android.content.Context;

import com.example.emili.firstapp.app.ChatApplication;
import com.example.emili.firstapp.network.CreateUserService;
import com.example.emili.firstapp.ui.mainActivity.CreateUserImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by emili on 15/10/2017.
 */


@Module
public class NetworkModule {

    private Context context;

    @Provides
    @Named("application.context")
    Context provideContext(){
        return ChatApplication.app();
    }

    @Provides
    @Singleton
    FirebaseDatabase firebaseDatabase(){
        return FirebaseDatabase.getInstance();
    }

    @Provides
    @Singleton
    DatabaseReference databaseReference(FirebaseDatabase firebaseDatabase){
        firebaseDatabase = FirebaseDatabase.getInstance();
        return firebaseDatabase.getReference();
    }

    @Provides
    @Singleton
    FirebaseAuth firebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    CreateUserService createUserService(Context context){
        return new CreateUserImpl(context);
    }
}
