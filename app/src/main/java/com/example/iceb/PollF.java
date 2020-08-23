package com.example.iceb;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.iceb.AdminUsage.PollUpload;
import com.example.iceb.AdminUsage.Studymf;
import com.example.iceb.AdminUsage.SubjectAdd;
import com.example.iceb.server.Controller;
import com.example.iceb.server.Poll;
import com.example.iceb.server2.AdminPollUP;
import com.example.iceb.server2.FetchInfo2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
public class PollF extends Fragment {
    String section;
    Integer semester;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    FetchInfo fetchInfo;
    int roll;
    SharedPreferences myuser;
    boolean admin;

    public PollF(String section,int roll,boolean admin) {
        // Required empty public constructor

        this.section = section;
        this.roll=roll;
        this.admin=admin;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_poll, container, false);
        recyclerView = view.findViewById(R.id.recycle);
        progressBar = view.findViewById(R.id.progresso);
        myuser=getContext().getSharedPreferences("Myapp2", Context.MODE_PRIVATE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
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
       // send(section,semester);

        getPollList();

        if(admin){
            FloatingActionButton fab = view.findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PollUpload(section)).addToBackStack(null).commit();

                }
            });
        }else{
            FloatingActionButton fab = view.findViewById(R.id.fab);
            fab.setVisibility(View.GONE);
        }





        return view;
    }


  /*  public void send(String section, Integer semester) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        fetchInfo = retrofit.create(FetchInfo.class);
        Call<Controller> call = fetchInfo.getpolllist(semester, section);
        call.enqueue(new Callback<Controller>() {
            @Override
            public void onResponse(Call<Controller> call, Response<Controller> response) {
                if (!(response.isSuccessful())) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                    return;
                }

                List<Poll> list = response.body().getPoll();
                if (list != null) {
                    recyclerView.setAdapter(new PollAdapter(list, section, semester,roll,getContext(),progressBar));
                  //  Toast.makeText(getContext(), "list found"+list.size(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "list not found", Toast.LENGTH_LONG).show();

                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Controller> call, Throwable t) {
                progressBar.setVisibility(View.GONE);


                Toast.makeText(getContext(), "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();


            }
        });

    }*/

    private void getPollList(){
       String base="https://academic-manager-nitt.el.r.appspot.com/";
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
        Call<List<AdminPollUP>> call=fetchInfo.getPollforClassroom(section);
        call.enqueue(new Callback<List<AdminPollUP>>() {
            @Override
            public void onResponse(Call<List<AdminPollUP>> call, Response<List<AdminPollUP>> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "No response from the server", Toast.LENGTH_LONG).show();


                    // Toast.makeText(getContext(), subject_id+" : "+topic+":"+currentDate, Toast.LENGTH_LONG).show();
                    return;
                }
                recyclerView.setAdapter(new PollAdapter(response.body(), section, semester,roll,getContext(),progressBar));
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<AdminPollUP>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
}
