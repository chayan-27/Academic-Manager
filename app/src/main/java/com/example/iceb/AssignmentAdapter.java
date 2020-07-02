package com.example.iceb;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iceb.server.Assignment;
import com.example.iceb.server.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentHolder> {
    List<Assignment> components;
    Context context;
    String section;
    ProgressBar progressBar;
    int roll;
    int semester;

    public AssignmentAdapter(List<Assignment> components, Context context, String section, ProgressBar progressBar, int roll, int semester) {
        this.components = components;
        this.context = context;
        this.section = section;
        this.progressBar = progressBar;
        this.roll = roll;
        this.semester = semester;
    }

    @NonNull
    @Override
    public AssignmentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.assigncard, viewGroup, false);
        return new AssignmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentHolder assignmentHolder, int i) {
        String subject = components.get(i).getSubject();
        String title = components.get(i).getTitle();
        String update = components.get(i).getUploadDate();
        String subdate = components.get(i).getSubbmissionDate();
        assignmentHolder.textView.setText(subject);
        assignmentHolder.textView1.setText(title);
        assignmentHolder.textView2.setText("Sent : " + update);
        assignmentHolder.textView3.setText("DeadLine : " + subdate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date strDate = null;
        try {
            strDate = sdf.parse(subdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (System.currentTimeMillis() <= strDate.getTime()) {
            assignmentHolder.cardView.setCardBackgroundColor(Color.parseColor("#88F39E"));
        } else {
            assignmentHolder.cardView.setCardBackgroundColor(Color.parseColor("#ff726f"));

        }
        assignmentHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                downloadassign(title, section, subject);
            }
        });

    }


    @Override
    public int getItemCount() {
        return components.size();
    }

    public class AssignmentHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        CardView cardView;

        public AssignmentHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.subject);
            textView1 = (TextView) itemView.findViewById(R.id.title);
            textView2 = (TextView) itemView.findViewById(R.id.udate);
            textView3 = (TextView) itemView.findViewById(R.id.sdate);
            cardView = (CardView) itemView.findViewById(R.id.cards);
        }
    }

    public void downloadassign(String title, String section, String subject) {
        String path = "Assignments/" + subject;
        String name = "/" + title + ".pdf";
        File file = new File(Objects.requireNonNull(context.getExternalFilesDir(path)).getAbsolutePath() + name);
        if (file.exists()) {
            progressBar.setVisibility(View.GONE);
            AppCompatActivity appCompatActivity = (AppCompatActivity) context;

            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UploadAssignF(file, section, roll, subject, title, semester)).addToBackStack(null).commit();

        } else {
            Toast.makeText(context, "Processing Please Wait....", Toast.LENGTH_LONG).show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            FetchInfo fetchInfo = retrofit.create(FetchInfo.class);
            Call<Controller> call = fetchInfo.downloadAssignment(title, section);
            call.enqueue(new Callback<Controller>() {
                @Override
                public void onResponse(Call<Controller> call, Response<Controller> response) {
                    if (!(response.isSuccessful())) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "No Response From The Server", Toast.LENGTH_LONG).show();
                        return;
                    }

                    List<Assignment> list = response.body().getAssignments();
                    String he = (String) list.get(0).getAssContent();
                    byte[] decodedString = Base64.decode(he.getBytes(), Base64.DEFAULT);
                    File root = new File(Objects.requireNonNull(context.getExternalFilesDir(path)).getAbsolutePath() + name);
                    try {
                        OutputStream fileOutputStream = new FileOutputStream(root);
                        fileOutputStream.write(decodedString);
                        fileOutputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(context, "Your File is Downloaded in your Internal storage/Android/data/com.example.iceb/files", Toast.LENGTH_LONG).show();
                    AppCompatActivity appCompatActivity = (AppCompatActivity) context;
                    progressBar.setVisibility(View.GONE);
                    // Toast.makeText(context, "File found", Toast.LENGTH_LONG).show();

                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UploadAssignF(file, section, roll, subject, title, semester)).addToBackStack(null).commit();


                }

                @Override
                public void onFailure(Call<Controller> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

                }
            });
        }
    }
}
