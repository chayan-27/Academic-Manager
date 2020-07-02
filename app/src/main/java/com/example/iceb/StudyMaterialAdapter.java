package com.example.iceb;

import android.content.Context;
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


import com.example.iceb.server.Controller;
import com.example.iceb.server.Studymaterial;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudyMaterialAdapter extends RecyclerView.Adapter<StudyMaterialAdapter.StudyMaterialHolder> {
    List<Studymaterial> components;
    Context context;
    String section;
    String subject;
    ProgressBar progressBar;

    public StudyMaterialAdapter(List<Studymaterial> components, Context context, String section, String subject, ProgressBar progressBar) {
        this.components = components;
        this.context = context;
        this.section = section;
        this.subject = subject;
        this.progressBar = progressBar;
    }

    @NonNull
    @Override
    public StudyMaterialHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.studymaterialcard, viewGroup, false);

        return new StudyMaterialHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudyMaterialHolder studyMaterialHolder, int i) {
        //  String subject = components.get(i).getSubject();
        String title = components.get(i).getTitle();
        String update = components.get(i).getUploadDate();
        //studyMaterialHolder.textView.setText(subject);
        studyMaterialHolder.textView1.setText(title);
        studyMaterialHolder.textView3.setText("Sent : " + update);
        studyMaterialHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                downloadstudymaterial(title, section, subject);
            }
        });

    }

    @Override
    public int getItemCount() {
        return components.size();
    }

    public class StudyMaterialHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView1;
        TextView textView3;
        CardView cardView;

        public StudyMaterialHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.subject);
            textView1 = (TextView) itemView.findViewById(R.id.title);

            textView3 = (TextView) itemView.findViewById(R.id.sdate);
            cardView = (CardView) itemView.findViewById(R.id.cards);
        }
    }

    public void downloadstudymaterial(String title, String section, String subject) {
        String path = "StudyMaterials/" + subject;
        String name = "/" + title + ".pdf";
        File file = new File(Objects.requireNonNull(context.getExternalFilesDir(path)).getAbsolutePath() + name);
        if (file.exists()) {
            progressBar.setVisibility(View.GONE);
            AppCompatActivity appCompatActivity = (AppCompatActivity) context;
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PDFViewfrag(file)).addToBackStack(null).commit();

        } else {
            Toast.makeText(context, "Processing Please Wait....", Toast.LENGTH_LONG).show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            FetchInfo fetchInfo = retrofit.create(FetchInfo.class);
            Call<Controller> call = fetchInfo.downloadStudyMaterial(title, section);
            call.enqueue(new Callback<Controller>() {
                @Override
                public void onResponse(Call<Controller> call, Response<Controller> response) {
                    if (!(response.isSuccessful())) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "No Response From The Server", Toast.LENGTH_LONG).show();
                        return;
                    }


                    List<Studymaterial> list = response.body().getStudymaterial();
                    String he = (String) list.get(0).getStuContent();
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

                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PDFViewfrag(file)).addToBackStack(null).commit();


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
