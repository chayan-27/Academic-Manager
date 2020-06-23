package com.example.iceb;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.IDNA;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class Information extends AppCompatActivity {

    public static String rngot;
    String pdgot;
    public static String name;
    public static int del;
   // public static byte[] nii;
    public static int flag;
    public static int flag2=1;
    ProgressBar progressBar;

int x;
public static int t=0;
FirebaseAuth mFirebaseAuth;
private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
        TextView t1=(TextView)findViewById(R.id.textView4);
        TextView t2=(TextView)findViewById(R.id.textView3);
        mFirebaseAuth=FirebaseAuth.getInstance();

        progressBar=(ProgressBar)findViewById(R.id.logi);
       final SharedPreferences myuser=this.getSharedPreferences("Myapp", Context.MODE_PRIVATE);
        x=myuser.getInt("sign",0);

        if(x==1&&t!=10)
        {
            t1.setVisibility(View.VISIBLE);
            t2.setVisibility(View.VISIBLE);

        }
        if(t==10)
        {
            x=0;
            SharedPreferences.Editor editor=myuser.edit();
            editor.putInt("sign",x);
            editor.commit();
        }

        t1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                x=1;
                t=0;
                SharedPreferences.Editor editor=myuser.edit();
                editor.putInt("sign",x);
                editor.commit();

                Intent intent=new Intent(Information.this,InformActivity.class);
                startActivity(intent);

            }
        });
        // myuser = this.getSharedPreferences("Myapp", Context.MODE_PRIVATE);
        //SharedPreferences myuser = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //final SharedPreferences myuser=this.getSharedPreferences("Myapp", Context.MODE_PRIVATE);
        final CheckBox checkBox=(CheckBox)findViewById(R.id.checkBox);
        if(checkBox.isChecked()) {

            SharedPreferences.Editor editor=myuser.edit();
            editor.putInt("flag",flag2);
            editor.commit();
            flag2=1;


            flag=myuser.getInt("flag",0);


            rngot = myuser.getString("username", "");
            pdgot = myuser.getString("password", "");
            EditText roll_no=(EditText)findViewById(R.id.editText);
            EditText pass=(EditText)findViewById(R.id.editText3);
            roll_no.setText(rngot);
            pass.setText(pdgot);

            if(!(rngot.equals(""))&&flag==1)
            {
                 roll_no=(EditText)findViewById(R.id.editText);
                pass=(EditText)findViewById(R.id.editText3);
                String rn=roll_no.getText().toString();
                String pwd=pass.getText().toString();

                if(!(rn.isEmpty()||pwd.isEmpty())){
                    if(isNetworkConnected()){
                        progressBar.setVisibility(View.VISIBLE);


                    mFirebaseAuth.signInWithEmailAndPassword(rn,pwd).addOnCompleteListener(Information.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!(task.isSuccessful())){
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast toast = Toast.makeText(Information.this, "Error Occurred,Please Login Again!! ", Toast.LENGTH_LONG);
                                toast.show();
                            }else{
                                progressBar.setVisibility(View.INVISIBLE);
                                Intent intent=new Intent(Information.this,Testingg.class);
                                intent.putExtra("roll",rn);
                                startActivity(intent);

                                //finish();
                            }
                        }
                    });}else{
                        progressBar.setVisibility(View.INVISIBLE);
                    Toast toast = Toast.makeText(Information.this, "No Internet Connection ", Toast.LENGTH_LONG);
                    toast.show();
                }}



                SQLiteOpenHelper dtb = new StudentDTB(Information.this);

                try
                {
                    SQLiteDatabase db=dtb.getReadableDatabase();
                    Cursor cursor = db.query ("USERS",
                            new String[] {"_id","ROLL_NO", "PASSWORD","NAME"},
                            "ROLL_NO = ?",
                            new String[] {rn},
                            null, null,null);

                    if(cursor.moveToFirst()) {
                        del=cursor.getInt(0);
                        rngot = cursor.getString(1);
                        pdgot = cursor.getString(2);
                        name=cursor.getString(3);
                        //nii=cursor.getBlob(4);
                        flag=1;
                        flag2=1;
                        if (rngot.equals(rn) && pdgot.equals(pwd)) {


                            if(checkBox.isChecked()) {

                                /* editor = myuser.edit();
                                editor.putString("username", rngot);
                                editor.putString("password", pdgot);
                                editor.putInt("flag",flag);
                                editor.commit();
                                System.out.println("success");*/
                            }else
                            {
                               /* editor=myuser.edit();
                                editor.putString("username","");
                                editor.putString("password","");
                                editor.putInt("flag",0);
                                editor.commit();*/
                            }

                          /*  Intent intent=new Intent(Information.this,Login_screen.class);
                            startActivity(intent);*/
                        } else {
                           /* Toast toast = Toast.makeText(Information.this, "Error Occurred,Please Login With Correct Details!! ", Toast.LENGTH_LONG);
                            toast.show();
                            System.out.println("failure");*/
                        }
                    }else
                    {

                       /*  db=dtb.getReadableDatabase();
                        Cursor newCursor=db.query ("USERS",
                                new String[] {"ROLL_NO", "PASSWORD"},
                                "ROLL_NO = ?",
                                new String[] {rn},
                                null, null,null);
                        cursor=newCursor;*/


                    }


                    cursor.close();
                    db.close();



                }catch(SQLiteException e) {
                    Toast toast = Toast.makeText(Information.this, "Invalid Username or Password", Toast.LENGTH_LONG);
                    toast.show();
                }


            }
        }







        Button login=(Button)findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText roll_no=(EditText)findViewById(R.id.editText);
                EditText pass=(EditText)findViewById(R.id.editText3);


                String rn=roll_no.getText().toString();
                String pwd=pass.getText().toString();

                if(!(rn.isEmpty()||pwd.isEmpty()&&isNetworkConnected())){
                    progressBar.setVisibility(View.VISIBLE);

                mFirebaseAuth.signInWithEmailAndPassword(rn,pwd).addOnCompleteListener(Information.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!(task.isSuccessful())){
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast toast = Toast.makeText(Information.this, "Error Occurred,Please Login Again!! ", Toast.LENGTH_LONG);
                            toast.show();
                        }else{

                            if(mFirebaseAuth.getCurrentUser().isEmailVerified()) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(Information.this, Testingg.class);
                                intent.putExtra("roll", rn);
                                flag=1;
                                if(checkBox.isChecked()) {

                                    SharedPreferences.Editor editor = myuser.edit();
                                    editor.putString("username", rn);
                                    editor.putString("password", pwd);
                                    editor.putInt("flag",flag);
                                    editor.commit();
                                    System.out.println("success");
                                }else{
                                    SharedPreferences.Editor editor=myuser.edit();
                                    editor=myuser.edit();
                                    editor.putString("username","");
                                    editor.putString("password","");
                                    editor.putInt("flag",0);
                                    editor.commit();
                                }
                                startActivity(intent);
                                finish();

                            }else {
                                progressBar.setVisibility(View.INVISIBLE);
                                mFirebaseAuth.getInstance().signOut();
                                Toast toast = Toast.makeText(Information.this, "E-mail not verified ", Toast.LENGTH_LONG);
                                toast.show();
                            }

                            //finish();
                        }
                    }
                });}else{
                    if(!isNetworkConnected()){
                        Toast toast = Toast.makeText(Information.this, "No Internet Connection ", Toast.LENGTH_LONG);
                        toast.show();
                    }else{
                        Toast toast = Toast.makeText(Information.this, "Invalid Username or Password", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }



                SQLiteOpenHelper dtb = new StudentDTB(Information.this);

                try
                {
                    SQLiteDatabase db=dtb.getReadableDatabase();
                     Cursor cursor = db.query ("USERS",
                            new String[] {"_id","ROLL_NO", "PASSWORD","NAME"},
                            "ROLL_NO = ?",
                            new String[] {rn},
                            null, null,null);

                    if(cursor.moveToFirst()) {
                        rngot = cursor.getString(1);
                        pdgot = cursor.getString(2);
                        name=cursor.getString(3);
                        //nii=cursor.getBlob(4);
                      //  flag=1;
                        if (rngot.equals(rn) && pdgot.equals(pwd)) {

                            if(checkBox.isChecked()) {

                               /* SharedPreferences.Editor editor = myuser.edit();
                                editor.putString("username", rngot);
                                editor.putString("password", pdgot);
                                editor.putInt("flag",flag);
                                editor.commit();
                                System.out.println("success");*/
                            }else
                            {
                               /* SharedPreferences.Editor editor=myuser.edit();
                                editor.putString("username","");
                                editor.putString("password","");
                                editor.putInt("flag",0);
                                editor.commit();*/
                            }

                           /* Intent intent=new Intent(Information.this,Login_screen.class);
                            startActivity(intent);*/
                        } else {
                          /*  Toast toast = Toast.makeText(Information.this, "Error Occurred,Please Login With Correct Details!!", Toast.LENGTH_LONG);
                            toast.show();*/
                            System.out.println("failure");
                        }
                    }else
                    {

                       /*  db=dtb.getReadableDatabase();
                        Cursor newCursor=db.query ("USERS",
                                new String[] {"ROLL_NO", "PASSWORD"},
                                "ROLL_NO = ?",
                                new String[] {rn},
                                null, null,null);
                        cursor=newCursor;*/


                    }
                    cursor.close();
                    db.close();
                }catch(SQLiteException e) {
                    Toast toast = Toast.makeText(Information.this, "Invalid Username or Password", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

        });

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void motivate()
    {
        AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent1 = new Intent(this, AlarmReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 200, notificationIntent1, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,7);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);


        //assert alarmManager1 != null;
        alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY, broadcast);
    }
    public void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name="Attendance";
            String description="Channel for Attendance";
            int importance= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel=new NotificationChannel("test",name,importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager =getSystemService(NotificationManager.class);
           // assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
    public void notification()
    {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,21);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        Intent intent=new Intent(getApplicationContext(),Notification_receiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
       // assert alarmManager != null;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }
    @Override
    public void onPause() {

        super.onPause();

    }
    @Override
    public void onStop() {

        super.onStop();
        finish();

    }


    public void fffgg(View view) {

        Intent intent=new Intent(Information.this,Forgetpass.class);
        startActivity(intent);

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void webmail(View view){
        Intent intent=new Intent(Information.this,Webmail.class);
        startActivity(intent);
    }







    }

