package com.example.iceb;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_OVERLAY_PERMISSION = 1 ;
    int first=0;
    String extension;






    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //ImageButton btn=(ImageButton)findViewById(R.id.imageButton);
        TextView textView=(TextView)findViewById(R.id.textView);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.transition);
        textView.startAnimation(animation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    "REAL",
                    "NotificationReal",
                    IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager1 = getSystemService(NotificationManager.class);
            // assert notificationManager1 != null;
            notificationManager1.createNotificationChannel(channel1);
        }
       /* FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successfull";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }


                    }
                });
        FirebaseMessaging.getInstance().subscribeToTopic("test").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });*/
        final SharedPreferences myuser=this.getSharedPreferences("Myapp2", Context.MODE_PRIVATE);
        first=myuser.getInt("first",0);

        if(Objects.equals(getIntent().getAction(), Intent.ACTION_SEND)){
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // String type = intent.getType();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
             extension=mime.getExtensionFromMimeType(intent.getType());
            Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    InputStream inputStream = null;
                    try {
                        assert uri != null;
                        inputStream = getApplicationContext().getContentResolver().openInputStream(uri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    try {
                        saveFile(getBytes(inputStream),extension);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        }





       /* btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification();
                Intent intent=new Intent(MainActivity.this,Information.class);
                startActivity(intent);

            }
        });*/
        Intent intent=new Intent(MainActivity.this,Information.class);

       Thread timer=new Thread(){
           @RequiresApi(api = Build.VERSION_CODES.KITKAT)
           public void run(){
               try {
                   sleep(1000);
               }catch (InterruptedException e) {
                   e.printStackTrace();
               }
               finally {
                   if(first!=0) {
                       intent.putExtra("extension",extension);

                       startActivity(intent);
                   }else{
                       Intent intent1=new Intent(MainActivity.this,Funny.class);
                       first=1;
                       SharedPreferences.Editor editor=myuser.edit();
                       editor.putInt("first",first);
                       editor.apply();
                       startActivity(intent1);
                   }



                   finish();

               }
           }
       };
       timer.start();
    }

    public void saveFile(byte[] decodedString,String extension) {
        String path = "Last Shared File";
        String name = "/" + "Transact" + "."+extension;
        File root = new File(Objects.requireNonNull(this.getExternalFilesDir(path)).getAbsolutePath() + name);
        try {
            OutputStream fileOutputStream = new FileOutputStream(root);
            fileOutputStream.write(decodedString);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

}
