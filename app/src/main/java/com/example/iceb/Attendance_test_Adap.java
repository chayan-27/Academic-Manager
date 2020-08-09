package com.example.iceb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Attendance_test_Adap extends RecyclerView.Adapter<Attendance_test_Adap.Attendance_holder> {
    List<String> list;
    Context context;
    final float[] pr1;
    final int[] pr2;
    final int[] pr3;
    DatabaseReference firebaseDatabase;
    boolean undo[];
    int z = 12;
    String g = "";
    ProgressBar icir1;
    ImageButton present1;
    ImageButton absent1;
    TextView tx11;
    TextView tx21;
    TextView tx31;
    ImageButton button1;
    ImageButton button2;
    List<String> presentli;
    List<String> absentli;
    List<List<String>> datap = new ArrayList<List<String>>();
    List<List<String>> dataab = new ArrayList<List<String>>();
    Statistics[] statistics1;
    Statistics statistics;
    boolean reset1[];
    List<List<String>> status = new ArrayList<>();


    public Attendance_test_Adap(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        for (int i = 0; i < list.size(); i++) {
            status.add(new ArrayList<>());
        }
        pr1 = new float[list.size()];
        pr2 = new int[list.size()];
        pr3 = new int[list.size()];
        undo = new boolean[list.size()];
        statistics1 = new Statistics[list.size()];
        reset1 = new boolean[list.size()];

    }

    @NonNull
    @Override
    public Attendance_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.attendance_card, viewGroup, false);
        return new Attendance_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Attendance_holder attendance_holder, int i) {
        attendance_holder.txname.setText(list.get(i));
        sheet(attendance_holder.icir, attendance_holder.present, attendance_holder.absent, attendance_holder.tx1, attendance_holder.tx2, attendance_holder.tx3, i, attendance_holder.button1, attendance_holder.button2, attendance_holder.require, attendance_holder.missed, attendance_holder.stats, attendance_holder.txname);
        attendance_holder.stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getData(i);
                } catch (Exception e) {
                    Toast.makeText(context, "No statistics data available", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Attendance_holder extends RecyclerView.ViewHolder {
        ProgressBar icir;
        ImageButton present;
        ImageButton absent;
        TextView txname;
        TextView tx1;
        TextView tx2;
        TextView tx3;
        ImageButton button1;
        ImageButton button2;
        TextView require;
        TextView missed;
        Button stats;

        public Attendance_holder(@NonNull View view) {
            super(view);
            button1 = (ImageButton) view.findViewById(R.id.iciu);
            button2 = (ImageButton) view.findViewById(R.id.icr);
            txname = (TextView) view.findViewById(R.id.icir3);
            icir = (ProgressBar) view.findViewById(R.id.progressBar2);
            present = (ImageButton) view.findViewById(R.id.imageButton2);
            absent = (ImageButton) view.findViewById(R.id.imageButton3);
            tx1 = (TextView) view.findViewById(R.id.present);
            tx2 = (TextView) view.findViewById(R.id.absent);
            tx3 = (TextView) view.findViewById(R.id.pericir);
            require = (TextView) view.findViewById(R.id.icirreq);
            missed = (TextView) view.findViewById(R.id.icirmis);
            stats = (Button) view.findViewById(R.id.statistics);

        }
    }

    public void sheet(ProgressBar icir, ImageButton present, ImageButton absent, TextView tx1, TextView tx2, TextView tx3, int c, ImageButton undoo, ImageButton reset, TextView require, TextView missed, Button iu, TextView yu) {
        //  Subjects subjects=new Subjects(pr1,pr2,pr3);

//else{
   /* FirebaseDatabase.getInstance().getReference().child("Subject"+c)
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pr1").setValue(pr1);
    FirebaseDatabase.getInstance().getReference().child("Subject"+c)
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("a1").setValue(pr2);
    FirebaseDatabase.getInstance().getReference().child("Subject"+c)
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("b1").setValue(pr3);*/


        if (isNetworkConnected()) {

            firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            firebaseDatabase.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        String name = dataSnapshot.child("pr1").getValue().toString();
                        pr1[c] = Float.parseFloat(name);

                        String name2 = dataSnapshot.child("a1").getValue().toString();
                        pr2[c] = Integer.parseInt(name2);

                        String name3 = dataSnapshot.child("b1").getValue().toString();
                        pr3[c] = Integer.parseInt(name3);

                        // icir.setScaleY(2f);
                        icir.setProgress((int) pr1[c]);
                        //  icir.setProgress((int) pr[c]);
                        if (pr1[c] < 75.0f) {
                            icir.setProgressTintList(ColorStateList.valueOf(Color.RED));
                            float r = pr1[c];
                            int a = pr2[c];
                            int b = pr3[c];
                            int f = 0;
                            while (r < 75.0f && (a + b) != 0) {
                                a = a + 1;
                                r = ((float) a / (a + b)) * 100.0f;
                                f++;
                            }
                            require.setText("Minimum classes required to reach 75% : " + f);
                            missed.setText("Maximum classes that can be missed : 0");


                        } else {
                            icir.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                            float r = pr1[c];
                            int a = pr2[c];
                            int b = pr3[c];
                            int f = 0;
                            while (r > 75.0f && (a + b) != 0) {
                                b = b + 1;
                                r = ((float) a / (a + b)) * 100.0f;
                                if (r <= 75.0f) {

                                } else {
                                    f++;
                                }
                            }
                            require.setText("Minimum classes required to reach 75% : 0");
                            missed.setText("Maximum classes that can be missed : " + f);

                        }
                        double roundOff = Math.round(pr1[c] * 100.0) / 100.0;
                        tx3.setText(roundOff + " %");
                        tx1.setText("DAYS PRESENT : " + pr2[c]);
                        tx2.setText("DAYS ABSENT : " + pr3[c]);
                    } catch (Exception e) {


                        Subjects subjects = new Subjects(0, 0, 0);
                        FirebaseDatabase.getInstance().getReference("Subject" + c)
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(subjects);
                        createStat(c);
                        // statistics(c);
                        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        firebaseDatabase.addValueEventListener(new ValueEventListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
                                    float r = pr1[c];
                                    int a = pr2[c];
                                    int b = pr3[c];
                                    int f = 0;
                                    while (r < 75.0f && (a + b) != 0) {
                                        a = a + 1;
                                        r = ((float) a / (a + b)) * 100.0f;
                                        f++;
                                    }
                                    require.setText("Minimum classes required to reach 75% : " + f);
                                    missed.setText("Maximum classes that can be missed : 0");


                                } else {
                                    icir.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                                    float r = pr1[c];
                                    int a = pr2[c];
                                    int b = pr3[c];
                                    int f = 0;
                                    while (r > 75.0f && (a + b) != 0) {
                                        b = b + 1;
                                        r = ((float) a / (a + b)) * 100.0f;
                                        if (r <= 75.0f) {

                                        } else {
                                            f++;
                                        }
                                    }
                                    require.setText("Minimum classes required to reach 75% : 0");
                                    missed.setText("Maximum classes that can be missed : " + f);

                                }
                                double roundOff = Math.round(pr1[c] * 100.0) / 100.0;
                                tx3.setText(roundOff + " %");
                                tx1.setText("DAYS PRESENT : " + pr2[c]);
                                tx2.setText("DAYS ABSENT : " + pr3[c]);

                                icir.setVisibility(View.GONE);
                                present.setVisibility(View.GONE);
                                absent.setVisibility(View.GONE);
                                tx1.setVisibility(View.GONE);
                                tx2.setVisibility(View.GONE);
                                tx3.setVisibility(View.GONE);
                                undoo.setVisibility(View.GONE);
                                reset.setVisibility(View.GONE);
                                require.setVisibility(View.GONE);
                                missed.setVisibility(View.GONE);
                                iu.setVisibility(View.GONE);
                                yu.setVisibility(View.GONE);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }


                        });

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            });
            statistics(c);

            //statistics(c);


            present.setOnClickListener(new View.OnClickListener() {
                // @SuppressLint("SetTextI18n")
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    List<String> xyz = new ArrayList<>();
                    pr2[c]++;
                    int chek = 0;
                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
                    try {
                        datap.get(c).add(currentDate + "\n" + currentTime);
                        statistics1[c] = new Statistics(datap.get(c), dataab.get(c));

                        chek = 0;
                    } catch (Exception e) {
                        createStat(c);
                        statistics(c);


                       /* datap.get(c).add(currentDate + "\n" + currentTime);
                        statistics1[c] = new Statistics(datap.get(c), dataab.get(c));*/




                       /* xyz.add(currentDate + "\n" + currentTime);
                        statistics1[c] = new Statistics(xyz, xyz);*/


                        //statistics1[c] = new Statistics(datap.get(c), dataab.get(c));


                        // Toast.makeText(context, "C is : " + c, Toast.LENGTH_LONG).show();

                    }

                    z = c;
                    g = "present";
                    status.get(c).add("present");
                    undo[c] = true;
                    reset1[c] = true;
                    icir1 = icir;
                    present1 = present;
                    absent1 = absent;
                    tx11 = tx1;
                    tx21 = tx2;
                    tx31 = tx3;


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
                        float r = pr1[c];
                        int a = pr2[c];
                        int b = pr3[c];
                        int f = 0;
                        while (r < 75.0f && (a + b) != 0) {
                            a = a + 1;
                            r = ((float) a / (a + b)) * 100.0f;
                            f++;
                        }
                        require.setText("Minimum classes required to reach 75% : " + f);
                        missed.setText("Maximum classes that can be missed : 0");
                    } else {
                        icir.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                        float r = pr1[c];
                        int a = pr2[c];
                        int b = pr3[c];
                        int f = 0;
                        while (r > 75.0f && (a + b) != 0) {
                            b = b + 1;
                            r = ((float) a / (a + b)) * 100.0f;
                            if (r <= 75.0f) {

                            } else {
                                f++;
                            }
                        }
                        require.setText("Minimum classes required to reach 75% : 0");
                        missed.setText("Maximum classes that can be missed : " + f);

                    }
                    icir.setProgress((int) pr1[c]);
                    double roundOff = Math.round(pr1[c] * 100.0) / 100.0;
                    tx1.setText("DAYS PRESENT : " + pr2[c]);
                    tx3.setText(roundOff + " %");
                    tx2.setText("DAYS ABSENT : " + pr3[c]);
                    FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pr1").setValue(pr1[c]);
                    FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("a1").setValue(pr2[c]);
                    FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("b1").setValue(pr3[c]);

                    FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Statistics").setValue(statistics1[c]);


                }
            });

            absent.setOnClickListener(new View.OnClickListener() {
                //  @SuppressLint("SetTextI18n")
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
               /* if(abs[c] <40&&((abs[c]+pres[c])!=40))
                {
                    pr[c]--;
                }*/
                    // if(abs[c]<40 && ((abs[c]+pres[c])!=40)) {
                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
                    List<String> xyz = new ArrayList<>();


                    pr3[c]++;
                    try {
                        dataab.get(c).add(currentDate + "\n" + currentTime);
                        statistics1[c] = new Statistics(datap.get(c), dataab.get(c));
                    } catch (Exception e) {
                        createStat(c);
                        statistics(c);
                       /* xyz.add(currentDate + "\n" + currentTime);
                        statistics1[c] = new Statistics(xyz, xyz);*/
                    }
                    if ((pr2[c] + pr3[c]) != 0) {
                        pr1[c] = ((float) pr2[c] / (pr2[c] + pr3[c])) * 100.0f;
                    }
                    z = c;
                    g = "absent";
                    status.get(c).add("absent");
                    undo[c] = true;
                    reset1[c] = true;
                    //}
                    icir1 = icir;
                    present1 = present;
                    absent1 = absent;
                    tx11 = tx1;
                    tx21 = tx2;
                    tx31 = tx3;

                    if (pr1[c] < 75.0f) {
                        icir.setProgressTintList(ColorStateList.valueOf(Color.RED));
                        float r = pr1[c];
                        int a = pr2[c];
                        int b = pr3[c];
                        int f = 0;
                        while (r < 75.0f && (a + b) != 0) {
                            a = a + 1;
                            r = ((float) a / (a + b)) * 100.0f;
                            f++;
                        }
                        require.setText("Minimum classes required to reach 75% : " + f);
                        missed.setText("Maximum classes that can be missed : 0");

                    } else {
                        icir.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                        float r = pr1[c];
                        int a = pr2[c];
                        int b = pr3[c];
                        int f = 0;
                        while (r > 75.0f && (a + b) != 0) {
                            b = b + 1;
                            r = ((float) a / (a + b)) * 100.0f;
                            if (r <= 75.0f) {

                            } else {
                                f++;
                            }
                        }
                        require.setText("Minimum classes required to reach 75% : 0");
                        missed.setText("Maximum classes that can be missed : " + f);

                    }
                    icir.setProgress((int) pr1[c]);
                    double roundOff = Math.round(pr1[c] * 100.0) / 100.0;
                    tx2.setText("DAYS ABSENT : " + pr3[c]);
                    tx3.setText(roundOff + " %");
                    tx1.setText("DAYS PRESENT : " + pr2[c]);
                    FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pr1").setValue(pr1[c]);
                    FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("a1").setValue(pr2[c]);
                    FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("b1").setValue(pr3[c]);
                    FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Statistics").setValue(statistics1[c]);

                }
            });

        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
        }

        undoo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (z != 12 && z == c) {
                    try {
                        undo(icir, present, absent, tx1, tx2, tx3, z);
                    } catch (Exception e) {
                    }
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    z = c;
                    if (z == c) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setMessage("Are you sure to Reset your attendance progress for this subject to 0%!!\nThe statistics data for this subject will be cleared.")
                                .setCancelable(false)
                                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        reset(icir, present, absent, tx1, tx2, tx3, z);
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alert.create();
                        alertDialog.setTitle("Reset Progress");
                        alertDialog.show();
                    }
                } catch (Exception e) {
                }
            }
        });


    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void undo(ProgressBar icir, ImageButton present, ImageButton absent, TextView tx1, TextView tx2, TextView tx3, int c) {
        if (undo[c] == true) {
            undo[c] = true;
            for (int i = 0; i < list.size(); i++) {
                if (i != c) {
                    undo[i] = false;
                    status.get(i).clear();
                }
            }
            if ((status.get(c).size() > 0) && (status.get(c).get(status.get(c).size() - 1).equals("present"))) {
                pr2[c] = pr2[c] - 1;
                g = "";
                status.get(c).remove(status.get(c).size() - 1);
                datap.get(c).remove(datap.get(c).size() - 1);
                statistics1[c] = new Statistics(datap.get(c), dataab.get(c));
                if (((pr2[c] + pr3[c]) != 0)) {
                    pr1[c] = ((float) pr2[c] / (pr2[c] + pr3[c])) * 100.0f;
                } else {
                    pr1[c] = 0;
                }
                if (pr1[c] < 75.0f) {
                    icir.setProgressTintList(ColorStateList.valueOf(Color.RED));
                } else {
                    icir.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                }
                icir.setProgress((int) pr1[c]);
                double roundOff = Math.round(pr1[c] * 100.0) / 100.0;
                tx1.setText("DAYS PRESENT : " + pr2[c]);
                tx3.setText(roundOff + " %");
                tx2.setText("DAYS ABSENT : " + pr3[c]);
                FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pr1").setValue(pr1[c]);
                FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("a1").setValue(pr2[c]);
                FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("b1").setValue(pr3[c]);
                FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Statistics").setValue(statistics1[c]);

                g = "";
            } else if ((status.get(c).size() > 0) && (status.get(c).get(status.get(c).size() - 1).equals("absent"))) {
                pr3[c] = pr3[c] - 1;
                g = "";
                status.get(c).remove(status.get(c).size() - 1);
                dataab.get(c).remove(dataab.get(c).size() - 1);
                statistics1[c] = new Statistics(datap.get(c), dataab.get(c));

                if ((pr2[c] + pr3[c]) != 0) {
                    pr1[c] = ((float) pr2[c] / (pr2[c] + pr3[c])) * 100.0f;
                } else {
                    pr1[c] = 0;
                }
                if (pr1[c] < 75.0f) {
                    icir.setProgressTintList(ColorStateList.valueOf(Color.RED));
                } else {
                    icir.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                }
                icir.setProgress((int) pr1[c]);
                double roundOff = Math.round(pr1[c] * 100.0) / 100.0;
                tx2.setText("DAYS ABSENT : " + pr3[c]);
                tx3.setText(roundOff + " %");
                tx1.setText("DAYS PRESENT : " + pr2[c]);
                FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pr1").setValue(pr1[c]);
                FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("a1").setValue(pr2[c]);
                FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("b1").setValue(pr3[c]);
                FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Statistics").setValue(statistics1[c]);

                g = "";
            } else {
                Toast.makeText(context, "No more undo action can be performed", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void reset(ProgressBar icir, ImageButton present, ImageButton absent, TextView tx1, TextView tx2, TextView tx3, int c) {

        reset1[c] = false;
        undo[c] = false;
        datap.get(c).clear();
        dataab.get(c).clear();

        datap.get(c).add("DATE\nTIME");
        dataab.get(c).add("DATE\nTIME");

        statistics1[c] = new Statistics(datap.get(c), dataab.get(c));
        pr2[c] = 0;
        g = "";
        pr1[c] = 0;
        pr3[c] = 0;
        icir.setProgress((int) pr1[c]);
        double roundOff = Math.round(pr1[c] * 100.0) / 100.0;
        tx1.setText("DAYS PRESENT : " + pr2[c]);
        tx3.setText(roundOff + " %");
        tx2.setText("DAYS ABSENT : " + pr3[c]);
        FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pr1").setValue(pr1[c]);
        FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("a1").setValue(pr2[c]);
        FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("b1").setValue(pr3[c]);
        FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Statistics").setValue(statistics1[c]);


    }

    public void statistics(int c) {
        FirebaseDatabase.getInstance().getReference().child("Subject" + c)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Statistics").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                presentli = (List<String>) dataSnapshot.child("present").getValue();
                absentli = (List<String>) dataSnapshot.child("absent").getValue();

                datap.add(presentli);
                dataab.add(absentli);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void createStat(int c) {
        List<String> p = new ArrayList<>();
        p.add("DATE\nTIME");
        List<String> ab = new ArrayList<>();
        ab.add("DATE\nTIME");
        Statistics statistcs = new Statistics(p, ab);
        FirebaseDatabase.getInstance().getReference("Subject" + c)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Statistics").setValue(statistcs);
    }

    public void getData(int s) {
        List<String> list = new ArrayList<>(datap.get(s));

        List<String> list1 = new ArrayList<>(dataab.get(s));

        list.remove(0);
        list1.remove(0);
        list.add("DATE\nTIME");
        list1.add("DATE\nTIME");

        Collections.reverse(list);
        Collections.reverse(list1);

        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Stats(list, list1)).addToBackStack(null).commit();

    }
}
