package com.example.iceb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iceb.server.Controller;
import com.example.iceb.server.Poll;
import com.example.iceb.server.Pollre;
import com.example.iceb.server2.AdminPollUP;
import com.example.iceb.server2.FetchInfo2;
import com.example.iceb.server2.PollRespond;
import com.example.iceb.server2.PollResults;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Streaming;

public class PollAdapter extends RecyclerView.Adapter<PollAdapter.PollHolder> {
    List<AdminPollUP> list;
    String section;
    Integer semester;
    int roll;
    Context context;
    String selection;
    int pos;
    ProgressBar progressBar;
    SharedPreferences myuser;
    SimpleDateFormat sdf;

    public PollAdapter(List<AdminPollUP> list, String section, Integer semester, int roll, Context context, ProgressBar progressBar) {
        this.list = list;
        Collections.reverse(list);
        this.section = section;
        this.semester = semester;
        this.roll = roll;
        this.context = context;
        this.progressBar = progressBar;
        myuser=context.getSharedPreferences("Myapp2", Context.MODE_PRIVATE);
         sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    }

    @NonNull
    @Override
    public PollHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.poll_card, parent, false);
        return new PollHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PollHolder holder, int position) {


        String title = list.get(position).getTitle();
        holder.textView.setText(title);
       // holder.deadline.setText("DeadLine : "+list.get(position).getDeadline());
        /*String[] dead=list.get(position).getDeadline().split(" ");
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
        String findead=dead2+" "+h1+":"+dead3[1]+" "+period;*/
        holder.deadline.setText(getDate(list.get(position).getDeadline()));
        String[] i2={list.get(position).getOption1(),list.get(position).getOption2(),list.get(position).getOption3(),list.get(position).getOption4(),list.get(position).getOption5()};
        List<String> list45= Arrays.asList(i2);
        List<String> list12=new ArrayList<>();
        for(String st:list45){
            if(!st.equals("null")){
                list12.add(st);
            }
        }
        /*String i = list.get(position).getOptions();
        String[] i1 = i.split(";");*/
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, list12);
        holder.spinner.setAdapter(adapter);
        selection = i2[0];
        pos = 0;
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selection = parent.getItemAtPosition(position).toString();
                pos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
      /*  Date strDate = null;
        try {
            strDate = sdf.parse(list.get(position).getDeadline());
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        try {
            if(System.currentTimeMillis()>sdf.parse(getDateinMillis(list.get(position).getDeadline())).getTime()){
                holder.button.setVisibility(View.GONE);
                holder.spinner.setVisibility(View.GONE);
                holder.options.setVisibility(View.GONE);
                holder.deadline.setVisibility(View.GONE);
                holder.results.setVisibility(View.VISIBLE);
                holder.cardView.setBackgroundColor(Color.parseColor("#FFFFFF"));


                // send(list.get(position).getPid(),i1,holder.results);
            }else{

                     holder.cardView.setBackgroundColor(Color.parseColor("#88F39E"));
                     holder.button.setVisibility(View.VISIBLE);
                     holder.spinner.setVisibility(View.VISIBLE);
                     holder.options.setVisibility(View.VISIBLE);
                     holder.deadline.setVisibility(View.VISIBLE);
                     holder.results.setVisibility(View.GONE);




            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(!myuser.getString("vote"+list.get(position).getId(),"").equals("")){
            holder.button.setVisibility(View.GONE);
            holder.spinner.setVisibility(View.GONE);
            String res=myuser.getString("vote"+list.get(position).getId(),"");
            holder.options.setText("Successfully Voted : "+res.substring(res.lastIndexOf("^")+1));


        }else{

            holder.options.setText("OPTIONS");


        }

        //Date finalStrDate = strDate;
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(System.currentTimeMillis()> sdf.parse(getDateinMillis(list.get(position).getDeadline())).getTime()){
                        holder.button.setVisibility(View.GONE);
                        holder.spinner.setVisibility(View.GONE);
                        holder.options.setVisibility(View.GONE);
                        holder.deadline.setVisibility(View.GONE);
                        holder.results.setVisibility(View.VISIBLE);

                        send(list.get(position).getId(),list12,holder.results);
                    }else{
                        //  holder.cardView.setBackgroundColor(Color.parseColor("#88F39E"));
                        holder.results.setVisibility(View.GONE);

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
                alertdialog.setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                send(list.get(position).getId(), String.valueOf(pos+1),holder.button,holder.options,holder.spinner,i2[pos]);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setMessage("Are you sure to finalize your option?");
                AlertDialog alertDialog = alertdialog.create();
                alertDialog.setTitle("Submit Response!");
                alertDialog.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class PollHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;
        Spinner spinner;
        Button button;
        TextView options;
        TextView deadline;
        TextView results;

        public PollHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.subject);
            cardView = (CardView) itemView.findViewById(R.id.cards);
            spinner = (Spinner) itemView.findViewById(R.id.sem);
            button = (Button) itemView.findViewById(R.id.response);
            options=(TextView)itemView.findViewById(R.id.options);
            deadline=(TextView)itemView.findViewById(R.id.dead);
            results=(TextView)itemView.findViewById(R.id.results);
        }
    }

    public void send(int pid, String response,Button button,TextView option,Spinner spinner,String ans) {
        progressBar.setVisibility(View.VISIBLE);


        String base = "http://192.168.1.6:8000/";
       // String base="https://academic-manager-nitt.el.r.appspot.com/";
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
        RequestBody poll_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(pid));
        RequestBody rollno1=RequestBody.create(MediaType.parse("text/plain"), String.valueOf(roll));
        RequestBody responsee=RequestBody.create(MediaType.parse("text/plain"), response);
        Call<PollRespond> call=fetchInfo.submitmyPollResponse(poll_id,rollno1,responsee);
        call.enqueue(new Callback<PollRespond>() {
            @Override
            public void onResponse(Call<PollRespond> call, Response<PollRespond> response) {
                if (!(response.isSuccessful())) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, "No response from the server", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(context, "Vote Successfully Casted", Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor=myuser.edit();
                editor.putString("vote"+pid,String.valueOf(pid)+"^"+ans);

                editor.apply();
                button.setVisibility(View.GONE);

                spinner.setVisibility(View.GONE);
                option.setText("Successfully Voted : "+ans);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PollRespond> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });


        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo fetchInfo = retrofit.create(FetchInfo.class);
        Call<Controller> call = fetchInfo.pollvote(new Poll(null, null, null, 0, null, pid, response, roll,null,currentDate));
        call.enqueue(new Callback<Controller>() {
            @Override
            public void onResponse(Call<Controller> call, Response<Controller> response) {
                if (!(response.isSuccessful())) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, response.body().getErrMsg() + "", Toast.LENGTH_LONG).show();
                    return;
                }
                if (response.body().getErrMsg() != null) {
                    Toast.makeText(context, response.body().getErrMsg() + "", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(context, "Vote Successfully Casted", Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor=myuser.edit();
                    editor.putString("vote"+pid,String.valueOf(pid)+"^"+ans);

                    editor.apply();
                    button.setVisibility(View.GONE);

                    spinner.setVisibility(View.GONE);
                    option.setText("Successfully Voted : "+ans);

                }
                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<Controller> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);

            }
        });*/
    }

    public void send(int pid, final List<String> list, final TextView textView){
        progressBar.setVisibility(View.VISIBLE);
        String base = "http://192.168.1.6:8000/";
       // String base="https://academic-manager-nitt.el.r.appspot.com/";
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo2 fetchInfo = retrofit.create(FetchInfo2.class);
        Call<PollResults> call=fetchInfo.checkresultsforpoll(String.valueOf(pid));
        call.enqueue(new Callback<PollResults>() {
            @Override
            public void onResponse(Call<PollResults> call, Response<PollResults> response) {
                if (!(response.isSuccessful())) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, "No response from the server", Toast.LENGTH_LONG).show();
                    return;
                }
                int[] votes={response.body().get1(),response.body().get2(),response.body().get3(),response.body().get4(),response.body().get5()};
                String l="";
                int sum=0;
                for(int i=0;i<list.size();i++){

                    sum=sum+votes[i];
                }
                double p;
                for(int i=0;i<list.size();i++){
                    p=(votes[i]/(double)sum)*100.0;
                    l=l+list.get(i)+" - "+Math.round(p * 100.0) / 100.0+"\n";

                }
                textView.setText(l+"Total votes = "+sum);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<PollResults> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                 progressBar.setVisibility(View.GONE);
            }
        });
      //  progressBar.setVisibility(View.VISIBLE);
       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo fetchInfo = retrofit.create(FetchInfo.class);
        Call<Controller> call = fetchInfo.viewpoll(pid);
        call.enqueue(new Callback<Controller>() {
            @Override
            public void onResponse(Call<Controller> call, Response<Controller> response) {
                if (!(response.isSuccessful())) {
                   // progressBar.setVisibility(View.GONE);
                 //   Toast.makeText(context, response.body().getErrMsg()+"", Toast.LENGTH_LONG).show();
                    return;
                }
                if(response.body().getErrMsg()!=null){
                //    Toast.makeText(context, response.body().getErrMsg()+"", Toast.LENGTH_LONG).show();

                }else{

                    String l="";
                    int c=0;
                    int sum=0;

                    List<Pollre> list=response.body().getPollres();
                    for(Pollre pollre:list){
                        l=l+i1[c]+" - "+pollre.getYesval()+"%"+" = "+pollre.getTotalvotes()+"\n";
                        sum=sum+pollre.getTotalvotes();
                        c++;
                    }

                    textView.setVisibility(View.VISIBLE);
                    textView.setText(l+"Total Votes = "+sum);

                }
                //progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<Controller> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
              //  progressBar.setVisibility(View.GONE);

            }
        });*/
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

    public String getDateinMillis(String date){
        String actual_date=date.substring(0,date.indexOf("T"));
        String actual_time=date.substring(date.indexOf("T")+1,date.indexOf("Z"));
        String now=actual_date+" "+actual_time;
        return now;
    }
}
