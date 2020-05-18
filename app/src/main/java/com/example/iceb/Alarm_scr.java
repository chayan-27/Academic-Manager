package com.example.iceb;

import android.content.Intent;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Alarm_scr extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_scr);
        EditText hour=(EditText)findViewById(R.id.editText4);
        EditText minutes=(EditText)findViewById(R.id.editText5);



        Button btn=(Button)findViewById(R.id.set);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {
                   int h = Integer.parseInt(hour.getText().toString());
                   int m = Integer.parseInt(minutes.getText().toString());

                   Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                   intent.putExtra(AlarmClock.EXTRA_HOUR, h);
                   intent.putExtra(AlarmClock.EXTRA_MINUTES, m);

                   if (h < 24 && m < 60) {
                       startActivity(intent);
                   } else {
                       Toast toast = Toast.makeText(Alarm_scr.this, "Enter valid hours and minutes", Toast.LENGTH_SHORT);
                       toast.show();
                   }
               }catch (Exception e)
               {
                   Toast toast = Toast.makeText(Alarm_scr.this, "Enter valid hours and minutes", Toast.LENGTH_SHORT);
                   toast.show();
               }



            }
        });
    }
}
