package com.example.ashish.facebookintegrationdemo.simple.simple;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ashish.facebookintegrationdemo.R;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareDialog;

import java.util.Arrays;
import java.util.List;

public class Main3Activity extends AppCompatActivity {

    Button postMultiple;
    ShareDialog shareDialog;
    CallbackManager mcaCallbackManager;
    LoginManager loginManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        postMultiple = (Button) findViewById(R.id.post);

        mcaCallbackManager = CallbackManager.Factory.create();
        List<String> permissionNeeds = Arrays.asList("publish_actions");
        loginManager = LoginManager.getInstance();
        loginManager.logInWithPublishPermissions(this, permissionNeeds);

        postMultiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });
    }

    private boolean post() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tiger);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .setUserGenerated(true)
                .setCaption("avika photos")
                .build();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.superman);
        SharePhoto photo2 = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .setCaption("avika photos")
                .build();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        SharePhoto photo3 = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .setCaption("avika photos")
                .build();



        ShareContent shareContent = new ShareMediaContent.Builder()
                .addMedium(photo)
                .addMedium(photo2)
                .addMedium(photo3)
                .build();

        ShareDialog shareDialog = new ShareDialog(Main3Activity.this);

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            shareDialog.show(shareContent);
            return true;
        }
        return false;
    }


}
