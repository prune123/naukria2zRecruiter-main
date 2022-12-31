package com.s3software.naukria2z_hiring.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.onesignal.OneSignal;
import com.s3software.naukria2z_hiring.Adapter.ShowPostAdapter;
import com.s3software.naukria2z_hiring.Auth.LoginActivity;
import com.s3software.naukria2z_hiring.ErrorLogs;
import com.s3software.naukria2z_hiring.Model.PostDatum;
import com.s3software.naukria2z_hiring.Model.ShowJobs;
import com.s3software.naukria2z_hiring.R;
import com.s3software.naukria2z_hiring.Util.BottomNavigationViewHelper;
import com.s3software.naukria2z_hiring.Util.SaveSharedPreference;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FloatingActionButton adspost;
    Toolbar toolbar;
    RecyclerView recyclerView;
    FrameLayout frameshow;
    ProgressDialog progressDialog;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String EmployeeID;
    ErrorLogs errorLogs;
    LinearLayout jobLayout;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences userdata = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        EmployeeID = userdata.getString("EmployeeID", "");
        final String User_Email = userdata.getString("User_Email", "");
        PostShowAPI(EmployeeID, User_Email);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences userdata = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        EmployeeID = userdata.getString("EmployeeID", "");
        final String User_Email = userdata.getString("User_Email", "");
        PostShowAPI(EmployeeID, User_Email);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Posted Jobs");
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        recyclerView = findViewById(R.id.recyclerview);
        mSwipeRefreshLayout = findViewById(R.id.swiperefresh_items);
        frameshow=findViewById(R.id.frameshow);
        adspost = findViewById(R.id.fab);
        jobLayout=findViewById(R.id.joblayout);
        errorLogs=new ErrorLogs(getApplicationContext());
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        adspost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPost.class);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.Support) {
                    Toast.makeText(MainActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.Jobs) {

                    return true;
                } else if (item.getItemId() == R.id.Profile) {
                    Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(i);
                    return true;
                } else if (item.getItemId() == R.id.Notification) {
                    Toast.makeText(MainActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        SharedPreferences userdata = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        EmployeeID = userdata.getString("EmployeeID", "");
        final String User_Email = userdata.getString("User_Email", "");
        PostShowAPI(EmployeeID, User_Email);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                PostShowAPI(EmployeeID, User_Email);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

        Date today = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy hh:mma");
        String dateToStr = format.format(today);
        SharedPreferences logintime=getApplication().getSharedPreferences("logintime",Context.MODE_PRIVATE);
        SharedPreferences.Editor logineditor = logintime.edit();
        logineditor.putString("datetime",dateToStr);
        logineditor.apply();
        callProfileAPI(User_Email);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Logout:
                SaveSharedPreference.setLoggedIn(getApplicationContext(), false);

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                return true;
            case R.id.feedback:
                startActivity(new Intent(getApplicationContext(), FeedbackActivity.class));
                return true;
        }
        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        if (menu instanceof MenuBuilder) {

            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }

        return true;
    }


    public void PostShowAPI(String employeeID, String user_Email) {
        progressDialog.show();
        String url = "http://api.mymakeover.club/api/MepJobs/ShowPost?HREmail=" + user_Email + "&EmployeeID=" + employeeID;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JsonParser jsonParser = new JsonParser();
                String resstring = jsonParser.parse(response).getAsString();
                Log.e("showPosts", "onResponse: " + resstring);
                Gson gson = new Gson();
                ShowJobs showJobs = gson.fromJson(resstring.toString(), ShowJobs.class);
                if (showJobs.getPostStatus().equals(true)) {
                    progressDialog.dismiss();
                    ArrayList<PostDatum> datumPost = new ArrayList<>();
                    for (int i = 0; i < showJobs.getPostData().size(); i++) {
                        datumPost.add(showJobs.getPostData().get(i));
                    }
                    callAdapter(MainActivity.this, datumPost);
                }
                else{
                    frameshow.setVisibility(View.VISIBLE);
                    jobLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
                errorLogs.AppErrorLog("showpostespost",error);

            }
        });
        requestQueue.add(stringRequest);

    }

    private void callAdapter(MainActivity context, ArrayList<PostDatum> datumPost) {
        ShowPostAdapter showPostAdapter = new ShowPostAdapter(context, datumPost);
        recyclerView.setAdapter(showPostAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onBackPressed() {
        SharedPreferences userdata = getSharedPreferences("logintime", Context.MODE_PRIVATE);
        String logintime = userdata.getString("datetime", "");

        Date today = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy hh:mma");
        String logoutdate = format.format(today);
        callUsageAPI(logintime,logoutdate);
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
    private void callUsageAPI(String logintime, String logoutdate) {

        String url="http://api.mymakeover.club/api/MepJobs/NaukriA2ZUsage?EmployeeID="+EmployeeID+"&LoginTime="+logintime+"&LogoutTime="+logoutdate;
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("usageStatus", "onResponse: "+response );

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
    private void callProfileAPI(String pUser_email) {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String url="http://api.mymakeover.club/api/MepJobs/RecruiterProfile?HREmail="+pUser_email;
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", "onResponse: "+response );
                JsonParser jsonParser=new JsonParser();
                String resstring=jsonParser.parse(response).getAsString();
                Log.e("profileresponse", "onResponse: "+resstring );
                try {
                    JSONObject jsonObject1=new JSONObject(resstring);
                    JSONObject jsonObject=jsonObject1.getJSONObject("ProfileData");
                    String Name=jsonObject.getString("Name");
                    String Personal_Email=jsonObject.getString("Personal_Email");
                    String Mobile_No=jsonObject.getString("Mobile_No");
                    String Designation=jsonObject.getString("Designation");
                    String CompanyLandLine_No=jsonObject.getString("CompanyLandLine_No");
                    String Company_Email=jsonObject.getString("Company_Email");
                    String Company_Address=jsonObject.getString("Company_Address");
                    String Company_Country=jsonObject.getString("Company_Country");
                    String Company_State=jsonObject.getString("Company_State");
                    String Company_City=jsonObject.getString("Company_City");
                    String Company_Website=jsonObject.getString("Company_Website");
                    String Company_Name=jsonObject.getString("Company_Name");
                    String Gender=jsonObject.getString("Gender");
                    String Profile_Image=jsonObject.getString("Profile_Image");
                  //  byte[] bytedata = Base64.decode(Profile_Image, Base64.DEFAULT);
                  //  Bitmap bitmap = BitmapFactory.decodeByteArray(bytedata, 0, bytedata.length);

                    SharedPreferences SharedPreferences = getApplicationContext().getSharedPreferences("ProfileDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = SharedPreferences.edit();
                    editor.putString("Name", Name);
                    editor.putString("User_Email", Personal_Email);
                    editor.putString("Phone_No", Mobile_No);
                    editor.putString("Designation", Designation);
                    editor.putString("CompanyLandLine_No", CompanyLandLine_No);
                    editor.putString("Company_Email", Company_Email);
                    editor.putString("Company_Address", Company_Address);
                    editor.putString("Company_Country", Company_Country);
                    editor.putString("Company_State", Company_State);
                    editor.putString("Company_City", Company_City);
                    editor.putString("Company_Website", Company_Website);
                    editor.putString("Company_Name", Company_Name);
                    editor.putString("gender", Gender);

                    editor.apply();
                    progressDialog.dismiss();


                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorLogs.AppErrorLog("profile",error);
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new String(jsonString), cacheEntry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(String response) {
                super.deliverResponse(response);
            }

            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };
        requestQueue.add(stringRequest);
    }

}



