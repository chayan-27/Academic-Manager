package com.example.iceb;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.text.util.Linkify;
import android.util.Base64;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iceb.server.Assignment;
import com.example.iceb.server.Controller;
import com.example.iceb.server.Courseplan;
import com.example.iceb.server.Studymaterial;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.acl.LastOwnerException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;

import me.saket.bettermovementmethod.BetterLinkMovementMethod;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    private List<String> components;
    private Context context;
    private String section;
    private Integer semester;
    ProgressBar progressBar;
    int roll;
    String uu;
    String batch;
    boolean admin;
    List<LiveTest> liveTests;


    public CourseAdapter(List<String> components, Context context, String section, Integer semester, int roll, ProgressBar progressBar, String batch, boolean admin,List<LiveTest> liveTests) {
        this.components = components;
        this.context = context;
        this.section = section;
        this.semester = semester;
        this.roll = roll;
        this.progressBar = progressBar;
        this.batch = batch;
        this.admin = admin;
        this.liveTests=liveTests;
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.announ, viewGroup, false);
        return new CourseHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull CourseHolder courseHolder, int i) {
        // courseHolder.textView.setText(components.get(i).substring(0,components.get(i).indexOf("$")).toUpperCase());
       String j=liveTests.get(i).getDate();
        //String j = components.get(i).substring(components.get(i).indexOf("$") + 1, components.get(i).lastIndexOf("$"));
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Date mydate = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String yestr = dateFormat.format(mydate);
       // String body = components.get(i).substring(components.get(i).indexOf(" ") + 1, components.get(i).indexOf("$"));
        String body=liveTests.get(i).getTitle()+"\n\n"+liveTests.get(i).getBody();
        SpannableStringBuilder sb = new SpannableStringBuilder(body + " ");
        List<Integer> list = new ArrayList<>();

        for (int k = 0; k < body.length(); k++) {
            if (body.charAt(k) == '*') {
                list.add(k);
            }
        }

        StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        for (int k = 0; k < list.size() - 1; k += 2) {
            bss = new StyleSpan(android.graphics.Typeface.BOLD);
            sb.setSpan(bss, list.get(k), list.get(k + 1), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        }

        for (int k = 0; k < sb.length(); k++) {

            if (sb.charAt(k) == '*') {
                sb.delete(k, k + 1);
            }
            // sb.delete(list.get(k+1),list.get(k+1)+1);
        }


        /*if (body.substring(body.lastIndexOf(" ") + 1).equals("Uploaded!")) {
            bss = new StyleSpan(Typeface.BOLD);
            sb.setSpan(bss, 0, body.indexOf("\n"), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        }*/

        courseHolder.textView.setText(sb);
        courseHolder.textView.setMovementMethod(BetterLinkMovementMethod.getInstance());
        BetterLinkMovementMethod.linkify(Linkify.ALL, courseHolder.textView).setOnLinkLongClickListener(new BetterLinkMovementMethod.OnLinkLongClickListener() {
            @Override
            public boolean onLongClick(TextView textView, String url) {
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText("label", url));
                Toast.makeText(context, "Link copied", Toast.LENGTH_LONG).show();


                return true;
            }
        });


        //Linkify.addLinks(courseHolder.textView, Linkify.ALL);


        if (j.equals(currentDate)) {
            courseHolder.imageView.setText("Today");
        } else if (j.equals(yestr)) {
            courseHolder.imageView.setText("Yesterday");
        } else {
            //courseHolder.imageView.setText(components.get(i).substring(components.get(i).indexOf("$") + 1, components.get(i).lastIndexOf("$")));
            courseHolder.imageView.setText(liveTests.get(i).getDate());


        }
       // courseHolder.cardView.setText(components.get(i).substring(components.get(i).lastIndexOf("$") + 1));
        courseHolder.cardView.setText(liveTests.get(i).getTime());


        if (i == 0) {
            courseHolder.cardView1.setCardBackgroundColor(Color.parseColor("#DCF8C6"));


        } else {
            courseHolder.cardView1.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        if (body.substring(body.lastIndexOf(" ") + 1).equals("Uploaded!")) {
            /*courseHolder.pdf.setVisibility(View.VISIBLE);
            new TestBack(courseHolder.pdf, body).execute();
            *//* *//* //  imageview(courseHolder.pdf,body);
            courseHolder.cardView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] by = body.split(" ");
                    if (by[0].equals("New")) {
                        AppCompatActivity appCompatActivity = (AppCompatActivity) context;

                        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StudyMaterialF(section, "yes", roll, "no", admin,batch)).addToBackStack(null).commit();

                    } else if(by[0].equalsIgnoreCase("Courseplan")) {
                        AppCompatActivity appCompatActivity = (AppCompatActivity) context;

                        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StudyMaterialF(section, "no", roll, "yes", admin,batch)).addToBackStack(null).commit();


                    }else{
                        AppCompatActivity appCompatActivity = (AppCompatActivity) context;

                        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StudyMaterialF(section, "no", roll, "no", admin,batch)).addToBackStack(null).commit();

                    }*/

                    // String subject = body.substring(0, body.indexOf("\n"));
                    //String title = body.substring(body.indexOf("\n\n") + 2, body.lastIndexOf(" "));
                  /*  String subject = by[0];
                    if(subject.contains("*")){
                        subject=subject.substring(1,subject.lastIndexOf("*"));
                    }
                    String h = by[1];
                    //System.out.println("//////////" + subject + h);
                    String title = h.substring(0, h.lastIndexOf(" "));


                    if (title.equals("CoursePlan")) {
                        courseHolder.progressBar.setVisibility(View.VISIBLE);
                        courseHolder.progressBar.setMax(100);
                        animation(0, 50, 10000,courseHolder.progressBar);
                        downloadcourseplan(section, subject,courseHolder.progressBar);
                    } else {
                        String[] assign = title.split(" ");
                        if (assign[0].equals("New")) {

                            if (assign.length == 3) {
                                courseHolder.progressBar.setVisibility(View.VISIBLE);
                                courseHolder.progressBar.setMax(100);
                                animation(0, 50, 10000,courseHolder.progressBar);
                                // Toast.makeText(context, "..." + subject + "   " + assign[1], Toast.LENGTH_LONG).show();

                               // downloadassign(assign[1], section, subject,courseHolder.progressBar);
                            } else {
                                int c = assign.length - 3;
                                String y = "";
                                for (int i = 1; i <= c + 1; i++) {
                                    y = y + assign[i] + " ";
                                }
                                courseHolder.progressBar.setVisibility(View.VISIBLE);
                                courseHolder.progressBar.setMax(100);
                                animation(0, 50, 10000,courseHolder.progressBar);
                                //Toast.makeText(context, "..." + subject + "   " +y, Toast.LENGTH_LONG).show();

                                //downloadassign(y, section, subject,courseHolder.progressBar);

                            }


                        } else {
                            courseHolder.progressBar.setVisibility(View.VISIBLE);
                           courseHolder. progressBar.setMax(100);
                            animation(0, 50, 10000,courseHolder.progressBar);
                            downloadstudymaterial(title, section, subject,courseHolder.progressBar);
                        }
                    }
*/

                /*}
            });*/

        } else {
            courseHolder.pdf.setVisibility(View.GONE);

            final boolean[] s = {true};
            courseHolder.cardView1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    try {

                        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboardManager.setPrimaryClip(ClipData.newPlainText("label", courseHolder.textView.getText().toString()));
                        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_LONG).show();
                        s[0] = false;


                    } catch (Exception e) {


                    }
                    return true;
                }
            });

           /* if (s[0]) {
                courseHolder.textView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboardManager.setPrimaryClip(ClipData.newPlainText("label", courseHolder.textView.getText().toString()));
                        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_LONG).show();

                        return true;
                    }
                });
            }*/
          /*  courseHolder.cardView1.setOnTouchListener(new View.OnTouchListener() {
                private long touchStart = 0l;
                private long touchEnd = 0l;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            this.touchStart = System.currentTimeMillis();
                            courseHolder.cardView1.setCardBackgroundColor(Color.parseColor("#34B7F1"));


                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if (i == 0) {
                                        courseHolder.cardView1.setCardBackgroundColor(Color.parseColor("#DCF8C6"));


                                    } else {
                                        courseHolder.cardView1.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                                    }
                                }
                            }, 1000);



                            return true;
                        case MotionEvent.ACTION_UP:
                            this.touchEnd = System.currentTimeMillis();
                            long touchtime = this.touchEnd - this.touchStart;

                            if (touchtime >= 1000) {
                                try {

                                    ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    clipboardManager.setPrimaryClip(ClipData.newPlainText("label", courseHolder.textView.getText().toString()));
                                    Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_LONG).show();

                                } catch (Exception e) {


                                }
                            }

                            if (i == 0) {
                                courseHolder.cardView1.setCardBackgroundColor(Color.parseColor("#DCF8C6"));


                            } else {
                                courseHolder.cardView1.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                            }
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            return true;


                        default:
                            if (i == 0) {
                                courseHolder.cardView1.setCardBackgroundColor(Color.parseColor("#DCF8C6"));


                            } else {
                                courseHolder.cardView1.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                            }
                            return true;

                    }


                }
            });*/
        }


    }

    @Override
    public int getItemCount() {
        return liveTests.size();
    }

    public class CourseHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView imageView;
        TextView cardView;
        CardView cardView1;
        ImageView pdf;
        ProgressBar progressBar;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            cardView = (TextView) itemView.findViewById(R.id.time);
            imageView = (TextView) itemView.findViewById(R.id.date);
            cardView1 = (CardView) itemView.findViewById(R.id.cards);
            pdf = (ImageView) itemView.findViewById(R.id.pdf);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pres);


        }
    }

    public void updateData(List<String> list) {
        this.components = list;
        notifyDataSetChanged();
    }

    public void downloadstudymaterial(String title, String section, String subject, ProgressBar progressBar) {
        String extension = title.substring(title.lastIndexOf(".") + 1);
        String hd = title;

        String path = "StudyMaterials/" + subject;
        String name;
        if (extension.equals("") || extension.equals(title)) {
            name = "/" + title + ".pdf";
            hd = title + ".pdf";
        } else {
            name = "/" + title + "." + extension;
            hd = title + "." + extension;
        }

        File file = new File(Objects.requireNonNull(context.getExternalFilesDir(path)).getAbsolutePath() + name);
        if (file.exists()) {
            progressBar.setVisibility(View.GONE);
            if (name.substring(name.lastIndexOf(".") + 1).equals("pdf")) {
                PdfViewAct.file1 = file;
                Intent intent = new Intent(context, PdfViewAct.class);
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri apkURI = FileProvider.getUriForFile(
                        context, context.getApplicationContext()

                                .getPackageName() + ".provider", file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setData(apkURI);
                context.startActivity(intent);
            }


         /*   AppCompatActivity appCompatActivity = (AppCompatActivity) context;
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PDFViewfrag(file)).addToBackStack(null).commit();*/

        } else {
            Toast.makeText(context, "Processing Please Wait....", Toast.LENGTH_LONG).show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            FetchInfo fetchInfo = retrofit.create(FetchInfo.class);
            Call<Controller> call = fetchInfo.downloadStudyMaterial(title, section);
            String finalHd = hd;
            call.enqueue(new Callback<Controller>() {
                @Override
                public void onResponse(Call<Controller> call, Response<Controller> response) {
                    if (!(response.isSuccessful())) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "No Response From The Server", Toast.LENGTH_LONG).show();
                        return;
                    }
                    String ext;

                    try {
                        List<Studymaterial> list = response.body().getStudymaterial();
                        String he = (String) list.get(0).getStuContent();
                        ext = list.get(0).getExt();
                        byte[] decodedString = Base64.decode(he.getBytes(), Base64.DEFAULT);
                        String path = "StudyMaterials/" + subject;
                        String name = "/" + title + "." + ext;
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
                        if (ext.equals("pdf") || ext.equals(null) || ext.equals("")) {
                            PdfViewAct.file1 = root;
                            Intent intent = new Intent(context, PdfViewAct.class);
                            context.startActivity(intent);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            Uri apkURI = FileProvider.getUriForFile(
                                    context, context.getApplicationContext()

                                            .getPackageName() + ".provider", root);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            intent.setData(apkURI);
                            context.startActivity(intent);
                        }
                        Toast.makeText(context, "Your File is Downloaded in your Internal storage/Android/data/com.example.iceb/files", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(context, "..." + subject + "   " + title + "......" + name + e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                    //  AppCompatActivity appCompatActivity = (AppCompatActivity) context;
                    progressBar.setVisibility(View.GONE);
                    // Toast.makeText(context, "File found", Toast.LENGTH_LONG).show();


                    // appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PDFViewfrag(file)).addToBackStack(null).commit();


                }

                @Override
                public void onFailure(Call<Controller> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    public void downloadcourseplan(String section, String subject, ProgressBar progressBar) {
        String path = "CoursePlan/" + subject;
        String name = "/" + subject + ".pdf";
        File file = new File(Objects.requireNonNull(context.getExternalFilesDir(path)).getAbsolutePath() + name);
        if (file.exists()) {
            progressBar.setVisibility(View.GONE);
            AppCompatActivity appCompatActivity = (AppCompatActivity) context;

            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PDFViewfrag(file)).addToBackStack(null).commit();

        } else {
            Toast.makeText(context, "Processing Please Wait....", Toast.LENGTH_LONG).show();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            FetchInfo fetchInfo = retrofit.create(FetchInfo.class);
            Call<Controller> call = fetchInfo.downloadCoursePlan(subject, section);
            call.enqueue(new Callback<Controller>() {
                @Override
                public void onResponse(Call<Controller> call, Response<Controller> response) {
                    if (!(response.isSuccessful())) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "No Response From The Server", Toast.LENGTH_LONG).show();
                        return;
                    }


                    try {
                        List<Courseplan> list = response.body().getCourseplan();
                        String he = (String) list.get(0).getCouContent();
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
                    } catch (Exception e) {
                        Toast.makeText(context, "..." + subject + "   " + section, Toast.LENGTH_LONG).show();

                    }
                    Toast.makeText(context, "Your File is Downloaded in your Internal storage/Android/data/com.example.iceb/files", Toast.LENGTH_LONG).show();
                    AppCompatActivity appCompatActivity = (AppCompatActivity) context;
                    progressBar.setVisibility(View.GONE);
                    // Toast.makeText(context, "File found", Toast.LENGTH_LONG).show();

                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PDFViewfrag(file)).addToBackStack(null).commit();


                }

                @Override
                public void onFailure(Call<Controller> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

   /* public void downloadassign(String title, String section, String subject,ProgressBar progressBar) {
        String path = "Assignments/" + subject;
        String name = "/" + title.trim() + ".pdf";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        File file = new File(Objects.requireNonNull(context.getExternalFilesDir(path)).getAbsolutePath() + name);
        if (file.exists()) {
            progressBar.setVisibility(View.GONE);

            AppCompatActivity appCompatActivity = (AppCompatActivity) context;

            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UploadAssignF(file, section, roll, subject, title, semester, new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()))).addToBackStack(null).commit();

        } else {
            Toast.makeText(context, "Processing Please Wait....", Toast.LENGTH_LONG).show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            FetchInfo fetchInfo = retrofit.create(FetchInfo.class);
            Call<Controller> call = fetchInfo.downloadAssignment(title, section);
            call.enqueue(new Callback<Controller>() {
                @Override
                public void onResponse(Call<Controller> call, Response<Controller> response) {
                    if (!(response.isSuccessful())) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "No Response From The Server", Toast.LENGTH_LONG).show();
                        return;
                    }
                    try {
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
                    } catch (Exception e) {
                        Toast.makeText(context, "..." + subject + "   " + title, Toast.LENGTH_LONG).show();

                    }
                    Toast.makeText(context, "Your File is Downloaded in your Internal storage/Android/data/com.example.iceb/files", Toast.LENGTH_LONG).show();
                    AppCompatActivity appCompatActivity = (AppCompatActivity) context;
                    progressBar.setVisibility(View.GONE);
                    // Toast.makeText(context, "File found", Toast.LENGTH_LONG).show();

                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UploadAssignF(file, section, roll, subject, title, semester, new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()))).addToBackStack(null).commit();


                }

                @Override
                public void onFailure(Call<Controller> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

                }
            });
        }
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

    public void imageview(ImageView imageView, String body) {
        String[] by = body.split("\n\n");

        // String subject = body.substring(0, body.indexOf("\n"));
        //String title = body.substring(body.indexOf("\n\n") + 2, body.lastIndexOf(" "));
        String subject = by[0];
        String h = by[1];
        //System.out.println("//////////" + subject + h);
        String title = h.substring(0, h.lastIndexOf(" "));


        if (title.equals("CoursePlan")) {
            imageView.setImageResource(R.drawable.pdfic);
        } else {
            String[] assign = title.split(" ");
            if (assign[0].equals("New")) {

                if (assign.length == 3) {
                    imageView.setImageResource(R.drawable.pdfic);
                } else {
                    imageView.setImageResource(R.drawable.pdfic);

                }


            } else {
                String extension = title.substring(title.lastIndexOf(".") + 1);
                if (extension.equals("") || extension.equals(title)) {
                    imageView.setImageResource(R.drawable.pdfic);
                } else {
                    if (extension.equals("pptx") || extension.equals("ppt")) {
                        imageView.setImageResource(R.drawable.ic_icons8_microsoft_powerpoint_2019);
                    } else if (extension.equals("doc")) {
                        imageView.setImageResource(R.drawable.ic_icons8_microsoft_word_2019);
                    } else if (extension.equals("jpg") || extension.equals("png") || extension.equalsIgnoreCase("jpeg")) {
                        imageView.setImageResource(R.drawable.ic_iconfinder_image_272704);
                    } else if (extension.equals("pdf")) {
                        imageView.setImageResource(R.drawable.pdfic);
                    } else {
                        imageView.setImageResource(R.drawable.ic_noun_file_);
                    }
                }
            }
        }
    }

    public class TestBack extends AsyncTask<Void, Void, String> {

        ImageView imageView;
        String body;

        public TestBack(ImageView imageView, String body) {
            this.imageView = imageView;
            this.body = body;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String[] by = body.split(" ");
            if(by[0].equals("New")){
                String u=by[1];
                return u.substring(u.lastIndexOf("."));
            }else{
                /*String u=by[0];
                return u.substring(u.lastIndexOf("."));*/
                return ".pdf";
            }


            // String subject = body.substring(0, body.indexOf("\n"));
            //String title = body.substring(body.indexOf("\n\n") + 2, body.lastIndexOf(" "));
            /*String subject = by[0];
            String h = by[1];
            //System.out.println("//////////" + subject + h);
            String title = h.substring(0, h.lastIndexOf(" "));

            if (title.equalsIgnoreCase("CoursePlan")) {
                return "pdf";
            } else {
                String[] assign = title.split(" ");
                if (assign[0].equals("New")) {

                    if (assign.length == 3) {
                       return "pdf";
                    } else {
                        return "pdf";

                    }


                } else {
                    String extension = title.substring(title.lastIndexOf(".") + 1);
                    if (extension.equals("") || extension.equals(title)) {
                       return "pdf";
                    }else{
                       return extension;
                    }
                }
            }*/

        }


        @Override
        protected void onPostExecute(String s) {
            if (s.equals(".pdf")) {
                imageView.setImageResource(R.drawable.pdfic);
            } else {
                if (s.equals(".pptx") || s.equals(".ppt")) {
                    imageView.setImageResource(R.drawable.ic_icons8_microsoft_powerpoint_2019);
                } else if (s.equals(".doc")) {
                    imageView.setImageResource(R.drawable.ic_icons8_microsoft_word_2019);
                } else if (s.equals(".jpg") || s.equals(".png") || s.equalsIgnoreCase(".jpeg")) {
                    imageView.setImageResource(R.drawable.ic_iconfinder_image_272704);
                } else {
                    imageView.setImageResource(R.drawable.ic_noun_file_);
                }
            }
        }
    }
}
