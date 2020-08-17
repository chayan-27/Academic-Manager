package com.example.iceb;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.iceb.server.Controller;
import com.example.iceb.server.Timetable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class Timet extends Fragment {
    ImageView imageView;
    ProgressBar progressBar;
    FetchInfo fetchInfo;
    String section;
    int semester=0;

    @SuppressLint("ValidFragment")
    public Timet(String section) {
        // Required empty public constructor
        this.section=section;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timet, container, false);
        imageView = (ImageView) view.findViewById(R.id.timetable);
        progressBar = (ProgressBar) view.findViewById(R.id.progresso);
        progressBar.setVisibility(View.VISIBLE);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        int month = Integer.parseInt(currentDate.substring(3, 5));
        int year = Integer.parseInt(currentDate.substring(6));
        if (month >= 1 && month <= 7) {
            if (year == 2020) {
                semester = 2;
            } else if (year == 2021) {
                semester = 4;
            } else if (year == 2022) {
                semester = 6;
            } else if (year == 2023) {
                semester = 8;
            }
        } else {
            if (year == 2020) {
                semester = 3;
            } else if (year == 2021) {
                semester = 5;
            } else if (year == 2022) {
                semester = 7;
            }
        }
        String path = "TimeTable/Semester"+semester;
        String name = "/timetable.jpg";
        File file = new File(requireContext().getExternalFilesDir(path).getAbsolutePath() + name);
        if (file.exists()) {
            progressBar.setVisibility(View.GONE);
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);

        } else {
            int semester = 0;
          /*  String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            int month = Integer.parseInt(currentDate.substring(3, 5));
            int year = Integer.parseInt(currentDate.substring(6));*/
            if (month >= 1 && month <= 7) {
                if (year == 2020) {
                    semester = 2;
                } else if (year == 2021) {
                    semester = 4;
                } else if (year == 2022) {
                    semester = 6;
                } else if (year == 2023) {
                    semester = 8;
                }
            } else {
                if (year == 2020) {
                    semester = 3;
                } else if (year == 2021) {
                    semester = 5;
                } else if (year == 2022) {
                    semester = 7;
                }
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            fetchInfo = retrofit.create(FetchInfo.class);
            Call<Controller> call = fetchInfo.getTimetest(semester, section);
            call.enqueue(new Callback<Controller>() {
                @Override
                public void onResponse(Call<Controller> call, Response<Controller> response) {
                    if (!(response.isSuccessful())) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                        return;
                    }

                    try{
                    List<Timetable> list = response.body().getTimetable();
                    String he = list.get(0).getTimetable();
                    byte[] decodedString = Base64.decode(he.getBytes(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageView.setImageBitmap(decodedByte);
                    progressBar.setVisibility(View.GONE);
                    File root = new File(requireContext().getExternalFilesDir(path).getAbsolutePath() + name);
                    try {
                        OutputStream fileOutputStream = new FileOutputStream(root);
                        fileOutputStream.write(decodedString);
                        fileOutputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }catch (Exception e){}


                }

                @Override
                public void onFailure(Call<Controller> call, Throwable t) {
                    Toast.makeText(getContext(), "Some Error Occured!Please Try Again", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                }
            });
        }


        return view;
    }
}
