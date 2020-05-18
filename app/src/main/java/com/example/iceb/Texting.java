package com.example.iceb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Texting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texting);
    }

    public void webb(View view) {
        Intent intent=new Intent(Texting.this,Webmail.class);
        startActivity(intent);
    }

    public void hooray(View view) {
        Information.flag2 = 0;
        Intent intent=new Intent(Texting.this,Information.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Information.flag2 = 0;
        Intent intent=new Intent(Texting.this,Information.class);
        startActivity(intent);
        finish();
    }
}
