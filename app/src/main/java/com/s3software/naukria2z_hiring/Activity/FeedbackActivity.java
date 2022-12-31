package com.s3software.naukria2z_hiring.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonParser;
import com.s3software.naukria2z_hiring.ErrorLogs;
import com.s3software.naukria2z_hiring.R;

public class FeedbackActivity extends AppCompatActivity {

    EditText data;
    Toolbar toolbar;
    RadioButton bug, enhance;
    Button submit;
    ProgressDialog progressDialog;
    ErrorLogs errorLogs;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        data = findViewById(R.id.data);
        submit = findViewById(R.id.submit);
        bug = findViewById(R.id.bug);
        enhance = findViewById(R.id.enhancement);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        bug.setChecked(true);
        progressDialog = new ProgressDialog(this);
        toolbar.setTitle("Feedback");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setContentInsetStartWithNavigation(0);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        errorLogs=new ErrorLogs(getApplicationContext());
        SharedPreferences userdata = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        final String First_Name = userdata.getString("First_Name", "");
        final String Last_Name = userdata.getString("Last_Name", "");
        final String User_Email = userdata.getString("User_Email", "");
        final String Phone_No = userdata.getString("Phone_No", "");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bug.isChecked()) {
                    String subject = "BUG";
                    String description = data.getText().toString();
                    progressDialog.show();
                    progressDialog.setMessage("Please wait..");
                    sendEmail(subject, description, First_Name, Last_Name, User_Email, Phone_No);
                } else {
                    progressDialog.show();
                    progressDialog.setMessage("Please wait..");
                    String subject = "Enhancement";
                    String description = data.getText().toString();
                    sendEmail(subject, description, First_Name, Last_Name, User_Email, Phone_No);
                }
            }
        });

    }

    protected void sendEmail(String subject1, final String description1, String first_Name, String last_Name, String User_Email, String Phone_No) {
        String url = "http://api.mymakeover.club/api/MepJobs/FeedbackEmail?Username=" + first_Name + last_Name + "&FromMail=" + User_Email + "&phone=" + Phone_No + "&subject=" + subject1 + "&body=" + description1;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();
                progressDialog.dismiss();
                String resstring = jsonParser.parse(response).getAsString();
                Log.e("response", "onResponse: " + resstring);
                Toast.makeText(FeedbackActivity.this, "Thanks for your feedback", Toast.LENGTH_SHORT).show();
                data.getText().clear();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorLogs.AppErrorLog("sendfeedback",error);
                progressDialog.dismiss();
            }
        });
        requestQueue.add(stringRequest);
    }
}
