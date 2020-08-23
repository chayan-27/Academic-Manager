package com.example.iceb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.iceb.server.APKcl;
import com.example.iceb.server.APKdown;
import com.example.iceb.server.Controller;
import com.example.iceb.server2.CreateClass;
import com.example.iceb.server2.CustomAdmin;
import com.example.iceb.server2.FetchInfo2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Testingg extends AppCompatActivity {

    BottomNavigationView bottomNav;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    FirebaseAuth mFirebaseAuth;
    TextView textView;
    TextView textView1;
    DatabaseReference firebaseDatabase;
    String section;
    ImageButton refresh;
    int r;

    String versionreal;
    SharedPreferences myuser;
    String check;
    String base;
    public static int classid;
    public static boolean admin = false;
    String batch;
    String real;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testingg);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setupToolBar();

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versionreal = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        myuser = this.getSharedPreferences("myuser", MODE_PRIVATE);
        check = myuser.getString("checkadminclass", "");
        base = "http://192.168.1.6:8000/";
       // base="https://academic-manager-nitt.el.r.appspot.com/";


        Intent intent = getIntent();
        String roll = intent.getStringExtra("roll");
        real = roll.substring(0, 9);
        r = Integer.parseInt(real);
        checkAdmin(real);



            /*FirebaseMessaging.getInstance().subscribeToTopic("class6").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("topic", "class" + classid);
                }
            });*/





        //new Bec().execute();
       /* if(myuser.getInt("class",-1)!=classid){
            FirebaseMessaging.getInstance().subscribeToTopic("class"+classid);
            FirebaseMessaging.getInstance().unsubscribeFromTopic("class"+myuser.getInt("class",-1));
        }else{
            FirebaseMessaging.getInstance().subscribeToTopic("class"+classid).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("topic","class"+classid);
                }
            });

        }*/
      /*  if (r % 2 == 0) {
            section = "ICEB";
           *//* FirebaseMessaging.getInstance().subscribeToTopic("weather")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String msg = "Successfull";
                            if (!task.isSuccessful()) {
                                msg = "Failed";
                            }


                        }
                    });*//*
            FirebaseMessaging.getInstance().subscribeToTopic("emergency").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
            FirebaseMessaging.getInstance().subscribeToTopic("test").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
            FirebaseMessaging.getInstance().subscribeToTopic("chintoo").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
            FirebaseMessaging.getInstance().unsubscribeFromTopic("weather1").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
        } else {
            section = "ICEA";
          *//*  FirebaseMessaging.getInstance().subscribeToTopic("weather1")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String msg = "Successfull";
                            if (!task.isSuccessful()) {
                                msg = "Failed";
                            }


                        }
                    });*//*
            FirebaseMessaging.getInstance().subscribeToTopic("emergency1").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
         *//*   FirebaseMessaging.getInstance().subscribeToTopic("test").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });*//*
            FirebaseMessaging.getInstance().unsubscribeFromTopic("weather").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
        }*/

            /*if(myuser.getInt("class",-1)!=classid){
                FirebaseMessaging.getInstance().subscribeToTopic("class"+classid);
                FirebaseMessaging.getInstance().unsubscribeFromTopic("class"+myuser.getInt("class",-1));
            }else{
                FirebaseMessaging.getInstance().subscribeToTopic("class"+classid).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("topic","class"+classid);
                    }
                });

            }*/
            /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Coursef(String.valueOf(classid), r, admin, batch)).commit();
            toolbar.setTitle("Announcements");*/


        bottomNav = findViewById(R.id.bottom_navigation);
        refresh = (ImageButton) findViewById(R.id.refresh);
        //bottomNav.setItemIconTintList(null);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        navigationView = (NavigationView) findViewById(R.id.meenuu);
        navigationView.setItemIconTintList(null);
        View head = navigationView.getHeaderView(0);
        textView = head.findViewById(R.id.name);
        textView1 = head.findViewById(R.id.roll);
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                String h = name.substring(6, name.length() - 1);
                textView.setText(h.toUpperCase());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

        textView1.setText(roll);
        /*checkAdmin(real);
        getClassId(real);*/

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        selectedFragment = new Coursef(String.valueOf(classid), r, admin, batch);
                        toolbar.setTitle("Announcements");
                        refresh.setVisibility(View.GONE);
                        break;
                    case R.id.navigation_dashboard:
                        selectedFragment = new acadf(String.valueOf(classid), r, admin, batch);
                        toolbar.setTitle("Academics");
                        refresh.setVisibility(View.GONE);
                        break;
                    case R.id.navigation_notifications:
                        // selectedFragment = new AssignmentF(section, r);
                        selectedFragment = new StudyMaterialF(String.valueOf(classid), "yes", Integer.parseInt(real), "no", admin, batch);
                        toolbar.setTitle("Assignments");
                        refresh.setVisibility(View.GONE);
                        break;


                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                return true;
            }
        });
        FragmentManager fragment = getSupportFragmentManager();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.wm:
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        Intent intent1 = new Intent(Testingg.this, WebmailReplace.class);
                        startActivity(intent1);
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        toolbar.setTitle("Announcements");
                        // refresh.setVisibility(View.GONE);

                        break;
                    case R.id.abt:
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        fragment.beginTransaction().replace(R.id.fragment_container, new Aboutf()).addToBackStack(null).commit();
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        toolbar.setTitle("ICE 2k23");
                        refresh.setVisibility(View.GONE);
                        break;
                    case R.id.upapk:
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                        send();
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        break;
                    case R.id.lo:
                        mFirebaseAuth.getInstance().signOut();
                        Information.flag2 = 0;
                        Intent intent = new Intent(Testingg.this, Information.class);
                        startActivity(intent);
                        finish();


                }
                return false;
            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupToolBar() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.announce);
        toolbar.setTitleTextColor(Color.parseColor("#000000"));
        //toolbar.setBackgroundResource(R.drawable.gradient7);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.announce, R.string.announce);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(Color.parseColor("#000000"));
        actionBarDrawerToggle.getDrawerArrowDrawable().setGapSize(20f);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    public void send() {
      /*   String path = "Updates";
        String name = "/ice2k23" + ".apk";
        File file = new File(Objects.requireNonNull(Testingg.this.getExternalFilesDir(path)).getAbsolutePath() + name);
        if (file.exists()) {


            // Uri uri=getFileUri(this,file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri apkURI = FileProvider.getUriForFile(
                    this,
                    this.getApplicationContext()
                            .getPackageName() + ".provider", file);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            intent.setDataAndType(apkURI, "application/vnd.android.package-archive");


            this.startActivity(Intent.createChooser(intent,"Update App"));
            Toast.makeText(Testingg.this, "Please wait till Download completes...", Toast.LENGTH_LONG).show();

        } else {*/


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo fetchInfo = retrofit.create(FetchInfo.class);
        Call<Controller> call = fetchInfo.downloadapk(new APKcl());

        call.enqueue(new Callback<Controller>() {
            @Override
            public void onResponse(Call<Controller> call, Response<Controller> response) {
                if (!(response.isSuccessful())) {
                    Toast.makeText(Testingg.this, "No Response From The Server", Toast.LENGTH_LONG).show();
                    return;
                }

                List<APKdown> list = response.body().getApk();
                if (list.size() > 0) {
                    String he = list.get(0).getCon();
                    if (!(he.equals(""))) {
                          /*  String path = "Updates";
                            String name = "/ice2k23" + ".apk";
                            byte[] decodedString = Base64.decode(he.getBytes(), Base64.DEFAULT);

                            File file = new File(Objects.requireNonNull(Testingg.this.getExternalFilesDir(path)).getAbsolutePath() + name);

                                try {
                                    OutputStream fileOutputStream = new FileOutputStream(file);
                                    fileOutputStream.write(decodedString);
                                    fileOutputStream.close();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }*/



                            /*Intent intent = new Intent(Intent.ACTION_VIEW);

                            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(Intent.createChooser(intent,"Update App"));*/
                        //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Studymf(he)).addToBackStack(null).commit();
                        String version = list.get(0).getVer();


                        if (version.equals(versionreal)) {
                            Toast.makeText(Testingg.this, "Your App is UpToDate", Toast.LENGTH_LONG).show();

                        } else {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(he));
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);


                        }


                    } else {
                        Toast.makeText(Testingg.this, "Your App is UpToDate", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(Testingg.this, "No Update Available", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<Controller> call, Throwable t) {
                Toast.makeText(Testingg.this, t.getMessage(), Toast.LENGTH_LONG).show();


            }
        });

    }

    public void reffresh(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Coursef(section, r, admin, batch)).commit();
        toolbar.setTitle("Announcements");
    }

    public boolean checkAdmin(String roll) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
        Call<List<CustomAdmin>> call = fetchInfo.getCustomAdmin();
        call.enqueue(new Callback<List<CustomAdmin>>() {
            @Override
            public void onResponse(Call<List<CustomAdmin>> call, Response<List<CustomAdmin>> response) {
                if (!response.isSuccessful()) {
                    //Toast.makeText(Testingg.this,"No response from server")
                    return;
                }
                List<CustomAdmin> list = response.body();
                for (CustomAdmin customAdmin : list) {
                    if (customAdmin.getAdminRollNo().equals(Integer.parseInt(roll))) {
                        if (check.length() == 0) {
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(base)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
                            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), roll);

                            Call<CreateClass> call1 = fetchInfo.getClassDetail(body);
                            call1.enqueue(new Callback<CreateClass>() {
                                @Override
                                public void onResponse(Call<CreateClass> call, Response<CreateClass> response) {
                                    if (!response.isSuccessful()) {
                                        // Toast.makeText(Testingg.this,"No res",Toast.LENGTH_SHORT).show();

                                        return;
                                    }
                                    SharedPreferences.Editor editor = myuser.edit();
                                    editor.putString("checkadminclass", roll);
                                    editor.apply();
                                }

                                @Override
                                public void onFailure(Call<CreateClass> call, Throwable t) {
                                    Toast.makeText(Testingg.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            admin = true;
                            break;
                        }
                        admin = true;

                    }
                }

                getClassId(real);


            }

            @Override
            public void onFailure(Call<List<CustomAdmin>> call, Throwable t) {
                Toast.makeText(Testingg.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return admin;
    }

    public int getClassId(String roll) {
        final int[] i = {0};
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
        Call<List<CreateClass>> call = fetchInfo.getClassID(roll);
        call.enqueue(new Callback<List<CreateClass>>() {
            @Override
            public void onResponse(Call<List<CreateClass>> call, Response<List<CreateClass>> response) {
                if (!response.isSuccessful()) {
                    // Toast.makeText(Testingg.this,"No res",Toast.LENGTH_SHORT).show();

                    return;
                }

                List<CreateClass> list = response.body();
                assert response.body() != null;
                classid = response.body().get(0).getId();
                batch = response.body().get(0).getBatch().toString();
                SharedPreferences.Editor editor = myuser.edit();
                editor.putInt("class", classid);
                editor.apply();

                String topic="class"+classid;

                FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("topic", topic);
                    }
                });

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Coursef(String.valueOf(classid), r, admin, batch)).commit();
                toolbar.setTitle("Announcements");

                //Toast.makeText(Testingg.this,""+response.body().get(0).getId(),Toast.LENGTH_SHORT).show();


                //i[0] =list.get(0).getId();
                //  Toast.makeText(Testingg.this,i[0],Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<List<CreateClass>> call, Throwable t) {
                Toast.makeText(Testingg.this, "Unable to Fetch Class.Please Try Again!!", Toast.LENGTH_SHORT).show();

            }
        });

        return classid;

    }


}


