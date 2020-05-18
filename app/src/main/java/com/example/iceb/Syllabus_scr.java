package com.example.iceb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Syllabus_scr extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus_scr);
    }
    public void icpc(View view)
    {
        Intent intent=new Intent(Syllabus_scr.this,Pdf1.class);
        intent.putExtra("icpc","icpc");
        startActivity(intent);
    }
    public void icir(View view)
    {
        Intent intent=new Intent(Syllabus_scr.this,Pdf1.class);
        intent.putExtra("icpc","icir");
        startActivity(intent);
    }
    public void eng(View view)
    {
        Intent intent=new Intent(Syllabus_scr.this,Pdf1.class);
        intent.putExtra("icpc","eng");
        startActivity(intent);
    }
    public void maths(View view)
    {
        Intent intent=new Intent(Syllabus_scr.this,Pdf1.class);
        intent.putExtra("icpc","maths");
        startActivity(intent);
    }
    public void chem1(View view)
    {
        Intent intent=new Intent(Syllabus_scr.this,Pdf1.class);
        intent.putExtra("icpc","chem1");
        startActivity(intent);
    }
    public void chem2(View view)
    {
        Intent intent=new Intent(Syllabus_scr.this,Pdf1.class);
        intent.putExtra("icpc","chem2");
        startActivity(intent);
    }
}
