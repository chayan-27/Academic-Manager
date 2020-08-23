package com.example.iceb.AdminUsage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iceb.AssignmentAdapter;
import com.example.iceb.R;
import com.example.iceb.server2.AdminAssignment;
import com.example.iceb.server2.AssignmentRe;
import com.example.iceb.server2.FetchInfo2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class TitlesF extends Fragment {
    String section;
    Integer sem;
    String subject;
    RecyclerView recyclerView;
    ProgressBar progressBar;


    public TitlesF(String section, Integer sem, String subject) {
        // Required empty public constructor
        this.section = section;
        this.sem = sem;
        this.subject = subject;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_titles, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        progressBar = (ProgressBar) view.findViewById(R.id.progresso);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        materials(section);
       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NotiApi notiApi = retrofit.create(NotiApi.class);
        Call<Controller> call = notiApi.gettitles(section, sem, subject);
        call.enqueue(new Callback<Controller>() {
            @Override
            public void onResponse(Call<Controller> call, Response<Controller> response) {
                if (!(response.isSuccessful())) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                    return;
                }

                List<AssignUPC> list = response.body().getAssignments();
                List<String> list1 = new ArrayList<>();
                for (AssignUPC assignUPC : list) {
                    list1.add(assignUPC.getTitle());
                }
                progressBar.setVisibility(View.GONE);


                recyclerView.setAdapter(new TitleAdapter(list1, getContext(), subject, sem, section));

            }

            @Override
            public void onFailure(Call<Controller> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();



            }
        });*/


        return view;
    }

    public void getResponses(String assignment_id){
        String base = "http://192.168.1.6:8000/";
       // String base="https://academic-manager-nitt.el.r.appspot.com/";
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
        Call<List<AssignmentRe>> call=fetchInfo.getResponsesofAssign(assignment_id);
        call.enqueue(new Callback<List<AssignmentRe>>() {
            @Override
            public void onResponse(Call<List<AssignmentRe>> call, Response<List<AssignmentRe>> response) {
                if (!(response.isSuccessful())) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<AssignmentRe>> call, Throwable t) {

            }
        });
    }

    public void materials(String subject_id){
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
                //recyclerView.setAdapter(new AssignmentAdapter(roll,response.body(),subjectt,getContext()));
                recyclerView.setAdapter(new TitleAdapter(response.body(),getContext(),subject,sem,section));

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
