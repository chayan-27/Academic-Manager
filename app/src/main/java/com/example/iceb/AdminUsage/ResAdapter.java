package com.example.iceb.AdminUsage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iceb.PdfViewAct;
import com.example.iceb.R;
import com.example.iceb.StudyMaterialAdapter;
import com.example.iceb.server.Assignment;
import com.example.iceb.server2.AssignmentRe;
import com.example.iceb.server2.FetchInfo2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ResAdapter extends RecyclerView.Adapter<ResAdapter.ResHolder> {
    String section;
    Integer semester;
    String subject;
    String title;
    ProgressBar progressBar;
    List<AssignmentRe> list;
    Context context;


    public ResAdapter(String section, Integer semester, String subject, String title, List<AssignmentRe> list, ProgressBar progressBar, Context context) {
        this.section = section;
        this.semester = semester;
        this.subject = subject;
        this.title = title;
        this.list = list;
        this.progressBar = progressBar;
        this.context = context;
    }

    @NonNull
    @Override
    public ResHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.announ, viewGroup, false);
        return new ResHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ResHolder resHolder, final int i) {
        final int r = list.get(i).getRollno();

        resHolder.textView.setText(String.valueOf(r));
        resHolder.date.setVisibility(View.GONE);
        resHolder.time.setText(getDate(list.get(i).getSubmissionTimestamp()));
        resHolder.time.setBackgroundColor(Color.parseColor("#000000"));
        String extension=list.get(i).getFile().substring(list.get(i).getFile().lastIndexOf("."));
        String path = "AssignmentResponses/" + subject + "/" + title;
        String name = "/" + r + extension;
        File file = new File(Objects.requireNonNull(context.getExternalFilesDir(path)).getAbsolutePath() + name);
        if (file.exists()) {
            resHolder.cardView.setCardBackgroundColor(Color.parseColor("#88F39E"));
        }else {
            resHolder.cardView.setCardBackgroundColor(Color.parseColor("#ff726f"));

        }
        resHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Processing Please Wait....", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.VISIBLE);
                resHolder.cardView.setCardBackgroundColor(Color.parseColor("#88F39E"));
                downloadAssign(file,list.get(i).getFile());


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ResHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;
        TextView date;
        TextView time;

        public ResHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            cardView = (CardView) itemView.findViewById(R.id.cards);
            date=(TextView)itemView.findViewById(R.id.date);
            time=(TextView)itemView.findViewById(R.id.time);

        }
    }

   /* public void download(Integer rollno, final Context context) {
        final String path = "AssignmentResponses/" + subject + "/" + title;
        final String name = "/" + rollno + ".pdf";
        File file = new File(Objects.requireNonNull(context.getExternalFilesDir(path)).getAbsolutePath() + name);
        if (file.exists()) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(context, "File Already Exists", Toast.LENGTH_LONG).show();
        } else {


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            NotiApi notiApi = retrofit.create(NotiApi.class);
            Call<Controller> call = notiApi.downloadre(new UserFile(null, section, semester, rollno, subject, title));
            call.enqueue(new Callback<Controller>() {
                @Override
                public void onResponse(Call<Controller> call, Response<Controller> response) {
                    if (!(response.isSuccessful())) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "No Response From The Server", Toast.LENGTH_LONG).show();
                        return;
                    }
                    List<UserFile> list = response.body().getUserFiles();
                    String he = list.get(0).getUploadedContent();
                    byte[] decodedString = Base64.decode(he.getBytes(), Base64.DEFAULT);
                    File root = new File(Objects.requireNonNull(context.getExternalFilesDir(path)).getAbsolutePath() + name);
                    try {
                        OutputStream fileOutputStream = new FileOutputStream(root);
                        fileOutputStream.write(decodedString);
                        fileOutputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(context, "Your File is Downloaded in your Internal storage/Android/data/com.example.icebadmin/files", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<Controller> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

                }
            });
        }

    }*/

   private void downloadAssign(File file,String url){

       if(file.exists()){
           progressBar.setVisibility(View.GONE);
           Toast.makeText(context, "File Already Exists", Toast.LENGTH_LONG).show();


       }else{
           String base = "http://192.168.1.6:8000/";
       // String base="https://academic-manager-nitt.el.r.appspot.com/";
        
           Retrofit retrofit = new Retrofit.Builder()
                   .baseUrl(base)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
           FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
           String fileurl=base+url.substring(1);
           Call<ResponseBody> call1 = fetchInfo.downloadFileWithDynamicUrlSync(fileurl);
           call1.enqueue(new Callback<ResponseBody>() {
               @Override
               public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                   if (!response.isSuccessful()) {
                       progressBar.setVisibility(View.GONE);
                       Toast.makeText(context, "No Response From The Server", Toast.LENGTH_LONG).show();
                       return;
                   }
                   boolean b= writeResponseBodyToDisk(response.body(),file);
                   progressBar.setVisibility(View.GONE);
                   if(b){
                       Toast.makeText(context, "Your File is Downloaded in your Internal storage/Android/data/com.example.iceb/files", Toast.LENGTH_LONG).show();

                   }

               }

               @Override
               public void onFailure(Call<ResponseBody> call, Throwable t) {
                   Toast.makeText(context, "Some Error Occured!Please Try Again", Toast.LENGTH_LONG).show();
                   progressBar.setVisibility(View.GONE);
               }
           });

       }

   }

    private boolean writeResponseBodyToDisk(ResponseBody body,File file) {
        try {

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();



                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
    public String getDate(String date){
        String actual_date=date.substring(0,date.indexOf("T"));
        String actual_time=date.substring(date.indexOf("T")+1,date.indexOf("Z"));
        String now=actual_date+" "+actual_time;
        String[] dead=now.split(" ");
        String[] dead1=dead[0].split("-");
        String dead2=dead1[2]+"-"+dead1[1]+"-"+dead1[0];
        String[] dead3=dead[1].split(":");
        Integer h1=Integer.parseInt(dead3[0]);
        String period="";
        if(h1>12){
            h1=h1-12;
            period="PM";
        }else{
            if(h1==0){
                h1=12;
            }
            period="AM";
        }
        String findead=dead2+" "+h1+":"+dead3[1]+" "+period;

        return findead;
    }
}
