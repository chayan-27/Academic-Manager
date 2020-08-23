package com.example.iceb;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.iceb.server2.AdminSubject;
import com.example.iceb.server2.FetchInfo2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class Attendance_Test extends Fragment {

    RecyclerView recyclerView;
    public List<String> list1 = new ArrayList<>();
    String batch;
    String class_id;

    public Attendance_Test(String batch,String class_id) {
        // Required empty public constructor
        this.batch = batch;
        this.class_id=class_id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendance__test, container, false);



       /* list.add("ICIR");
        list.add("CHIR");
        list.add("ICPC");
        list.add("HSIR");
        list.add("MAIR");*/
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        getSubjects(class_id,manipulatesem(batch));
        /*FirebaseDatabase.getInstance().getReference().child("SubName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //list1= (List<String>) dataSnapshot.child("Subj").getValue();
                //  DataSnapshot dataSnapshot1 = (DataSnapshot) dataSnapshot.child("Subj").getChildren().iterator();

                long count = dataSnapshot.getChildrenCount();
                for (int i = 0; i < count; i++) {
                    list1.add(dataSnapshot.child("s" + i).getValue().toString());
                }
                recyclerView.setAdapter(new Attendance_test_Adap(list1, getContext()));

                try {
                    //  Toast.makeText(getContext(), list1.size()+""+dataSnapshot.getChildrenCount(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();

            }
        });*/


        return view;
    }

    public String manipulatesem(String batc) {
        Log.d("batch",batc);
        int semester = 0;
        int batch = Integer.parseInt(batc);
        Log.d("batch", String.valueOf(batch));
        Calendar calendar=Calendar.getInstance();

        int year =calendar.get(Calendar.YEAR);
        int yearofstudy = year - batch;
        Log.d("batch", String.valueOf(yearofstudy));
        switch (yearofstudy) {
            case 0:
                calendar.get(Calendar.MONTH);
                if (calendar.get(Calendar.MONTH) <= 5) {

                } else {
                    semester = 1;
                }
                break;

            case 1:
                calendar.get(Calendar.MONTH);
                if (calendar.get(Calendar.MONTH) <= 5) {
                    semester = 2;
                } else {
                    semester = 3;
                }
                break;
            case 2:
                calendar.get(Calendar.MONTH);
                if (calendar.get(Calendar.MONTH) <= 5) {
                    semester = 4;
                } else {
                    semester = 5;
                }
                break;
            case 3:
                calendar.get(Calendar.MONTH);
                if (calendar.get(Calendar.MONTH) <= 5) {
                    semester = 6;
                } else {
                    semester = 7;
                }
                break;
            case 4:
                calendar.get(Calendar.MONTH);
                if (calendar.get(Calendar.MONTH) <= 5) {
                    semester = 8;
                }
                break;



        }
        return String.valueOf(semester);


    }

    public void getSubjects(String class_id, String semester) {
       String base="https://academic-manager-nitt.el.r.appspot.com/";
        
       // String base="https://academic-manager-nitt.el.r.appspot.com/";
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
        Call<List<AdminSubject>> call = fetchInfo.getSubjects(class_id, semester);
        call.enqueue(new Callback<List<AdminSubject>>() {
            @Override
            public void onResponse(Call<List<AdminSubject>> call, Response<List<AdminSubject>> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                    return;
                }

               List<String> subject_name = new ArrayList<>();
                for (AdminSubject adminSubject : response.body()) {

                    subject_name.add(adminSubject.getSubjectCode());
                }

                recyclerView.setAdapter(new Attendance_test_Adap(subject_name,getContext()));

               // recyclerView.setAdapter(new StudyMaterialSubjectAdap(null, getContext(), section, Integer.parseInt(semester), subject_ids, subject_name, assignment, roll, courseplan));



            }

            @Override
            public void onFailure(Call<List<AdminSubject>> call, Throwable t) {

                Toast.makeText(getContext(), "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

            }
        });
    }


}
