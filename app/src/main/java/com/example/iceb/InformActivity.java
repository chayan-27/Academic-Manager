package com.example.iceb;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

public class InformActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    ImageView img;
    public static Uri selectedimage;
    int c = 0;
    public String check = "Hello";
    public static int x;
    ProgressBar progressBar;

    /* @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if(data!=null && requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK) {
              selectedimage = data.getData();
             img.setImageURI(selectedimage);
             c=1;
         }
     }*/
    EditText name;
    EditText rollno;
    EditText password;
    EditText mobile;
    EditText lastname;
    Button btn;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inform);
        name = (EditText) findViewById(R.id.editText2);
        rollno = (EditText) findViewById(R.id.editText5);
        password = (EditText) findViewById(R.id.editText6);
        mobile = (EditText) findViewById(R.id.editText7);
        lastname = (EditText) findViewById(R.id.editText9);
        mFirebaseAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.situ);


       /* img = (ImageView) findViewById(R.id.imageView);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryintent, RESULT_LOAD_IMAGE);

            }
        });*/


        // EditText mobile = (EditText) findViewById(R.id.editText7);

        //getting details
        btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   /* EditText name = (EditText) findViewById(R.id.editText2);
                    EditText rollno = (EditText) findViewById(R.id.editText5);
                    EditText password = (EditText) findViewById(R.id.editText6);
                    EditText mobile=(EditText)findViewById(R.id.editText7);*/
                    /*ImageView dp = (ImageView) findViewById(R.id.imageView);

                    Bitmap photo = ((BitmapDrawable) dp.getDrawable()).getBitmap();
                    photo = Bitmap.createScaledBitmap(photo, 200, 150, true);

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 0, bos);
                    byte[] bArray = bos.toByteArray();*/
                if (isNetworkConnected()) {


                    try {
                        String name1 = name.getText().toString() + " " + lastname.getText().toString();
                        String rollno1 = rollno.getText().toString() + "@nitt.edu";
                        String password1 = password.getText().toString();
                        String mobile1 = mobile.getText().toString();

                        int rn1 = 0;


                        String usr = rollno1.substring(0, 9);
                        String iu = rollno1.substring(9);
                        rn1 = Integer.parseInt(usr);
                        System.out.println(rn1);


                        // int rn1 = Integer.parseInt(rollno1);

                        if (!(name1.equals("")) && (rn1 >= 110119001 && rn1 <= 110119129) && (iu.equals("@nitt.edu"))) {
                            if ((mobile1.equals(password1)) && mobile1.length() >= 6) {


                                SQLiteOpenHelper dtb = new StudentDTB(InformActivity.this);
                                SQLiteDatabase db = dtb.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                // values.put("IMAGE_RESOURCE_ID", bArray);
                                values.put("NAME", name1);
                                values.put("ROLL_NO", rollno1);
                                values.put("PASSWORD", password1);
                                values.put("MOBILE", mobile1);
                                db.insert("USERS", null, values);
                                progressBar.setVisibility(View.VISIBLE);
                                mFirebaseAuth.createUserWithEmailAndPassword(rollno1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if ((task.isSuccessful())) {
                                            User user = new User(name1);
                                            FirebaseDatabase.getInstance().getReference("Users")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(user);
                                            List<String> p = new ArrayList<>();
                                            p.add("DATE\nTIME");
                                            List<String> ab = new ArrayList<>();
                                            ab.add("DATE\nTIME");
                                            Statistics statistics = new Statistics(p, ab);

                                            Subjects subjects = new Subjects(0, 0, 0);
                                            Subjects subjects1 = new Subjects(0, 0, 0);
                                            Subjects subjects2 = new Subjects(0, 0, 0);
                                            Subjects subjects3 = new Subjects(0, 0, 0);
                                            Subjects subjects4 = new Subjects(0, 0, 0);
                                            for (int i = 0; i < 50; i++) {
                                                FirebaseDatabase.getInstance().getReference("Subject" + i)
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .setValue(subjects);
                                            }
                                            for (int i = 0; i < 50; i++) {
                                                FirebaseDatabase.getInstance().getReference("Subject" + i)
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .child("Statistics").setValue(statistics);
                                            }
                                            /*FirebaseDatabase.getInstance().getReference("Subject0")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(subjects);
                                            FirebaseDatabase.getInstance().getReference("Subject1")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(subjects1);
                                            FirebaseDatabase.getInstance().getReference("Subject2")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(subjects2);
                                            FirebaseDatabase.getInstance().getReference("Subject3")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(subjects3);
                                            FirebaseDatabase.getInstance().getReference("Subject4")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(subjects4);
                                            FirebaseDatabase.getInstance().getReference("Subject0")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .child("Statistics").setValue(statistics);
                                            FirebaseDatabase.getInstance().getReference("Subject1")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .child("Statistics").setValue(statistics);
                                            FirebaseDatabase.getInstance().getReference("Subject2")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .child("Statistics").setValue(statistics);
                                            FirebaseDatabase.getInstance().getReference("Subject3")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .child("Statistics").setValue(statistics);
                                            FirebaseDatabase.getInstance().getReference("Subject4")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .child("Statistics").setValue(statistics);*/

                                            FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast toast = Toast.makeText(InformActivity.this, "Verification E-mail sent", Toast.LENGTH_LONG);
                                                    toast.show();
                                                    Information.flag2 = 0;
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    //  createNotificationChannel();
                                                    // notification();
                                                    //motivate();

                                                    Intent intent = new Intent(InformActivity.this, Texting.class);
                                                    startActivity(intent);
                                                    finish();

                                                }
                                            });
                                        } else {
                                            progressBar.setVisibility(View.GONE);

                                            Toast toast = Toast.makeText(InformActivity.this, "User Already Exists", Toast.LENGTH_LONG);
                                            toast.show();


                                        }
                                    }
                                });



             /*  String name1=name.getText().toString();
                String rollno1=rollno.getText().toString();
                 String password1=password.getText().toString();
                 String mobile1=mobile.getText().toString();
                 int dpres=R.id.imageView;

                StudentDTB.nm=name1;
                StudentDTB.rn=rollno1;
                StudentDTB.pd=password1;
                StudentDTB.mn=mobile1;
                StudentDTB.imgrid=String.valueOf(dpres);*/


                               /* Toast toast = Toast.makeText(InformActivity.this, "Successfully Registered", Toast.LENGTH_LONG);
                                toast.show();
                                Information.flag2 = 0;

                                Intent intent = new Intent(InformActivity.this, Information.class);
                                startActivity(intent);
                                finish();*/
                            } else {
                                if (mobile1.length() < 6)
                                    Toast.makeText(InformActivity.this, "Minimum length of password should be 6 characters", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(InformActivity.this, "Passwords not matched", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(InformActivity.this, "Enter Correct Details", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast toast = Toast.makeText(InformActivity.this, "Enter Correct Details", Toast.LENGTH_LONG);
                        toast.show();
                    }
                } else {
                    Toast.makeText(InformActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

               /* Intent intent=new Intent(InformActivity.this,StudentDTB.class);
                intent.putExtra("name",name1);
                intent.putExtra("rollno",rollno1);
                intent.putExtra("password",password1);
                intent.putExtra("mobileno.",mobile1);
                intent.putExtra("imageid",String.valueOf(dpres));



                startActivity(intent);*/


            }
        });


    }

    /*public class StudentDTB extends SQLiteOpenHelper {

        private static final String DB_NAME="Students";
        private static final int DB_VERSION=1;

        private String nm="name";
        private String rn="rollno";
        private String pd="password";
        private String mn="mobileno.";
        private String imgrid="imageid";


        StudentDTB(Context context)
        {
            super(context,DB_NAME,null,DB_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE USERS(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+"NAME TEXT,"+
                    "ROLL_NO TEXT,"+
                    "PASSWORD TEXT,"+
                    "MOBILE TEXT," +"IMAGE_RESOURCE_ID TEXT);");



            Intent intent=getIntent();


            String name=intent.getStringExtra(nm);
            String roll_no=intent.getStringExtra(rn);
            String password=intent.getStringExtra(pd);
            String mobile_no=intent.getStringExtra(mn);
            String res_id=intent.getStringExtra(imgrid);

            insertData(name,roll_no,password,mobile_no,res_id,db);






        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public void insertData(String name,String roll_no,String password,String mobile,String res_id,SQLiteDatabase db)
        {

            ContentValues details=new ContentValues();
            details.put("NAME",name);
            details.put("ROLL_NO.",roll_no);
            details.put("PASSWORD",password);
            details.put("MOBILE",mobile);
            details.put("IMAGE_RESOURCE_ID",res_id);

            db.insert("USERS",null,details);


        }
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Information.flag2 = 0;
        Information.t = 10;
        Intent intent = new Intent(InformActivity.this, Information.class);
        startActivity(intent);
        finish();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void motivate() {
        AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent1 = new Intent(this, AlarmReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 200, notificationIntent1, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 7);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);


        //assert alarmManager1 != null;
        alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Attendance";
            String description = "Channel for Attendance";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("test", name, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            // assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void notification() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent intent = new Intent(getApplicationContext(), Notification_receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // assert alarmManager != null;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
