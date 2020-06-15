package com.example.iceb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    List<String> components;
    Context context;


    public CourseAdapter(List<String> components, Context context) {
        this.components = components;
        this.context = context;
    }
    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.compo, viewGroup, false);
        return new CourseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder courseHolder, int i) {
        courseHolder.textView.setText(components.get(i).toUpperCase());
        courseHolder.imageView.setImageResource(R.drawable.planic);
        AppCompatActivity appCompatActivity=(AppCompatActivity)context;
        FragmentManager fragment=appCompatActivity.getSupportFragmentManager();
        courseHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==0){
                    fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.coursef,new Pdff("ICPC13-Thermodynamics_and_Fluid_Mechanics_B.pdf")).addToBackStack(null).commit();
                }else if(i==1){
                    fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.coursef,new Pdff("COURSE PLANicpc.pdf")).addToBackStack(null).commit();
                }else if(i==2){
                    fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.coursef,new Pdff("HSIR11-English_for_Communication_B.pdf")).addToBackStack(null).commit();
                }else if(i==3){
                    fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.coursef,new Pdff("MAIR22-Complex_Analyays_&_DE.pdf")).addToBackStack(null).commit();
                }else if(i==4){
                    fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.coursef,new Pdff("CHIR11-Chemistry_Theory_B.pdf")).addToBackStack(null).commit();
                }else if(i==5){
                    fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.coursef,new Pdff("CHIR12-Chemistry_Lab_B.pdf")).addToBackStack(null).commit();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return components.size();
    }

    public class CourseHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        CardView cardView;
        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            cardView = (CardView) itemView.findViewById(R.id.cards);
            imageView = (ImageView) itemView.findViewById(R.id.pokemi);
        }
    }
}
