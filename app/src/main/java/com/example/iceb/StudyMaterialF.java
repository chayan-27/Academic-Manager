package com.example.iceb;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iceb.AdminUsage.AssignmentUP;
import com.example.iceb.AdminUsage.Studymf;
import com.example.iceb.AdminUsage.SubjectAdd;
import com.example.iceb.AdminUsage.TimeTablef;
import com.example.iceb.server.Controller;
import com.example.iceb.server.Studymaterial;
import com.example.iceb.server2.AdminSubject;
import com.example.iceb.server2.FetchInfo2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class StudyMaterialF extends Fragment {
    RecyclerView recyclerView;
    Spinner sem;
    Button button;
    TextView textView;
    int semester = 0;
    FetchInfo fetchInfo;
    String section;
    ProgressBar progressBar;
    boolean admin;
    List<String> subject_ids;
    List<String> subject_name;
    String assignment;
    int roll;
    String courseplan;
    String batch;
    LinearLayout subject_add;
    Button button1;
    EditText editText;
    FloatingActionButton fab;


    @SuppressLint("ValidFragment")
    public StudyMaterialF(String section, String assignment, int roll, String courseplan, boolean admin, String batch) {
        // Required empty public constructor
        this.section = section;
        this.assignment = assignment;
        this.roll = roll;
        this.courseplan = courseplan;
        this.admin = admin;
        this.batch = batch;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_study_material, container, false);
        recyclerView = view.findViewById(R.id.recycle);
        sem = view.findViewById(R.id.spinner);
        button = view.findViewById(R.id.button);
        textView = view.findViewById(R.id.textView);
        progressBar = view.findViewById(R.id.progresso);
        subject_add = view.findViewById(R.id.subject_add);
        button1 = (Button) view.findViewById(R.id.button2);
        editText = (EditText) view.findViewById(R.id.editText4);
        fab = view.findViewById(R.id.fab);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.semester, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem.setAdapter(arrayAdapter);
        /*String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        int month = Integer.parseInt(currentDate.substring(3, 5));
        int year = Integer.parseInt(currentDate.substring(6));
        if (month >= 1 && month <= 7) {
            if (year == 2020) {
                semester = 2;
            } else if (year == 2021) {
                semester = 4;
            } else if (year == 2022) {
                semester = 6;
            } else if (year == 2023) {
                semester = 8;
            }
        } else {
            if (year == 2020) {
                semester = 3;
            } else if (year == 2021) {
                semester = 5;
            } else if (year == 2022) {
                semester = 7;
            }
        }*/
        semester = Integer.parseInt(manipulatesem(batch));
        sem.setSelection(Integer.parseInt(manipulatesem(batch)) - 1);
        if (semester != 0) {
            try {
                progressBar.setVisibility(View.VISIBLE);
                getSubjects(section, String.valueOf(semester));
            } catch (Exception e) {
                progressBar.setVisibility(View.GONE);

            }
           /* try {
                progressBar.setVisibility(View.VISIBLE);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                fetchInfo = retrofit.create(FetchInfo.class);
                Call<Controller> call = fetchInfo.getstudymaterialsubject(semester, section);
                call.enqueue(new Callback<Controller>() {
                    @Override
                    public void onResponse(Call<Controller> call, Response<Controller> response) {
                        if (!(response.isSuccessful())) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                            return;
                        }

                        progressBar.setVisibility(View.GONE);


                        List<Studymaterial> list = response.body().getStudymaterial();
                        if (list != null) {
                            recyclerView.setAdapter(new StudyMaterialSubjectAdap(list, getContext(), section, semester));
                        } else {
                            // Toast.makeText(getContext(), "No Data Available!", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Controller> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);


                        Toast.makeText(getContext(), "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

                    }
                });
            } catch (Exception e) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getContext(), "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

            }*/
        }
        sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                semester = Integer.parseInt(text);
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    getSubjects(section, String.valueOf(semester));
                    subject_add.setVisibility(View.GONE);
                    fab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_input_add));

                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);

                }
                /*try {
                    progressBar.setVisibility(View.VISIBLE);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    fetchInfo = retrofit.create(FetchInfo.class);
                    Call<Controller> call = fetchInfo.getstudymaterialsubject(semester, section);
                    call.enqueue(new Callback<Controller>() {
                        @Override
                        public void onResponse(Call<Controller> call, Response<Controller> response) {
                            if (!(response.isSuccessful())) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                                return;
                            }

                            progressBar.setVisibility(View.GONE);


                            List<Studymaterial> list = response.body().getStudymaterial();
                            if (list != null) {
                                recyclerView.setAdapter(new StudyMaterialSubjectAdap(list, getContext(), section, semester));
                            } else {
                                //   Toast.makeText(getContext(), "No Data Available!", Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<Controller> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);


                            Toast.makeText(getContext(), "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

                        }
                    });
                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(getContext(), "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (admin) {
           // FloatingActionButton fab = view.findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);
            /*if(assignment.equals("yes")){
                //fab.setBackgroundResource(R.drawable.ic_open_book_top_view_svgrepo_com);
               // fab.setImageResource(R.drawable.ic_open_book_top_view_svgrepo_com);

            }else {
                //fab.setBackgroundResource();
            }*/
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (assignment.equals("ghfhgf")) {
                        if (subject_ids != null && subject_ids.size() > 0) {
                            //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AssignmentUP(section, "", subject_ids, subject_name, batch)).addToBackStack(null).commit();
                        } else {
                            Toast.makeText(getContext(), "No subjects available for this semester", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        //fab.setVisibility(View.GONE);
                        if(subject_add.getVisibility()==View.VISIBLE){
                            subject_add.setVisibility(View.GONE);
                            fab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_input_add));

                        }else {
                            fab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_delete));
                            subject_add.setVisibility(View.VISIBLE);
                        }
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (!editText.getText().toString().equals("")) {
                                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
                                    alertdialog.setCancelable(false)
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Sty();
                                                    subject_add.setVisibility(View.GONE);
                                                    fab.setVisibility(View.VISIBLE);
                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                    subject_add.setVisibility(View.GONE);
                                                    fab.setVisibility(View.VISIBLE);

                                                }
                                            }).setMessage("Are you sure to add " + editText.getText().toString() + " to Subjects");
                                    AlertDialog alertDialog = alertdialog.create();
                                    alertDialog.setTitle("Add Subject");
                                    alertDialog.show();

                                } else {
                                    Toast.makeText(getContext(), "Enter Valid Details", Toast.LENGTH_LONG).show();
                                    subject_add.setVisibility(View.GONE);
                                    fab.setVisibility(View.VISIBLE);

                                }

                            }
                        });

                       /* if (subject_ids != null&&subject_ids.size()>0) {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Studymf(section, "", subject_ids, subject_name, batch)).addToBackStack(null).commit();
                        }else{
                            Toast.makeText(getContext(),"No subjects available for this semester",Toast.LENGTH_SHORT).show();

                        }*/
                    }

                }
            });


            FloatingActionButton fab1 = view.findViewById(R.id.fab2);
            if (assignment.equals("yes")) {
               fab1.setVisibility(View.GONE);

                fab1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fab1.setVisibility(View.GONE);

                        subject_add.setVisibility(View.VISIBLE);
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (!editText.getText().toString().equals("")) {
                                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
                                    alertdialog.setCancelable(false)
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Sty();
                                                    subject_add.setVisibility(View.GONE);
                                                    fab1.setVisibility(View.VISIBLE);
                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                    subject_add.setVisibility(View.GONE);
                                                    fab1.setVisibility(View.VISIBLE);

                                                }
                                            }).setMessage("Are you sure to add " + editText.getText().toString() + " to Subjects");
                                    AlertDialog alertDialog = alertdialog.create();
                                    alertDialog.setTitle("Add Subject");
                                    alertDialog.show();

                                } else {
                                    Toast.makeText(getContext(), "Enter Valid Details", Toast.LENGTH_LONG).show();
                                    subject_add.setVisibility(View.GONE);
                                    fab1.setVisibility(View.VISIBLE);

                                }

                            }
                        });

                        // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SubjectAdd(section, batch)).addToBackStack(null).commit();

                    }
                });
            }else{
                fab1.setVisibility(View.GONE);
            }
        } else {

           // FloatingActionButton fab = view.findViewById(R.id.fab);
            fab.setVisibility(View.GONE);


            FloatingActionButton fab1 = view.findViewById(R.id.fab2);
            fab1.setVisibility(View.GONE);

        }



     /*   button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (semester != 0) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SecondStudyF(section, semester)).addToBackStack(null).commit();
                   /* button.setVisibility(View.GONE);
                    sem.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    fetchInfo = retrofit.create(FetchInfo.class);
                    Call<Controller> call = fetchInfo.getstudymaterialsubject(semester, section);
                    call.enqueue(new Callback<Controller>() {
                        @Override
                        public void onResponse(Call<Controller> call, Response<Controller> response) {
                            if (!(response.isSuccessful())) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                                return;
                            }

                            progressBar.setVisibility(View.GONE);


                            List<Studymaterial> list = response.body().getStudymaterial();
                            recyclerView.setAdapter(new StudyMaterialSubjectAdap(list, getContext(), section,semester));
                        }

                        @Override
                        public void onFailure(Call<Controller> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);


                            Toast.makeText(getContext(), "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

                        }
                    });*/

           /*     } else {
                    Toast.makeText(getContext(), "Enter Valid Semester", Toast.LENGTH_SHORT).show();
                }
            }
        });*/


        return view;
    }

    public void getSubjects(String class_id, String semester) {
        String base = "https://academic-manager-nitt.el.r.appspot.com/";

        // String base="https://academic-manager-nitt.el.r.appspot.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
        Call<List<AdminSubject>> call = fetchInfo.getSubjects(class_id, semester);
        call.enqueue(new Callback<List<AdminSubject>>() {
            @Override
            public void onResponse(Call<List<AdminSubject>> call, Response<List<AdminSubject>> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                    return;
                }
                subject_ids = new ArrayList<>();
                subject_name = new ArrayList<>();
                for (AdminSubject adminSubject : response.body()) {
                    subject_ids.add(adminSubject.getId().toString());
                    subject_name.add(adminSubject.getSubjectCode());
                }

                recyclerView.setAdapter(new StudyMaterialSubjectAdap(null, getContext(), section, Integer.parseInt(semester), subject_ids, subject_name, assignment, roll, courseplan, admin,batch));
                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<List<AdminSubject>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

            }
        });
    }

    public String manipulatesem(String batc) {
        Log.d("batch", batc);
        int semester = 0;
        int batch = Integer.parseInt(batc);
        Log.d("batch", String.valueOf(batch));
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int yearofstudy = year - batch;
        Log.d("batch", String.valueOf(yearofstudy));
        switch (yearofstudy) {
            case 0:
                calendar.get(Calendar.MONTH);
                if (calendar.get(Calendar.MONTH) <= 5) {

                } else {
                    semester = 1;
                }
                break;

            case 1:
                calendar.get(Calendar.MONTH);
                if (calendar.get(Calendar.MONTH) <= 5) {
                    semester = 2;
                } else {
                    semester = 3;
                }
                break;
            case 2:
                calendar.get(Calendar.MONTH);
                if (calendar.get(Calendar.MONTH) <= 5) {
                    semester = 4;
                } else {
                    semester = 5;
                }
                break;
            case 3:
                calendar.get(Calendar.MONTH);
                if (calendar.get(Calendar.MONTH) <= 5) {
                    semester = 6;
                } else {
                    semester = 7;
                }
                break;
            case 4:
                calendar.get(Calendar.MONTH);
                if (calendar.get(Calendar.MONTH) <= 5) {
                    semester = 8;
                }
                break;


        }
        return String.valueOf(semester);


    }

    public void Sty() {
        progressBar.setVisibility(View.VISIBLE);
        String base = "https://academic-manager-nitt.el.r.appspot.com/";

        // String base="https://academic-manager-nitt.el.r.appspot.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
        RequestBody class_id = RequestBody.create(MediaType.parse("text/plain"), section);
        RequestBody semester1 = RequestBody.create(MediaType.parse("text/plain"), manipulatesem(batch));
        RequestBody subject_code = RequestBody.create(MediaType.parse("text/plain"), editText.getText().toString());
        Call<AdminSubject> call = fetchInfo.sendSubjects(class_id, semester1, subject_code);
        call.enqueue(new Callback<AdminSubject>() {
            @Override
            public void onResponse(Call<AdminSubject> call, Response<AdminSubject> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "No response from the server", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                Toast.makeText(getContext(), "Subject Successfully Added", Toast.LENGTH_SHORT).show();
                editText.setText("");
                progressBar.setVisibility(View.GONE);
                getSubjects(section, manipulatesem(batch));
                sem.setSelection(Integer.parseInt(manipulatesem(batch)) - 1);


            }

            @Override
            public void onFailure(Call<AdminSubject> call, Throwable t) {
                Toast.makeText(getContext(), "No response from the server", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }



}




