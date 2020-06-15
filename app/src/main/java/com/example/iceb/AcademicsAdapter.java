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

public class AcademicsAdapter extends RecyclerView.Adapter<AcademicsAdapter.AcadHolder> {
    List<String> components;
    Context context;
    int ar[];

    public AcademicsAdapter(List<String> components, Context context, int[] ar) {
        this.components = components;
        this.context = context;
        this.ar = ar;
    }

    @NonNull
    @Override
    public AcadHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.compo, viewGroup, false);
        return new AcadHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AcadHolder acadHolder, int i) {
        acadHolder.textView.setText(components.get(i).toUpperCase());
        acadHolder.imageView.setImageResource(ar[i]);
        AppCompatActivity appCompatActivity=(AppCompatActivity)context;
        FragmentManager fragment=appCompatActivity.getSupportFragmentManager();
        acadHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (i) {
                    case 0:
                        fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.fragment_container,new Timet()).addToBackStack(null).commit();
                        break;
                    case 1:
                        fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.fragment_container,new Studymf("https://drive.google.com/open?id=1Em0c6h8scxmh3ZInGDCI3FFrdWtNnYtq")).addToBackStack(null).commit();
                        break;
                    case 2:
                        fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.fragment_container,new Coursef()).addToBackStack(null).commit();
                        break;
                    case 3:
                        fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.fragment_container,new Attendant()).addToBackStack(null).commit();
                        break;
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return components.size();
    }

    public class AcadHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        CardView cardView;

        public AcadHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            cardView = (CardView) itemView.findViewById(R.id.cards);
            imageView = (ImageView) itemView.findViewById(R.id.pokemi);

        }
    }
}
