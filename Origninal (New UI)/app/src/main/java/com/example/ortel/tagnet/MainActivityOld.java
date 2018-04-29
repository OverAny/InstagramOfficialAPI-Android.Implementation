package com.example.ortel.tagnet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


//import com.example.ortel.tagnet.InstagramApp.OAuthAuthenticationListener;
import lazyload.ImageLoader;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ortel.tagnet.InstagramApp.OAuthAuthenticationListener;

import static com.example.ortel.tagnet.InstagramApp.TAG_DATA;
import static com.example.ortel.tagnet.InstagramApp.TAG_ID;
import static com.example.ortel.tagnet.InstagramApp.TAG_PROFILE_PICTURE;
import static com.example.ortel.tagnet.InstagramApp.TAG_USERNAME;
import static com.example.ortel.tagnet.InstagramApp.WHAT_ERROR;
import static com.example.ortel.tagnet.InstagramApp.WHAT_FINALIZE;
//import static com.tag.instagramdemo.R.id.bottomBar;
//import com.roughike.bottombar.OnMenuItemClickListner;

public class MainActivityOld extends FragmentActivity implements View.OnClickListener{
    private Button btnConnect;
    private LinearLayout llAfterLoginView;
    public static HashMap<String, String> userInfoHashmap = new HashMap<String, String>();
    public static String url;
    public static String url1;

