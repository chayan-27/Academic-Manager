package com.example.iceb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;

import java.io.File;

public class PdfViewAct extends AppCompatActivity {
    public static File file1;
    PDFView pdfView;
    TextView current;
    TextView total;
    LinearLayout pagedial;
    EditText pagenumber;
    Button pageok;
    int currentpage;
    int totalpages;
    float x, y;
    float x1, y1;
    Paint paint;
    Path path;
    boolean brush = false;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        current = (TextView) findViewById(R.id.current);
        total = (TextView) findViewById(R.id.total);
        pagedial = (LinearLayout) findViewById(R.id.pageDial);
        pagenumber = (EditText) findViewById(R.id.pagenumber);
        pageok = (Button) findViewById(R.id.pageok);
        imageButton = (ImageButton) findViewById(R.id.pen);

        paint = new Paint();
        path = new Path();
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (brush == false) {
                    brush = true;
                    imageButton.setColorFilter(Color.GREEN);
                } else {
                    brush = false;
                    imageButton.setColorFilter(Color.BLACK);

                }
            }
        });


        pdfView.fromFile(file1).onPageChange(new OnPageChangeListener() {
            @Override
            public void onPageChanged(int page, int pageCount) {
                current.setText("" + (page + 1));
                total.setText("/" + pageCount);
                totalpages = pageCount;
            }
        }).enableAntialiasing(true)
                .load();


        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagedial.setVisibility(View.VISIBLE);
                pageok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            int p = Integer.parseInt(pagenumber.getText().toString());
                            if (p >= 1 && p <= totalpages) {
                                pdfView.jumpTo(p - 1);
                                pagedial.setVisibility(View.GONE);
                                pagenumber.setText("");
                            }
                        } catch (Exception e) {

                        }

                    }
                });
            }
        });

        // pdfView.jumpTo(Integer.parseInt(String.valueOf(current.getText())));


        /*  pdfView.fromFile(file1).onPageChange(new OnPageChangeListener() {
               @Override
               public void onPageChanged(int page, int pageCount) {
                   current.setText(String.valueOf(pageCount+page));
               }
           });*/


        // total.setText(String.valueOf(totalpages));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
