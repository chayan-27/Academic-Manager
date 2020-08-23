package com.example.iceb.AdminUsage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.iceb.R;
import com.example.iceb.server2.FetchInfo2;
import com.example.iceb.server2.Mess;
import com.example.iceb.server2.Notif;
import com.example.iceb.server2.Notification;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class Notificationf extends Fragment {
    private static final int REQUEST_CODE = 102;
    Button button;
    EditText editText;
    EditText editText1;
    String encoded = "no";
    FetchInfo2 notiApi;
    ProgressBar progressBar;
    String section;
    String topic;

    @SuppressLint("ValidFragment")
    public Notificationf(String section) {
        // Required empty public constructor
        this.section = section;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notificationf, container, false);
        button = (Button) view.findViewById(R.id.button2);
        editText = (EditText) view.findViewById(R.id.editText4);
        editText1 = (EditText) view.findViewById(R.id.editText5);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        /*if (section.equals("ICEB")) {
            topic = "/topics/class";
        } else if (section.equals("ICEA")) {
            topic = "/topics/weather1";
        }*/
        // topic="/topics/class"+section;
        String i = "class" + section;
        topic = "/topics/"+i;


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    String title = editText.getText().toString();
                    String body = editText1.getText().toString();

                    if (!(body.equals(""))) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://fcm.googleapis.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        notiApi = retrofit.create(FetchInfo2.class);
                        Call<Mess> call = notiApi.sendnoti(new Notif(topic, new Notification(title, body, encoded.toString())));
                        call.enqueue(new Callback<Mess>() {
                            @Override
                            public void onResponse(Call<Mess> call, Response<Mess> response) {
                                if (!response.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);

                                    //  Toast.makeText(getContext(), response.raw().toString(), Toast.LENGTH_LONG).show();
                                    return;
                                }
                                progressBar.setVisibility(View.GONE);
                                //Toast.makeText(getContext(), response.raw().toString(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onFailure(Call<Mess> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Successfully sent", Toast.LENGTH_SHORT).show();
                                Log.d("Sent_topic",topic);


                            }
                        });
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Empty Body", Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Empty Body", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


}
