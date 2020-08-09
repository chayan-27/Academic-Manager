package com.example.iceb;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.iceb.server.Assignment;
import com.example.iceb.server.Controller;

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
public class SecondAssignmentF extends Fragment {
    RecyclerView recyclerView;
    int semester = 0;
    FetchInfo fetchInfo;
    String section;
    ProgressBar progressBar;
    List<Assignment> list1;
    int roll;
    @SuppressLint("ValidFragment")
    public SecondAssignmentF(String section, int roll, int semester) {
        // Required empty public constructor
        this.section = section;
        this.roll=roll;
        this.semester=semester;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_second_assignment, container, false);
        recyclerView = view.findViewById(R.id.recycle);
        progressBar = view.findViewById(R.id.progresso);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(linearLayoutManager);

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
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                    return;
                }

                progressBar.setVisibility(View.INVISIBLE);
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
                progressBar.setVisibility(View.INVISIBLE);


                Toast.makeText(getContext(), "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

            }
        });


        return view;
    }
}
