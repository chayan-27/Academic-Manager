package com.example.iceb;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.iceb.server.Studymaterial;

import java.util.List;

public class StudyMaterialSubjectAdap extends RecyclerView.Adapter<StudyMaterialSubjectAdap.StudyMaterialSubHolder> {
    List<Studymaterial> components;
    Context context;
    String section;
    Integer semester;
    List<String> subject_ids;
    List<String> subject_names;
    String assignment;
    int roll;
    String courseplan;


    public StudyMaterialSubjectAdap(List<Studymaterial> components, Context context, String section, Integer semester, List<String> subject_ids,
                                    List<String> subject_names, String assignment,int roll,String courseplan) {
        this.components = components;
        this.context = context;
        this.section = section;
        this.semester = semester;
        this.subject_ids = subject_ids;
        this.subject_names = subject_names;
        this.assignment = assignment;
        this.roll=roll;
        this.courseplan=courseplan;
    }

    @NonNull
    @Override
    public StudyMaterialSubHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.courseplancard, viewGroup, false);

        return new StudyMaterialSubHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudyMaterialSubHolder studyMaterialSubHolder, int i) {
        // String subject = components.get(i).getSubject();
        studyMaterialSubHolder.textView.setText(subject_names.get(i));
        studyMaterialSubHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) context;
                if (assignment.equals("yes")) {
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AssignmentF(subject_ids.get(i),roll,subject_names.get(i),subject_ids,subject_names)).addToBackStack(null).commit();
                } else {
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StudyMaterialAF(semester, subject_names.get(i), section, subject_ids.get(i),courseplan)).addToBackStack(null).commit();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return subject_names.size();
    }

    public class StudyMaterialSubHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;

        public StudyMaterialSubHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.subject);
            cardView = (CardView) itemView.findViewById(R.id.cards);

        }
    }
}
