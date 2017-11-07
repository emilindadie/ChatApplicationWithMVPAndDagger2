package com.example.emili.firstapp.notification;

import android.content.Context;

/**
 * Created by emili on 02/11/2017.
 */

public class ActionsNotifies {


    public static final String ACTION_ANSWER_MESSAGE_NOTIFICATION = "answer_message_notification";

    public static final String ACTION_DISMISS_MESSAGE_NOTIFICATION ="dismiss_message_notification";


    public static void executeTask(Context context, String action){

        if (action.equals(ACTION_ANSWER_MESSAGE_NOTIFICATION)){
            compter(context);

        }else if(action.equals(ACTION_DISMISS_MESSAGE_NOTIFICATION)){

        }
    }


    private static void compter(Context context){


    }
}
