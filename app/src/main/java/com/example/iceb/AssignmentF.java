package com.example.iceb;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iceb.AdminUsage.AssignmentUP;
import com.example.iceb.AdminUsage.Studymf;
import com.example.iceb.server.Assignment;
import com.example.iceb.server.Controller;
import com.example.iceb.server2.AdminAssignment;
import com.example.iceb.server2.FetchInfo2;
import com.example.iceb.server2.SubjectResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class AssignmentF extends Fragment {
    RecyclerView recyclerView;
    Spinner sem;
    Button button;
    TextView textView;
    int semester = 0;
    FetchInfo fetchInfo;
    String section;
    ProgressBar progressBar;
    List<Assignment> list1;
    int roll;
    String subjectt;
    List<String> subject_id;
    List<String> subject_name;

    @SuppressLint("ValidFragment")
    public AssignmentF(String section, int roll,String subjectt,List<String> subject_id,List<String> subject_name) {
        // Required empty public constructor
        this.section = section;
        this.roll = roll;
        this.subjectt=subjectt;
        this.subject_id=subject_id;
        this.subject_name=subject_name;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assignment, container, false);
        recyclerView = view.findViewById(R.id.recycle);
        sem = view.findViewById(R.id.spinner);
        button = view.findViewById(R.id.button);
        textView = view.findViewById(R.id.textView);
        progressBar = view.findViewById(R.id.progresso);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.semester, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem.setAdapter(arrayAdapter);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
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
        }
        sem.setSelection(semester - 1);

        if (semester != 0) {
            materials(section);
            /*try {
                progressBar.setVisibility(View.VISIBLE);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                fetchInfo = retrofit.create(FetchInfo.class);
                Call<Controller> call = fetchInfo.getAssignment(semester, section);
                call.enqueue(new Callback<Controller>() {
                    @Override
                    public void onResponse(Call<Controller> call, Response<Controller> response) {
                        if (!(response.isSuccessful())) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                            return;
                        }

                        progressBar.setVisibility(View.GONE);
                        int i = 0;
                        List<Integer> aid = new ArrayList<>();
                        List<Assignment> list3 = new ArrayList<>();


                        List<Assignment> list = response.body().getAssignments();
                        if (list != null) {
                            list1 = new ArrayList<>(list.size());
                            for (Assignment assignment : list) {
                                aid.add(assignment.getAid());
                                list1.add(assignment);
                            }
                            Collections.sort(aid);
                            for (int i1 = 0; i1 < aid.size(); i1++) {
                                int j = aid.get(i1);
                                for (Assignment assignment : list1) {
                                    if (assignment.getAid() == j) {
                                        list3.add(assignment);
                                    }
                                }
                            }
                            Collections.reverse(list3);

                            recyclerView.setAdapter(new AssignmentAdapter(list3, getContext(), section, progressBar, roll, semester));
                        } else {
                          //  Toast.makeText(getContext(), "No Data Available!", Toast.LENGTH_LONG).show();

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
                materials(section);
                /*try {
                    progressBar.setVisibility(View.VISIBLE);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    fetchInfo = retrofit.create(FetchInfo.class);
                    Call<Controller> call = fetchInfo.getAssignment(semester, section);
                    call.enqueue(new Callback<Controller>() {
                        @Override
                        public void onResponse(Call<Controller> call, Response<Controller> response) {
                            if (!(response.isSuccessful())) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                                return;
                            }

                            progressBar.setVisibility(View.GONE);
                            int i = 0;
                            List<Integer> aid = new ArrayList<>();
                            List<Assignment> list3 = new ArrayList<>();


                            List<Assignment> list = response.body().getAssignments();
                            if (list != null) {
                                list1 = new ArrayList<>(list.size());
                                for (Assignment assignment : list) {
                                    aid.add(assignment.getAid());
                                    list1.add(assignment);
                                }
                                Collections.sort(aid);
                                for (int i1 = 0; i1 < aid.size(); i1++) {
                                    int j = aid.get(i1);
                                    for (Assignment assignment : list1) {
                                        if (assignment.getAid() == j) {
                                            list3.add(assignment);
                                        }
                                    }
                                }
                                Collections.reverse(list3);
                                recyclerView.setAdapter(new AssignmentAdapter(list3, getContext(), section, progressBar, roll, semester));
                            }else {
                                //Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();

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

      /*  button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (semester != 0) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SecondAssignmentF(section, roll, semester)).addToBackStack(null).commit();

                /*    button.setVisibility(View.GONE);
                    sem.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    fetchInfo = retrofit.create(FetchInfo.class);
                    Call<Controller> call = fetchInfo.getAssignment(semester, section);
                    call.enqueue(new Callback<Controller>() {
                        @Override
                        public void onResponse(Call<Controller> call, Response<Controller> response) {
                            if (!(response.isSuccessful())) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                                return;
                            }

                            progressBar.setVisibility(View.GONE);
                            int i = 0;
                            List<Integer> aid = new ArrayList<>();
                            List<Assignment> list3 = new ArrayList<>();


                            List<Assignment> list = response.body().getAssignments();
                            list1 = new ArrayList<>(list.size());
                            for (Assignment assignment : list) {
                                aid.add(assignment.getAid());
                                list1.add(assignment);
                            }
                            Collections.sort(aid);
                            for (int i1 = 0; i1 < aid.size(); i1++) {
                                int j = aid.get(i1);
                                for (Assignment assignment : list1) {
                                    if (assignment.getAid() == j) {
                                        list3.add(assignment);
                                    }
                                }
                            }
                            Collections.reverse(list3);
                            recyclerView.setAdapter(new AssignmentAdapter(list3, getContext(), section, progressBar,roll,semester));
                        }

                        @Override
                        public void onFailure(Call<Controller> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);


                            Toast.makeText(getContext(), "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

                        }
                    });*/

               /* } else {
                    Toast.makeText(getContext(), "Enter Valid Semester", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        /*FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AssignmentUP(section,"",subject_id,subject_name)).addToBackStack(null).commit();

            }
        });*/

        return view;
    }

    public void materials(String subject_id){
        //String base = "http://192.168.1.6:8000/";
        String base = "http://192.168.1.6:8000/";
       // String base="https://academic-manager-nitt.el.r.appspot.com/";
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
       Call<List<AdminAssignment>> call=fetchInfo.getAssignments(subject_id);
       call.enqueue(new Callback<List<AdminAssignment>>() {
           @Override
           public void onResponse(Call<List<AdminAssignment>> call, Response<List<AdminAssignment>> response) {
               if (!response.isSuccessful()) {
                   progressBar.setVisibility(View.GONE);
                   Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                   return;
               }
               recyclerView.setAdapter(new AssignmentAdapter(roll,response.body(),subjectt,getContext()));

               progressBar.setVisibility(View.GONE);
           }

           @Override
           public void onFailure(Call<List<AdminAssignment>> call, Throwable t) {
               progressBar.setVisibility(View.GONE);
               Toast.makeText(getContext(), "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

           }
       });
    }


}
