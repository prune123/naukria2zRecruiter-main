package com.s3software.naukria2z_hiring.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonParser;
import com.s3software.naukria2z_hiring.ErrorLogs;
import com.s3software.naukria2z_hiring.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    EditText extraspecialisation,especialisation,ecountry, epost, currency, estate, ecity, efrom, eto, evacancy, eabout, equalification, eexperience, ejobtype, esoftwareknow, ecategory, ecname, ecwebsite;
    Spinner Scountry, Sstate, Scity;
    ArrayList<String> arrayList_country, arrayList_conID, arrayList_stateID;
    Button update, submit, cancel;
    ProgressDialog progressDialog;
    Spinner qual, specialization10;
    LinearLayout oQual,streamlayout,editstreamlayout;
    EditText  otherqualification, specializationOther;
    String  minexp,maxexp;
    private ArrayList<String> arraylistQual,arraylistQualID;
    private String qualification_id,qualification_name,stream_name;
    private String qualificationdata,streamSpecialisationdata,Indus;
    ErrorLogs errorLogs;
    TextView streamSelected;
    String[] streamlistItems;
    boolean[] streamcheckItems;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        epost = findViewById(R.id.post);
        ecountry = findViewById(R.id.country);
        estate = findViewById(R.id.state);
        ecity = findViewById(R.id.city);
        efrom = findViewById(R.id.from);
        eto = findViewById(R.id.to);
        currency = findViewById(R.id.currency);
        evacancy = findViewById(R.id.vacancy);
        eabout = findViewById(R.id.aboutwork);
        equalification = findViewById(R.id.qualification);
        eexperience = findViewById(R.id.experience);
        ejobtype = findViewById(R.id.jobtype);
        esoftwareknow = findViewById(R.id.softwareknowledge);
        ecategory = findViewById(R.id.category);
        ecname = findViewById(R.id.cname);
        ecwebsite = findViewById(R.id.cwebsite);
        update = findViewById(R.id.updatejob);
        especialisation=findViewById(R.id.specialisation);
        arrayList_country = new ArrayList<>();
        arrayList_conID = new ArrayList<>();
        arrayList_stateID = new ArrayList<>();
        extraspecialisation=findViewById(R.id.extraspecialisation);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        errorLogs=new ErrorLogs(getApplicationContext());
        Intent i = getIntent();

        final String HREmail = i.getStringExtra("HREmail");
        final int PostID = i.getIntExtra("PostID", 0);
        final String PostedbyID = i.getStringExtra("PostedbyID");
        String jobtitle = i.getStringExtra("jobtitle");
        String salaryFrom = i.getStringExtra("salaryFrom");
        String salaryTo = i.getStringExtra("salaryTo");
        String vacancy = i.getStringExtra("vacancy");
        String experience = i.getStringExtra("experience");
        String qualification = i.getStringExtra("qualification");
        String Specialisation=i.getStringExtra("Specialisation");
        String Country = i.getStringExtra("Country");
        String State = i.getStringExtra("State");
        String city = i.getStringExtra("city");
        String AboutWork = i.getStringExtra("AboutWork");
        String jobType = i.getStringExtra("jobType");
        String software = i.getStringExtra("software");
        String Category = i.getStringExtra("Category");
        String CompanyName = i.getStringExtra("CompanyName");
        String CompanyWebsite = i.getStringExtra("CompanyWebsite");
        final String location = i.getStringExtra("location");
        String Currency = i.getStringExtra("Currency");
        String extra_spec=i.getStringExtra("extra_spec");
        String FormUrl=i.getStringExtra("FormUrl");

        currency.setText(Currency);
        epost.setText(jobtitle);
        ecountry.setText(Country);
        estate.setText(State);
        ecity.setText(city);
        efrom.setText(salaryFrom);
        eto.setText(salaryTo);
        evacancy.setText(vacancy);
       // eabout.setText(AboutWork);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            eabout.setText(Html.fromHtml(AboutWork, Html.FROM_HTML_MODE_COMPACT));
        } else {
            eabout.setText(Html.fromHtml(AboutWork));
        }
        equalification.setText(qualification);
        especialisation.setText(Specialisation);
        eexperience.setText(experience);
        ejobtype.setText(jobType);
        esoftwareknow.setText(software);
        ecategory.setText(Category);
        ecname.setText(CompanyName);
        ecwebsite.setText(CompanyWebsite);
        extraspecialisation.setText(extra_spec);
        ecountry.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= ecountry.getRight() - ecountry.getTotalPaddingRight()) {
                        // your action for drawable click event
                        openDialog();
                        return true;
                    }
                }
                return false;
            }
        });
        eexperience.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= eexperience.getRight() - eexperience.getTotalPaddingRight()) {
                        // your action for drawable click event
                       
                        openExpDialog();
                        return true;
                    }
                }
                return false;
            }
        });
        ejobtype.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= ejobtype.getRight() - ejobtype.getTotalPaddingRight()) {
                        // your action for drawable click event
                        
                        openJobDialog();
                        return true;
                    }
                }
                return false;
            }
        });
        equalification.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= equalification.getRight() - equalification.getTotalPaddingRight()) {
                        // your action for drawable click event
                       
                        qualificationDialog();
                        return true;
                    }
                }
                return false;
            }
        });
        ecategory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= ecategory.getRight() - ecategory.getTotalPaddingRight()) {
                        // your action for drawable click event
                  
                        openIndusDialog();
                        return true;
                    }
                }
                return false;
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String aPost = epost.getText().toString();
                String aCountry = ecountry.getText().toString();
                String aState = estate.getText().toString();
                String aCity = ecity.getText().toString();
                String aFrom = efrom.getText().toString();
                String aTo = eto.getText().toString();
                String aVacancy = evacancy.getText().toString();
                String aAbout = eabout.getText().toString();
                String aQualification = equalification.getText().toString();
                String aExperience = eexperience.getText().toString();
                String aJobType = ejobtype.getText().toString();
                String aSoftwareKnowledge = esoftwareknow.getText().toString();
                String aCategory = ecategory.getText().toString();
                String aCname = ecname.getText().toString();
                String aCwebsite = ecwebsite.getText().toString();
                String Currency=currency.getText().toString();
                String aspecialisation=especialisation.getText().toString();
                String Extraspecialisation=extraspecialisation.getText().toString();
 if (!(efrom.getText().toString().isEmpty())) {
                    if (Integer.parseInt(efrom.getText().toString()) > Integer.parseInt(eto.getText().toString())) {
                        Toast.makeText(EditActivity.this, "Invalid values of salary", Toast.LENGTH_SHORT).show();
                    }else{
                    callPostedApi(HREmail, PostID, PostedbyID, aPost, aCountry, aState, aCity, aFrom, aTo, aVacancy, aAbout, aQualification, aExperience, aJobType, aSoftwareKnowledge, aCategory, aCname, aCwebsite, location,Currency,aspecialisation,Extraspecialisation);
            }}}
        });

    }

    private void openExpDialog() {
        Button submit,cancel,button_close;
        final Spinner yearmax,yearmin;
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.exp_edit_dialog);
        yearmax=dialog.findViewById(R.id.experience);
        yearmin=dialog.findViewById(R.id.experience1);

        submit = dialog.findViewById(R.id.submit);
        cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        button_close=dialog.findViewById(R.id.button_close);
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String exp=  yearmax.getSelectedItem().toString()+"-"+yearmin.getSelectedItem().toString();
              maxexp=yearmin.getSelectedItem().toString();
               minexp=yearmax.getSelectedItem().toString();
                eexperience.setText(exp);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void openJobDialog() {
        Button submit,cancel,button_close;
        final Spinner jobtype;
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.jobtype_dialog);
        jobtype=dialog.findViewById(R.id.jobtype);


        submit = dialog.findViewById(R.id.submit);
        cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        button_close=dialog.findViewById(R.id.button_close);
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eexperience.setText(jobtype.getSelectedItem().toString());
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void openIndusDialog() {
        
            Button submit,cancel,button_close;
            final Spinner Industry;
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.industrylayout);
            Industry=dialog.findViewById(R.id.industry);

            submit = dialog.findViewById(R.id.submit);
            cancel = dialog.findViewById(R.id.cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            button_close=dialog.findViewById(R.id.button_close);
            button_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ecategory.setText(Industry.getSelectedItem().toString());
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    private void qualificationDialog() {
        Button submit, cancel, button_close;
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.qualificationlayout);
        qual = dialog.findViewById(R.id.qualification);
        oQual = dialog.findViewById(R.id.oqual);
        otherqualification = dialog.findViewById(R.id.otherqualification);
        specializationOther = dialog.findViewById(R.id.specilization3);
        streamlayout = dialog.findViewById(R.id.streamlayout);
        editstreamlayout = dialog.findViewById(R.id.editstreamlayout);
        Button getstream_btn=dialog.findViewById(R.id.getstream);
        streamSelected=dialog.findViewById(R.id.stream);
        getstream_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStreams();
            }
        });
        button_close = dialog.findViewById(R.id.button_close);
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        QualificationAPI();
        submit = dialog.findViewById(R.id.submit);
        cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                qualificationcheck();
                if (equalification.getText().toString().isEmpty()) {
                    equalification.setText(qualificationdata);
                    if(streamSpecialisationdata != null && !(streamSpecialisationdata.equals(""))){
                        especialisation.setText(streamSpecialisationdata);}
                } else {

                    qualificationdata= equalification.getText().toString()+","+qualificationdata;
                    if(!(especialisation.getText().toString().isEmpty())){

                        streamSpecialisationdata= especialisation.getText().toString()+","+streamSpecialisationdata;
                    }
                    equalification.setText(qualificationdata);
                    especialisation.setText(streamSpecialisationdata);
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void openDialog() {
        Button button_close;
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.input_dialog);
        Scountry = dialog.findViewById(R.id.country);
        Sstate = dialog.findViewById(R.id.state);
        Scity = dialog.findViewById(R.id.city);
        submit = dialog.findViewById(R.id.submit);
        button_close=dialog.findViewById(R.id.button_close);
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Country = Scountry.getSelectedItem().toString();
                String State = Sstate.getSelectedItem().toString();
                String City = Scity.getSelectedItem().toString();

                ecountry.setText(Country);
                estate.setText(State);
                ecity.setText(City);
                dialog.dismiss();
            }
        });

        countryAPI();
        dialog.show();
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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditActivity.this, android.R.layout.simple_spinner_item, arrayList_country);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Scountry.setAdapter(arrayAdapter);
                Scountry.setSelection(100);
                Scountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String country_ = arrayList_conID.get(position).toString();
                        String CountryName = arrayList_country.get(position).toString();

                        StateAPI(country_);
                        currencyAPI(CountryName);
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
                Toast.makeText(EditActivity.this, "Poor Internet Connection", Toast.LENGTH_SHORT).show();

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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditActivity.this, android.R.layout.simple_spinner_item, arrayList_state);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Sstate.setAdapter(arrayAdapter);
                if (Scountry.getSelectedItem().equals("India")) {
                    Sstate.setSelection(9);
                }

                Sstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditActivity.this, android.R.layout.simple_spinner_item, arrayList_city);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Scity.setAdapter(arrayAdapter);
                Scity.setSelection(1);

                Scity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    private void currencyAPI(String countryName) {
        String URL = "http://api.mymakeover.club/api/AppTrack/CurrencyList?country=" + countryName;
        final ArrayList<String> arrayList_currency = new ArrayList<>();
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
                    if (jsonArray.length() != 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                            String Currency = jsonObject1.getString("code");
                            currency.setText(Currency);
                        }
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
    private void QualificationAPI()  {

        arraylistQual     =new ArrayList<>();
        arraylistQualID   =new ArrayList<>();
        String URL = "http://api.mymakeover.club/api/MepJobs/GetQualification";
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
                        String qualname = jsonObject1.getString("Qualification");
                        String qualID = jsonObject1.getString("Id");
                        arraylistQualID.add(qualID);
                        arraylistQual.add(qualname);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditActivity.this, android.R.layout.simple_spinner_item, arraylistQual);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                qual.setAdapter(arrayAdapter);
                qual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        qualification_id = arraylistQualID.get(position).toString();
                        qualification_name = arraylistQual.get(position).toString();
                        if(qualification_name.equals("10th")){
                            streamlayout.setVisibility(View.GONE);
                            oQual.setVisibility(View.GONE);
                        }else if(qualification_name.equals("Other")){
                            oQual.setVisibility(View.VISIBLE);
                            streamlayout.setVisibility(View.GONE);
                            editstreamlayout.setVisibility(View.VISIBLE);
                        }
                        else{
                            oQual.setVisibility(View.GONE);
                            editstreamlayout.setVisibility(View.GONE);
                            streamlayout.setVisibility(View.VISIBLE);
                            GetQualSpecialisation(qualification_id);}
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
                Toast.makeText(EditActivity.this, "Connectivity issue", Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        requestQueue.add(stringRequest);

    }
    private void getStreams() {
        final ArrayList<Integer> mStreamUserItems = new ArrayList<>();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Stream");
        mBuilder.setMultiChoiceItems(streamlistItems, streamcheckItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                if (isChecked) {
                    mStreamUserItems.add(position);
                } else {
                    mStreamUserItems.remove((Integer.valueOf(position)));
                }
            }
        });

        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String item = "";
                for (int i = 0; i < mStreamUserItems.size(); i++) {
                    item = item + streamlistItems[mStreamUserItems.get(i)];
                    if (i != mStreamUserItems.size() - 1) {
                        item = item + ", ";
                    }
                }

                if (item.contains("Other")) {
                    item = item.replace(", Other", "");
                    streamSelected.setText(item);
                    editstreamlayout.setVisibility(View.VISIBLE);
                    if (!(streamSelected.getText().toString().isEmpty())) {
                        streamSpecialisationdata = streamSelected.getText().toString() + specializationOther.getText().toString();
                    }
                } else {
                    streamSelected.setText(item);
                    editstreamlayout.setVisibility(View.GONE);
                    streamSpecialisationdata = streamSelected.getText().toString();

                }

            }
        });

        mBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });



        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
    private void GetQualSpecialisation(String qualification_id) {
        String URL = "http://api.mymakeover.club/api/MepJobs/GetQualificationSpecialisation?qualId=" + qualification_id;
        final ArrayList<String> arrayListSpecialisation = new ArrayList<>();
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
                        String streamname = jsonObject1.getString("Course_Specialization");
                        arrayListSpecialisation.add(streamname);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                streamlistItems = arrayListSpecialisation.toArray(new String[0]);

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
    public void qualificationcheck(){

        if(qualification_name.equals("10th")){
            streamlayout.setVisibility(View.GONE);
            oQual.setVisibility(View.GONE);
            qualificationdata=qualification_name;


        }else if(qualification_name.equals("Other")){
            oQual.setVisibility(View.VISIBLE);
            streamlayout.setVisibility(View.GONE);
            editstreamlayout.setVisibility(View.VISIBLE);
            qualificationdata=otherqualification.getText().toString();
            streamSpecialisationdata=specializationOther.getText().toString();
        }
        else{

            oQual.setVisibility(View.GONE);
            editstreamlayout.setVisibility(View.GONE);
            streamlayout.setVisibility(View.VISIBLE);
            qualificationdata=qualification_name;
            GetQualSpecialisation(qualification_id);

        }

        if(editstreamlayout.getVisibility()== View.VISIBLE){
            streamSpecialisationdata = streamSelected.getText().toString()+","+specializationOther.getText().toString();

        }

    }
    private void callPostedApi(String HREmail, int postID, String postedbyID, String aPost, String aCountry, String aState, String aCity, String aFrom, String aTo, String aVacancy, String aAbout, String aQualification, String aExperience, String aJobType, String aSoftwareKnowledge, String aCategory, String aCname, String aCwebsite, String location, String currency,String aspecialisation,String Extraspecialisation) {

        String Url = "http://api.mymakeover.club/api/MepJobs/EditPostedJob?HREmail="+HREmail+"&PostID="+postID;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("HR_EmployeeId", postedbyID);
            jsonBody.put("Job_Designation", aPost);
            jsonBody.put("Job_Description", aAbout);
            jsonBody.put("Job_Location", location);
            jsonBody.put("Country", aCountry);
            jsonBody.put("State", aState);
            jsonBody.put("City", aCity);
            jsonBody.put("Salary_from", aFrom);
            jsonBody.put("Salary_to", aTo);
            jsonBody.put("Vacancy", aVacancy);
            jsonBody.put("Qualification", aQualification);
            jsonBody.put("Specialization", aspecialisation);
            jsonBody.put("Experience", aExperience);
            jsonBody.put("Job_Type", aJobType);
            jsonBody.put("Software_Knowledge", aSoftwareKnowledge);
            jsonBody.put("Company_Name", aCname);
            jsonBody.put("Company_Website", aCwebsite);
            jsonBody.put("Postedby_email", HREmail);
            jsonBody.put("Postedby_ID", postedbyID);
            jsonBody.put("Category", aCategory);
            jsonBody.put("Currency", currency);
            jsonBody.put("Extra_Specialization",Extraspecialisation);
            if(maxexp != null){
            jsonBody.put("Maximum_Experience", maxexp);
            jsonBody.put("Minimum_Experience", minexp);}
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody = jsonBody.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("ResponseOfMessage", "onResponse: " + response);
                JsonParser jsonParser = new JsonParser();
                String resstring = jsonParser.parse(response).getAsString();
                try {
                    JSONObject jsonObject = new JSONObject(resstring);
                    String status = jsonObject.getString("RegistrationStatus");
                    if (status.equals("1")) {
                        progressDialog.dismiss();
                        Toast.makeText(EditActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(EditActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "onErrorResponse: " + error.networkResponse.data);
                errorLogs.AppErrorLog("Editjobpost",error);
                progressDialog.dismiss();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("content-type", "application/json");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
