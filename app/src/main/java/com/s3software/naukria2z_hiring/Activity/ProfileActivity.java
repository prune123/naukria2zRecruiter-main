package com.s3software.naukria2z_hiring.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;

import com.android.volley.DefaultRetryPolicy;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonParser;
import com.s3software.naukria2z_hiring.ErrorLogs;
import com.s3software.naukria2z_hiring.R;
import com.s3software.naukria2z_hiring.RetrofitAPICall.ApiConfig;
import com.s3software.naukria2z_hiring.RetrofitAPICall.AppConfig;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ProfileActivity extends AppCompatActivity {

    FloatingActionButton editDetail, updateDetail;
    LinearLayout moredetails;
    EditText email,name, company_landline, company_email, company_country, company_state, company_city, company_website, pincode, gender, company_name, mobile, designation;
    Toolbar toolbar;
    ProgressDialog progressDialog;
    FloatingActionButton profilePicChange;
    ImageView profile;
    String profileData,mediaPath;
    Spinner Scountry, Sstate, Scity;
    String PEmployeeID, PUser_Email;
    Button update, submit, cancel, button_close;
    ArrayList<String> arrayList_country, arrayList_conID, arrayList_stateID;
    ErrorLogs errorLogs;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences userProfile = this.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        PUser_Email = userProfile.getString("User_Email", "");
        PEmployeeID = userProfile.getString("EmployeeID", "");

        callProfileAPI(PUser_Email);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences userProfile = this.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        PUser_Email = userProfile.getString("User_Email", "");
        PEmployeeID = userProfile.getString("EmployeeID", "");

        callProfileAPI(PUser_Email);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        name = findViewById(R.id.name);
        email = findViewById(R.id.pemail);
        profile = findViewById(R.id.profile);
        company_name = findViewById(R.id.companyname);
        mobile = findViewById(R.id.pcontactno);
        designation = findViewById(R.id.designation);
        company_landline = findViewById(R.id.companyno);
        company_email = findViewById(R.id.companyemail);
        company_country = findViewById(R.id.country);
        company_state = findViewById(R.id.state);
        company_city = findViewById(R.id.city1);
        company_website = findViewById(R.id.companywebsite);
        gender = findViewById(R.id.gender);
        pincode = findViewById(R.id.pincode);
        toolbar = findViewById(R.id.toolbar);
        editDetail = findViewById(R.id.editdetail);
        updateDetail = findViewById(R.id.updatedetail);
        arrayList_country = new ArrayList<>();
        arrayList_conID = new ArrayList<>();
        arrayList_stateID = new ArrayList<>();
        errorLogs=new ErrorLogs(getApplicationContext());
        profilePicChange = findViewById(R.id.chngeprofilepic);
        toolbar.setTitle("Profile");

        toolbar.setTitleTextColor(Color.WHITE);
        progressDialog = new ProgressDialog(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        SharedPreferences userProfile = this.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        PUser_Email = userProfile.getString("User_Email", "");
        PEmployeeID = userProfile.getString("EmployeeID", "");

        company_country.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= company_country.getRight() - company_country.getTotalPaddingRight()) {
                        // your action for drawable click event

                        openDialog();
                        return true;
                    }
                }
                return false;
            }
        });
        gender.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= company_country.getRight() - company_country.getTotalPaddingRight()) {
                        // your action for drawable click event

                        openDialogGender();
                        return true;
                    }
                }
                return false;
            }
        });
        profilePicChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });
        editDetail.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                updateDetail.setVisibility(View.VISIBLE);
                editDetail.setVisibility(View.GONE);
                name.setEnabled(true);
                mobile.setEnabled(true);
                designation.setEnabled(true);
                gender.setEnabled(true);
                company_name.setEnabled(true);
                company_landline.setEnabled(true);
                company_email.setEnabled(true);
                company_country.setEnabled(true);
                company_state.setEnabled(true);
                company_city.setEnabled(true);
                company_website.setEnabled(true);
                pincode.setEnabled(true);

                Drawable[] compoundDrawables = gender.getCompoundDrawables();
                Drawable drawableRight = compoundDrawables[2].mutate();
                drawableRight.setColorFilter(new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN));

                Drawable[] compoundDrawables1 = company_country.getCompoundDrawables();
                Drawable drawableRight1 = compoundDrawables1[2].mutate();
                drawableRight1.setColorFilter(new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN));
            }
        });
        updateDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Uname = name.getText().toString();
                String Umobile = mobile.getText().toString();
                String Udesignation = designation.getText().toString();
                String Ugender = gender.getText().toString();
                String Ucompany_name = company_name.getText().toString();
                String Ucompany_landline = company_landline.getText().toString();
                String Ucompany_email = company_email.getText().toString();
                String Ucompany_country = company_country.getText().toString();
                String Ucompany_state = company_state.getText().toString();
                String Ucompany_city = company_city.getText().toString();
                String Ucompany_website = company_website.getText().toString();
                String Upincode = pincode.getText().toString();

              boolean Validate=  validate();
              if(Validate) {
                  CallUpdateProfileAPI(PUser_Email, PEmployeeID, Uname, Umobile, Udesignation, Ugender, Ucompany_name, Ucompany_landline, Ucompany_email, Ucompany_country, Ucompany_state
                          , Ucompany_city, Ucompany_website, Upincode);
              }


                Drawable[] compoundDrawables = gender.getCompoundDrawables();
                Drawable drawableRight = compoundDrawables[2].mutate();
                drawableRight.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN));

                Drawable[] compoundDrawables1 = company_country.getCompoundDrawables();
                Drawable drawableRight1 = compoundDrawables1[2].mutate();
                drawableRight1.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN));


            }
        });
        callProfileAPI(PUser_Email);
    }

    private boolean  validate() {
        boolean success;
        String phoneregex="^[6-9][0-9]{9}$";
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if(!(mobile.getText().toString().matches(phoneregex))){
            success=  false;
            mobile.setError("invalid");
        }
        else if(!(company_email.getText().toString().matches(emailPattern))){
            success=  false;
            company_email.setError("invalid");
        }
        else{
            success=  true;
        }
      return   success;
    }

    private void openDialogGender() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.input_gender);
        final Spinner Sgender = dialog.findViewById(R.id.gender);
        submit = dialog.findViewById(R.id.submit);
        cancel = dialog.findViewById(R.id.cancel);
        button_close = dialog.findViewById(R.id.button_close);
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gendervalue = Sgender.getSelectedItem().toString();
                gender.setText(gendervalue);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.input_dialog);
        Scountry = dialog.findViewById(R.id.country);
        Sstate = dialog.findViewById(R.id.state);
        Scity = dialog.findViewById(R.id.city);
        submit = dialog.findViewById(R.id.submit);
        cancel = dialog.findViewById(R.id.cancel);
        button_close = dialog.findViewById(R.id.button_close);
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
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

                company_country.setText(Country);
                company_state.setText(State);
                company_city.setText(City);
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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_spinner_item, arrayList_country);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Scountry.setAdapter(arrayAdapter);
                Scountry.setSelection(100);
                Scountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String country_ = arrayList_conID.get(position).toString();
                        String CountryName = arrayList_country.get(position).toString();

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
                Toast.makeText(ProfileActivity.this, "Server Error", Toast.LENGTH_SHORT).show();

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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_spinner_item, arrayList_state);
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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_spinner_item, arrayList_city);
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


    private void callProfileAPI(String pUser_email) {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String url = "http://api.mymakeover.club/api/MepJobs/RecruiterProfile?HREmail=" + pUser_email;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", "onResponse: " + response);
                JsonParser jsonParser = new JsonParser();
                String resstring = jsonParser.parse(response).getAsString();
                Log.e("profileresponse", "onResponse: " + resstring);
                try {
                    JSONObject jsonObject1 = new JSONObject(resstring);
                    JSONObject jsonObject = jsonObject1.getJSONObject("ProfileData");
                    String Name = jsonObject.getString("Name");
                    String Personal_Email = jsonObject.getString("Personal_Email");
                    String Mobile_No = jsonObject.getString("Mobile_No");
                    String Designation = jsonObject.getString("Designation");
                    String CompanyLandLine_No = jsonObject.getString("CompanyLandLine_No");
                    String Company_Email = jsonObject.getString("Company_Email");
                    String Company_Address = jsonObject.getString("Company_Address");
                    String Company_Country = jsonObject.getString("Company_Country");
                    String Company_State = jsonObject.getString("Company_State");
                    String Company_City = jsonObject.getString("Company_City");
                    String Company_Website = jsonObject.getString("Company_Website");
                    String Company_Name = jsonObject.getString("Company_Name");
                    String Gender = jsonObject.getString("Gender");
                    String Profile_Image = jsonObject.getString("Profile_Image");
                    String Pincode = jsonObject.getString("Company_Pincode");
                   /* byte[] bytedata = Base64.decode(Profile_Image, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytedata, 0, bytedata.length);
                    if (bitmap != null) {
                        circleimage(bitmap);
                    }*/

                    if(Profile_Image.equals("null") || Profile_Image.equals("")){


                    }else{
                        Picasso.get()
                                .load(Profile_Image)
                                .into(profile);
                    }
                    name.setText(Name);
                    email.setText(Personal_Email);
                    mobile.setText(Mobile_No);
                    designation.setText(Designation);
                    gender.setText(Gender);
                    if (!(Company_Name.equals("null"))) {
                        company_name.setText(Company_Name);
                    }
                    if (!(CompanyLandLine_No.equals("null"))) {
                        company_landline.setText(CompanyLandLine_No);
                    }
                    if (!(Company_Email.equals("null"))) {
                        company_email.setText(Company_Email);
                    }
                    company_country.setText(Company_Country);
                    company_state.setText(Company_State);
                    company_city.setText(Company_City);
                    if (!(Company_Website.equals("null"))) {
                        company_website.setText(Company_Website);
                    }
                    pincode.setText(Pincode);
                    progressDialog.dismiss();
                  //  username.setText(Name);
                  //  useremail.setText(Personal_Email);
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                errorLogs.AppErrorLog("profile",error);
                Toast.makeText(ProfileActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void selectImage() {
        final CharSequence[] options = { "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Choose from Gallery")) {

                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, 2);
                    }

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};


                Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                profile.setImageBitmap(bitmap);
                cursor.close();
                UpdateProfileAPI();
                // bitmapToBase64(bitmap);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }


        }
    }


    private void UpdateProfileAPI() {

        MultipartBody.Part body=null;
        if(mediaPath!=null){
            File file = new File(mediaPath);

            RequestBody fileResume = RequestBody.create(MediaType.parse("*/*"), file);
            String filenewName= PEmployeeID+"."+ file.getName().substring(file.getName().lastIndexOf(".") + 1);

            body = MultipartBody.Part.createFormData("file", filenewName, fileResume);

        }


        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        Call<ResponseBody> call = null;
        RequestBody empId = RequestBody.create(MultipartBody.FORM, PEmployeeID.toString());



        call = getResponse.uploadProfileImage(empId, body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   retrofit2.Response<ResponseBody> response) {
                try {


                    assert response.body() != null;
                    String res = response.body().string();
                    JsonParser jsonParser = new JsonParser();
                    String resstring = jsonParser.parse(res).getAsString();

                    try {
                        JSONObject jsonObject = new JSONObject(resstring);
                        String status = jsonObject.getString("ResumeStatus");
                        if (status.equals("1")) {

                            Toast.makeText(ProfileActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                            //SaveSharedPreference.setLoggedIn(getApplicationContext(), true);

                        }
                        else{
                            Toast.makeText(ProfileActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.v("Upload", resstring);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });


    }

    private void CallUpdateProfileAPI(String PUser_Email, String PEmployeeID, String uname, String umobile, String udesignation, String ugender, String ucompany_name, String ucompany_landline, String ucompany_email, String ucompany_country, String ucompany_state, String ucompany_city, String ucompany_website, String Upincode) {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String Url = "http://api.mymakeover.club/api/MepJobs/UpdateProfileRecruiter?value=2&Uemail=" + PUser_Email + "&UemployeeID=" + PEmployeeID;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("Name", uname);
            jsonBody.put("Mobile_No", umobile);
            jsonBody.put("CompanyLandLine_No", ucompany_landline);
            jsonBody.put("Company_Name", ucompany_name);
            jsonBody.put("Designation", udesignation);
            jsonBody.put("Company_Country", ucompany_country);
            jsonBody.put("Company_State", ucompany_state);
            jsonBody.put("Company_City", ucompany_city);
            jsonBody.put("Gender", ugender);
            jsonBody.put("Company_Email", ucompany_email);
            jsonBody.put("Company_Pincode", Upincode);
            jsonBody.put("Company_Website", ucompany_website);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody = jsonBody.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(String response) {
                Log.e("ResponseOfMessage", "onResponse: " + response);
                JsonParser jsonParser = new JsonParser();
                String resstring = jsonParser.parse(response).getAsString();
                try {
                    JSONObject jsonObject = new JSONObject(resstring);
                    Log.e("response", "onResponse: " + jsonObject);
                    String status = jsonObject.getString("UpdateStatus");
                    if (status.equals("1")) {

                        Toast.makeText(ProfileActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                        editDetail.setVisibility(View.VISIBLE);
                        updateDetail.setVisibility(View.GONE);
                        name.setEnabled(false);
                        mobile.setEnabled(false);
                        designation.setEnabled(false);
                        gender.setEnabled(false);
                        company_name.setEnabled(false);
                        company_landline.setEnabled(false);
                        company_email.setEnabled(false);
                        company_country.setEnabled(false);
                        company_state.setEnabled(false);
                        company_city.setEnabled(false);
                        company_website.setEnabled(false);
                        pincode.setEnabled(false);
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "onErrorResponse: " + error.networkResponse.data);
                errorLogs.AppErrorLog("updateprofile",error);
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