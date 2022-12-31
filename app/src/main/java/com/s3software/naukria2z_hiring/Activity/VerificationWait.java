package com.s3software.naukria2z_hiring.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonParser;
import com.s3software.naukria2z_hiring.Auth.LoginActivity;
import com.s3software.naukria2z_hiring.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class VerificationWait extends AppCompatActivity {
   Button sendmail;
    boolean status;
    TimerTask doAsynchronousTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_wait);
        sendmail=findViewById(R.id.sendmail);
        SharedPreferences userProfile = this.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
       final String PUser_Email = userProfile.getString("User_Email", "");
       String PEmployeeID = userProfile.getString("EmployeeID", "");
       final String name=userProfile.getString("First_Name","");
        sendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMailAPI(PUser_Email,name);
            }
        });
        final Handler handler = new Handler();
        Timer timer = new Timer();
        doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            if(!status) {
                                boolean getstatus = checkActivation(PUser_Email);
                            }
                            else{
                                doAsynchronousTask.cancel();
                                handler.removeCallbacks(this);
                            }
                        } catch (Exception e) {
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 6000);
    }

    private boolean checkActivation(String pUser_email) {

        String URL = "http://api.mymakeover.club/api/MepJobs/CheckActivation?email="+pUser_email+"&table=1";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();

                String resstring = jsonParser.parse(response).getAsString();
                try {
                    JSONObject jsonObject=new JSONObject(resstring);
                    String result=jsonObject.getString("Verify");
                    if(result.equals("true")){
                        Intent i=new Intent(VerificationWait.this, MainActivity.class);
                        startActivity(i);
                        doAsynchronousTask.cancel();
                        finish();

                    }else{
                        status=false;}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("mailresponse", "onResponse: " + resstring);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        requestQueue.add(stringRequest);
        return  status;
    }

    private void sendMailAPI(String PUser_Email, String name) {
        String URL = "http://api.mymakeover.club/api/MepJobs/Verificationmail?email="+PUser_Email+"&name="+name+"&value=1";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();

                String resstring = jsonParser.parse(response).getAsString();
                Log.e("mailresponse", "onResponse: " + resstring);

                try {
                    JSONObject jsonObject=new JSONObject(resstring);
                    String response1=jsonObject.getString("Verify");
                    if(response1.equals("true")){
                        Toast.makeText(VerificationWait.this, "successfully sent", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        requestQueue.add(stringRequest);
    }


}
