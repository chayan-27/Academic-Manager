package com.example.iceb;

import android.content.Context;
import android.graphics.Color;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        View view = layoutInflater.inflate(R.layout.announ, viewGroup, false);
        return new CourseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder courseHolder, int i) {
        // courseHolder.textView.setText(components.get(i).substring(0,components.get(i).indexOf("$")).toUpperCase());
        String j = components.get(i).substring(components.get(i).indexOf("$") + 1, components.get(i).lastIndexOf("$"));
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Date mydate = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String yestr = dateFormat.format(mydate);


        courseHolder.textView.setText(components.get(i).substring(components.get(i).indexOf(" ") + 1, components.get(i).indexOf("$")));
        if (j.equals(currentDate)) {
            courseHolder.imageView.setText("Today");
        } else if (j.equals(yestr)) {
            courseHolder.imageView.setText("Yesterday");
        } else {
            courseHolder.imageView.setText(components.get(i).substring(components.get(i).indexOf("$") + 1, components.get(i).lastIndexOf("$")));

        }
        courseHolder.cardView.setText(components.get(i).substring(components.get(i).lastIndexOf("$") + 1));


        if (i == 0) {
            courseHolder.cardView1.setCardBackgroundColor(Color.parseColor("#88F39E"));


        } else {
            courseHolder.cardView1.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }


    }

    @Override
    public int getItemCount() {
        return components.size();
    }

    public class CourseHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView imageView;
        TextView cardView;
        CardView cardView1;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            cardView = (TextView) itemView.findViewById(R.id.time);
            imageView = (TextView) itemView.findViewById(R.id.date);
            cardView1 = (CardView) itemView.findViewById(R.id.cards);
        }
    }

    public void updateData(List<String> list) {
        this.components = list;
        notifyDataSetChanged();
    }
}
