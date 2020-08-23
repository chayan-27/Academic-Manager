package com.example.iceb;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.iceb.AdminUsage.TimeTablef;
import com.example.iceb.server.Controller;
import com.example.iceb.server.Timetable;
import com.example.iceb.server2.FetchInfo2;
import com.example.iceb.server2.TimeTable1;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class Timet extends Fragment {
    ImageView imageView;
    ProgressBar progressBar;
    FetchInfo fetchInfo;
    String section;
    int semester = 0;
    boolean admin;
    String batch;

    @SuppressLint("ValidFragment")
    public Timet(String section, boolean admin, String batch) {
        // Required empty public constructor
        this.section = section;
        this.admin = admin;
        this.batch = batch;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timet, container, false);
        imageView = (ImageView) view.findViewById(R.id.timetable);
        progressBar = (ProgressBar) view.findViewById(R.id.progresso);
        progressBar.setVisibility(View.VISIBLE);
       /* String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
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
        }*/
        String path = "TimeTable/Semester" + manipulatesem(batch);
        String name = "/timetable.jpg";
        File file = new File(requireContext().getExternalFilesDir(path).getAbsolutePath() + name);
        if (file.exists()) {
            progressBar.setVisibility(View.GONE);
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);

        } else {
            /*int semester = 0;
             *//*  String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            int month = Integer.parseInt(currentDate.substring(3, 5));
            int year = Integer.parseInt(currentDate.substring(6));*//*
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
            }*/
            getTimetablecredits(section);


           /* Retrofit retrofit = new Retrofit.Builder()
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
            });*/
        }
        if (admin) {
            FloatingActionButton fab = view.findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimeTablef(section, batch)).addToBackStack(null).commit();

                }
            });
        } else {
            FloatingActionButton fab = view.findViewById(R.id.fab);
            fab.setVisibility(View.INVISIBLE);
        }


        return view;
    }

    public void getTimetablecredits(String class_id) {
        //String base = "http://192.168.1.6:8000/";
        String base = "http://192.168.1.6:8000/";
        // String base="https://academic-manager-nitt.el.r.appspot.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
        Call<List<TimeTable1>> call = fetchInfo.getTimetable(class_id, manipulatesem(batch));
        call.enqueue(new Callback<List<TimeTable1>>() {
            @Override
            public void onResponse(Call<List<TimeTable1>> call, Response<List<TimeTable1>> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                    return;
                }

                // Toast.makeText(getContext(), class_id + semester, Toast.LENGTH_LONG).show();

                try {


                    String fileurl = base + response.body().get(0).getFile().substring(1);
                    Call<ResponseBody> call1 = fetchInfo.downloadFileWithDynamicUrlSync(fileurl);
                    call1.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (!response.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "No Response From The Server", Toast.LENGTH_LONG).show();
                                return;
                            }
                            writeResponseBodyToDisk(response.body());
                            progressBar.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getContext(), "Some Error Occured!Please Try Again", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(getContext(), "No information to show", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<TimeTable1>> call, Throwable t) {
                Toast.makeText(getContext(), "Some Error Occured!Please Try Again", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {

            String path = "TimeTable/Semester" + semester;
            String name = "/timetable.jpg";
            File file = new File(requireContext().getExternalFilesDir(path).getAbsolutePath() + name);
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();
                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    public String manipulatesem(String batc) {
        Log.d("batch", batc);
        int semester = 0;
        int batch = Integer.parseInt(batc);
        Log.d("batch", String.valueOf(batch));
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int yearofstudy = year - batch;
        Log.d("batch", String.valueOf(yearofstudy));
        switch (yearofstudy) {
            case 0:
                calendar.get(Calendar.MONTH);
                if (calendar.get(Calendar.MONTH) <= 5) {

                } else {
                    semester = 1;
                }
                break;

            case 1:
                calendar.get(Calendar.MONTH);
                if (calendar.get(Calendar.MONTH) <= 5) {
                    semester = 2;
                } else {
                    semester = 3;
                }
                break;
            case 2:
                calendar.get(Calendar.MONTH);
                if (calendar.get(Calendar.MONTH) <= 5) {
                    semester = 4;
                } else {
                    semester = 5;
                }
                break;
            case 3:
                calendar.get(Calendar.MONTH);
                if (calendar.get(Calendar.MONTH) <= 5) {
                    semester = 6;
                } else {
                    semester = 7;
                }
                break;
            case 4:
                calendar.get(Calendar.MONTH);
                if (calendar.get(Calendar.MONTH) <= 5) {
                    semester = 8;
                }
                break;


        }
        return String.valueOf(semester);


    }
}
