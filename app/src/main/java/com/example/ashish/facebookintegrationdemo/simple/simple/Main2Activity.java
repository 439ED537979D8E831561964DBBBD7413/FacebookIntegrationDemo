package com.example.ashish.facebookintegrationdemo.simple.simple;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ashish.facebookintegrationdemo.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;

import java.util.Arrays;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    Button post, post_pic, post_video, post_multiple;
    ShareDialog shareDialog;
    CallbackManager mcaCallbackManager;
    LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mcaCallbackManager = CallbackManager.Factory.create();

        post = (Button)findViewById(R.id.post);
        post_pic = (Button)findViewById(R.id.post_pic);
        post_video = (Button)findViewById(R.id.post_video);
        post_multiple = (Button)findViewById(R.id.post_multiple);
        shareDialog = new ShareDialog(this);

        /*
         *   THIS LINE WILL POP UP THE SCREEN THAT USER WANTS TO COUNTINUE AS A USER
         */
        List<String> permissionNeeds = Arrays.asList("publish_actions");
        loginManager = LoginManager.getInstance();
        loginManager.logInWithPublishPermissions(this,permissionNeeds);


        post_multiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                startActivity(intent);
            }
        });

        post_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri videoFileUri = Uri.parse("android.resource://com.example.ashish.facebookintegrationdemo"+R.raw.video);
                ShareVideo video= new ShareVideo.Builder()
                        .setLocalUrl(videoFileUri)
                        .build();
                ShareVideoContent content = new ShareVideoContent.Builder()
                        .setVideo(video)
                        .build();
                ShareApi.share(content, null);
                Toast.makeText(Main2Activity.this, "video uploaded", Toast.LENGTH_SHORT).show();
            }
        });

        post_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap image = BitmapFactory.decodeResource(getResources(),R.drawable.superman);
                SharePhoto sharephoto = new SharePhoto.Builder()
                        .setBitmap(image)
                        .setCaption("pic uploaded from android application not from the facebook...")
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(sharephoto)
                        .build();
                ShareApi.share(content, null);
                Toast.makeText(Main2Activity.this, "image posted", Toast.LENGTH_SHORT).show();
            }
        }); //post_pic

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareDialog.registerCallback(mcaCallbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(Main2Activity.this, "posted data", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                            .build();
                    shareDialog.show(linkContent);
                }
            }
        });//post
    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mcaCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}


//https://stackoverflow.com/questions/14706543/share-multiple-photos-on-facebook
//https://sites.google.com/site/lokeshurl/facebook-integration/share-multiple-photos-in-facebook-in-android