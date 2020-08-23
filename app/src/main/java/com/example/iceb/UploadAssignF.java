package com.example.iceb;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iceb.server.Controller;
import com.example.iceb.server.UserFile;
import com.example.iceb.server2.AdminAssignment;
import com.example.iceb.server2.FetchInfo2;
import com.example.iceb.server2.SubjectResponse;
import com.example.iceb.server2.SubmitAssignment;
import com.github.barteksc.pdfviewer.PDFView;
import com.squareup.okhttp.OkHttpClient;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
   String subdate;
   Button unsub;
   String assigned_assignment_id;
    File file23;
    MultipartBody.Part multipartBody;
    SharedPreferences myuser;

    private static final int REQUEST_CODE = 100;


    @SuppressLint("ValidFragment")
    public UploadAssignF(File file, String section, Integer rollno, String subject, String title, Integer semester,String subdate,String assigned_assignment_id) {
        // Required empty public constructor
        this.file = file;
        this.section = section;
        this.rollno = rollno;
        this.subject = subject;
        this.title = title;
        this.semester = semester;
        this.subdate=subdate;
        this.assigned_assignment_id=assigned_assignment_id;
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
        unsub=(Button)view.findViewById(R.id.unsub);
          myuser = getContext().getSharedPreferences("Myapp", Context.MODE_PRIVATE);
        String pass = myuser.getString("pass"+title+rollno+section+subject, "");
        previewFile=myuser.getString("preview"+title+rollno+section+subject,"");
        if(pass.equals("submit")){
            submit.setVisibility(View.VISIBLE);
           // unsub.setVisibility(View.VISIBLE);
          //  button.setTextColor(Color.GREEN);
        }else{
           /* SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date strDate = null;
            try {
                strDate = sdf.parse(subdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(System.currentTimeMillis()<=strDate.getTime()){
               // submit.setVisibility(View.GONE);
            }else{
               *//* submit.setText("Missing!");
                submit.setTextColor(Color.RED);*//*
            }*/

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
                                target.setType("*/*");
                                startActivityForResult(target, REQUEST_CODE);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setMessage("Please choose file for upload!");
                AlertDialog alertDialog = alertdialog.create();
                alertDialog.setTitle("Choose File");
                alertDialog.show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!(multipartBody==null)) {
                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
                    alertdialog.setCancelable(false)
                            .setMessage("Are you sure to submit your assignment ?")
                            .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    animation(0,50,10000,progressBar);
                                    sendAssignment();
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
               // previewFile=myuser.getString("preview"+title+rollno+section+subject,"");
                String path="Assignment Submissions/"+subject+"/"+title;
                String name="/"+rollno+myuser.getString("preview"+title+rollno+section+subject,"");
                File root1 = new File(Objects.requireNonNull(requireContext().getExternalFilesDir(path)).getAbsolutePath() + name);



                if(root1.exists()){

                    //File file=new File(previewFile);

                 //   byte[] decodedString = Base64.decode(previewFile.getBytes(), Base64.DEFAULT);
                   // new File(Objects.requireNonNull(requireContext().getExternalFilesDir(path)).getAbsolutePath() + name);
                   /* String path="Assignment Submissions/"+subject+"/"+title;
                    String name="/"+rollno+file23.getName().substring(file23.getName().lastIndexOf("."));

                    File root = new File(Objects.requireNonNull(requireContext().getExternalFilesDir(path)).getAbsolutePath() + name);
                   */ Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri apkURI = FileProvider.getUriForFile(
                            getContext(), getContext().getApplicationContext()

                                    .getPackageName() + ".provider", root1);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setData(apkURI);
                    getContext().startActivity(intent);
                //    File root1=new File(getContext().getExternalFilesDir(previewFile).getAbsolutePath());


                        /*try {
                            OutputStream fileOutputStream = new FileOutputStream(root);
                            fileOutputStream.write(decodedString);
                            fileOutputStream.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                       /* PdfViewAct.file1=root;
                        Intent intent=new Intent(getContext(),PdfViewAct.class);
                        startActivity(intent);*/



                    //File file =new File(previewFile);
                   // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PDFViewfrag(file)).addToBackStack(null).commit();*/
                   /* try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setDataAndType(Uri.parse(previewFile), "application/pdf");

                        startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(getContext(),"Unable to open file",Toast.LENGTH_LONG).show();

                    }*/
                   //PdfViewAct.file1=
                }else{
                    Toast.makeText(getContext(),"No File Available",Toast.LENGTH_LONG).show();
                }

            }
        });

       /* unsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(previewFile.equals(""))) {
                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
                    alertdialog.setCancelable(false)
                            .setMessage("Are you sure to UNSUBMIT your assignment ?")
                            .setPositiveButton("UNSUBMIT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    animation(0,50,10000,progressBar);
                                    unsubmit();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertdialog.create();
                    alertDialog.setTitle("Unsubmit Assignment!");
                    alertDialog.show();

                } else {
                    Toast.makeText(getContext(), "File not available", Toast.LENGTH_LONG).show();
                }
            }
        });*/
        return view;
    }

    /*public void send() {
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
                   *//* SharedPreferences.Editor editor = myuser.edit();
                    editor.putString("pass"+title+rollno+section+subject, "submit");
                    editor.commit();

                    submit.setVisibility(View.VISIBLE);*//*


                } else {
                    SharedPreferences.Editor editor = myuser.edit();
                    editor.putString("pass"+title+rollno+section+subject, "submit");
                    editor.putString("preview"+title+rollno+section+subject,previewFile);
                    editor.commit();
                    submit.setVisibility(View.VISIBLE);
                    submit.setText("Successfully Submitted");
                    unsub.setVisibility(View.VISIBLE);
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
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == RESULT_OK) {
            if (data != null) {
                final Uri uri = data.getData();
                pr=data.getData();
                try {
                    final String filepath = uri.getPath();
                    file23 = new File(filepath);


                    Thread thread=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            InputStream inputStream = null;
                            try {
                                inputStream = getContext().getContentResolver().openInputStream(uri);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            try {
                                saveFile(getBytes(inputStream),file23.getName().substring(file23.getName().lastIndexOf(".")));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();



                 /*   InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                    byte[] bytes = getBytes(inputStream);

                    encoded = Base64.encodeToString(bytes, Base64.DEFAULT);


                    encoded = encoded.replace("\n", "").replace("\r", "");
                   */ Toast.makeText(getContext(), "Selected File : " + filepath, Toast.LENGTH_LONG).show();
                    previewFile=filepath;
                   /* String path="Assignment Submissions/"+subject+"/"+title;
                    String name="/"+rollno+".pdf";
                    byte[] decodedString = Base64.decode(encoded.getBytes(), Base64.DEFAULT);
                    File root = new File(requireContext().getExternalFilesDir(path).getAbsolutePath() + name);
                    try {
                        OutputStream fileOutputStream = new FileOutputStream(root);
                        fileOutputStream.write(decodedString);
                        fileOutputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/


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

    /*public void unsubmit(){
        SharedPreferences myuser = getContext().getSharedPreferences("Myapp", Context.MODE_PRIVATE);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                .addConverterFactory(GsonConverterFactory.create())

                .build();
        FetchInfo fetchInfo = retrofit.create(FetchInfo.class);
        Call<Controller> call = fetchInfo.unsubassign(new UserFile(null, section, semester, rollno, subject, title));
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
                   *//* SharedPreferences.Editor editor = myuser.edit();
                    editor.putString("pass"+title+rollno+section+subject, "submit");
                    editor.commit();

                    submit.setVisibility(View.VISIBLE);*//*


                } else {
                    SharedPreferences.Editor editor = myuser.edit();
                    editor.putString("pass"+title+rollno+section+subject, "");
                    editor.putString("preview"+title+rollno+section+subject,"");
                    editor.commit();
                    submit.setVisibility(View.GONE);
                    unsub.setVisibility(View.GONE);
                    previewFile="";
                    //  button.setTextColor(Color.GREEN);
                    Toast.makeText(getContext(), "Successfully Unsubmitted", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Controller> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error ! Please Try Again!!", Toast.LENGTH_LONG).show();

            }
        });
    }*/

    public void animation(int a, int b, int time, ProgressBar progressBar) {
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", a, b);
        animation.setDuration(time);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //do something when the countdown is complete
                if (b == 50) {
                    animation(50, 75, 20000, progressBar);
                } else if (b == 75) {
                    animation(75, 88, 40000, progressBar);
                } else if (b == 88) {
                    animation(88, 94, 80000, progressBar);
                } else if (b == 94) {
                    animation(94, 97, 160000, progressBar);
                } else if (b == 97) {
                    animation(97, 99, 320000, progressBar);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animation.start();
    }

    public void sendAssignment(){
       String base="https://academic-manager-nitt.el.r.appspot.com/";
        
       // String base="https://academic-manager-nitt.el.r.appspot.com/";
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
        RequestBody assigned_assignment_id1 = RequestBody.create(MediaType.parse("text/plain"), assigned_assignment_id);
        RequestBody rollno1=RequestBody.create(MediaType.parse("text/plain"), String.valueOf(rollno));
       String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        RequestBody upload_datee=RequestBody.create(MediaType.parse("text/plain"), currentDate);
        Call<SubmitAssignment> call=fetchInfo.sendmyassignment(assigned_assignment_id1,rollno1,upload_datee,multipartBody);
        call.enqueue(new Callback<SubmitAssignment>() {
            @Override
            public void onResponse(Call<SubmitAssignment> call, Response<SubmitAssignment> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "No response from the server", Toast.LENGTH_LONG).show();


                    // Toast.makeText(getContext(), subject_id+" : "+topic+":"+currentDate, Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Successfully Uploaded", Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = myuser.edit();
                editor.putString("pass"+title+rollno+section+subject, "submit");
                editor.commit();

                submit.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<SubmitAssignment> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();


            }
        });

    }

    public void saveFile(byte[] decodedString,String extension) {
        String path="Assignment Submissions/"+subject+"/"+title;
        String name="/"+rollno+extension;
        File root = new File(Objects.requireNonNull(requireContext().getExternalFilesDir(path)).getAbsolutePath() + name);
        try {
            OutputStream fileOutputStream = new FileOutputStream(root);
            fileOutputStream.write(decodedString);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),root);

        multipartBody = MultipartBody.Part.createFormData("file",file23.getName(),requestFile);
        SharedPreferences.Editor editor = myuser.edit();

        editor.putString("preview"+title+rollno+section+subject,extension);
        editor.apply();


    }

}
