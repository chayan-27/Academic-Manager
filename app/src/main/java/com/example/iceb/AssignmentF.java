package com.example.iceb;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @SuppressLint("ValidFragment")
    public AssignmentF(String section) {
        // Required empty public constructor
        this.section = section;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.semester, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem.setAdapter(arrayAdapter);
        sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                semester = Integer.parseInt(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                semester = 0;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (semester != 0) {
                    button.setVisibility(View.GONE);
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
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                                return;
                            }

                            progressBar.setVisibility(View.INVISIBLE);
                            int i = 0;
                            List<Integer> aid=new ArrayList<>();
                            List<Assignment> list3=new ArrayList<>();


                            List<Assignment> list = response.body().getAssignments();
                            list1 = new ArrayList<>(list.size());
                            for (Assignment assignment : list) {
                                aid.add(assignment.getAid());
                                list1.add( assignment);
                            }
                            Collections.sort(aid);
                            for(int i1=0;i1<aid.size();i1++){
                                int j=aid.get(i1);
                                for(Assignment assignment:list1){
                                    if(assignment.getAid()==j){
                                        list3.add(assignment);
                                    }
                                }
                            }
                            Collections.reverse(list3);
                            recyclerView.setAdapter(new AssignmentAdapter(list3, getContext(), section,progressBar));
                        }

                        @Override
                        public void onFailure(Call<Controller> call, Throwable t) {
                            progressBar.setVisibility(View.INVISIBLE);


                            Toast.makeText(getContext(), "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

                        }
                    });

                } else {
                    Toast.makeText(getContext(), "Enter Valid Semester", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}