package com.example.emili.firstapp.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import com.example.emili.firstapp.R;
import com.example.emili.firstapp.ui.mainActivity.MainActivity;

/**
 * Created by emili on 30/10/2017.
 */

public class NotificationUtils {
    private static final String CHAT_MESSAGE_REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";
    private static final int CHAT_MESSAGE_REMINDER_NOTIFICATION__ID = 7;
    private static final int CHAT_MESSAGE_REMINDER_PENDING_INTENT_ID = 1994;

    private static PendingIntent contentIntent(Context context){
        Intent intent= new Intent(context, MainActivity.class);
        return PendingIntent.getActivities(context, CHAT_MESSAGE_REMINDER_PENDING_INTENT_ID, new Intent[]{intent},PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap largeIcon(Context context){
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.anonyme);
        return largeIcon;
    }

    public static void remindUserBecauseCharging(Context context, String title, String text) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHAT_MESSAGE_REMINDER_NOTIFICATION_CHANNEL_ID, context.getString(R.string.main_notification_channel_name), NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.anonyme).setLargeIcon(largeIcon(context))
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(
                        R.string.charging_reminder_notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE).setContentIntent(contentIntent(context)).setAutoCancel(true);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(CHAT_MESSAGE_REMINDER_NOTIFICATION__ID, notificationBuilder.build());
    }
}
