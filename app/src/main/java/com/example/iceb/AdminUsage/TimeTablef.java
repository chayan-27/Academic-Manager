package com.example.iceb.AdminUsage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.iceb.R;
import com.example.iceb.server2.FetchInfo2;
import com.example.iceb.server2.TimeTable1;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class TimeTablef extends Fragment {
    private static final int REQUEST_CODE = 100;
    Button button;
    Context context;
    Button button1;
    Spinner sem;

    int semester = 0;
    ProgressBar progressBar;

    MultipartBody.Part multipartBody;

    //  NotiApi notiApi;


    TextView textView;
    String encoded;
    String section;
    File file;
    String batch;

    @SuppressLint("ValidFragment")
    public TimeTablef(String section,String batch) {
        // Required empty public constructor
        this.section = section;
        this.batch=batch;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_tablef, container, false);
        button = view.findViewById(R.id.button3);
        context = getContext();
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        button1 = (Button) view.findViewById(R.id.file);
        textView = (TextView) view.findViewById(R.id.path);

        sem = view.findViewById(R.id.sem);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.semester, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem.setAdapter(arrayAdapter);
      //  showChooser(getContext());
        /*String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
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
        semester=Integer.parseInt(manipulatesem(batch));
        sem.setSelection(Integer.parseInt(manipulatesem(batch))-1);
        sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                semester = Integer.parseInt(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooser(getContext());
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (!(semester == 0 )&&!(multipartBody==null)) {
                        progressBar.setVisibility(View.VISIBLE);
                        sendTimetable();
                        // send();
                    } else {
                        Toast.makeText(getContext(), "Enter Valid Details", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Enter Valid Details", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    public void showChooser(Context context) {
        Intent target = new Intent(Intent.ACTION_GET_CONTENT);
        target.setType("image/*");
        startActivityForResult(target, REQUEST_CODE);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == RESULT_OK) {
            if (data != null) {
                final Uri uri = data.getData();

                try {
                    final String filepath = getPath(uri);
                    file = new File(filepath);

                    Thread thread=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            InputStream inputStream = null;
                            try {
                                inputStream = getContext().getContentResolver().openInputStream(uri);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            try {
                                saveFile(getBytes(inputStream),".jpg");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();









                    /*InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                    byte[] bytes = getBytes(inputStream);

                    encoded = Base64.encodeToString(bytes, Base64.DEFAULT);*/


                    textView.setText(filepath);

                    /*AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
                    alertdialog.setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                  sendTimetable();

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    getActivity().onBackPressed();
                                }
                            }).setMessage("Are you sure to change the timetable file of Semester"+semester+" to selected file");
                    AlertDialog alertDialog = alertdialog.create();
                    alertDialog.setTitle("Upload Timetable");
                    alertDialog.show();*/

                   /* String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    encoded = encoded.replace("\n", "").replace("\r", "");
*/

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    /*public void send(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        notiApi = retrofit.create(NotiApi.class);
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Call<Controller> call = notiApi.uploadtimetable(new TimeTable(section,semester,encoded));
        call.enqueue(new Callback<Controller>() {
            @Override
            public void onResponse(Call<Controller> call, Response<Controller> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    assert response.body() != null;
                    Toast.makeText(getContext(), "Error ! Please Try Again !!", Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Successfully Uploaded", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Controller> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error ! Please Try Again !!", Toast.LENGTH_LONG).show();

            }
        });
    }*/

    public void sendTimetable(){
       String base="https://academic-manager-nitt.el.r.appspot.com/";
        
       // String base="https://academic-manager-nitt.el.r.appspot.com/";
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
        RequestBody classroom_id = RequestBody.create(MediaType.parse("text/plain"), section);
        RequestBody semester=RequestBody.create(MediaType.parse("text/plain"),String.valueOf(this.semester));
       // RequestBody file=RequestBody.create(MediaType.parse("multipart/form-data"),this.file);
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        RequestBody upload_datee=RequestBody.create(MediaType.parse("text/plain"), currentDate);

        Call<TimeTable1> call=fetchInfo.sendTimetable(classroom_id,semester,upload_datee,multipartBody);
        call.enqueue(new Callback<TimeTable1>() {
            @Override
            public void onResponse(Call<TimeTable1> call, Response<TimeTable1> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    assert response.body() != null;
                    Toast.makeText(getContext(), "No response from the server!", Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Successfully Uploaded", Toast.LENGTH_LONG).show();
                getActivity().onBackPressed();

            }

            @Override
            public void onFailure(Call<TimeTable1> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    public String getPath(Uri uri) {

        String path = null;
        String[] projection = { MediaStore.Files.FileColumns.DATA };
        Cursor cursor = getContext().getContentResolver().query(uri, projection, null, null, null);

        if(cursor == null){
            path = uri.getPath();
        }
        else{
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndexOrThrow(projection[0]);
            path = cursor.getString(column_index);
            cursor.close();
        }

        return ((path == null || path.isEmpty()) ? (uri.getPath()) : path);

    }

    public void saveFile(byte[] decodedString,String extension) {
        String path = "Last Shared File";
        String name = "/" + "Transact"+extension;
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
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),root);

        multipartBody = MultipartBody.Part.createFormData("file",file.getName()+extension,requestFile);

    }

    public String manipulatesem(String batc) {
        Log.d("batch",batc);
        int semester = 0;
        int batch = Integer.parseInt(batc);
        Log.d("batch", String.valueOf(batch));
        Calendar calendar=Calendar.getInstance();

        int year =calendar.get(Calendar.YEAR);
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
