package com.example.iceb;

import android.app.Notification;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class Mymessaging extends FirebaseMessagingService {
   // DatabaseReference databaseReference;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        shownotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

    }

    public void shownotification(String title, String message){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"REAL")

                .setSmallIcon(R.mipmap.iceb)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setVibrate(new long[]{200, 200,200,200,200})


                .setPriority(NotificationCompat.PRIORITY_HIGH)


                .setAutoCancel(true);
        NotificationManagerCompat notificationManager= NotificationManagerCompat.from(this);


        notificationManager.notify(500,builder.build());


    }
}

