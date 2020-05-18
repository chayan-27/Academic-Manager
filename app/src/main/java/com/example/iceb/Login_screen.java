package com.example.iceb;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class Login_screen extends AppCompatActivity {

    //ImageView imageView;
    //private static  final int RESULT_LOAD_IMAGE=1;
   // public static Uri selectedimage;
    //int d=Information.del;
    //byte[] xy=Information.nii;
    //int c;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference firebaseDatabase;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        button=(Button)findViewById(R.id.updates);
        TextView textView=(TextView)findViewById(R.id.textView6);
       firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
       firebaseDatabase .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                String h=name.substring(6,name.length()-1);
                textView.setText("WELCOME "+h.toUpperCase());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
       // textView.setText("WELCOME "+Information.name.toUpperCase());
        /*SQLiteOpenHelper dtb = new StudentDTB(Login_screen.this);
        SQLiteDatabase db=dtb.getReadableDatabase();





            try {
                ImageView imageView = (ImageView) findViewById(R.id.yes);
                byte[] bb=Information.nii;
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(bb, 0, bb.length));
            } catch (Exception e) {
                Toast.makeText(Login_screen.this, "No Image", Toast.LENGTH_SHORT).show();
            }*/
           Intent intent=getIntent();
           String roll=intent.getStringExtra("roll");
        if(roll.equals("110119044@nitt.edu")){

             button.setVisibility(View.VISIBLE);
        }







}

    public void status(View view) {
        Intent intent=new Intent(Login_screen.this,Attendance.class);
        startActivity(intent);
    }

    public void time(View view)
    {
        Intent intent=new Intent(Login_screen.this,Timetable.class);
        startActivity(intent);
    }

    public void pddf(View view)
    {
        Intent intent=new Intent(Login_screen.this,Syllabus_scr.class);
        startActivity(intent);

    }
    public void set(View view)
    {
        Intent intent=new Intent(Login_screen.this,Alarm_scr.class);
        startActivity(intent);
    }
    public void sm(View view)
    {
        Intent intent=new Intent(Login_screen.this,Study.class);
        startActivity(intent);
    }
    public void wha(View view)
    {
        Intent intent = new Intent(Login_screen.this,Chats.class);

        startActivity(intent);
    }

    public void about(View view)
    {
        Intent intent=new Intent(Login_screen.this,About.class);
        startActivity(intent);
    }

    public void getty(View view)
    {
        mFirebaseAuth.getInstance().signOut();
        Information.flag2=0;
        Intent intent=new Intent(Login_screen.this,Information.class);
        startActivity(intent);
        finish();

    }
    public void ass(View view)
    {
        Intent intent=new Intent(Login_screen.this,Assignments.class);
        startActivity(intent);

    }

    public void poll(View view)
    {
        Intent intent=new Intent(Login_screen.this,Poll.class);
        startActivity(intent);
    }

    public void upd(View view){
        Intent intent=new Intent(Login_screen.this,Updatescl.class);
        startActivity(intent);

    }

    public void announce(View view){
        Intent intent=new Intent(Login_screen.this,Announcenotify.class);
        startActivity(intent);
    }

    public void webmail(View view){
        Intent intent=new Intent(Login_screen.this,Webmail.class);
        startActivity(intent);
    }

   /* public void imgg(View view)
    {
        imageView = (ImageView) findViewById(R.id.yes);


                Intent galleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryintent, RESULT_LOAD_IMAGE);


        //ImageView dp = (ImageView) findViewById(R.id.imageView);

        Bitmap photo = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        photo = Bitmap.createScaledBitmap(photo, 200, 150, true);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 0, bos);
        byte[] bArray = bos.toByteArray();
        SQLiteOpenHelper dtb = new StudentDTB(Login_screen.this);
        SQLiteDatabase db = dtb.getWritableDatabase();
        ContentValues cv = new ContentValues();
       // xy=bArray;
        cv.put("IMAGE_RESOURCE_ID", bArray);
        db.update("USERS", cv,"_id="+Information.del,null);
        db.close();



    }*/
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null && requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK) {
            selectedimage = data.getData();
            imageView.setImageURI(selectedimage);
        }
    }*/

}
