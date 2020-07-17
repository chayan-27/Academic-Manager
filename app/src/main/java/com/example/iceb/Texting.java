package com.example.iceb;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class Texting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
