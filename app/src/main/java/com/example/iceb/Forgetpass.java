package com.example.iceb;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgetpass extends AppCompatActivity {
   // FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);

        EditText editText=(EditText)findViewById(R.id.recov);
        Button button=(Button)findViewById(R.id.btl);
        Button button1=(Button)findViewById(R.id.pd);
        progressBar=(ProgressBar)findViewById(R.id.prwd);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{


                String mb = editText.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);

               /*try {
                    SQLiteOpenHelper dtb = new StudentDTB(Forgetpass.this);

                    TextView textView = (TextView) findViewById(R.id.txtp);
                    SQLiteDatabase db = dtb.getReadableDatabase();
                    Cursor cursor = db.query("USERS",
                            new String[]{"PASSWORD", "MOBILE"},
                            "MOBILE = ?",
                            new String[]{mb},
                            null, null, null);
                    if (cursor.moveToFirst()) {
                        String pass = "Your Password is "+cursor.getString(0);
                        textView.setText(pass);

                    } else {
                        Toast.makeText(Forgetpass.this, "Data Unavailable", Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                    db.close();



                }catch(Exception e) {
                    Toast toast = Toast.makeText(Forgetpass.this, "Data Unavailable", Toast.LENGTH_LONG);
                    toast.show();
                }*/
                FirebaseAuth auth = FirebaseAuth.getInstance();

                auth.sendPasswordResetEmail(mb)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(Forgetpass.this, "Password reset link has been sent to the provided mail", Toast.LENGTH_SHORT).show();
                                    button.setVisibility(View.VISIBLE);
                                    // do something when mail was sent successfully.
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    // ...
                                    Toast.makeText(Forgetpass.this, "Data Unavailable", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }catch (Exception e){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Forgetpass.this, "Data Unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public void again(View view)
    {
        Information.flag2=0;
        Intent intent=new Intent(Forgetpass.this,Information.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Information.flag2=0;
        Intent intent=new Intent(Forgetpass.this,Information.class);
        startActivity(intent);
        finish();

    }
}
