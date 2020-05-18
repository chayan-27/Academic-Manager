package com.example.iceb;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Chats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
    }

    public void nice(View view)
    {
        Intent intentWhatsapp = new Intent(Intent.ACTION_VIEW);
        String url = "https://chat.whatsapp.com/ErW9WwvaxSXFcjvvyf5qkS";
        intentWhatsapp.setData(Uri.parse(url));
        intentWhatsapp.setPackage("com.whatsapp");
        startActivity(intentWhatsapp);
    }
    public void off(View view)
    {
        Intent intentWhatsapp = new Intent(Intent.ACTION_VIEW);
        String url = "https://chat.whatsapp.com/HvUglTszh5sBx0EMqxMSm4";
        intentWhatsapp.setData(Uri.parse(url));
        intentWhatsapp.setPackage("com.whatsapp");
        startActivity(intentWhatsapp);

    }
    public void unoff(View view)
    {
        Intent intentWhatsapp = new Intent(Intent.ACTION_VIEW);
        String url = "https://chat.whatsapp.com/I181wC0IANhIzenn5mvaTx";
        intentWhatsapp.setData(Uri.parse(url));
        intentWhatsapp.setPackage("com.whatsapp");
        startActivity(intentWhatsapp);

    }
}
