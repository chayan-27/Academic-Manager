package com.example.iceb;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Base64;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Mymessaging extends FirebaseMessagingService {
   // DatabaseReference databaseReference;
   SharedPreferences sharedPreferences;
    Set<String> set;
    List<String> list=new ArrayList<>();
    String s;
    String ima;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String, String> data = remoteMessage.getData();
        int c=0;
        String title=data.get("title");
        String body=data.get("body");
        ima=data.get("image");
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
        sharedPreferences= getSharedPreferences("App_settings", MODE_PRIVATE);
       set = sharedPreferences.getStringSet("DATE_LIST", null);
        if(set!=null) {


               list.addAll(set);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        s=body+"$"+currentDate+"$"+currentTime;

        if(set!=null) {
            list.add(set.size() + " " + s);
        }else {
            list.add(0+" "+s);
        }

        set = new TreeSet<String>();
        set.addAll(list);
        editor.putStringSet("DATE_LIST", set);
        editor.apply();
       // String url=remoteMessage.getNotification().getLink().toString();
       // if(url!=null) {
        shownotification(title,body);
           // shownotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
      //  }else{
          //  shownotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(),"");

      //  }

    }

    public void shownotification(String title, String message){
        byte[] decodedString = Base64.decode(ima, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        boolean b;
        if(ima.equals("no")){
            b=true;
        }else
        {
            b=false;
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "REAL");


       // Bitmap bitmap=BitmapFactory.decodeResource(this.getResources(),R.drawable.assignmentic1);
        if(b==false) {

                    builder.setSmallIcon(R.mipmap.iceb)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))

                    .setVibrate(new long[]{200, 200, 200, 200, 200})
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(decodedByte).setSummaryText(message))


                    .setPriority(NotificationCompat.PRIORITY_HIGH)



                    .setAutoCancel(true);
        }else{
           // NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "REAL")

                   builder .setSmallIcon(R.mipmap.iceb)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))

                    .setVibrate(new long[]{200, 200, 200, 200, 200})



                    .setPriority(NotificationCompat.PRIORITY_HIGH)


                    .setAutoCancel(true);
        }
        NotificationManagerCompat notificationManager= NotificationManagerCompat.from(this);
        notificationManager.notify(500,builder.build());
    }
}

