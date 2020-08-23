package com.example.iceb.AdminUsage;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
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

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.iceb.FetchInfo;
import com.example.iceb.R;
import com.example.iceb.server2.AdminPollUP;
import com.example.iceb.server2.FetchInfo2;
import com.example.iceb.server2.Mess;
import com.example.iceb.server2.Notif;
import com.example.iceb.server2.Notification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class PollUpload extends Fragment {
    Button button;
    FetchInfo2 notiApi;
    Spinner type;
    String text = "SINGLE";
    EditText editText1;
    int c;
    Button button1;
    List<EditText> editTexts = new ArrayList<>();
    String options = "";
    ProgressBar progressBar;
    String title;
    String section;
    Integer semester;
    int h, m;
    String subtime = "";
    TextView subbtime;
    TextView submission;
    String timee = "";
    String option1;
    String option2;
    String option3;
    String option4;
    String option5;

    public PollUpload(String section) {
        // Required empty public constructor
        this.section = section;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_poll_upload, container, false);
        button = (Button) view.findViewById(R.id.button2);
        button1 = (Button) view.findViewById(R.id.button3);
        type = view.findViewById(R.id.sem);
        submission = (TextView) view.findViewById(R.id.submission);
        subbtime = (TextView) view.findViewById(R.id.submissiontime);
        submission.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
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

        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        editText1 = (EditText) view.findViewById(R.id.editText5);


        final ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.linear);
        c = 0;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c < 5) {
                    EditText editText = new EditText(getContext());
                    editText.setHint("Option " + (c + 1));
                    editText.setBackgroundResource(R.drawable.progress_round_test);
                    editText.setPadding(8, 16, 8, 16);
                    editText.setWidth(1000);
                    editText.setHeight(100);
                    viewGroup.addView(editText, c);
                    editTexts.add(editText);


                    c++;
                } else {
                    Toast.makeText(getContext(), "Maximum 5 options available", Toast.LENGTH_LONG).show();

                }
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean success = true;
                for (int i = 0; i < c; i++) {
                    if (editTexts.get(i).getText().toString().equals("")) {
                        success = false;
                        break;
                    }
                }
                title = editText1.getText().toString();
                if (success && !(title.equals(""))) {
                    List<String> y = new ArrayList<>();
                    for (int i = 0; i < c; i++) {
                        //  options = options + editTexts.get(i).getText().toString() + ";";
                        y.add(editTexts.get(i).getText().toString());
                    }
                    for (int j = y.size() - 1; j < 5; j++) {
                        y.add("null");
                    }
                    if (!timee.equals("") && !subtime.equals("")) {
                        String[] ar= new String[5];
                       ar= y.toArray(ar);



                        send(ar, title, timee+" "+subtime);

                       /* for(int i=0;i<c;i++){
                            viewGroup.removeView(editTexts.get(i));
                        }
                        editTexts.clear();
                        editTexts=new ArrayList<>();
                        c=0;*/

                        // editTexts=new ArrayList<>();
                    } else {
                        Toast.makeText(getContext(), "Enter Options and Deadline!", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getContext(), "Enter Valid Details", Toast.LENGTH_LONG).show();

                }
            }
        });


        return view;
    }

    public void send(String[] options, String title, String deadline) {
        progressBar.setVisibility(View.VISIBLE);
      String base="https://academic-manager-nitt.el.r.appspot.com/";
        
       // String base="https://academic-manager-nitt.el.r.appspot.com/";
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
        RequestBody class_id = RequestBody.create(MediaType.parse("text/plain"), section);
        RequestBody titlee = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody opt1 = RequestBody.create(MediaType.parse("text/plain"), options[0]);
        RequestBody opt2 = RequestBody.create(MediaType.parse("text/plain"), options[1]);
        RequestBody opt3 = RequestBody.create(MediaType.parse("text/plain"), options[2]);
        RequestBody opt4 = RequestBody.create(MediaType.parse("text/plain"), options[3]);
        RequestBody opt5 = RequestBody.create(MediaType.parse("text/plain"), options[4]);
        RequestBody deadlinee = RequestBody.create(MediaType.parse("text/plain"), deadline);

        Call<AdminPollUP> call = fetchInfo.uploadPollNow(class_id, titlee, opt1, opt2, opt3, opt4, opt5, deadlinee);
        call.enqueue(new Callback<AdminPollUP>() {
            @Override
            public void onResponse(Call<AdminPollUP> call, Response<AdminPollUP> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Error ! Please Try Again!!", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getContext(), "Successfully Uploaded", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                notification("New Poll Launched", "Make sure to cast your vote without fail before " + timee + " " + subtime + "!\nYour decision is our destination");
                FragmentTransaction tr = getFragmentManager().beginTransaction();
                tr.replace(R.id.fragment_container, new PollUpload(section));
                tr.commit();



            }

            @Override
            public void onFailure(Call<AdminPollUP> call, Throwable t) {

            }
        });
        //Toast.makeText(getContext(), "" + c, Toast.LENGTH_LONG).show();
       /* String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        notiApi = retrofit.create(NotiApi.class);
        Call<Controller> call = notiApi.sendpoll(new PollUp(0, title, section, semester, options, text, 0, null, timee + " " + subtime, currentDate));
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
                notification("New Poll Launched", "Make sure to cast your vote without fail before " + timee + " " + subtime + "!\nYour decision is our destination");

                progressBar.setVisibility(View.GONE);
                FragmentTransaction tr = getFragmentManager().beginTransaction();
                tr.replace(R.id.listingf, new PollUpload(section));
                tr.commit();


            }

            @Override
            public void onFailure(Call<Controller> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error ! Please Try Again!!", Toast.LENGTH_LONG).show();

            }
        });*/
    }

    public void notification(String title, String body) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        notiApi = retrofit.create(FetchInfo2.class);
        String topic="/topics/class"+section;
        /*if (section.equals("ICEB")) {
            topic = "/topics/test";
        } else {
            topic = "/topics/weather1";
        }*/

        String oi=timee+" "+subtime;
        String[] dead=oi.split(" ");
        String[] dead1=dead[0].split("-");
        String dead2="*"+dead1[2]+"/"+dead1[1]+"/"+dead1[0]+"*";
        String[] dead3=dead[1].split(":");
        Integer h1=Integer.parseInt(dead3[0]);
        String period="";
        if(h1>12){
            h1=h1-12;
            period="PM!*";
        }else{
            if(h1==0){
                h1=12;
            }
            period="AM!*";
        }
        String findead=dead2+" *"+h1+":"+dead3[1]+" "+period;
        body="Make sure to cast your vote without fail before " + findead + "\nYour decision is our destination";
        Call<Mess> call = notiApi.sendnoti(new Notif(topic, new Notification(title, body, "no")));
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

    public void showTime() {
        Calendar calendar = Calendar.getInstance();
        h = calendar.get(Calendar.HOUR_OF_DAY);
        m = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String y = "";


                if (minute >= 0 && minute <= 9) {
                    subtime = hourOfDay + ":0" + minute ;

                } else {
                    subtime = hourOfDay + ":" + minute;
                }
                subbtime.setText(subtime);


            }
        }, h, m, true);
        timePickerDialog.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showDatePicker() {
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (month >= 0 && month <= 8) {
                    if (dayOfMonth >= 1 && dayOfMonth <= 9) {
                        // timee = "0" + dayOfMonth + "-0" + (month + 1) + "-" + year;
                        timee = year + "-0" + (month + 1) + "-0" + dayOfMonth;


                    } else {
                        //timee = dayOfMonth + "-0" + (month + 1) + "-" + year;
                        timee = year + "-0" + (month + 1) + "-" + dayOfMonth;

                    }
                } else {
                    if (dayOfMonth >= 1 && dayOfMonth <= 9) {
                        // timee = "0" + dayOfMonth + "-" + (month + 1) + "-" + year;
                        timee = year + "-" + (month + 1) + "-0" + dayOfMonth;

                    } else {
                        // timee = dayOfMonth + "-" + (month + 1) + "-" + year;
                        timee = year + "-" + (month + 1) + "-" + dayOfMonth;

                    }

                }

                submission.setText(timee);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH)
                , Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );


        datePickerDialog.show();
    }


}
