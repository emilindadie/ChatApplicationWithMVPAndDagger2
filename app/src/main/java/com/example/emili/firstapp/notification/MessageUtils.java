package com.example.emili.firstapp.notification;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by emili on 02/11/2017.
 */

public class MessageUtils {


    public static void compterMessage(Context context, String message){

        int longuer = 0;
        for(int i =0; i< message.length(); i++){

            if(i == message.length()  -1){
                longuer = message.length() -1;
            }
        }

        Toast.makeText(context, "la longuer du message est de "+longuer , Toast.LENGTH_LONG).show();
    }
}
