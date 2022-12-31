package com.s3software.naukria2z_hiring.Auth;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
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
import com.s3software.naukria2z_hiring.Activity.MainActivity;
import com.s3software.naukria2z_hiring.Activity.VerificationWait;
import com.s3software.naukria2z_hiring.ErrorLogs;
import com.s3software.naukria2z_hiring.R;
import com.s3software.naukria2z_hiring.Util.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecruiterDetail extends AppCompatActivity {
    EditText fname, lname, personalemail, Mobileno, Designation, Comapanyno, comemail, Companyaddress, Postalcode, Companywebsite, companyname;
    Button next, submit, previous1;
    FrameLayout f1, f2;
    Spinner country, state, city,gender;
    ProgressDialog progressDialog;
    ArrayList<String> arrayList_country, arrayList_conID, arrayList_stateID;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ErrorLogs errorLogs;
    private int indusflag=0;
    private String Indus;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_detail);
        companyname = findViewById(R.id.Companyname);
        previous1 = findViewById(R.id.previous1);
        fname = findViewById(R.id.name);
        lname = findViewById(R.id.lname);
        next = findViewById(R.id.next);
        submit = findViewById(R.id.submit);
        personalemail = findViewById(R.id.Personalemail);
        Mobileno = findViewById(R.id.Mobileno);
        Designation = findViewById(R.id.Designation);
        Comapanyno = findViewById(R.id.Comapanyno);
        comemail = findViewById(R.id.comemail);
        Companyaddress = findViewById(R.id.Companyaddress);

        Postalcode = findViewById(R.id.Postalcode);
        Companywebsite = findViewById(R.id.Companywebsite);

        gender = findViewById(R.id.gender);
        country = findViewById(R.id.country);
        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
        // eIndus=findViewById(R.id.otherindustry);
        //  industry=findViewById(R.id.industry);

        f1 = findViewById(R.id.f1);
        f2 = findViewById(R.id.f2);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        errorLogs=new ErrorLogs(getApplicationContext());
        arrayList_country = new ArrayList<>();
        arrayList_conID = new ArrayList<>();
        arrayList_stateID = new ArrayList<>();

        SharedPreferences userdata = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        final String EmployeeID = userdata.getString("EmployeeID", "");
        final String First_Name = userdata.getString("First_Name", "");
        final String Last_Name = userdata.getString("Last_Name", "");
        final String User_Email = userdata.getString("User_Email", "");
        final String User_Pass = userdata.getString("User_Pass", "");
        final String Phone_No = userdata.getString("Phone_No", "");
        countryAPI();

        fname.setText(First_Name);
        lname.setText(Last_Name);
        personalemail.setText(User_Email);
        Mobileno.setText(Phone_No);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fname.getText().toString().isEmpty()) {
                    fname.setError("Please fill it");
                } else if (lname.getText().toString().isEmpty()) {
                    lname.setError("Please fill it");
                } else if (personalemail.getText().toString().isEmpty()) {
                    personalemail.setError("Please fill it");
                } else if (Mobileno.getText().toString().isEmpty()) {
                    Mobileno.setError("Please fill it");
                } else if (Designation.getText().toString().isEmpty()) {
                    Designation.setError("Please fill it");
                } else if (Postalcode.getText().toString().isEmpty()) {
                    Postalcode.setError("Please fill it");
                } else {
                    f1.setVisibility(View.INVISIBLE);
                    f2.setVisibility(View.VISIBLE);
                }


            }
        });

        Mobileno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Mobileno.getText().toString().matches("^[6-9][0-9]{9}$")) {

                } else {
                    Mobileno.setError("InValid");
                }
            }
        });


        previous1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f2.setVisibility(View.INVISIBLE);
                f1.setVisibility(View.VISIBLE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(fname.getText().toString().isEmpty() || lname.getText().toString().isEmpty() || personalemail.getText().toString().isEmpty() ||
                        Mobileno.getText().toString().isEmpty() || Designation.getText().toString().isEmpty() || companyname.getText().toString().isEmpty() ||
                        Companyaddress.getText().toString().isEmpty())) {
                    String f_name = fname.getText().toString();
                    String l_name = lname.getText().toString();
                    String pemail = personalemail.getText().toString();
                    String mob = Mobileno.getText().toString();
                    String designation = Designation.getText().toString();
                    String comname = companyname.getText().toString();
                    String commobile = Comapanyno.getText().toString();
                    String comEmail = comemail.getText().toString();
                    String comaddress = Companyaddress.getText().toString();
                    String comweb = Companywebsite.getText().toString();
                    String Country = null, State = null, City = null;
                    String user_gender = gender.getSelectedItem().toString();

                    String user_country=null,user_city=null,user_state=null;
                    try{
                        user_country = country.getSelectedItem().toString();
                        user_city = city.getSelectedItem().toString();
                        user_state = state.getSelectedItem().toString();
                    }
                    catch (Exception e){
                        Toast.makeText(RecruiterDetail.this, "Country State City loading...", Toast.LENGTH_SHORT).show();
                    }
                    String postalcode = Postalcode.getText().toString();

                    if (Postalcode.getText().toString().isEmpty() || Postalcode.length() > 6) {
                        Postalcode.setError("Invalid");
                    } else {
                        callRecruiterAPI(EmployeeID, f_name, l_name, pemail, mob, designation, comname, commobile, comEmail, comaddress, comweb, User_Pass, user_country, user_state, user_city, postalcode, user_gender);

                    }
                }
            }
        });

    }

    private void callRecruiterAPI(String employeeID, String f_name, String l_name, final String pemail, String mob, String designation, String comname, String commobile, String comEmail, String comaddress, String comweb, String user_Pass, String country, String state, String city, String postalcode, String gender) {

        progressDialog.show();
        String name = f_name + l_name;
        String url = "http://api.mymakeover.club/api/MepJobs/AddRecruiter?employeeID=" + employeeID + "&fname=" + name + "&email=" + pemail + "&password=" + user_Pass + "&mob=" + mob + "&designation=" + designation + "&cname=" + comname + "&cno=" + commobile + "&cemail=" + comEmail + "&caddress=" + comaddress + "&country=" + country + "&state=" + state + "&city=" + city + "&postalcode=" + postalcode + "&cwebsite=" + comweb + "&gender=" + gender;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();

                String resstring = jsonParser.parse(response).getAsString();
                Log.e("RecruiterDetail", "onResponse: " + resstring);
                try {
                    JSONObject jsonObject = new JSONObject(resstring);
                    String status = jsonObject.getString("RegistrationStatus");
                    if (status.equals("1")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("Data");
                        String name = jsonObject1.getString("Name");
                        String Personal_Email = jsonObject1.getString("Personal_Email");
                        String Mobile_No = jsonObject1.getString("Mobile_No");

                        SharedPreferences SharedPreferences = getApplicationContext().getSharedPreferences("ProfileDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = SharedPreferences.edit();

                        editor.putString("Name", name);
                        editor.putString("User_Email", Personal_Email);
                        editor.putString("Phone_No", Mobile_No);

                        editor.apply();

                        progressDialog.dismiss();
                        //    MailAPI(name, pemail);
                        //  SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                        Intent i = new Intent(RecruiterDetail.this, VerificationWait.class);
                        startActivity(i);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    progressDialog.dismiss();
                    Toast.makeText(RecruiterDetail.this, "Network Problem", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorLogs.AppErrorLog("LoginRecruiter",error);
                Log.e("RecruiterDetail", "onErrorResponse: " + error);
                progressDialog.dismiss();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void MailAPI(String name, String pemail) {
        String URL = "http://api.mymakeover.club/api/MepJobs/MailAPI?fname=" + name + "&email=" + pemail + "&PStatus=1";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();

                String resstring = jsonParser.parse(response).getAsString();
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

    }

    private void countryAPI() {

        String URL = "http://api.mymakeover.club/api/AppTrack/CountryList";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();

                String resstring = jsonParser.parse(response).getAsString();
                Log.e("response", "onResponse: " + resstring);
                try {
                    JSONObject jsonObject = new JSONObject(resstring);
                    JSONArray jsonArray = jsonObject.getJSONArray("Name");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        String countryname = jsonObject1.getString("Name");
                        String countryID = jsonObject1.getString("ID");
                        arrayList_conID.add(countryID);
                        arrayList_country.add(countryname);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RecruiterDetail.this, android.R.layout.simple_spinner_item, arrayList_country);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                country.setAdapter(arrayAdapter);
                country.setSelection(100);
                country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String country_ = arrayList_conID.get(position).toString();
                        StateAPI(country_);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "onErrorResponse: " + error);
                Toast.makeText(RecruiterDetail.this, "Poor Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        requestQueue.add(stringRequest);

    }

    private void StateAPI(final String country_) {

        String URL = "http://api.mymakeover.club/api/AppTrack/StateList?CountryId=" + country_;
        final ArrayList<String> arrayList_state = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();

                String resstring = jsonParser.parse(response).getAsString();
                Log.e("response", "onResponse: " + resstring);
                try {
                    JSONObject jsonObject = new JSONObject(resstring);
                    JSONArray jsonArray = jsonObject.getJSONArray("State");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        String statename = jsonObject1.getString("Name");
                        String stateID = jsonObject1.getString("ID");
                        arrayList_stateID.add(stateID);
                        arrayList_state.add(statename);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RecruiterDetail.this, android.R.layout.simple_spinner_item, arrayList_state);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                state.setAdapter(arrayAdapter);
                state.setSelection(9);
                progressDialog.dismiss();
                state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String StateID = arrayList_stateID.get(position).toString();
                        CityAPI(StateID);
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
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        requestQueue.add(stringRequest);

    }

    private void CityAPI(String stateID) {

        String URL = "http://api.mymakeover.club/api/AppTrack/CityList?StateId=" + stateID;
        final ArrayList<String> arrayList_city = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();

                String resstring = jsonParser.parse(response).getAsString();
                Log.e("response", "onResponse: " + resstring);
                try {
                    JSONObject jsonObject = new JSONObject(resstring);
                    JSONArray jsonArray = jsonObject.getJSONArray("City");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        String statename = jsonObject1.getString("Name");

                        arrayList_city.add(statename);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RecruiterDetail.this, android.R.layout.simple_spinner_item, arrayList_city);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                city.setAdapter(arrayAdapter);
                city.setSelection(1);

                city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        requestQueue.add(stringRequest);


    }


}