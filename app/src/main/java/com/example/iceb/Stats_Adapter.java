package com.example.iceb;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Stats_Adapter extends RecyclerView.Adapter<Stats_Adapter.Stats_Holder> {
    List<String> list;

    public Stats_Adapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Stats_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.courseplancard, viewGroup, false);
        return new Stats_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Stats_Holder stats_holder, int i) {
        stats_holder.textView.setText(list.get(i));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Stats_Holder extends RecyclerView.ViewHolder {
        TextView textView;

        public Stats_Holder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.subject);
        }
    }
}
