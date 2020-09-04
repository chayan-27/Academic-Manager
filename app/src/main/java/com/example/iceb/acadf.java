package com.example.iceb;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class acadf extends Fragment {
    List<String> list;
    int ar[];
    RecyclerView recyclerView;
    String section;
    int roll;
    boolean admin;
    String batch;

    @SuppressLint("ValidFragment")
    public acadf(String section,int roll,boolean admin,String batch) {
        this.section = section;
        this.roll=roll;
        this.admin=admin;
        this.batch=batch;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acadf, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.academics);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        list = new ArrayList<>();
        list.add("TimeTable");
        list.add("Materials");
        list.add("Course Plan");
        list.add("Attendance");
        list.add("Poll");
        list.add("Drive");
        ar = new int[]{R.drawable.timeic, R.drawable.stfinal, R.drawable.planic, R.drawable.attendanceic, R.drawable.ic_pollic, R.drawable.driveic1};
        // Inflate the layout for this fragment

        recyclerView.setAdapter(new AcademicsAdapter(list, getContext(), ar, section,roll,admin,batch));


         return view;
    }

}
