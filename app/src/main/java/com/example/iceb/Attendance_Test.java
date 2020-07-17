package com.example.iceb;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Attendance_Test extends Fragment {

    RecyclerView recyclerView;
    public List<String> list1 = new ArrayList<>();

    public Attendance_Test() {
        // Required empty public constructor
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
        FirebaseDatabase.getInstance().getReference().child("SubName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //list1= (List<String>) dataSnapshot.child("Subj").getValue();
              //  DataSnapshot dataSnapshot1 = (DataSnapshot) dataSnapshot.child("Subj").getChildren().iterator();

                    long count=  dataSnapshot.getChildrenCount();
                    for(int i=0;i<count;i++){
                        list1.add(dataSnapshot.child("s"+i).getValue().toString());
                    }
                    recyclerView.setAdapter(new Attendance_test_Adap(list1,getContext()));

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
        });




        return view;
    }
}
