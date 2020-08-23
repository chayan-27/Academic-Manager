package com.example.iceb.AdminUsage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iceb.R;
import com.example.iceb.server2.AssignmentRe;
import com.example.iceb.server2.FetchInfo2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class AssignResponsesF extends Fragment {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    String title;
    String subject;
    Integer semester;
    String section;
    String assignment_id;

    @SuppressLint("ValidFragment")
    public AssignResponsesF(String title, String subject, Integer semester, String section,String assignment_id) {
        this.title = title;
        this.subject = subject;
        this.semester = semester;
        this.section = section;
        this.assignment_id=assignment_id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assign_responses, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        progressBar = (ProgressBar) view.findViewById(R.id.progresso);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getResponses(assignment_id);
       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NotiApi notiApi = retrofit.create(NotiApi.class);
        Call<Controller> call = notiApi.getfiles(section, semester, title, subject);
        call.enqueue(new Callback<Controller>() {
            @Override
            public void onResponse(Call<Controller> call, Response<Controller> response) {
                if (!(response.isSuccessful())) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                    return;
                }
                List<UserFile> list = response.body().getUserFiles();
                List<Integer> list1 = new ArrayList<>();
                for (UserFile userFile : list) {
                    list1.add(userFile.getRollNo());
                }
                Collections.sort(list1);
                try {


                    Toast.makeText(getContext(), list1.size() + "", Toast.LENGTH_LONG).show();
                    if (list1.size() > 0) {
                        recyclerView.setAdapter(new ResAdapter(section, semester, subject, title, list1, progressBar, getContext()));
                    }
                } catch (Exception e) {
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<Controller> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

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

                recyclerView.setAdapter(new ResAdapter(section,semester,subject,title,response.body(),progressBar,getContext()));
                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<List<AssignmentRe>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();

            }
        });
    }
}
