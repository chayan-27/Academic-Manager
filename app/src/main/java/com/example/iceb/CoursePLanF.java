package com.example.iceb;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iceb.server.Controller;
import com.example.iceb.server.Courseplan;

import java.text.SimpleDateFormat;
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
public class CoursePLanF extends Fragment {
    RecyclerView recyclerView;
    Spinner sem;
    Button button;
    TextView textView;
    int semester = 0;
    FetchInfo fetchInfo;
    String section;
    ProgressBar progressBar;


    @SuppressLint("ValidFragment")
    public CoursePLanF(String section) {
        // Required empty public constructor
        this.section = section;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_p_lan, container, false);
        recyclerView = view.findViewById(R.id.recycle);
        sem = view.findViewById(R.id.spinner);
        button = view.findViewById(R.id.button);
        textView = view.findViewById(R.id.textView);
        progressBar = view.findViewById(R.id.progresso);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
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
            try {
                progressBar.setVisibility(View.VISIBLE);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                fetchInfo = retrofit.create(FetchInfo.class);
                Call<Controller> call = fetchInfo.getcourseplan(semester, section);
                call.enqueue(new Callback<Controller>() {
                    @Override
                    public void onResponse(Call<Controller> call, Response<Controller> response) {
                        if (!(response.isSuccessful())) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                            return;
                        }

                        progressBar.setVisibility(View.GONE);


                        List<Courseplan> list = response.body().getCourseplan();
                        if (list != null) {
                            recyclerView.setAdapter(new CoursePlanAdapter(list, getContext(), section, progressBar));
                        }else {
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

            }

        }
        sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                semester = Integer.parseInt(text);
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    animation(0,50,10000,progressBar);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    fetchInfo = retrofit.create(FetchInfo.class);
                    Call<Controller> call = fetchInfo.getcourseplan(semester, section);
                    call.enqueue(new Callback<Controller>() {
                        @Override
                        public void onResponse(Call<Controller> call, Response<Controller> response) {
                            if (!(response.isSuccessful())) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                                return;
                            }

                            progressBar.setVisibility(View.GONE);


                            List<Courseplan> list = response.body().getCourseplan();
                            if (list != null) {
                                recyclerView.setAdapter(new CoursePlanAdapter(list, getContext(), section, progressBar));
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

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (semester != 0) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SecondCoursePlanF(section, semester)).addToBackStack(null).commit();

                  /*  button.setVisibility(View.GONE);
                    sem.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    fetchInfo = retrofit.create(FetchInfo.class);
                    Call<Controller> call = fetchInfo.getcourseplan(semester, section);
                    call.enqueue(new Callback<Controller>() {
                        @Override
                        public void onResponse(Call<Controller> call, Response<Controller> response) {
                            if (!(response.isSuccessful())) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                                return;
                            }

                            progressBar.setVisibility(View.GONE);


                            List<Courseplan> list = response.body().getCourseplan();
                            recyclerView.setAdapter(new CoursePlanAdapter(list, getContext(), section,progressBar));
                        }

                        @Override
                        public void onFailure(Call<Controller> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);


                            Toast.makeText(getContext(), "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

                        }
                    });*/

              /*  } else {
                    Toast.makeText(getContext(), "Enter Valid Semester", Toast.LENGTH_SHORT).show();
                }
            }
        });*/


        return view;
    }

    public void animation(int a, int b, int time, ProgressBar progressBar) {
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", a, b);
        animation.setDuration(time);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //do something when the countdown is complete
                if (b == 50) {
                    animation(50, 75, 20000, progressBar);
                } else if (b == 75) {
                    animation(75, 88, 40000, progressBar);
                } else if (b == 88) {
                    animation(88, 94, 80000, progressBar);
                } else if (b == 94) {
                    animation(94, 97, 160000, progressBar);
                } else if (b == 97) {
                    animation(97, 99, 320000, progressBar);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animation.start();
    }


}
