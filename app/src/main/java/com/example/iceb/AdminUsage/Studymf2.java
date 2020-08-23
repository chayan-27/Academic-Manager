package com.example.iceb.AdminUsage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iceb.R;
import com.example.iceb.server2.FetchInfo2;
import com.example.iceb.server2.Mess;
import com.example.iceb.server2.Notif;
import com.example.iceb.server2.Notification;
import com.example.iceb.server2.SubjectResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
 * Use the {@link Studymf2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Studymf2 extends Fragment {
    private static final int REQUEST_CODE = 100;
    Button button;
    Context context;
    Button button1;
    String subject;
    FetchInfo2 notiApi;
    EditText tl;
    String title;
    TextView textView;
    String subject_id;
    ProgressBar progressBar;
    MultipartBody.Part multipartBody;
    String extension;
    String section;
    String batch;
    File file;
    String courseplan;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Studymf2(String subject,String subject_id,String section,String courseplan) {
        // Required empty public constructor
        this.subject=subject;
        this.subject_id=subject_id;
        this.section=section;
        this.courseplan=courseplan;
    }
    public Studymf2(){

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Studymf2.
     */
    // TODO: Rename and change types and number of parameters
    public static Studymf2 newInstance(String param1, String param2) {
        Studymf2 fragment = new Studymf2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_studymf3, container, false);
        button = view.findViewById(R.id.button3);
        context = getContext();
        tl = (EditText) view.findViewById(R.id.title);
        button1 = (Button) view.findViewById(R.id.file);
        textView = (TextView) view.findViewById(R.id.path);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);

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
                    title = tl.getText().toString();
                    if (!(title.equals("") || subject_id.equals("") || multipartBody==null)) {
                        progressBar.setVisibility(View.VISIBLE);
                        if(courseplan.equals("yes")){
                            title="Courseplan";
                        }
                        getFiletoBeSent(subject_id,title);
                        //  send();
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

    public void getFiletoBeSent(String subject_id,String topic){
        String base="https://academic-manager-nitt.el.r.appspot.com/";

        // String base="https://academic-manager-nitt.el.r.appspot.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
        RequestBody subjectt_id = RequestBody.create(MediaType.parse("text/plain"), subject_id);
        RequestBody topicc=RequestBody.create(MediaType.parse("text/plain"), topic);
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        RequestBody upload_datee=RequestBody.create(MediaType.parse("text/plain"), currentDate);
        Call<SubjectResponse> call=fetchInfo.sendMaterial(subjectt_id,topicc,upload_datee,multipartBody);
        call.enqueue(new Callback<SubjectResponse>() {
            @Override
            public void onResponse(Call<SubjectResponse> call, Response<SubjectResponse> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(getContext(), subject_id+" : "+topic+":"+currentDate, Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Successfully Uploaded", Toast.LENGTH_LONG).show();
                notification(subject,title+extension+" Uploaded");
                getActivity().onBackPressed();
            }

            @Override
            public void onFailure(Call<SubjectResponse> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();


            }
        });


    }

    public void notification(String title, String body) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        notiApi = retrofit.create(FetchInfo2.class);
        String topic="/topics/class"+section;
        /*if (section.equals("ICEB")) {
            //  topic = "/topics/weather";
           // topic = "/topics/test";
        } else {
            //  topic = "/topics/weather1";
            topic = "/topics/test";
        }*/
        Call<Mess> call = notiApi.sendnoti(new Notif(topic, new Notification(title,  body + "!", "no")));
        call.enqueue(new Callback<Mess>() {
            @Override
            public void onResponse(Call<Mess> call, Response<Mess> response) {
                if (!response.isSuccessful()) {

                }

                //Toast.makeText(getContext(), response.raw().toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Mess> call, Throwable t) {

            }
        });
    }

    public void showChooser(Context context) {
        Intent target = new Intent(Intent.ACTION_GET_CONTENT);
        target.setType("*/*");
        startActivityForResult(target, REQUEST_CODE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == RESULT_OK) {
            if (data != null) {
                final Uri uri = data.getData();
                try {
                    final String filepath = uri.getPath();
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
                                saveFile(getBytes(inputStream),file.getName().substring(file.getName().lastIndexOf(".")));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                    extension = filepath.substring(filepath.lastIndexOf(".") );


                   /* InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                    byte[] bytes = getBytes(inputStream);

                    encoded = Base64.encodeToString(bytes, Base64.DEFAULT);*/


                    textView.setText(filepath);

                    /* encoded = encoded.replace("\n", "").replace("\r", "");
                     */ String h = filepath.substring(filepath.lastIndexOf("/") + 1, filepath.lastIndexOf("."));
                    tl.setText(h);
                    tl.setSelection(h.length());
                    //   Toast.makeText(getContext(), extension, Toast.LENGTH_LONG).show();


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

        multipartBody = MultipartBody.Part.createFormData("file",file.getName(),requestFile);

    }

}