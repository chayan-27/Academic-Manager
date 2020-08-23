package com.example.iceb;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iceb.server.Assignment;
import com.example.iceb.server.Controller;
import com.example.iceb.server2.AdminAssignment;
import com.example.iceb.server2.FetchInfo2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentHolder> {
    List<Assignment> components;
    Context context;
    String section;
    ProgressBar progressBar;
    int roll;
    int semester;
    String l;
    String subh;
    List<AdminAssignment> list;
    String subjectt;
    SimpleDateFormat sdf;

    public AssignmentAdapter(List<Assignment> components, Context context, String section, ProgressBar progressBar, int roll, int semester) {
        this.components = components;
        this.context = context;
        this.section = section;
        this.progressBar = progressBar;
        this.roll = roll;
        this.semester = semester;
    }

    public AssignmentAdapter(int roll, List<AdminAssignment> body, String subjectt, Context context) {
        this.roll = roll;
        this.list = body;
        this.subjectt = subjectt;
        this.context = context;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    }

    @NonNull
    @Override
    public AssignmentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.assigncard, viewGroup, false);
        return new AssignmentHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull AssignmentHolder assignmentHolder, int i) {
        //String subject = components.get(i).getSubject();
        String title = list.get(i).getTopic();
        String update = list.get(i).getUploadDate();
        assignmentHolder.textView.setText(subjectt);
        assignmentHolder.textView1.setText(title);
        /*String[] up1 = new String[2];
        try {


            up1 = update.split("&");
            if (up1.length == 0) {
                up1 = new String[2];
                up1[1] = update;
                l = "";
                up1[0] = "";
            } else {
                l = up1[0];
            }
        } catch (Exception e) {
        }*/
        String subdate = list.get(i).getDeadline();
        assignmentHolder.textView2.setText("Sent : " + getDate(update));
        assignmentHolder.textView3.setText("DeadLine : " + getDate(subdate));



       /* try {


            assignmentHolder.textView2.setText("Sent : " + up1[1]);
            assignmentHolder.textView3.setText("DeadLine : " + subdate + "\n" + up1[0]);
        } catch (Exception e) {
            assignmentHolder.textView2.setText("Sent : " + update);
            assignmentHolder.textView3.setText("DeadLine : " + subdate);

        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date strDate = null;
        try {
            strDate = sdf.parse(subdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        try {
            if (System.currentTimeMillis() <= sdf.parse(getDateforMillis(subdate)).getTime()) {
                assignmentHolder.cardView.setCardBackgroundColor(Color.parseColor("#88F39E"));
            } else {
                assignmentHolder.cardView.setCardBackgroundColor(Color.parseColor("#ff726f"));
                /*if (subdate.equals(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()))) {
                    assignmentHolder.textView2.setTextColor(Color.BLACK);
                    *//*try {
                        if (up1[0].charAt(up1[0].length() - 1) == '0') {
                            assignmentHolder.textView3.setText("DeadLine : Today");
                        }else{
                            assignmentHolder.textView3.setText("DeadLine : Today\n" + up1[0]);

                        }

                    } catch (Exception e) {
                        assignmentHolder.textView3.setText("DeadLine : Today");
                    }*//*
                    assignmentHolder.textView3.setTextColor(Color.BLACK);
                }*/
                //Toast.makeText(context,new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()),Toast.LENGTH_LONG).show();


            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String extension = list.get(i).getFile().substring(list.get(i).getFile().lastIndexOf("."));

        String path = "Assignments/" + subjectt;
        String name = "/" + title + extension;
        File file = new File(Objects.requireNonNull(context.getExternalFilesDir(path)).getAbsolutePath() + name);
        new TestBack(assignmentHolder.imgpdf, extension).execute(file);

        /*if (file.exists()) {
            ParcelFileDescriptor parcelFileDescriptor= null;
            try {
                parcelFileDescriptor = ParcelFileDescriptor.open(file,ParcelFileDescriptor.MODE_READ_ONLY);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            PdfRenderer renderer = null;
            try {
                renderer = new PdfRenderer(parcelFileDescriptor);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_4444);
            PdfRenderer.Page page = renderer.openPage(0);
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            assignmentHolder.imgpdf.setImageBitmap(bitmap);

        }*/


        assignmentHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignmentHolder.progressBar.setVisibility(View.VISIBLE);
                animation(0, 50, 10000, assignmentHolder.progressBar);
                downloadassignment(file, extension, list.get(i).getFile(), assignmentHolder.imgpdf, assignmentHolder.progressBar, title, list.get(i).getId().toString());
                //downloadassign(title, section, subject,assignmentHolder.imgpdf,assignmentHolder.progressBar);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AssignmentHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        CardView cardView;
        ImageView imgpdf;
        ProgressBar progressBar;

        public AssignmentHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.subject);
            textView1 = (TextView) itemView.findViewById(R.id.title);
            textView2 = (TextView) itemView.findViewById(R.id.udate);
            textView3 = (TextView) itemView.findViewById(R.id.sdate);
            cardView = (CardView) itemView.findViewById(R.id.cards);
            imgpdf = (ImageView) itemView.findViewById(R.id.imgpdf);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pres);
        }
    }

    public void downloadassign(String title, String section, String subject, ImageView imgpdf, ProgressBar progressBar) {
        String path = "Assignments/" + subject;
        String name = "/" + title + ".pdf";
        File file = new File(Objects.requireNonNull(context.getExternalFilesDir(path)).getAbsolutePath() + name);
        if (file.exists()) {
            progressBar.setVisibility(View.GONE);
            AppCompatActivity appCompatActivity = (AppCompatActivity) context;

            //appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UploadAssignF(file, section, roll, subject, title, semester,subh)).addToBackStack(null).commit();

        } else {
            Toast.makeText(context, "Processing Please Wait....", Toast.LENGTH_LONG).show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            FetchInfo fetchInfo = retrofit.create(FetchInfo.class);
            Call<Controller> call = fetchInfo.downloadAssignment(title, section);
            call.enqueue(new Callback<Controller>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onResponse(Call<Controller> call, Response<Controller> response) {
                    if (!(response.isSuccessful())) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "No Response From The Server", Toast.LENGTH_LONG).show();
                        return;
                    }

                    List<Assignment> list = response.body().getAssignments();
                    String he = (String) list.get(0).getAssContent();
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
                    Toast.makeText(context, "Your File is Downloaded in your Internal storage/Android/data/com.example.iceb/files", Toast.LENGTH_LONG).show();
                    AppCompatActivity appCompatActivity = (AppCompatActivity) context;
                    progressBar.setVisibility(View.GONE);
                    // Toast.makeText(context, "File found", Toast.LENGTH_LONG).show();

                    // appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UploadAssignF(file, section, roll, subject, title, semester,subh)).addToBackStack(null).commit();

                    ParcelFileDescriptor parcelFileDescriptor = null;
                    try {
                        parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    PdfRenderer renderer = null;
                    try {
                        renderer = new PdfRenderer(parcelFileDescriptor);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_4444);
                    PdfRenderer.Page page = renderer.openPage(0);
                    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                    imgpdf.setImageBitmap(bitmap);


                }

                @Override
                public void onFailure(Call<Controller> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

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

    public class TestBack extends AsyncTask<File, Void, String> {


        ImageView imageView;
        String extension;

        Bitmap bitmap1;

        public TestBack(ImageView imageView, String extension) {
            this.imageView = imageView;
            this.extension = extension;

        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This will normally run on a background thread. But to better
         * support testing frameworks, it is recommended that this also tolerates
         * direct execution on the foreground thread, as part of the {@link #execute} call.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param files The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected String doInBackground(File... files) {
            if (files[0].exists() && extension.equals(".pdf")) {

                ParcelFileDescriptor parcelFileDescriptor = null;
                try {
                    parcelFileDescriptor = ParcelFileDescriptor.open(files[0], ParcelFileDescriptor.MODE_READ_ONLY);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                PdfRenderer renderer = null;
                try {
                    renderer = new PdfRenderer(parcelFileDescriptor);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bitmap1 = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_4444);
                PdfRenderer.Page page = renderer.openPage(0);
                page.render(bitmap1, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                return "exists";

           /* AppCompatActivity appCompatActivity = (AppCompatActivity) context;
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PDFViewfrag(file)).addToBackStack(null).commit();*/

            } else {
                return "no";

            }

        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.
         * To better support testing frameworks, it is recommended that this be
         * written to tolerate direct execution as part of the execute() call.
         * The default version does nothing.</p>
         *
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param s The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         */
        @Override
        protected void onPostExecute(String s) {
            if (s.equals("exists")) {
                imageView.setImageBitmap(bitmap1);

            } else {
                if (extension.equals("")) {

                } else {
                    if (extension.equals(".pptx") || extension.equals(".ppt")) {
                        imageView.setImageResource(R.drawable.ic_icons8_microsoft_powerpoint_2019);
                    } else if (extension.equals(".doc")) {
                        imageView.setImageResource(R.drawable.ic_icons8_microsoft_word_2019);
                    } else if (extension.equals(".jpg") || extension.equals(".png") || extension.equalsIgnoreCase(".jpeg")) {
                        imageView.setImageResource(R.drawable.ic_iconfinder_image_272704);
                    } else if (extension.equals(".pdf")) {
                        imageView.setImageResource(R.drawable.pdfic);
                    } else {
                        imageView.setImageResource(R.drawable.ic_noun_file_);
                    }
                }
            }
        }
    }

    private String getDate(String date) {
        String actual_date = date.substring(0, date.indexOf("T"));
        String actual_time = date.substring(date.indexOf("T") + 1, date.indexOf("Z"));
        String now = actual_date + " " + actual_time;
        String[] dead = now.split(" ");
        String[] dead1 = dead[0].split("-");
        String dead2 = dead1[2] + "-" + dead1[1] + "-" + dead1[0];
        String[] dead3 = dead[1].split(":");
        Integer h1 = Integer.parseInt(dead3[0]);
        String period = "";
        if (h1 > 12) {
            h1 = h1 - 12;
            period = "PM";
        } else {
            if (h1 == 0) {
                h1 = 12;
            }
            period = "AM";
        }
        String findead = dead2 + " " + h1 + ":" + dead3[1] + " " + period;

        return findead;
    }

    private String getDateforMillis(String date) {
        String actual_date = date.substring(0, date.indexOf("T"));
        String actual_time = date.substring(date.indexOf("T") + 1, date.indexOf("Z"));
        String now = actual_date + " " + actual_time;
        return now;
    }

    public void downloadassignment(File file, String extension, String url, ImageView imageView, ProgressBar progressBar, String title, String assignment_id) {
        if (file.exists()) {
            progressBar.setVisibility(View.GONE);
            if (extension.equals(".pdf")) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) context;

                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UploadAssignF(file, section, roll, subjectt, title, semester, subh, assignment_id)).addToBackStack(null).commit();

            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri apkURI = FileProvider.getUriForFile(
                        context, context.getApplicationContext()

                                .getPackageName() + ".provider", file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setData(apkURI);
                context.startActivity(intent);
            }
        } else {
           // String base = "http://192.168.1.6:8000/";
            String base = "http://192.168.1.6:8000/";
       // String base="https://academic-manager-nitt.el.r.appspot.com/";
        
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(base)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
            String fileurl = base + url.substring(1);
            Call<ResponseBody> call1 = fetchInfo.downloadFileWithDynamicUrlSync(fileurl);
            call1.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (!response.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "No Response From The Server", Toast.LENGTH_LONG).show();
                        return;
                    }
                    boolean b = writeResponseBodyToDisk(response.body(), file);
                    progressBar.setVisibility(View.GONE);
                    if (b) {
                        if (extension.equals(".pdf")) {
                            AppCompatActivity appCompatActivity = (AppCompatActivity) context;

                            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UploadAssignF(file, section, roll, subjectt, title, semester, subh, assignment_id)).addToBackStack(null).commit();
//new StudyMaterialAdapter.TestBack(imageView, extension,"").execute(file);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            Uri apkURI = FileProvider.getUriForFile(
                                    context, context.getApplicationContext()

                                            .getPackageName() + ".provider", file);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            intent.setData(apkURI);
                            context.startActivity(intent);
                        }
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

    private boolean writeResponseBodyToDisk(ResponseBody body, File file) {
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


}
