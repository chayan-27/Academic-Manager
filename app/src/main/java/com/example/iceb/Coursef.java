package com.example.iceb;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Coursef extends Fragment {
    RecyclerView recyclerView;
    List<String> list;
    SharedPreferences sharedPreferences;
    List<String> list1;
    String num;
    int number;
    Handler handler;
    ProgressBar progressBar;
    String section;
    int semester = 0;
    int roll;
    String ar[];
    DataBase dataBase;



    public Coursef(String section, int roll) {
        // Required empty public constructor
        this.section = section;
        this.roll = roll;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coursef, container, false);
        progressBar = view.findViewById(R.id.progresso);
        dataBase=DataBase.getInstance(getContext());


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


        /*list = new ArrayList<>();
        list1 = new ArrayList<>();

        sharedPreferences = getContext().getSharedPreferences("App_settings", MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet("DATE_LIST", null);

        if (set != null) {
            list.addAll(set);
            ar = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                String answer = list.get(i);
                num = answer.substring(0, answer.indexOf(" "));
                number = Integer.parseInt(num);
                ar[number] = answer;
            }
            list1 = Arrays.asList(ar);
            Collections.reverse(list1);

        } else {
            list1.add("12 Welcome To ICE 2k23$00$00");
        }*/

        /*list.add("ICPC13");
        list.add("ICIR");
        list.add("HSIR11");
        list.add("MAIR12");
        list.add("CHIR11");
        list.add("CHIR12");*/

        recyclerView = (RecyclerView) view.findViewById(R.id.course);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        TestLive();

       // recyclerView.setAdapter(new CourseAdapter(list1, getContext(), section, semester, roll, progressBar));

       /* MutableLiveData<Set<String>> setLiveData = new MutableLiveData<>();
        setLiveData.setValue(sharedPreferences.getStringSet("DATE_LIST", null));

        setLiveData.observe(getViewLifecycleOwner(), new Observer<Set<String>>() {
            @Override
            public void onChanged(Set<String> strings) {
                Log.d("hello","Onchanged called");
                Set<String> set = strings;
                list = new ArrayList<>();
                list1 = new ArrayList<>();

                if (set != null) {
                    list.addAll(set);
                    ar = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        String answer = list.get(i);
                        num = answer.substring(0, answer.indexOf(" "));
                        number = Integer.parseInt(num);
                        ar[number] = answer;
                    }
                    list1 = Arrays.asList(ar);
                    Collections.reverse(list1);

                } else {
                    list1.add("12 Welcome To ICE 2k23$00$00");
                }

                recyclerView.setAdapter(new CourseAdapter(list1, getContext(), section, semester, roll, progressBar));
            }
        });*/


        // Inflate the layout for this fragment
        return view;

    }

    public void TestLive(){
        LiveData<List<LiveTest>> liveTests=dataBase.getDao().loadall();
        liveTests.observe(getViewLifecycleOwner(), new Observer<List<LiveTest>>() {
            @Override
            public void onChanged(List<LiveTest> liveTests) {
                List<String> list=new ArrayList<>();
                for(LiveTest liveTest:liveTests){
                    list.add(" "+liveTest.getBody());
                }
                if(list.size()==0){
                    list.add("12 Welcome To ICE 2k23$00$00");
                }else{
                    Collections.reverse(list);
                }
                recyclerView.setAdapter(new CourseAdapter(list, getContext(), section, semester, roll, progressBar));

            }
        });
    }
}
