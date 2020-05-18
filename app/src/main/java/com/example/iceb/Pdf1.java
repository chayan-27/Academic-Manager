package com.example.iceb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class Pdf1 extends AppCompatActivity {
    PDFView mpdfview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf1);

        mpdfview=(PDFView)findViewById(R.id.pdfView);


        Intent intent=getIntent();
        String sub=intent.getStringExtra("icpc");

        if(sub.equals("icpc"))
        {
            mpdfview.fromAsset("ICPC13-Thermodynamics_and_Fluid_Mechanics_B.pdf").load();
        }
        else if(sub.equals("icir"))
        {
            mpdfview.fromAsset("COURSE PLANicpc.pdf").load();
        }
        else if(sub.equals("eng"))
        {
            mpdfview.fromAsset("HSIR11-English_for_Communication_B.pdf").load();
        }
        else if(sub.equals("maths"))
        {
            mpdfview.fromAsset("MAIR22-Complex_Analyays_&_DE.pdf").load();
        }
        else if(sub.equals("chem1"))
        {
            mpdfview.fromAsset("CHIR11-Chemistry_Theory_B.pdf").load();
        }
        else if(sub.equals("chem2"))
        {
            mpdfview.fromAsset("CHIR12-Chemistry_Lab_B.pdf").load();
        }

    }
}
