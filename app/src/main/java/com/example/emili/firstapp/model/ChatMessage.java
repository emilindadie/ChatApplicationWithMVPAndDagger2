package com.example.emili.firstapp.model;

/**
 * Created by emili on 26/10/2017.
 */

public class ChatMessage {

    private String auteur;
    private String text = "";
    private String photoUrl;
    private double longitude;
    private double latitude;
    private String userid;
    private boolean notified = false;

    public ChatMessage() {
    }

    public ChatMessage(String auteur, String text, String photoUrl, String userid, boolean notified) {
        this.auteur = auteur;
        this.text = text;
        this.photoUrl = photoUrl;
        this.userid = userid;
        this.notified = notified;
    }

    public ChatMessage(String auteur, String text, String photoUrl, double longitude, double latitude, String userid, boolean notified) {
        this.auteur = auteur;
        this.text = text;
        this.photoUrl = photoUrl;
        this.longitude = longitude;
        this.latitude = latitude;
        this.userid = userid;
        this.notified = notified;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getText() {
        return text;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public boolean hasLongitude() {
        return longitude != 0;
    }

    public boolean hasLatitude() {
        return latitude != 0;
    }

    public boolean hasText() {
        return !text.equals("");
    }

    public String getUserid() {
        return userid;
    }


    public boolean isNotified() {
        return notified;
    }

}
