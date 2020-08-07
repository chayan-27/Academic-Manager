package com.example.iceb;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.ParcelFileDescriptor;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.iceb.server.Controller;
import com.example.iceb.server.Studymaterial;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudyMaterialAdapter extends RecyclerView.Adapter<StudyMaterialAdapter.StudyMaterialHolder> {
    List<Studymaterial> components;
    Context context;
    String section;
    String subject;
    ProgressBar progressBar;


    public StudyMaterialAdapter(List<Studymaterial> components, Context context, String section, String subject, ProgressBar progressBar) {
        this.components = components;
        this.context = context;
        this.section = section;
        this.subject = subject;
        this.progressBar = progressBar;
    }

    @NonNull
    @Override
    public StudyMaterialHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.studymaterialcard, viewGroup, false);

        return new StudyMaterialHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull StudyMaterialHolder studyMaterialHolder, int i) {
        //  String subject = components.get(i).getSubject();
        String title = components.get(i).getTitle();
        String update = components.get(i).getUploadDate();
        String extension = components.get(i).getTitle().substring(components.get(i).getTitle().lastIndexOf(".") + 1);
        studyMaterialHolder.textView.setVisibility(View.GONE);
        studyMaterialHolder.textView1.setText(title);
        studyMaterialHolder.textView3.setText("Sent : " + update);
        String path = "StudyMaterials/" + subject;
        String name = "/" + title + ".pdf";
        final boolean[] check = {false};
        File file = new File(Objects.requireNonNull(context.getExternalFilesDir(path)).getAbsolutePath() + name);

        if (file.exists()) {
            check[0] = true;
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
            studyMaterialHolder.imgpdf.setImageBitmap(bitmap);

           /* AppCompatActivity appCompatActivity = (AppCompatActivity) context;
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PDFViewfrag(file)).addToBackStack(null).commit();*/

        } else {
            if (extension.equals("") || extension.equals("pdf") || extension.equals(title)) {

            }else{
                studyMaterialHolder.imgpdf.setImageResource(R.drawable.ic_noun_file_);
            }

        }
        studyMaterialHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setMax(100);
                animation(0, 50, 10000);
                try {
                    if (extension.equals("") || extension.equals("pdf") || extension.equals(title)) {
                        downloadstudymaterial(title, section, subject, studyMaterialHolder.imgpdf, "pdf");
                    } else {
                        downloadstudymaterial(title, section, subject, studyMaterialHolder.imgpdf, extension);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return components.size();
    }

    public class StudyMaterialHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView1;
        TextView textView3;
        CardView cardView;
        ImageView imgpdf;

        public StudyMaterialHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.subject);
            textView1 = (TextView) itemView.findViewById(R.id.title);

            textView3 = (TextView) itemView.findViewById(R.id.sdate);
            cardView = (CardView) itemView.findViewById(R.id.cards);
            imgpdf = (ImageView) itemView.findViewById(R.id.imgpdf);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void downloadstudymaterial(String title, String section, String subject, ImageView imgpdf, String extension) throws IOException {
        String path = "StudyMaterials/" + subject;
        String name;
        if (extension.equals("pdf")) {
            name = "/" + title + ".pdf";
        } else {
            name = "/" + title + "." + extension;
        }

        File file = new File(Objects.requireNonNull(context.getExternalFilesDir(path)).getAbsolutePath() + name);
        if (file.exists()) {
            progressBar.setVisibility(View.GONE);
            if (extension.equals("pdf")) {
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
           /* AppCompatActivity appCompatActivity = (AppCompatActivity) context;
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PDFViewfrag(file)).addToBackStack(null).commit();*/

        } else {
            Toast.makeText(context, "Processing Please Wait....", Toast.LENGTH_LONG).show();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            FetchInfo fetchInfo = retrofit.create(FetchInfo.class);
            Call<Controller> call = fetchInfo.downloadStudyMaterial(title, section);
            //    Toast.makeText(context, ""+ call.request().body().contentLength(), Toast.LENGTH_LONG).show();


            call.enqueue(new Callback<Controller>() {
                @Override
                public void onResponse(Call<Controller> call, Response<Controller> response) {
                    if (!(response.isSuccessful())) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "No Response From The Server", Toast.LENGTH_LONG).show();
                        return;
                    }


                    //  Toast.makeText(context, ""+response.headers().size()+"+++"+response.raw().headers().size(), Toast.LENGTH_LONG).show();


                    List<Studymaterial> list = response.body().getStudymaterial();
                    String he = (String) list.get(0).getStuContent();
                    String ext = list.get(0).getExt();
                    byte[] decodedString = Base64.decode(he.getBytes(), Base64.DEFAULT);
                    String path = "StudyMaterials/" + subject;
                    String name = "";


                    name = "/" + title + "." + ext;


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
                    if (ext.equals("pdf") || ext.equals(null) || ext.equals("")) {
                        PdfViewAct.file1 = file;
                        Intent intent = new Intent(context, PdfViewAct.class);
                        context.startActivity(intent);

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
                    } else {

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        Uri apkURI = FileProvider.getUriForFile(
                                context, context.getApplicationContext()

                                        .getPackageName() + ".provider", root);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setData(apkURI);
                        context.startActivity(intent);

                    }


                    //  appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PDFViewfrag(file)).addToBackStack(null).commit();


                }

                @Override
                public void onFailure(Call<Controller> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, "Error Occured!!Please Try Again Later", Toast.LENGTH_LONG).show();

                }


            });
        }
    }

    public void animation(int a, int b, int time) {
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
                    animation(50, 75, 20000);
                } else if (b == 75) {
                    animation(75, 88, 40000);
                } else if (b == 88) {
                    animation(88, 94, 80000);
                } else if (b == 94) {
                    animation(94, 97, 160000);
                } else if (b == 97) {
                    animation(97, 99, 320000);
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
}
