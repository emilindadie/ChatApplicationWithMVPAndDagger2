package com.example.emili.firstapp.model;

import android.net.Uri;

/**
 * Created by emili on 15/10/2017.
 */

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String ID;
    private boolean firstConnecting = false;
    private String profilPicture;

    public User(){
    }

    public User(String firstName, String lastName, String email, boolean firstConnecting, String profilPicture){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.firstConnecting = firstConnecting;
        this.profilPicture = profilPicture;
    }

    public User(String ID, String firstName, String lastName, String email, boolean firstConnecting, String profilPicture){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.ID = ID;
        this.firstConnecting = firstConnecting;
        this.profilPicture = profilPicture;
    }

    public String getID(){
        return ID;
    }
    public String getFirstName(){
        return  firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getEmail(){
        return email;
    }

    public boolean getFirstConnecting(){
        return firstConnecting;
    }

    public String getProfilPicture(){
        return profilPicture;
    }
}
