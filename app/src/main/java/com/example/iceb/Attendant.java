package com.example.iceb;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Attendant extends Fragment {
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
    int sevp;
    int sevab;

    boolean undo[] = new boolean[5];
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
    boolean reset1[] = new boolean[5];

    public Attendant() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendancef, container, false);

        button1 = (ImageButton) view.findViewById(R.id.iciu);
        button2 = (ImageButton) view.findViewById(R.id.icr);
        icir = (ProgressBar) view.findViewById(R.id.progressBar2);
        present = (ImageButton) view.findViewById(R.id.imageButton2);
        absent = (ImageButton) view.findViewById(R.id.imageButton3);
        tx1 = (TextView) view.findViewById(R.id.present);
        tx2 = (TextView) view.findViewById(R.id.absent);
        tx3 = (TextView) view.findViewById(R.id.pericir);


        sheet(icir, present, absent, tx1, tx2, tx3, 0, button1, button2);

        ProgressBar chem1 = (ProgressBar) view.findViewById(R.id.chemis);
        ImageButton chem2 = (ImageButton) view.findViewById(R.id.pchem);
        ImageButton chem3 = (ImageButton) view.findViewById(R.id.abchem);
        TextView chem4 = (TextView) view.findViewById(R.id.txtpchem);
        TextView chem5 = (TextView) view.findViewById(R.id.txtabchem);
        TextView chem6 = (TextView) view.findViewById(R.id.perchir);
        ImageButton chem7 = (ImageButton) view.findViewById(R.id.chu);
        ImageButton chem8 = (ImageButton) view.findViewById(R.id.chr);

        sheet(chem1, chem2, chem3, chem4, chem5, chem6, 1, chem7, chem8);

        ProgressBar icpc1 = (ProgressBar) view.findViewById(R.id.progicpc);
        ImageButton icpc2 = (ImageButton) view.findViewById(R.id.picpc);
        ImageButton icpc3 = (ImageButton) view.findViewById(R.id.abicpc);
        TextView icpc4 = (TextView) view.findViewById(R.id.txtpicpc);
        TextView icpc5 = (TextView) view.findViewById(R.id.txtabicpc);
        TextView icpc6 = (TextView) view.findViewById(R.id.pericpc);
        ImageButton icpc7 = (ImageButton) view.findViewById(R.id.icpcu);
        ImageButton icpc8 = (ImageButton) view.findViewById(R.id.icpcr);

        sheet(icpc1, icpc2, icpc3, icpc4, icpc5, icpc6, 2, icpc7, icpc8);

        ProgressBar eng1 = (ProgressBar) view.findViewById(R.id.progeng);
        ImageButton eng2 = (ImageButton) view.findViewById(R.id.peng);
        ImageButton eng3 = (ImageButton) view.findViewById(R.id.abeng);
        TextView eng4 = (TextView) view.findViewById(R.id.txtpeng);
        TextView eng5 = (TextView) view.findViewById(R.id.txtabeng);
        TextView eng6 = (TextView) view.findViewById(R.id.pereng);
        ImageButton eng7 = (ImageButton) view.findViewById(R.id.engu);
        ImageButton eng8 = (ImageButton) view.findViewById(R.id.engr);

        sheet(eng1, eng2, eng3, eng4, eng5, eng6, 3, eng7, eng8);

        ProgressBar math1 = (ProgressBar) view.findViewById(R.id.progmath);
        ImageButton math2 = (ImageButton) view.findViewById(R.id.pmath);
        ImageButton math3 = (ImageButton) view.findViewById(R.id.abmath);
        TextView math4 = (TextView) view.findViewById(R.id.ptxtm);
        TextView math5 = (TextView) view.findViewById(R.id.abtxtm);
        TextView math6 = (TextView) view.findViewById(R.id.permath);
        ImageButton math7 = (ImageButton) view.findViewById(R.id.mathu);
        ImageButton math8 = (ImageButton) view.findViewById(R.id.mathr);

        sheet(math1, math2, math3, math4, math5, math6, 4, math7, math8);

       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragment=getActivity().getSupportFragmentManager();
                fragment.beginTransaction().replace(R.id.atten,new Perf()).addToBackStack(null).commit();

            }
        });*/
        return view;


    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void sheet(ProgressBar icir, ImageButton present, ImageButton absent, TextView tx1, TextView tx2, TextView tx3, int c, ImageButton undoo, ImageButton reset) {


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
                        int c = 0;
                        while (r < 75.0f && a != 0) {
                            a = a + 1;
                            r = ((float) a / (a + b)) * 100.0f;
                            c++;
                        }

                    } else {
                        icir.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                        float r = pr1[c];
                        int a = pr2[c];
                        int b = pr3[c];
                        int c = 0;
                        while (r >= 75.0f && a != 0) {
                            b = b + 1;
                            r = ((float) a / (a + b)) * 100.0f;
                            c++;
                        }
                    }
                    double roundOff = Math.round(pr1[c] * 100.0) / 100.0;
                    tx3.setText(roundOff + " %");
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
                    z = c;
                    g = "present";
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
                    z = c;
                    g = "absent";
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
                }
            });

        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }

        undoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (z != 12 && z == c) {
                    undo(icir, present, absent, tx1, tx2, tx3, z);
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (z != 12 && z == c) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setMessage("Are you sure to Reset your attendance progress for this subject to 0%!!\nThe statistics data for this subject will be cleared.")
                            .setCancelable(false)
                            .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
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
            }
        });

        // Inflate the layout for this fragment

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void undo(ProgressBar icir, ImageButton present, ImageButton absent, TextView tx1, TextView tx2, TextView tx3, int c) {
        if (undo[c] == true) {
            undo[c] = false;
            if (g.equals("present")) {
                pr2[c] = pr2[c] - 1;
                g = "";
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
                g = "";
            } else if (g.equals("absent")) {
                pr3[c] = pr3[c] - 1;
                g = "";
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
                g = "";
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void reset(ProgressBar icir, ImageButton present, ImageButton absent, TextView tx1, TextView tx2, TextView tx3, int c) {
        if (reset1[c] == true) {
            reset1[c] = false;
            undo[c] = false;

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
        }

    }


}
