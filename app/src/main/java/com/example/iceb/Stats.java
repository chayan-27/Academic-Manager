package com.example.iceb;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class Stats extends Fragment {
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    List<String> present;
    List<String> absent;

    @SuppressLint("ValidFragment")
    public Stats(List<String> present, List<String>absent) {
        // Required empty public constructor
        this.present=present;
        this.absent=absent;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        recyclerView1=(RecyclerView)view.findViewById(R.id.present);
        recyclerView2=(RecyclerView)view.findViewById(R.id.absent);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView2.setLayoutManager(linearLayoutManager1);
        recyclerView1.setAdapter(new Stats_Adapter(present));
        recyclerView2.setAdapter(new Stats_Adapter(absent));
        return view;
    }
}
