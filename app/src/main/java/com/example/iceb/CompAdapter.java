package com.example.iceb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class CompAdapter extends RecyclerView.Adapter<CompAdapter.CompHolder> {
    List<String> components;
    Context context;
    int ar[];
    FirebaseAuth mFirebaseAuth;

    public CompAdapter(List<String> components, Context context,int ar[]) {
        this.components = components;
        this.context=context;
        this.ar=new int[components.size()];
        this.ar=ar;

    }

    @NonNull
    @Override
    public CompHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.compo, viewGroup, false);
        return new CompHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompHolder compHolder, int i) {
        compHolder.textView.setText(components.get(i).toUpperCase());
        compHolder.imageView.setImageResource(ar[i]);
        AppCompatActivity appCompatActivity=(AppCompatActivity)context;
        FragmentManager fragment=appCompatActivity.getSupportFragmentManager();

        compHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==0){
                    fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.compff,new Timet()).addToBackStack(null).commit();
                }else if(i==1){
                    fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.compff,new announcef()).addToBackStack(null).commit();
                }else if(i==2){
                    fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.compff,new Alarmf()).addToBackStack(null).commit();
                }else if(i==3){
                    fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.compff,new Attendant()).addToBackStack(null).commit();
                }else if(i==4){
                    fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.compff,new Studymf("https://drive.google.com/open?id=1Em0c6h8scxmh3ZInGDCI3FFrdWtNnYtq")).addToBackStack(null).commit();
                }else if(i==5){
                    fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.compff,new Studymf("https://drive.google.com/open?id=15zhjORLoDc6ib59Lg0cKCvFcKwGTGdgA")).addToBackStack(null).commit();
                }else if(i==6){
                    fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.compff,new Chatf()).addToBackStack(null).commit();
                }else if(i==7){
                    fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.compff,new Coursef()).addToBackStack(null).commit();
                }else if(i==8){
                    fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.compff,new Studymf("https://docs.google.com/forms/d/e/1FAIpQLScd26k0BVjrqC6LQ0UoTpg7a-BOSGI6de3J1cvu5JfPfEqVEg/viewform")).addToBackStack(null).commit();
                }else if(i==9){
                   // fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.compff,new Aboutf()).addToBackStack(null).commit();
                    Intent intent=new Intent(context,Testingg.class);
                    context.startActivity(intent);
                }else if(i==10){
                    fragment.beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right).replace(R.id.compff,new Aboutf()).addToBackStack(null).commit();
                }else if(i==11){
                    mFirebaseAuth.getInstance().signOut();
                    Information.flag2 = 0;
                    Intent intent = new Intent(context, Information.class);
                    appCompatActivity.startActivity(intent);
                    appCompatActivity.finish();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return components.size();
    }


    public class CompHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;
        ImageView imageView;

        public CompHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            cardView = (CardView) itemView.findViewById(R.id.cards);
            imageView=(ImageView) itemView.findViewById(R.id.pokemi);
        }
    }
}
