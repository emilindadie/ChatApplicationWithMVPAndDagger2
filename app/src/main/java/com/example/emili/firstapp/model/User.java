package com.example.emili.firstapp.model;

/**
 * Created by emili on 15/10/2017.
 */

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String ID;
    private boolean firstConnecting = false;


    public User(){
    }

    public User(String firstName, String lastName, String email, boolean firstConnecting){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.firstConnecting = false;
    }



    public User(String ID, String firstName, String lastName, String email, boolean firstConnecting){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.ID = ID;
        this.firstConnecting = false;
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
}
