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

    public StudyMaterialSubjectAdap(List<Studymaterial> components, Context context,String section,Integer semester) {
        this.components = components;
        this.context = context;
        this.section=section;
        this.semester=semester;
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
        String subject = components.get(i).getSubject();
        studyMaterialSubHolder.textView.setText(subject);
        studyMaterialSubHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity=(AppCompatActivity)context;
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new StudyMaterialAF(semester,subject,section)).addToBackStack(null).commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return components.size();
    }

    public class StudyMaterialSubHolder extends RecyclerView.ViewHolder{
        TextView textView;
        CardView cardView;
        public StudyMaterialSubHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.subject);
            cardView = (CardView) itemView.findViewById(R.id.cards);

        }
    }
}
