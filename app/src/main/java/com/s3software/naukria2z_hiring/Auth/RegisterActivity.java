package com.s3software.naukria2z_hiring.Auth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.s3software.naukria2z_hiring.ErrorLogs;
import com.s3software.naukria2z_hiring.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {

    TextView output;
    Button checkEmail, previous, Register;
    EditText ed_checkemail, name, lname, phoneno, email, password,specify1;
    FrameLayout f1, f2;
   Spinner indus;
    private AlertDialog progressDialog;
    ErrorLogs errorLogs;
    ArrayList<String> arrayList_country,arrayList_conID,arrayList_stateID;
    String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private int indusflag=0;
    private String Indus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Register = findViewById(R.id.Register);
        previous = findViewById(R.id.previous);
        output = findViewById(R.id.result);
        ed_checkemail = findViewById(R.id.emailcheck);
        name = findViewById(R.id.name);
        lname = findViewById(R.id.lname);
        phoneno = findViewById(R.id.Mobileno);
        email = findViewById(R.id.Personalemail);
        password = findViewById(R.id.pass);
       // gender = findViewById(R.id.gender);
       // country = findViewById(R.id.country);
       // state = findViewById(R.id.state);
        //city = findViewById(R.id.city);
       indus=findViewById(R.id.industry);
        f1 = findViewById(R.id.frame1);
        f2 = findViewById(R.id.frame2);
        specify1=findViewById(R.id.otherindustry);
        checkEmail = findViewById(R.id.submitt);
       // arrayList_country = new ArrayList<>();
     //   arrayList_conID=new ArrayList<>();
       // arrayList_stateID=new ArrayList<>();
        errorLogs=new ErrorLogs(getApplicationContext());
        progressDialog = new SpotsDialog(this, R.style.Custom);
      //  countryAPI();
        GetIndustry();
        phoneno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (phoneno.getText().toString().matches("^[6-9][0-9]{9}$")) {

                } else {
                    phoneno.setError("InValid");
                }
            }
        });
        checkEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getemail = ed_checkemail.getText().toString().trim();
                if (!(getemail.matches(emailPattern))) {
                    ed_checkemail.setError("Please write valid email");
                } else {
                    progressDialog.show();
                    checkEmailAPI(getemail);
                }
            }


        });

    }

    private void checkEmailAPI(String getemail) {

        String URl = "http://api.mymakeover.club/api/MepJobs/RecruiterRegistrationCheck_Email?email=" + getemail;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();
                String resstring = jsonParser.parse(response).getAsString();
                Log.e("loginres", "onResponse: " + resstring);
                try {
                    JSONObject jsonObject = new JSONObject(resstring);
                    String status = jsonObject.getString("RegistrationStatus");
                    if (status.equals("true")) {

                        previous.setVisibility(View.VISIBLE);
                        checkEmail.setVisibility(View.GONE);
                        output.setText("You have already registered ,Please login !!!");
                        progressDialog.dismiss();
                        previous.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(i);

                            }
                        });

                    } else {
                        progressDialog.dismiss();
                        f2.setVisibility(View.VISIBLE);
                        f1.setVisibility(View.INVISIBLE);
                        registerAPI();
                    }

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });
        requestQueue.add(stringRequest);
    }
    private void registerAPI() {

        email.setText(ed_checkemail.getText().toString());
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty()){
                    name.setError("Invalid");
                }

                else if(email.getText().toString().isEmpty()){
                    email.setError("Invalid");
                }
                else if(password.getText().toString().isEmpty()){
                    password.setError("Invalid");
                }
                else if(phoneno.getText().toString().isEmpty()){
                    password.setError("Invalid");
                }
                else {
                    String user_name = name.getText().toString();
                    String user_lname = lname.getText().toString();
                    String user_phoneno = phoneno.getText().toString();
                    String user_email = email.getText().toString();
                    String user_password = password.getText().toString();
                    if( indusflag==1){
                        Indus=specify1.getText().toString();
                    }else{
                        Indus = indus.getSelectedItem().toString();
                    }

                    progressDialog.show();

                    callapi(user_name, user_lname, user_phoneno, user_email, user_password, "", "", "", Indus, "");
                }
            }
        });

    }
    private void callapi(String user_name, String user_lname, String user_phoneno, String user_email, String user_password, String user_gender, String user_country, String user_city, String user_indus, String user_state) {
        String url = "http://api.mymakeover.club/api/MepJobs/Registration?firstname="+user_name+"&Lastname="+user_lname+"&phoneno="+user_phoneno+"&gender="+user_gender+"&email="+user_email+"&password="+user_password+"&industry="+user_indus+"&country="+user_country+"&city="+user_city+"&state="+user_state+"&Image=&userid=1003&job_seeker=0&recruiter=1";

        Log.e("url", "callapi: " + url);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();

                String resstring = jsonParser.parse(response).getAsString();
                Log.e("response", "onResponse: " + resstring);
                try {
                    JSONObject jsonObject = new JSONObject(resstring);
                    String data = jsonObject.getString("RegistrationStatus");


                    if (data.equals("1")) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                        JSONObject userdata = jsonObject.getJSONObject("RegistrationData");

                        SharedPreferences SharedPreferences = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = SharedPreferences.edit();
                        editor.putString("EmployeeID", userdata.getString("EmployeeID"));
                        editor.putString("First_Name", userdata.getString("First_Name"));
                        editor.putString("Last_Name", userdata.getString("Last_Name"));
                        editor.putString("User_Email", userdata.getString("User_Email"));
                        editor.putString("User_Pass", userdata.getString("User_Pass"));
                        editor.putString("Industry",  userdata.getString("Industry"));
                        editor.putString("Gender", userdata.getString("Gender"));
                        editor.putString("Phone_No", userdata.getString("Phone_No"));
                        editor.putString("State", userdata.getString("State"));
                        editor.putString("City", userdata.getString("City"));
                        editor.putString("Country",userdata.getString("Country"));
                        editor.apply();

                        Intent intent = new Intent(RegisterActivity.this, RecruiterDetail.class);
                        startActivity(intent);

                    } else if (data.equals("Email already exist")) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Email Already Exist", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Something went wrong,try again", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorLogs.AppErrorLog("Register",error);
                progressDialog.dismiss();
            }
        });
        requestQueue.add(stringRequest);

    }


    private void GetIndustry(){
        final ArrayList<String> arrayListIndustry=new  ArrayList<>();
        String URL="http://api.mymakeover.club/api/MepJobs/GetIndustryData?i=1";
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JsonParser jsonParser = new JsonParser();

                String resstring = jsonParser.parse(response).getAsString();
                Log.e("response", "onResponse: " + resstring);
                try {
                    JSONObject jsonObject = new JSONObject(resstring);
                    JSONArray jsonArray = jsonObject.getJSONArray("Industry");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        String industryname = jsonObject1.getString("Industry_Name");
                        arrayListIndustry.add(industryname);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, arrayListIndustry);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                indus.setAdapter(arrayAdapter);
                indus.setSelection(0);

                indus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String indust= parent.getItemAtPosition(position).toString();
                        if (indust.equals("Other")) {
                            indusflag=1;
                            specify1.setVisibility(View.VISIBLE);
                            Indus = specify1.getText().toString();
                        } else {
                            indusflag=0;
                            specify1.setVisibility(View.GONE);
                            Indus = indus.getSelectedItem().toString();
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }); stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );

        requestQueue.add(stringRequest);

    }
}