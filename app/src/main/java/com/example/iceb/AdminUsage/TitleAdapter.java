package com.example.iceb.AdminUsage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iceb.R;
import com.example.iceb.server2.AdminAssignment;

import java.util.List;

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.TitleHolder> {
    List<AdminAssignment> list;
    Context context;
    String subject;
    Integer semester;
    String section;

    public TitleAdapter(List<AdminAssignment> list, Context context, String subject, Integer semester, String section) {
        this.list = list;
        this.context = context;
        this.subject=subject;
        this.semester=semester;
        this.section=section;
    }

    @NonNull
    @Override
    public TitleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.announ, parent, false);
        return new TitleHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TitleHolder holder, final int position) {
        holder.textView.setText(list.get(position).getTopic());
        holder.date.setVisibility(View.GONE);
        holder.time.setVisibility(View.GONE);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity=(AppCompatActivity)context;
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AssignResponsesF(list.get(position).getTopic(),subject,semester,section,list.get(position).getId().toString())).addToBackStack(null).commit();
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TitleHolder extends RecyclerView.ViewHolder{
        TextView textView;
        CardView cardView;
        TextView date;
        TextView time;

        public TitleHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            cardView = (CardView) itemView.findViewById(R.id.cards);
            date=(TextView)itemView.findViewById(R.id.date);
            time=(TextView)itemView.findViewById(R.id.time);

        }
    }
}
