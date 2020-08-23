package com.example.iceb.AdminUsage;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.iceb.R;
import com.example.iceb.server2.AdminAssignment;
import com.example.iceb.server2.AdminSubject;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
 */
@SuppressLint("ValidFragment")
public class AssignmentUP extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final int REQUEST_CODE = 100;
    Button button;
    Button button1;
    Context context;
    Spinner sem;
    Spinner sub;
    int semester = 0;
    String subject;
   FetchInfo2 notiApi;
    EditText tl;
    String title;
    TextView textView;
    TextView submission;
    String timee;
    String encoded;
    ProgressBar progressBar;
    String section;
    Button fileres;
    String path;
    TextView subbtime;
    int h, m;
    String subtime = "";
    String extension;
    List<String> subject_id;
    List<String> subject_name;
    String idsub;
    MultipartBody.Part multipartBody;
    File file;
    String batch;

    @SuppressLint("ValidFragment")
    public AssignmentUP(String section, String path, List<String> subject_id, List<String> subject_name,String batch) {
        // Required empty public constructor
        this.section = section;
        this.path = path;
        this.subject_id = subject_id;
        this.subject_name = subject_name;
        idsub=subject_id.get(0);
        this.batch=batch;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assignment_u_p, container, false);
        button = view.findViewById(R.id.button3);
        fileres = view.findViewById(R.id.fileres);
        context = getContext();
        tl = (EditText) view.findViewById(R.id.title);
        textView = (TextView) view.findViewById(R.id.path);
        submission = (TextView) view.findViewById(R.id.submission);
        subbtime = (TextView) view.findViewById(R.id.submissiontime);
        button1 = (Button) view.findViewById(R.id.file);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        submission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        subbtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"",Toast.LENGTH_LONG).show();
                showTime();
            }
        });


        sem = view.findViewById(R.id.sem);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.semester, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem.setAdapter(arrayAdapter);
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
        getSubjects(section,manipulatesem(batch));
        sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                semester = Integer.parseInt(text);
                getSubjects(section,text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sub = view.findViewById(R.id.subject);
       /* ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(getContext(), R.array.subjects, android.R.layout.simple_spinner_item);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sub.setAdapter(arrayAdapter1);*/
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, subject_name);
        sub.setAdapter(adapter);
        sub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject = parent.getItemAtPosition(position).toString();
                idsub = subject_id.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                subject = "";
            }
        });

        if (!(path.equals(""))) {
            encoded = others(path);
            if (!(extension.equals("pdf"))) {
                encoded = "";
                textView.setText("Only PDF format supported\nSelect new File");

            } else {
                textView.setText("File Already Selected\nEnter Title and Upload");
            }
        }


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
                    if (!(title.equals("") || timee.equals("") || subject.equals("") || semester == 0 || subtime.equals("")||multipartBody==null)) {
                        progressBar.setVisibility(View.VISIBLE);
                        getFiletoBeSent(idsub,title,timee+" "+subtime);
                        notification(subject, "New " + title+extension+ " Assignment Uploaded");
                    } else {
                        Toast.makeText(getContext(), "Enter Valid Details", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Enter Valid Details", Toast.LENGTH_LONG).show();
                }
            }
        });

        fileres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //title = tl.getText().toString();
                    if (!(subject.equals("") || semester == 0)) {

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TitlesF(idsub, semester, subject)).addToBackStack(null).commit();
                    } else {
                        Toast.makeText(getContext(), "Enter Title,Subject and Semester to see responses ", Toast.LENGTH_LONG).show();

                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Enter Title,Subject and Semester to see responses", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
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

                    encoded = Base64.encodeToString(bytes, Base64.DEFAULT);

*/
                    textView.setText(filepath);

                   // encoded = encoded.replace("\n", "").replace("\r", "");
                    String h = filepath.substring(filepath.lastIndexOf("/") + 1, filepath.lastIndexOf("."));
                    tl.setText(h);
                    tl.setSelection(h.length());


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

    public void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH)
                , Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (month >= 0 && month <= 8) {
           // timee = dayOfMonth + "-0" + (month + 1) + "-" + year;
            if (dayOfMonth >= 1 && dayOfMonth <= 9) {
               //  timee = "0" + dayOfMonth + "-0" + (month + 1) + "-" + year;
                timee = year + "-0" + (month + 1) + "-0" + dayOfMonth;


            } else {
                //timee = dayOfMonth + "-0" + (month + 1) + "-" + year;
                timee = year + "-0" + (month + 1) + "-" + dayOfMonth;

            }
        } else {
           // timee = dayOfMonth + "-" + (month + 1) + "-" + year;
            if (dayOfMonth >= 1 && dayOfMonth <= 9) {
                // timee = "0" + dayOfMonth + "-" + (month + 1) + "-" + year;
                timee = year + "-" + (month + 1) + "-0" + dayOfMonth;

            } else {
               //  timee = dayOfMonth + "-" + (month + 1) + "-" + year;
                timee = year + "-" + (month + 1) + "-" + dayOfMonth;

            }

        }

        submission.setText(timee);
    }

   /* public void send() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        notiApi = retrofit.create(NotiApi.class);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Call<Controller> call = notiApi.upload(new AssignUPC(encoded, section, semester, timee, subject, title, subtime + "&" + currentDate));
        call.enqueue(new Callback<Controller>() {
            @Override
            public void onResponse(Call<Controller> call, Response<Controller> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    assert response.body() != null;
                    Toast.makeText(getContext(), "Error ! Please Try Again!!", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getContext(), "Successfully Uploaded", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Controller> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error ! Please Try Again!!", Toast.LENGTH_LONG).show();

            }
        });
    }*/

    /*public void getfiles() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        notiApi = retrofit.create(NotiApi.class);
        Call<Controller> call = notiApi.getfiles(section, semester, title, subject);
        call.enqueue(new Callback<Controller>() {
            @Override
            public void onResponse(Call<Controller> call, Response<Controller> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    assert response.body() != null;
                    Toast.makeText(getContext(), "Error ! Please Try Again!!", Toast.LENGTH_LONG).show();
                    return;
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Controller> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error ! Please Try Again!!", Toast.LENGTH_LONG).show();

            }
        });
    }*/

    public void getFile(String filepath) throws IOException {

        Uri uri = Uri.parse(filepath);
        File file = new File(filepath);


        InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
        byte[] bytes = getBytes(inputStream);

        encoded = Base64.encodeToString(bytes, Base64.DEFAULT);


        textView.setText(filepath);

        encoded = encoded.replace("\n", "").replace("\r", "");

    }

    public void notification(String title, String body) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        notiApi = retrofit.create(FetchInfo2.class);
        String topic;
        /*if (section.equals("ICEB")) {
            topic = "/topics/test";
        } else {
            topic = "/topics/weather1";
        }*/
        topic="/topics/class"+section;
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

    public String others(String filepath) {
        File file = new File(filepath);

        extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1);

        try {

            InputStream inputStream = getContext().getContentResolver().openInputStream(Uri.fromFile(file));
            byte[] bytes = getBytes(inputStream);

            encoded = Base64.encodeToString(bytes, Base64.DEFAULT);


            textView.setText(filepath);

            encoded = encoded.replace("\n", "").replace("\r", "");
        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        }
        return encoded;
    }

    public void showTime() {
        Calendar calendar = Calendar.getInstance();
        h = calendar.get(Calendar.HOUR_OF_DAY);
        m = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String y = "";
                if (hourOfDay > 0 && hourOfDay < 12) {
                    y = "am";

                    subbtime.setText(hourOfDay + ":" + minute + " " + y);
                    subtime = hourOfDay + ":" + minute + " " + y;
                } else if (hourOfDay > 12) {
                    y = "pm";
                    subbtime.setText((hourOfDay - 12) + ":" + minute + " " + y);
                    subtime = (hourOfDay - 12) + ":" + minute + " " + y;

                } else if (hourOfDay == 12) {
                    y = "pm";
                    subbtime.setText(12 + ":" + minute + " " + y);
                    subtime = 12 + ":" + minute + " " + y;

                } else {
                    y = "am";
                    subbtime.setText(12 + ":" + minute + " " + y);
                    subtime = 12 + ":" + minute + " " + y;

                }

                if (minute >= 0 && minute <= 9) {
                    subtime = hourOfDay + ":0" + minute ;

                } else {
                    subtime = hourOfDay + ":" + minute;
                }


            }
        }, h, m, false);
        timePickerDialog.show();

    }

    public void getSubjects(String class_id,String semester){
        //String base = "http://192.168.1.6:8000/";
       String base="https://academic-manager-nitt.el.r.appspot.com/";
        
       // String base="https://academic-manager-nitt.el.r.appspot.com/";
        
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
        Call<List<AdminSubject>> call=fetchInfo.getSubjects(class_id,semester);
        call.enqueue(new Callback<List<AdminSubject>>() {
            @Override
            public void onResponse(Call<List<AdminSubject>> call, Response<List<AdminSubject>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                subject_id=new ArrayList<>();
                subject_name=new ArrayList<>();
                for(AdminSubject adminSubject:response.body()){
                    subject_id.add(adminSubject.getId().toString());
                    subject_name.add(adminSubject.getSubjectCode());
                }
                ArrayAdapter adapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, subject_name);
                sub.setAdapter(adapter);

                if(subject_id.size()!=0) {
                    idsub = subject_id.get(0);
                }else{
                    idsub="0";
                }





            }

            @Override
            public void onFailure(Call<List<AdminSubject>> call, Throwable t) {

            }
        });
    }

    public void getFiletoBeSent(String subject_id,String topic,String deadline){
        //String base = "http://192.168.1.6:8000/";
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
        RequestBody deadlinee=RequestBody.create(MediaType.parse("text/plain"), deadline);

        Call<AdminAssignment> call=fetchInfo.assignAssignment(subjectt_id,topicc,upload_datee,deadlinee,multipartBody);
        call.enqueue(new Callback<AdminAssignment>() {
            @Override
            public void onResponse(Call<AdminAssignment> call, Response<AdminAssignment> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(getContext(), subject_id+" : "+topic+":"+currentDate, Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Successfully Uploaded", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<AdminAssignment> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();


            }
        });


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