    public static int gotted = 0;
    private Handler handler = new Handler(new Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == WHAT_FINALIZE) {
                //userInfoHashmap = mApp.getUserInfo();
                userInfoHashmap = mApp.getUserInfo();
                url =  "https://api.instagram.com/v1/users/"
                        + userInfoHashmap.get(InstagramApp.TAG_ID)
                        + "/follows?access_token=" + mApp.getTOken();
                url1 =  "https://api.instagram.com/v1/users/"
                        + userInfoHashmap.get(InstagramApp.TAG_ID)
                        + "/followed-by?access_token=" + mApp.getTOken();

                displayInfoDialogView();
            }
            return false;
        }
    });
    public static InstagramApp mApp;

    public interface GlobalConstants {
        public static HashMap<String, String> userInfoHashmap = mApp.getUserInfo();


    }
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getAllMediaImages2();
        getAllMediaImages3();
        mApp = new InstagramApp(this, ApplicationData.CLIENT_ID,
                ApplicationData.CLIENT_SECRET, ApplicationData.CALLBACK_URL);
        userInfoHashmap = mApp.getUserInfo();
        Log.d("OkHttp1234", String.valueOf(userInfoHashmap));


        mApp.setListener(new OAuthAuthenticationListener() {
            @Override
            public void onSuccess() {
                mApp.fetchUserName(handler);

            }
            @Override
            public void onFail(String error) {
                Toast.makeText(MainActivityOld.this, error, Toast.LENGTH_SHORT)
                        .show();
            }

        });

        setWidgetReference();
        bindEventHandlers();
        if (mApp.hasAccessToken()){
            getAllMediaImages2();
            getAllMediaImages3();
            mApp = new InstagramApp(this, ApplicationData.CLIENT_ID,
                    ApplicationData.CLIENT_SECRET, ApplicationData.CALLBACK_URL);

            mApp.fetchUserName(handler);
        }


    }




    private void bindEventHandlers() {
        btnConnect.setOnClickListener(this);

    }

    private void setWidgetReference() {
        llAfterLoginView = (LinearLayout) findViewById(R.id.llAfterLoginView);
        btnConnect = (Button) findViewById(R.id.btnConnect);

    }


    @Override
    public void onClick(View v) {
        if (v == btnConnect) {
            connectOrDisconnectUser();

        }
    }




    private void connectOrDisconnectUser() {
       // mApp = new InstagramApp(this, ApplicationData.CLIENT_ID,
         //       ApplicationData.CLIENT_SECRET, ApplicationData.CALLBACK_URL);

            mApp.authorize();

            //startActivity(new Intent(MainActivityOld.this, MainActivity.class));



    }

    private void displayInfoDialogView() {
        getAllMediaImages2();
        getAllMediaImages3();
        startActivity(new Intent(MainActivityOld.this, MainActivity.class));

    }
    private void getAllMediaImages3() {
        usersInfo3.clear();
        int progress = 0;

        //pd = ProgressDialog.show(getContext(), "", "Loading...");
        //pd.setCancelable(false);
        //ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar4);
        //progressBar.setSecondaryProgress(50);

        new Thread(new Runnable() {

            @Override
            public void run() {
                int what = WHAT_FINALIZE;
                try {
                    // URL url = new URL(mTokenUrl + "&code=" + code);
                    JSONParser jsonParser = new JSONParser();
                    final JSONObject jsonObject = jsonParser.getJSONFromUrlByGet(url1);


                    JSONArray data = jsonObject.getJSONArray(TAG_DATA);

                    for (int data_i = 0; data_i < data.length(); data_i++) {

                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_id = data_obj.getString(TAG_ID);

                        hashMap.put(TAG_PROFILE_PICTURE,
                                data_obj.getString(TAG_PROFILE_PICTURE));

                        // String str_username =
                        // data_obj.getString(TAG_USERNAME);
                        //
                        // String str_bio = data_obj.getString(TAG_BIO);
                        //
                        // String str_website = data_obj.getString(TAG_WEBSITE);
                        //USERNAME

                        hashMap.put(TAG_USERNAME,
                                data_obj.getString(TAG_USERNAME));
                        Log.d("MyAppUSERNAME",data_obj.getString(TAG_USERNAME));
                        if (data_obj.getString(TAG_USERNAME).equals("test")){

                        }else{



                            usersInfo3.add(hashMap);

                            //Log.d("MyAppusersinfo3", String.valueOf(usersInfo3));

                        }




                    }
                    System.out.println("jsonObject::" + jsonObject);

                } catch (Exception exception) {
                    exception.printStackTrace();
                    what = WHAT_ERROR;
                }
                // pd.dismiss();
                //mListener.onSuccess();
                //handler.sendEmptyMessage(what);
            }
        }).start();

    }
    private void getAllMediaImages2() {
        Log.d("MyApp","Iamhere12");
        usersInfo2.clear();
        //ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar4);
        //progressBar.setSecondaryProgress(50);

        //pd = ProgressDialog.show(getContext(), "", "Loading...");
        //pd.setCancelable(false);
        new Thread(new Runnable() {

            @Override
            public void run() {
                int what = WHAT_FINALIZE;
                try {
                    Log.d("MyApp","Iamhere2");
                    Log.d("MyAppUrl", url);
                    // URL url = new URL(mTokenUrl + "&code=" + code);
                    JSONParser jsonParser = new JSONParser();
                    final JSONObject jsonObject = jsonParser.getJSONFromUrlByGet(url);
                    Log.d("MyAppJSON", String.valueOf(jsonObject));
                    Log.d("MyAppHASH", String.valueOf(userInfoHashmap));


                    JSONArray data = jsonObject.getJSONArray(TAG_DATA);
                    Log.d("MyAppData", String.valueOf(data));
                    Log.d("MyAppTEST","test");
                    for (int data_i = 0; data_i < data.length(); data_i++) {

                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_id = data_obj.getString(TAG_ID);
                        Log.d("MyAppTEST1",
                                "test1");

                        hashMap.put(TAG_PROFILE_PICTURE,
                                data_obj.getString(TAG_PROFILE_PICTURE));

                        // String str_username =
                        // data_obj.getString(TAG_USERNAME);
                        //
                        // String str_bio = data_obj.getString(TAG_BIO);
                        //
                        // String str_website = data_obj.getString(TAG_WEBSITE);
                        //USERNAME
                        hashMap.put(TAG_USERNAME,
                                data_obj.getString(TAG_USERNAME));
                        Log.d("MyAppUSERNAME",data_obj.getString(TAG_USERNAME));
                        if (data_obj.getString(TAG_USERNAME).equals("test")){


                        }else{



                            usersInfo2.add(hashMap);

                            //Log.d("MyAppusersinfo2 " , String.valueOf(usersInfo2));

                        }




                    }
                    System.out.println("jsonObject::" + jsonObject);

                } catch (Exception exception) {
                    exception.printStackTrace();
                    what = WHAT_ERROR;
                }
                // pd.dismiss();
                //mListener.onSuccess();
                //handler.sendEmptyMessage(what);
            }
        }).start();
    }
    public static ArrayList<HashMap<String, String>> usersInfo2 = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> usersInfo3 =  new ArrayList<HashMap<String, String>>();


    //public String Test = userInfoHashmap.get(InstagramApp.TAG_USERNAME);
}
