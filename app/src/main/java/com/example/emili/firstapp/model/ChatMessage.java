package com.example.emili.firstapp.model;

/**
 * Created by emili on 26/10/2017.
 */

public class ChatMessage {

    private String auteur;
    private String text;
    private String photoUrl;
    private double longitude;
    private double latitude;

    public ChatMessage(){
    }


    public ChatMessage(String auteur, String text, String photoUrl){
        this.auteur = auteur;
        this.text = text;
        this.photoUrl = photoUrl;
    }

    public ChatMessage(String auteur, String text, String photoUrl, double longitude, double latitude){
        this.auteur = auteur;
        this.text = text;
        this.photoUrl = photoUrl;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getAuteur(){
        return auteur;
    }

    public String getText(){
        return text;
    }

    public String getPhotoUrl(){
        return photoUrl;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }


}
