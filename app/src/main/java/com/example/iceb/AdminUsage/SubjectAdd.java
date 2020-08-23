package com.example.iceb.AdminUsage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.iceb.R;
import com.example.iceb.server2.AdminSubject;
import com.example.iceb.server2.FetchInfo2;

import java.util.Calendar;

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
public class SubjectAdd extends Fragment {
    Button button;
    EditText editText;
    String semester;
    ProgressBar progressBar;
    String section;

    public SubjectAdd(String section,String semester) {
        // Required empty public constructor
        this.section = section;
        this.semester=semester;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subject_add, container, false);
        button = (Button) view.findViewById(R.id.button2);
        editText = (EditText) view.findViewById(R.id.editText4);
        progressBar=(ProgressBar)view.findViewById(R.id.progress);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().equals("")){
                    progressBar.setVisibility(View.VISIBLE);
                   String base="https://academic-manager-nitt.el.r.appspot.com/";
        
       // String base="https://academic-manager-nitt.el.r.appspot.com/";
        
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(base)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
                    RequestBody class_id = RequestBody.create(MediaType.parse("text/plain"), section);
                    RequestBody semester1 = RequestBody.create(MediaType.parse("text/plain"),manipulatesem(semester));
                    RequestBody subject_code = RequestBody.create(MediaType.parse("text/plain"), editText.getText().toString());
                    Call<AdminSubject> call=fetchInfo.sendSubjects(class_id,semester1,subject_code);
                    call.enqueue(new Callback<AdminSubject>() {
                        @Override
                        public void onResponse(Call<AdminSubject> call, Response<AdminSubject> response) {
                            if(!response.isSuccessful()){
                                Toast.makeText(getContext(),"No response from the server",Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                return;
                            }

                            Toast.makeText(getContext(),"Subject Successfully Added",Toast.LENGTH_SHORT).show();
                            editText.setText("");
                            progressBar.setVisibility(View.GONE);



                        }

                        @Override
                        public void onFailure(Call<AdminSubject> call, Throwable t) {
                            Toast.makeText(getContext(),"No response from the server",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
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
}
