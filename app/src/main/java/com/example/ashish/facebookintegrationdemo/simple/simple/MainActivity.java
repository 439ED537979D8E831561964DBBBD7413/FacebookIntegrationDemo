package com.example.ashish.facebookintegrationdemo.simple.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ashish.facebookintegrationdemo.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.facebook.GraphRequest.newMyFriendsRequest;

public class MainActivity extends AppCompatActivity {

    CallbackManager mcaCallbackManager;
    LoginButton fblogin;
    AccessTokenTracker tokenTracker;
    ProfileTracker profileTracker;
    FacebookCallback<LoginResult> callback;
    ImageView img;
    Button nextPage;
    AccessToken accessToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.setApplicationId(getResources().getString(R.string.app_id));
        setContentView(R.layout.activity_main);

        mcaCallbackManager = CallbackManager.Factory.create();
        fblogin = (LoginButton) findViewById(R.id.fblogin);
        img = (ImageView) findViewById(R.id.img);
        nextPage = (Button) findViewById(R.id.nextPage);


        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        if (accessToken != null) {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        }

        fblogin.setReadPermissions("public_profile");

        fblogin.registerCallback(mcaCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();
         /*
         *USING BELOW CODE I CAN FATCH THE PROFILE PICTURE
         */
                final GraphRequestAsyncTask request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        String userID = null;
                        try {
                            userID = (String) object.get("id");
                            String userName = (String) object.get("name");
                            String email = (String) object.get("email");
                            String gen = (String) object.get("gender");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Glide.with(MainActivity.this).
                                load("https://graph.facebook.com/" + userID + "/picture?type=large").into(img);

                        Log.e("result", response.toString());
                    }
                }).executeAsync();


        /*
         *
         *USING BELOW CODE I CAN FATCH THE PROFILE PICTURE
         *
         */
                AccessToken access_token = loginResult.getAccessToken();
                GraphRequestBatch batch =
                        new GraphRequestBatch(GraphRequest.newMeRequest(access_token,
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject jsonObject,
                                            GraphResponse response) {
                                        // Application code for user
                                        Log.e("GraphRequestBatch", response.toString());
                                    }
                                }));
                newMyFriendsRequest(
                        access_token,
                        new GraphRequest.GraphJSONArrayCallback() {
                            @Override
                            public void onCompleted(
                                    JSONArray jsonArray,
                                    GraphResponse response) {
                                // Application code for users friends
                                Log.e("GraphRequest", response.toString());
                            }
                        });
                batch.addCallback(new GraphRequestBatch.Callback() {
                    @Override
                    public void onBatchCompleted(GraphRequestBatch graphRequests) {
                        // Application code for when the batch finishes
                    }
                });
                batch.executeAsync();


                /*
                *
                * USING THIS I CAN GET THE DETAILS AS BELOW:
                *
                */
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();

                profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        Intent intent = new Intent(MainActivity.this, DisplayProfile.class);
                        intent.putExtra("name", currentProfile.getName());
                        intent.putExtra("fnm", currentProfile.getFirstName());
                        intent.putExtra("lnm", currentProfile.getLastName());
                        intent.putExtra("mnm", currentProfile.getMiddleName());
                        intent.putExtra("id", currentProfile.getId());
                        intent.putExtra("link", currentProfile.getLinkUri());
                        intent.putExtra("uri", currentProfile.getProfilePictureUri(100, 100));
                        startActivity(intent);
                        String name = currentProfile.getName();
                        Log.e("name", name);
                        profileTracker.stopTracking();
                    }
                };

                String id = loginResult.getAccessToken().getUserId();
                String token = loginResult.getAccessToken().getToken();
                Log.e("data", id + token);
            }

            @Override
            public void onCancel() {
                Log.e("cancle", "login cancle");
            }

            @Override
            public void onError(FacebookException error) {

            }


        });//oncreate
    }
    private void regesterData() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mcaCallbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
