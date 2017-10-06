package com.example.ashish.facebookintegrationdemo.simple.simple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ashish.facebookintegrationdemo.R;

public class DisplayProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_profile);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String fnm = intent.getStringExtra("fnm");
        String lnm = intent.getStringExtra("lnm");
        String mnm = intent.getStringExtra("mnm");
        String id = intent.getStringExtra("id");
        String link = intent.getStringExtra("link");
        String uri = intent.getStringExtra("uri");

        Log.e("name",name);
        Log.e("fnm",fnm);
        Log.e("lnm",lnm);
        Log.e("mnm",mnm);
        Log.e("id",id);
        if(link!=null){
            Log.e("link",link);
        }
       if(uri != null){
        Log.e("uri",uri);
       }
    }
}
