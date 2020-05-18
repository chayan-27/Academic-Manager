package com.example.iceb;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Percentage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage);

        Button button=(Button)findViewById(R.id.calc);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EditText editText1 = (EditText) findViewById(R.id.pres);
                    EditText editText2 = (EditText) findViewById(R.id.total);

                    int i1 = Integer.parseInt(editText1.getText().toString());
                    int i2 = Integer.parseInt(editText2.getText().toString());

                    double p = ((double) i1 / i2) * 100.0;
                    String g = String.valueOf(p);
                    TextView textView = (TextView) findViewById(R.id.stat);

                    if(p>=75.0&&p<=100.0)
                    {
                        textView.setTextColor(Color.parseColor("#7FFF00"));
                        textView.setVisibility(View.VISIBLE);
                    }
                    else if(p>=0&&p<75)
                    {
                        textView.setTextColor(Color.parseColor("#FF0000"));
                        textView.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        Toast.makeText(Percentage.this,"Enter valid Details",Toast.LENGTH_SHORT).show();
                    }
                    textView.setText(g);
                }catch (Exception e)
                {
                    Toast.makeText(Percentage.this,"Enter valid Details",Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
}
