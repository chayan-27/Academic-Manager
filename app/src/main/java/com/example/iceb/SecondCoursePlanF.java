package com.example.iceb;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.iceb.server.Controller;
import com.example.iceb.server.Courseplan;

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
public class SecondCoursePlanF extends Fragment {
    RecyclerView recyclerView;
    int semester = 0;
    FetchInfo fetchInfo;
    String section;
    ProgressBar progressBar;

    @SuppressLint("ValidFragment")
    public SecondCoursePlanF(String section, int semester) {
        // Required empty public constructor
        this.section = section;
        this.semester = semester;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_second_course_plan, container, false);
        recyclerView = view.findViewById(R.id.recycle);
        progressBar = view.findViewById(R.id.progresso);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
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
        });

        return view;
    }
}
