package com.example.iceb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Attendance extends AppCompatActivity {


    final float[] pr1 = new float[5];
    final int[] pr2 = new int[5];
    final int[] pr3 = new int[5];
    DatabaseReference firebaseDatabase;
    ProgressBar icir;
    ImageButton present;
    ImageButton absent;
    TextView tx1;
    TextView tx2;
    TextView tx3;
    //public static String id;






    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        icir=(ProgressBar)findViewById(R.id.progressBar2);
         present=(ImageButton)findViewById(R.id.imageButton2);
         absent=(ImageButton)findViewById(R.id.imageButton3);
         tx1=(TextView)findViewById(R.id.present);
         tx2=(TextView)findViewById(R.id.absent);
         tx3=(TextView)findViewById(R.id.pericir);



        sheet(icir,present,absent,tx1,tx2,tx3,0);

        ProgressBar chem1=(ProgressBar)findViewById(R.id.chemis);
        ImageButton chem2=(ImageButton)findViewById(R.id.pchem);
        ImageButton chem3=(ImageButton)findViewById(R.id.abchem);
        TextView chem4=(TextView)findViewById(R.id.txtpchem);
        TextView chem5=(TextView)findViewById(R.id.txtabchem);
        TextView chem6=(TextView)findViewById(R.id.perchir);

        sheet(chem1,chem2,chem3,chem4,chem5,chem6,1);

        ProgressBar icpc1=(ProgressBar)findViewById(R.id.progicpc);
        ImageButton icpc2=(ImageButton)findViewById(R.id.picpc);
        ImageButton icpc3=(ImageButton)findViewById(R.id.abicpc);
        TextView icpc4=(TextView)findViewById(R.id.txtpicpc);
        TextView icpc5=(TextView)findViewById(R.id.txtabicpc);
        TextView icpc6=(TextView)findViewById(R.id.pericpc);

        sheet(icpc1,icpc2,icpc3,icpc4,icpc5,icpc6,2);

        ProgressBar eng1=(ProgressBar)findViewById(R.id.progeng);
        ImageButton eng2=(ImageButton)findViewById(R.id.peng);
        ImageButton eng3=(ImageButton)findViewById(R.id.abeng);
        TextView eng4=(TextView)findViewById(R.id.txtpeng);
        TextView eng5=(TextView)findViewById(R.id.txtabeng);
        TextView eng6=(TextView)findViewById(R.id.pereng);

        sheet(eng1,eng2,eng3,eng4,eng5,eng6,3);

        ProgressBar math1=(ProgressBar)findViewById(R.id.progmath);
        ImageButton math2=(ImageButton)findViewById(R.id.pmath);
        ImageButton math3=(ImageButton)findViewById(R.id.abmath);
        TextView math4=(TextView)findViewById(R.id.ptxtm);
        TextView math5=(TextView)findViewById(R.id.abtxtm);
        TextView math6=(TextView)findViewById(R.id.permath);

        sheet(math1,math2,math3,math4,math5,math6,4);




    }
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void sheet( ProgressBar icir, ImageButton present, ImageButton absent,  TextView tx1, TextView tx2,  TextView tx3, int c) {


        //  Subjects subjects=new Subjects(pr1,pr2,pr3);

//else{
   /* FirebaseDatabase.getInstance().getReference().child("Subject"+c)
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pr1").setValue(pr1);
    FirebaseDatabase.getInstance().getReference().child("Subject"+c)
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("a1").setValue(pr2);
    FirebaseDatabase.getInstance().getReference().child("Subject"+c)
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("b1").setValue(pr3);*/

    if(isNetworkConnected()){
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("pr1").getValue().toString();
                pr1[c] = Float.parseFloat(name);

                String name2 = dataSnapshot.child("a1").getValue().toString();
                pr2[c] = Integer.parseInt(name2);
                String name3 = dataSnapshot.child("b1").getValue().toString();
                pr3[c] = Integer.parseInt(name3);

                icir.setScaleY(2f);
                icir.setProgress((int) pr1[c]);
                //  icir.setProgress((int) pr[c]);
                if (pr1[c] < 75.0f) {
                    icir.setProgressTintList(ColorStateList.valueOf(Color.RED));
                } else {
                    icir.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                }
                double roundOff = Math.round(pr1[c] * 100.0) / 100.0;
                tx3.setText("PERCENTAGE : " + roundOff);
                tx1.setText("DAYS PRESENT : " + pr2[c]);
                tx2.setText("DAYS ABSENT : " + pr3[c]);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


        present.setOnClickListener(new View.OnClickListener() {
            // @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                pr2[c]++;


                if (((pr2[c] + pr3[c]) != 0)) {
                    pr1[c] = ((float) pr2[c] / (pr2[c] + pr3[c])) * 100.0f;
                }
               /* if(pres[c] <40 && ((abs[c]+pres[c])!=40))
                {

                }*/
                // if(pres[c]<40 && ((abs[c]+pres[c])!=40)) {

                // }
                if (pr1[c] < 75.0f) {
                    icir.setProgressTintList(ColorStateList.valueOf(Color.RED));
                } else {
                    icir.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                }
                icir.setProgress((int) pr1[c]);
                double roundOff = Math.round(pr1[c] * 100.0) / 100.0;
                tx1.setText("DAYS PRESENT : " + pr2[c]);
                tx3.setText("PERCENTAGE : " + roundOff);
                tx2.setText("DAYS ABSENT : " + pr3[c]);
                FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pr1").setValue(pr1[c]);
                FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("a1").setValue(pr2[c]);
                FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("b1").setValue(pr3[c]);


            }
        });

        absent.setOnClickListener(new View.OnClickListener() {
            //  @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
               /* if(abs[c] <40&&((abs[c]+pres[c])!=40))
                {
                    pr[c]--;
                }*/
                // if(abs[c]<40 && ((abs[c]+pres[c])!=40)) {
                pr3[c]++;
                if ((pr2[c] + pr3[c]) != 0) {
                    pr1[c] = ((float) pr2[c] / (pr2[c] + pr3[c])) * 100.0f;
                }
                //}

                if (pr1[c] < 75.0f) {
                    icir.setProgressTintList(ColorStateList.valueOf(Color.RED));
                } else {
                    icir.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                }
                icir.setProgress((int) pr1[c]);
                double roundOff = Math.round(pr1[c] * 100.0) / 100.0;
                tx2.setText("DAYS ABSENT : " + pr3[c]);
                tx3.setText("PERCENTAGE : " + roundOff);
                tx1.setText("DAYS PRESENT : " + pr2[c]);
                FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pr1").setValue(pr1[c]);
                FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("a1").setValue(pr2[c]);
                FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("b1").setValue(pr3[c]);
            }
        });

    }else{
        Toast.makeText(Attendance.this,"No Internet Connection",Toast.LENGTH_LONG).show();
    }
    }



    public void fin(View view)
    {
        Intent intent=new Intent(Attendance.this,Percentage.class);
        startActivity(intent);
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


}
