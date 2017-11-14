package com.example.emili.firstapp.adapter;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.emili.firstapp.R;
import com.example.emili.firstapp.data.GPSHelper;
import com.example.emili.firstapp.model.ChatMessage;
import com.example.emili.firstapp.model.User;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by emili on 28/10/2017.
 */

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    int taille;
    LayoutInflater inflater;
    List<ChatMessage> messageList;
    GPSHelper gpsHelper;
    User user;

    public ChatMessageAdapter(Context context, User user, List<ChatMessage> messageList, GPSHelper gpsHelper){

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.messageList = messageList;
        this.gpsHelper = gpsHelper;
        this.user = user;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.chat_message, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder holderl = (MyHolder) holder;
        ChatMessage current = messageList.get(position);
        if(current.hasText()){
            holderl.message.setText(current.getText());
        }else {
            holderl.message.setVisibility(View.INVISIBLE);
        }

        holderl.nom.setText(current.getAuteur());
        if(current.getPhotoUrl() != null){
            Glide.with(context)
                    .load(current.getPhotoUrl())
                    .override(100, 100)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holderl.imageView);
        }else {
            holderl.imageView.setVisibility(View.INVISIBLE);
        }

        if(current.hasLatitude() && current.hasLongitude()){

            if(("tyryrt").equals(current.getUserid())){
                holderl.distance.setVisibility(View.INVISIBLE);
            }else {
                double longitude = current.getLongitude();
                double latitude = current.getLatitude();
                float distance = getDistance(context, longitude, latitude);
                holderl.distance.setText(String.valueOf(String.format("%.1f" +distance+" km")));
            }
        }else {
            holderl.distance.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        taille = messageList.size();
        return taille;
    }

    public void clear() {
        messageList.clear();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView message;
        TextView nom;
        ImageView imageView;
        TextView distance;

        public MyHolder(View itemView) {
            super(itemView);

            message = (TextView) itemView.findViewById(R.id.message_text);
            nom = (TextView) itemView.findViewById(R.id.auteur);
            imageView = (ImageView) itemView.findViewById(R.id.messagePicture) ;
            distance = (TextView) itemView.findViewById(R.id.distances);
        }
    }


    public void ajouterTous(List<ChatMessage> messageList){

        this.messageList = messageList;
    }


    private float getDistance(Context context, final double Platitude, final double Plongitude) {
        float distance = 0;
        Location current = gpsHelper.getMyPosition();
        if(current == null){
            Toast.makeText(context,"GPS unable to get Value",Toast.LENGTH_SHORT).show();
        }else {

            Location stoot = new Location("Location stoot");
            stoot.setLatitude(Platitude);
            stoot.setLongitude(Plongitude);

            distance = current.distanceTo(stoot) / 1000;
        }
        return distance;
    }

    public void addMessage(ChatMessage message){
        this.messageList.add(message);
    }
}

