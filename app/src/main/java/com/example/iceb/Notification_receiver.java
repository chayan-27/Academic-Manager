package com.example.iceb;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import static androidx.core.content.ContextCompat.getSystemService;

public class Notification_receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      // NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent repeating_intent=new Intent(context,MainActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       PendingIntent pendingIntent=PendingIntent.getActivity(context,100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"test")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ice)
                .setContentTitle("Check you progress!!!")
                .setContentText("Don't forget to update your Attendance")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager= NotificationManagerCompat.from(context);


        notificationManager.notify(100,builder.build());
    }
}

