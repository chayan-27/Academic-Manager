package com.example.iceb;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.iceb.server.Controller;
import com.example.iceb.server.Studymaterial;

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
public class StudyMaterialAF extends Fragment {
    Integer semester;
    String subject;
    String section;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @SuppressLint("ValidFragment")
    public StudyMaterialAF(Integer semester, String subject, String section) {
        // Required empty public constructor
        this.section = section;
        this.semester = semester;
        this.subject = subject;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_study_material_a, container, false);
        recyclerView = view.findViewById(R.id.recycle);
        progressBar = view.findViewById(R.id.pres);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(linearLayoutManager);
        animation();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo fetchInfo = retrofit.create(FetchInfo.class);
        Call<Controller> call=fetchInfo.getstudymaterial(semester,section,subject);
        call.enqueue(new Callback<Controller>() {
            @Override
            public void onResponse(Call<Controller> call, Response<Controller> response) {
                if (!(response.isSuccessful())) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                    return;
                }
                List<Studymaterial> list = response.body().getStudymaterial();
                recyclerView.setAdapter(new StudyMaterialAdapter(list, getContext(), section,subject,progressBar));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Controller> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();


            }
        });


        return view;
    }

    public void animation(){
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 180);
        animation.setDuration(20000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) { }

            @Override
            public void onAnimationEnd(Animator animator) {
                //do something when the countdown is complete
            }

            @Override
            public void onAnimationCancel(Animator animator) { }

            @Override
            public void onAnimationRepeat(Animator animator) { }
        });
        animation.start();
    }

}
