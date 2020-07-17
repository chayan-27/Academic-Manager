package com.example.iceb;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iceb.server.Controller;
import com.example.iceb.server.UserFile;
import com.github.barteksc.pdfviewer.PDFView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class UploadAssignF extends Fragment {
    File file;
    ImageButton imageButton;
    Button button;
    String section;
    Integer rollno;
    String subject;
    String title;
    Integer semester;
    String encoded = "";
    ProgressBar progressBar;
    TextView submit;
    Button preview;
    String previewFile="";
   Uri pr;

    private static final int REQUEST_CODE = 100;


    @SuppressLint("ValidFragment")
    public UploadAssignF(File file, String section, Integer rollno, String subject, String title, Integer semester) {
        // Required empty public constructor
        this.file = file;
        this.section = section;
        this.rollno = rollno;
        this.subject = subject;
        this.title = title;
        this.semester = semester;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_assign, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PDFView pdfView = (PDFView) view.findViewById(R.id.pdfView);
        pdfView.fromFile(file).load();
        imageButton = (ImageButton) view.findViewById(R.id.upload);
        submit=(TextView)view.findViewById(R.id.submit);
        preview=(Button)view.findViewById(R.id.preview);
         SharedPreferences myuser = getContext().getSharedPreferences("Myapp", Context.MODE_PRIVATE);
        String pass = myuser.getString("pass"+title+rollno+section+subject, "");
        previewFile=myuser.getString("preview"+title+rollno+section+subject,"");
        if(pass.equals("submit")){
            submit.setVisibility(View.VISIBLE);
          //  button.setTextColor(Color.GREEN);
        }else{
            submit.setVisibility(View.GONE);
        }


        button = (Button) view.findViewById(R.id.send);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
                alertdialog.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent target = new Intent(Intent.ACTION_GET_CONTENT);
                                target.setType("application/pdf");
                                startActivityForResult(target, REQUEST_CODE);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setMessage("Please choose file for upload(PDF Only)!");
                AlertDialog alertDialog = alertdialog.create();
                alertDialog.setTitle("Choose File");
                alertDialog.show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!(encoded.equals(""))) {
                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
                    alertdialog.setCancelable(false)
                            .setMessage("Are you sure to submit your assignment ?")
                            .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    send();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertdialog.create();
                    alertDialog.setTitle("Submit Assignment!");
                    alertDialog.show();

                } else {
                    Toast.makeText(getContext(), "Please choose a file!", Toast.LENGTH_LONG).show();
                }
            }
        });

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!previewFile.equals("")){
                    //File file =new File(previewFile);
                   // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PDFViewfrag(file)).addToBackStack(null).commit();*/
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setDataAndType(Uri.parse(previewFile), "application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(getContext(),"Unable to open file",Toast.LENGTH_LONG).show();

                    }
                }else{
                    Toast.makeText(getContext(),"No File Selected",Toast.LENGTH_LONG).show();
                }

            }
        });
        return view;
    }

    public void send() {
        SharedPreferences myuser = getContext().getSharedPreferences("Myapp", Context.MODE_PRIVATE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo fetchInfo = retrofit.create(FetchInfo.class);
        Call<Controller> call = fetchInfo.sendassign(new UserFile(encoded, section, semester, rollno, subject, title));
        call.enqueue(new Callback<Controller>() {
            @Override
            public void onResponse(Call<Controller> call, Response<Controller> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    assert response.body() != null;
                    Toast.makeText(getContext(), "Could not complete request ! Please Try Again!!", Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.GONE);
                if (response.body().getErrMsg()!=null) {
                    Toast.makeText(getContext(), "Server Error : "+response.body().getErrMsg(), Toast.LENGTH_LONG).show();
                   /* SharedPreferences.Editor editor = myuser.edit();
                    editor.putString("pass"+title+rollno+section+subject, "submit");
                    editor.commit();

                    submit.setVisibility(View.VISIBLE);*/


                } else {
                    SharedPreferences.Editor editor = myuser.edit();
                    editor.putString("pass"+title+rollno+section+subject, "submit");
                    editor.putString("preview"+title+rollno+section+subject,pr.toString());
                    editor.commit();
                    submit.setVisibility(View.VISIBLE);
                  //  button.setTextColor(Color.GREEN);
                    Toast.makeText(getContext(), "Successfully Uploaded", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Controller> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error ! Please Try Again!!", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == RESULT_OK) {
            if (data != null) {
                final Uri uri = data.getData();
                pr=data.getData();
                try {
                    final String filepath = uri.getPath();
                    File file = new File(filepath);



                    InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                    byte[] bytes = getBytes(inputStream);

                    encoded = Base64.encodeToString(bytes, Base64.DEFAULT);


                    encoded = encoded.replace("\n", "").replace("\r", "");
                    Toast.makeText(getContext(), "Selected File : " + filepath, Toast.LENGTH_LONG).show();
                    previewFile=pr.toString();


                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

}
