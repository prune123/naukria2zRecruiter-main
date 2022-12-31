package com.s3software.naukria2z_hiring.Auth;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonParser;
import com.s3software.naukria2z_hiring.Activity.MainActivity;
import com.s3software.naukria2z_hiring.Activity.VerificationWait;
import com.s3software.naukria2z_hiring.ErrorLogs;
import com.s3software.naukria2z_hiring.R;
import com.s3software.naukria2z_hiring.Util.SaveSharedPreference;


import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText user_pass;
    Button login;
    EditText user_email;
    TextView textView;
    private AlertDialog progressDialog;
    TextView forgotpassword;
    EditText cemail;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    Dialog dialog;
    ErrorLogs errorLogs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        user_email = findViewById(R.id.useremail);
        user_pass = findViewById(R.id.userpass);
        textView = findViewById(R.id.newuser);
        forgotpassword=findViewById(R.id.forgotpassword);
        errorLogs=new ErrorLogs(getApplicationContext());
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        if (SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        loginPrefsEditor = loginPreferences.edit();
        progressDialog = new SpotsDialog(LoginActivity.this, R.style.Custom);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_email.getText().toString().equals(""))
                {
                    user_email.setError("Email required!!!");
                }
               else if(user_pass.getText().toString().equals(""))
                {
                    user_pass.setError("Password required!!!");
                }
               else if(!(user_email.getText().toString().isEmpty() && user_pass.getText().toString().isEmpty())) {
                    progressDialog.show();
                    String u_email=user_email.getText().toString();
                    String u_pass=user_pass.getText().toString();
                    CallLoginAPI(u_email,u_pass);

                }else{
                    String title="";
                    String message="Invalid Email or Password";
                    showMessage(title,message);
                }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this , RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(LoginActivity.this);
                dialog.setContentView(R.layout.forgotcredential);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                Button csubmit;
                csubmit=dialog.findViewById(R.id.csubmit);
                cemail=dialog.findViewById(R.id.cemail);
                csubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(cemail.getText().toString().isEmpty()){
                            cemail.setError("invalid");
                        }
                        else{
                            String email=cemail.getText().toString();
                            progressDialog.show();
                            progressDialog.setMessage("Please wait while we check your data..");
                            callforgetpasswordapi(email);

                        }
                    }
                });
                dialog.show();
            }
        });
    }

    public void callforgetpasswordapi(final String email) {
        String url = "http://api.mymakeover.club/api/MepJobs/ForgotPasswordOTP?UserEmailId="+email;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();

                String resstring = jsonParser.parse(response).getAsString();
                Log.e("response", "onResponse: " + resstring);
                try {
                    JSONObject jsonObject = new JSONObject(resstring);
                    Log.e("response", "onResponse: "+response);
                    String data = jsonObject.getString("Status");
                    String code=jsonObject.getString("Data");

                    if (data.equals("true")) {
                        progressDialog.dismiss();
                        dialog.cancel();
                        Toast.makeText(LoginActivity.this, "OTP  has been sent to your email address", Toast.LENGTH_SHORT).show();
                        openDialog(code,email);
                    } else {
                        progressDialog.dismiss();
                        dialog.cancel();
                        Toast.makeText(LoginActivity.this, "This Email Address is not registered", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    dialog.cancel();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }

    private void openDialog(final String code, final String Useremail) {
        TextView heading;
        dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.forgotcredential);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Button csubmit;
        csubmit=dialog.findViewById(R.id.csubmit);
        cemail=dialog.findViewById(R.id.cemail);
        heading=dialog.findViewById(R.id.text);
        heading.setText("Enter OTP");
        csubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cemail.getText().toString().isEmpty()){
                    cemail.setError("invalid");
                }
                else{
                    String email=cemail.getText().toString();
                    // callforgetpasswordapi(email);
                    if(email.equals(code)){
                        dialog.cancel();
                        openPasswordresetDialog(Useremail);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        dialog.show();
    }

    private void openPasswordresetDialog(final String email) {
        final TextInputEditText password,cpassword;
        dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.passwordreset);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Button csubmit;
        csubmit=dialog.findViewById(R.id.csubmit);
        password=dialog.findViewById(R.id.password);
        cpassword=dialog.findViewById(R.id.cpassword);
        csubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().isEmpty()){
                    password.setError("please write password");
                }
                else if(cpassword.getText().toString().isEmpty()){
                    cpassword.setError("please write password");
                }
                else if(!(password.getText().toString().equals(cpassword.getText().toString()))){
                    Toast.makeText(LoginActivity.this, "Password Did not match", Toast.LENGTH_SHORT).show();
                }
                else{
                    String Password=password.getText().toString();
                    dialog.cancel();
                    callPasswordResetAPI(email,Password);
                }
            }
        });
        dialog.show();
    }

    private void callPasswordResetAPI(String email, String password) {
        progressDialog.show();
        String URl="http://api.mymakeover.club/api/MepJobs/NewPassword?newpassword="+password+"&Email="+email;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();

                String resstring = jsonParser.parse(response).getAsString();
                Log.e("response", "onResponse: " + resstring);
                try {
                    JSONObject jsonObject = new JSONObject(resstring);
                    String data = jsonObject.getString("response");
                    Log.e("response", "onResponse: "+data);
                    progressDialog.dismiss();
                    dialog.cancel();
                    Toast.makeText(LoginActivity.this, data, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    dialog.cancel();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Server not responding", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }


    private void CallLoginAPI(String u_email, String u_pass) {
        String URl="http://api.mymakeover.club/api/MepJobs/Recruiter_Login?useremail="+u_email+"&password="+u_pass;
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();
                String resstring = jsonParser.parse(response).getAsString();
                Log.e("loginres", "onResponse: "+resstring );
                try {
                    JSONObject jsonObject=new JSONObject(resstring);
                    String loginStatus=jsonObject.getString("LoginStatus");
                    String verify=jsonObject.getString("Verify");
                    if(loginStatus.equals("Exist")&& verify.equals("1")){
                        progressDialog.dismiss();
                        JSONObject jsonObject1=jsonObject.getJSONObject("OPdata");
                        String EmployeeID=jsonObject1.getString("EmployeeId");
                        String First_Name=jsonObject1.getString("Name");
                        String User_Email=jsonObject1.getString("Personal_Email");
                        SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                        storeInSharesprefrence(EmployeeID,First_Name,"",User_Email,"","","","","","","");

                        Intent i=new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                    else if(loginStatus.equals("Exist")&& verify.equals("0")){
                        progressDialog.dismiss();
                        JSONObject jsonObject1=jsonObject.getJSONObject("OPdata");
                        String EmployeeID=jsonObject1.getString("EmployeeId");
                        String First_Name=jsonObject1.getString("Name");
                        String User_Email=jsonObject1.getString("Personal_Email");
                        //SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                        storeInSharesprefrence(EmployeeID,First_Name,"",User_Email,"","","","","","","");

                        Intent i=new Intent(LoginActivity.this, VerificationWait.class);
                        startActivity(i);
                    }
                   else if(loginStatus.equals("true")){

                        JSONObject jsonObject1=jsonObject.getJSONObject("Opdata1");
                        String EmployeeID=jsonObject1.getString("EmployeeID");
                        String First_Name=jsonObject1.getString("First_Name");
                        String Last_Name=jsonObject1.getString("Last_Name");
                        String User_Email=jsonObject1.getString("User_Email");
                        String User_Pass=jsonObject1.getString("User_Pass");
                        String Industry=jsonObject1.getString("Industry");
                        String Phone_No=jsonObject1.getString("Phone_No");
                        String gender=jsonObject1.getString("Gender");
                        String State=jsonObject1.getString("State");
                        String City=jsonObject1.getString("City");
                        String Country=jsonObject1.getString("Country");

                       storeInSharesprefrence(EmployeeID,First_Name,Last_Name,User_Email,User_Pass,Industry,Phone_No,gender,State,City,Country);
                        progressDialog.dismiss();
                        Intent i=new Intent(LoginActivity.this, RecruiterDetail.class);
                        startActivity(i);
                       // callTableCheckAPI(User_Email);

                    }
                    else if(loginStatus.equals("false")){
                        progressDialog.dismiss();
                        String title="";
                        String message="Invalid Email or Password";
                        showMessage(title,message);
                    }
                    else{
                        progressDialog.dismiss();
                        String title="Your are not registered";
                        String message="Please register yourself";
                        showMessage(title,message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    String message="Please try again later";
                    String title="Sorry,something went wrong";
                    showMessage(title,message);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
             //   errorLogs.AppErrorLog("LoginRecruiter",error);
                String message="Please try again later";
                String title="Sorry,something went wrong";
                showMessage(title,message);
            }
        });
        requestQueue.add(stringRequest);
    }

    private void storeInSharesprefrence(String EmployeeID,String First_Name,String Last_Name,String User_Email
            ,String User_Pass,String Industry,String Phone_No,String gender
            ,String Country,String State,String City) {

        SharedPreferences SharedPreferences = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SharedPreferences.edit();
        editor.putString("EmployeeID", EmployeeID);
        editor.putString("First_Name", First_Name);
        editor.putString("Last_Name", Last_Name);
        editor.putString("User_Email", User_Email);
        editor.putString("User_Pass", User_Pass);
        editor.putString("Industry", Industry);
        editor.putString("Phone_No", Phone_No);
        editor.putString("Gender",gender);
        editor.putString("Country", Country);
        editor.putString("State", State);
        editor.putString("City", City);
        editor.apply();
    }

    private void callTableCheckAPI(String user_email) {
        String url="http://api.mymakeover.club/api/MepJobs/checkRecruiterInTable?u_email="+user_email;
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();
                String resstring = jsonParser.parse(response).getAsString();
                Log.e("tableres", "onResponse: "+resstring );
                try {
                    JSONObject jsonObject=new JSONObject(resstring);
                    String status=jsonObject.getString("Status");
                    if(status.equals("false")){
                        progressDialog.dismiss();
                        Intent i=new Intent(LoginActivity.this, RecruiterDetail.class);
                        startActivity(i);
                    }
                    else{

                        progressDialog.dismiss();
                        Intent i=new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorLogs.AppErrorLog("LoginRecruiter",error);
                progressDialog.dismiss();
            }
        });
        requestQueue.add(stringRequest);
    }
    private void showMessage(String title, String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();}

}