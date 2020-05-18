package com.example.iceb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Quote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        try {
            if (AlarmReceiver.qt != null) {
                String quo = AlarmReceiver.qt;
                TextView textView = (TextView) findViewById(R.id.textView7);
                textView.setText(quo);
            }
        }catch (Exception e){
            Toast.makeText(this,"Hope You Have a Good Day",Toast.LENGTH_SHORT).show();
        }
    }
}
