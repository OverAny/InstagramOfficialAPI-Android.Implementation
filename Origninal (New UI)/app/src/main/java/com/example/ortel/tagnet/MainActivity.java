package com.example.ortel.tagnet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.telephony.SmsManager;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.util.HashMap;

import android.util.Log;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.ortel.tagnet.InstagramApp.TAG_DATA;
import static com.example.ortel.tagnet.InstagramApp.TAG_ID;
import static com.example.ortel.tagnet.InstagramApp.TAG_PROFILE_PICTURE;
import static com.example.ortel.tagnet.InstagramApp.TAG_USERNAME;
import static com.example.ortel.tagnet.InstagramApp.WHAT_ERROR;
import static com.example.ortel.tagnet.InstagramApp.WHAT_FINALIZE;
import static com.example.ortel.tagnet.InstagramApp.likedFive;
import static com.example.ortel.tagnet.InstagramApp.likedFour;
import static com.example.ortel.tagnet.InstagramApp.likedOne;
import static com.example.ortel.tagnet.InstagramApp.likedThree;
import static com.example.ortel.tagnet.InstagramApp.likedTwo;
import static com.example.ortel.tagnet.MainActivityOld.url;
import static com.example.ortel.tagnet.MainActivityOld.url1;
import static com.example.ortel.tagnet.MainActivityOld.userInfoHashmap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private LayoutInflater inflater;
    private int gotted;
    public static ArrayList<HashMap<String, String>> usersInfoTwo = new ArrayList<HashMap<String, String>>();
    Context context;
    Button myButton;
    View myView;
    boolean isUp;
    View main = null;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    public static final String TwoT = "TwoT";
    private int mCurrRotation = 0; // takes the place of getRotation()
    private int mCurrRotation2 = 0; // takes the place of getRotation()

    private InstagramApp1 mApp1;
    //public static HashMap<String, String> userInfoHashmap = new HashMap<String, String>();
    public static ArrayList<HashMap<String, String>> usersInfo2 = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> usersInfo3 =  new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String, String>> usersInfo = new ArrayList<HashMap<String, String>>();
    public String test = "1";

    private ViewPager mViewPager;
    public static InstagramApp mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = new InstagramApp(this, ApplicationData.CLIENT_ID,
                ApplicationData.CLIENT_SECRET, ApplicationData.CALLBACK_URL);

        //userInfoHashmap = mApp.getUserInfo();
        Log.d("OkHttp123", String.valueOf(userInfoHashmap));
        Log.d("OkHttp123", String.valueOf(mApp));
        setContentView(R.layout.bar);
        main = (View) findViewById(R.id.container) ;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        final Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content), "test", Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null);
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getAllMediaImages2();
        getAllMediaImages3();
        mApp1 = new InstagramApp1(this, ApplicationData.CLIENT_ID,
                ApplicationData.CLIENT_SECRET, ApplicationData.CALLBACK_URL);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        View h1View =  navigationView.getHeaderView(1);

        ImageView nav_user = (ImageView)hView.findViewById(R.id.imageView);
        TextView text = (TextView) hView.findViewById(R.id.textView);
        TextView text1 = (TextView) hView.findViewById(R.id.textView1);
        navigationView.setNavigationItemSelectedListener(this);
        Picasso.with(MainActivity.this)
                .load(userInfoHashmap.get(InstagramApp.TAG_PROFILE_PICTURE))
                .into(nav_user);
        text.setText(userInfoHashmap.get(InstagramApp.TAG_USERNAME));
        text1.setText(userInfoHashmap.get(InstagramApp.TAG_BIO));
        // get menu from navigationView
        Menu menu = navigationView.getMenu();

        // find MenuItem you want to change
        MenuItem nav_fullname = menu.findItem(R.id.nav_fullname);
        MenuItem nav_followers = menu.findItem(R.id.nav_followers);
        MenuItem nav_following = menu.findItem(R.id.nav_follows);
        MenuItem nav_posts = menu.findItem(R.id.nav_posts);

        // set new title to the MenuItem
        String fullname = ("FullName: " + "<b>"+ "<i>"+userInfoHashmap.get(InstagramApp.TAG_FULL_NAME)+"</b>"+ "<i>");
        String followers = ("Followers: " + "<b>"+ "<i>"+userInfoHashmap.get(InstagramApp.TAG_FOLLOWED_BY)+"</b>"+ "<i>");
        String following = ("Following: " + "<b>"+ "<i>"+userInfoHashmap.get(InstagramApp.TAG_FOLLOWS)+"</b>"+ "<i>");
        String posts = ("Posts: " + "<b>"+ "<i>"+userInfoHashmap.get(InstagramApp.TAG_MEDIA)+"</b>"+ "<i>");

        nav_fullname.setTitle(Html.fromHtml(fullname));
        nav_followers.setTitle(Html.fromHtml(followers));
        nav_following.setTitle(Html.fromHtml(following));
        nav_posts.setTitle(Html.fromHtml(posts));

        final ImageView fab1 = (ImageView) findViewById(R.id.fab1);
        myView = findViewById(R.id.my_view1);
        myView.setVisibility(View.INVISIBLE);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Fab", String.valueOf(mCurrRotation));
                mCurrRotation %= 360;
                mCurrRotation2 %= 360;
                float fromRotation = mCurrRotation;
                float fromRotation2 = mCurrRotation2;
                boolean x = true;

                if(mCurrRotation == 0 || mCurrRotation == 360) {
                    float toRotation = mCurrRotation += 90;
                    float toRotation2 = mCurrRotation2 -= 360;
                    final RotateAnimation rotateAnim = new RotateAnimation(
                            fromRotation, toRotation, fab1.getWidth() / 2, fab1.getHeight() / 2);
                    rotateAnim.setDuration(200); // Use 0 ms to rotate instantly
                    rotateAnim.setFillAfter(true); // Must be true or the animation will reset
                    fab1.startAnimation(rotateAnim);
                    View main = (View) findViewById(R.id.container) ;
                    slideUp(myView);


                    test = "2";


                }else{
                    float toRotation = mCurrRotation -= 90;
                    float toRotation2 = mCurrRotation2 += 360;
                    final RotateAnimation rotateAnim = new RotateAnimation(
                            fromRotation, toRotation, fab1.getWidth() / 2, fab1.getHeight() / 2);
                    rotateAnim.setDuration(200); // Use 0 ms to rotate instantly
                    rotateAnim.setFillAfter(true); // Must be true or the animation will reset
                    fab1.startAnimation(rotateAnim);
                    test = "1";
                    View main = (View) findViewById(R.id.container) ;

                    slideDown(myView);

                    x = true;
                 }

            }
        });



    }

    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        View main = (View) findViewById(R.id.container) ;
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        //main.setVisibility(View.GONE);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        View main = (View) findViewById(R.id.container) ;
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        //main.setVisibility(View.VISIBLE);

    }

    private void getAllMediaImages3() {
        Log.d("MyApp","Iamhere1");
        usersInfo3.clear();
        new Thread(new Runnable() {

            @Override
            public void run() {
                int what = WHAT_FINALIZE;
                try {
                    Log.d("MyApp","Iamhere2");
                    Log.d("MyAppUrl", url1);
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

                        hashMap.put(TAG_USERNAME,
                                data_obj.getString(TAG_USERNAME));
                        Log.d("MyApp",data_obj.getString(TAG_USERNAME));
                        if (data_obj.getString(TAG_USERNAME).equals("test")){

                        }else{



                            usersInfo3.add(hashMap);


                        }




                    }

                } catch (Exception exception) {
                    exception.printStackTrace();
                    what = WHAT_ERROR;
                }

            }
        }).start();

    }

    private void getAllMediaImages2() {
        usersInfo2.clear();

        new Thread(new Runnable() {

            @Override
            public void run() {
                int what = WHAT_FINALIZE;
                try {
                    Log.d("MyApp","Iamhere2");
                    Log.d("MyAppUrl", url);
                    // URL url = new URL(mTokenUrl + "&code
                    //=" + code);
                    JSONParser jsonParser = new JSONParser();
                    final JSONObject jsonObject = jsonParser.getJSONFromUrlByGet(url);
                    Log.d("MyAppJSON", String.valueOf(jsonObject));
                    Log.d("MyAppHASH", String.valueOf(userInfoHashmap));


                    JSONArray data = jsonObject.getJSONArray(TAG_DATA);
                    Log.d("MyAppData", String.valueOf(data));




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
                        Log.d("MyApp",data_obj.getString(TAG_USERNAME));
                        if (data_obj.getString(TAG_USERNAME).equals("test")){


                        }else{



                            usersInfo2.add(hashMap);

                            Log.d("MyAppusersinfo2" , String.valueOf(usersInfo2));

                        }




                    }
                    System.out.println("jsonObject::" + jsonObject);

                } catch (Exception exception) {
                    exception.printStackTrace();
                    what = WHAT_ERROR;
                }


            }
        }).start();
    }

@Override
    public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    public void showGenderPopup(View v)
    {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main, popup.getMenu());
        popup.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
               getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this);
            builder.setMessage("Disconnect from Instagram?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    mApp.resetAccessToken();
                                    startActivity(new Intent(MainActivity.this, MainActivityOld.class));
                                }
                            })
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.cancel();
                                }
                            });
            final AlertDialog alert = builder.create();
            alert.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
              return true;
    }

}


