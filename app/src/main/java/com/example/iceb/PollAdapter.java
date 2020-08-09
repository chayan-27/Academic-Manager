package com.example.iceb;

import android.content.Context;
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

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Streaming;

public class PollAdapter extends RecyclerView.Adapter<PollAdapter.PollHolder> {
    List<Poll> list;
    String section;
    Integer semester;
    int roll;
    Context context;
    String selection;
    int pos;
    ProgressBar progressBar;

    public PollAdapter(List<Poll> list, String section, Integer semester, int roll, Context context,ProgressBar progressBar) {
        this.list = list;
        Collections.reverse(list);
        this.section = section;
        this.semester = semester;
        this.roll = roll;
        this.context = context;
        this.progressBar=progressBar;
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

        String title = list.get(position).getPTitle();
        holder.textView.setText(title);
        String i = list.get(position).getOptions();
        String[] i1 = i.split(";");
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, i1);
        holder.spinner.setAdapter(adapter);
        selection = i1[0];
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


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                char ch = (char)(65 + pos);
                send(list.get(position).getPid(),ch+";");
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

        public PollHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.subject);
            cardView = (CardView) itemView.findViewById(R.id.cards);
            spinner = (Spinner) itemView.findViewById(R.id.sem);
            button = (Button) itemView.findViewById(R.id.response);
        }
    }

    public void send(int pid,String response){
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ice.com.144-208-108-137.ph103.peopleshostshared.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FetchInfo fetchInfo = retrofit.create(FetchInfo.class);
        Call<Controller> call = fetchInfo.pollvote(new Poll(null,null,null,0,null,pid,response,roll));
        call.enqueue(new Callback<Controller>() {
            @Override
            public void onResponse(Call<Controller> call, Response<Controller> response) {
                if (!(response.isSuccessful())) {
                   progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, response.body().getErrMsg()+"", Toast.LENGTH_LONG).show();
                    return;
                }
                if(response.body().getErrMsg()!=null){
                        Toast.makeText(context, response.body().getErrMsg()+"", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(context, "Vote Successfully Casted", Toast.LENGTH_LONG).show();

                }
                  progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<Controller> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);

            }
        });
    }
}
