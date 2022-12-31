package com.s3software.naukria2z_hiring.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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

import dmax.dialog.SpotsDialog;

public class AddPost extends AppCompatActivity {
    Spinner Slocation, Scountry, currency, qual, Sstate;
    LinearLayout llstate, llcity, explay;
    EditText designation, from_salary, to_salary, totalvacancy, aboutwork, specializationOther, Tstate, Tcity;
    ArrayList<String> arrayList_country, arrayList_conID, arrayList_stateID, arraylistQual, arraylistQualID, arrayListSpecialisation, arrayListIndustry;
    FloatingActionButton nextcom, previouscom, nextDescription, previousJobDes;
    Spinner qualification, experience, jobtype, category, otherJobType, experience2;
    EditText extraspec, otherqualification, othersoft, othercategory;
    LinearLayout oSoft, oQual, streamlayout, editstreamlayout;
    EditText comname, comweb;
    FrameLayout frame1, frame2, frame3;
    CheckBox handicap;
    Button chooseSoft_btn, submit;
    TextView mItemSelected_soft, streamSelected, citySelected;
    String[] listItems, streamlistItems, citylistItems;
    boolean[] checkedItems, streamcheckItems, citycheckItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    int flag, expflag = 1, catflag = 0, formUrlFlag = 0, citystateFlag = 0;
    RadioGroup radioGroup, jobProcess_RadioG;
    RadioButton job, internship, fresher, form_radio, seekerEmail_radio;
    String qualificationdata, streamSpecialisationdata;
    ErrorLogs errorLogs;
    EditText qualificationn, streamm, formUrl;
    private AlertDialog progressDialog;
    private String softwareknow, category1, experience1, experience11, jobtype1, minexp, maxexp;
    private String disabilities = "No";
    private String qualification_id, qualification_name;
    private String country_;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        errorLogs = new ErrorLogs(getApplicationContext());
        init();
        SoftwareAPI();
        flag = 0;
        SharedPreferences userdata1 = getSharedPreferences("ProfileDetails", Context.MODE_PRIVATE);
        final String Name = userdata1.getString("Name", "");
        final String User_Email1 = userdata1.getString("User_Email", "");
        String Company_Website = userdata1.getString("Company_Website", "");
        String Company_Name = userdata1.getString("Company_Name", "");

        if (!(Company_Name.equals("null"))) {
            comname.setText(Company_Name);
        }
        if (!(Company_Website.equals("null"))) {
            comweb.setText(Company_Website);
        }

        qualificationn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= qualificationn.getRight() - qualificationn.getTotalPaddingRight()) {
                        // your action for drawable click event

                        qualificationDialog();
                        return true;
                    }
                }
                return false;
            }
        });
        Tstate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= Tstate.getRight() - Tstate.getTotalPaddingRight()) {
                        // your action for drawable click event

                        StateCityDialog();
                        return true;
                    }
                }
                return false;
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.job) {
                    expflag = 1;
                    explay.setVisibility(View.VISIBLE);
                    experience11 = experience.getSelectedItem().toString() + "-" + experience2.getSelectedItem().toString();
                    experience1 = experience11;


                } else if (checkedId == R.id.internship) {
                    expflag = 0;
                    explay.setVisibility(View.GONE);
                    experience1 = "Internship";
                    minexp = "0 year";
                    maxexp = "1 year";
                } else {
                    expflag = 0;
                    explay.setVisibility(View.GONE);
                    experience1 = "Fresher";
                    minexp = "0 year";
                    maxexp = "1 year";
                }
            }

        });
        handicap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (handicap.isChecked()) {
                    disabilities = "Yes";
                } else {
                    disabilities = "No";
                }

            }
        });

        jobProcess_RadioG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.seekeremail) {
                    formUrl.setVisibility(View.GONE);
                    formUrlFlag = 0;
                } else {
                    formUrl.setVisibility(View.VISIBLE);
                    formUrlFlag = 1;
                }
            }
        });

        chooseSoft_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddPost.this);
                mBuilder.setTitle("title");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                        if (isChecked) {
                            mUserItems.add(position);
                        } else {
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        mItemSelected_soft.setText(item);

                        if (item.contains("Other")) {
                            oSoft.setVisibility(View.VISIBLE);
                            if (!(mItemSelected_soft.getText().toString().isEmpty())) {
                                softwareknow = mItemSelected_soft.getText().toString();
                            }
                        } else {
                            oSoft.setVisibility(View.GONE);
                            softwareknow = mItemSelected_soft.getText().toString();

                        }
                    }
                });

                mBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton("clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            mItemSelected_soft.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        arrayList_country = new ArrayList<>();
        arrayList_conID = new ArrayList<>();
        arrayList_stateID = new ArrayList<>();

        jobtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (jobtype.getSelectedItem().toString().equals("Part time")) {
                    otherJobType.setVisibility(View.VISIBLE);
                    jobtype1 = otherJobType.getSelectedItem().toString();
                } else {
                    otherJobType.setVisibility(View.GONE);
                    jobtype1 = jobtype.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        countryAPI();

        Slocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String location = parent.getItemAtPosition(position).toString();
                if (location.equals("InterNational")) {
                    llstate.setVisibility(View.GONE);
                    llcity.setVisibility(View.GONE);
                } else {
                    llstate.setVisibility(View.VISIBLE);
                    llcity.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //QualificationAPI();
        GetIndustry();

        previousJobDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frame1.setVisibility(View.GONE);
                frame2.setVisibility(View.VISIBLE);
                frame3.setVisibility(View.GONE);
            }
        });
        previouscom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frame1.setVisibility(View.VISIBLE);
                frame2.setVisibility(View.GONE);
                frame3.setVisibility(View.GONE);
            }
        });

        nextDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }

                try {
                    if (designation.getText().toString().isEmpty()) {
                        designation.setError("InValid");
                    } else if (!(from_salary.getText().toString().isEmpty())) {
                        if (Integer.parseInt(from_salary.getText().toString()) > Integer.parseInt(to_salary.getText().toString())) {
                            Toast.makeText(AddPost.this, "Invalid values of salary", Toast.LENGTH_SHORT).show();
                        } else {
                            if (totalvacancy.getText().toString().isEmpty()) {
                                totalvacancy.setError("InValid");
                            } else if (aboutwork.getText().toString().isEmpty()) {
                                aboutwork.setError("InValid");
                            } else {
                                frame1.setVisibility(View.GONE);
                                frame2.setVisibility(View.VISIBLE);
                                frame3.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        if (totalvacancy.getText().toString().isEmpty()) {
                            totalvacancy.setError("InValid");
                        } else if (aboutwork.getText().toString().isEmpty()) {
                            aboutwork.setError("InValid");
                        } else {
                            frame1.setVisibility(View.GONE);
                            frame2.setVisibility(View.VISIBLE);
                            frame3.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        nextcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }
                frame1.setVisibility(View.GONE);
                frame2.setVisibility(View.GONE);
                frame3.setVisibility(View.VISIBLE);
                if (catflag == 1) {
                    category1 = othercategory.getText().toString();
                }
                if (expflag == 1) {
                    experience11 = experience.getSelectedItem().toString() + "-" + experience2.getSelectedItem().toString();
                    experience1 = experience11;
                    minexp = experience.getSelectedItem().toString();
                    maxexp = experience2.getSelectedItem().toString();

                } else {
                    minexp = "0 year";
                    maxexp = "1 year";
                }
                if (!(othersoft.getText().toString().isEmpty())) {
                    softwareknow = othersoft.getText().toString() + "," + mItemSelected_soft.getText().toString();
                }

            }
        });

        SharedPreferences userdata = AddPost.this.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        final String User_Email = userdata.getString("User_Email", "");
        final String EmployeeID = userdata.getString("EmployeeID", "");

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String Designation = designation.getText().toString();
                String From_salary = from_salary.getText().toString();
                String To_salary = to_salary.getText().toString();
                String TotalVacancy = totalvacancy.getText().toString();
                String Aboutwork = aboutwork.getText().toString();
                String CountryValue = Scountry.getSelectedItem().toString();
                String StateValue = Tstate.getText().toString();
                String CityValue = Tcity.getText().toString();
                String LocationValue = Slocation.getSelectedItem().toString();
                String companyname = comname.getText().toString();
                String companyweb = comweb.getText().toString();
                String Extraspec = extraspec.getText().toString();
                String qualdata = qualificationn.getText().toString();
                String streamdata = streamm.getText().toString();
                jobtype1 = jobtype.getSelectedItem().toString();
                String Currency = currency.getSelectedItem().toString();
                String FormURL = "";
                if (formUrl.getVisibility() == View.VISIBLE) {
                    FormURL = formUrl.getText().toString();
                }

                if (comname.getText().toString().isEmpty()) {
                    comname.setError("InValid");
                } else if (!(comweb.getText().toString().isEmpty())) {
                    String comwebsite = comweb.getText().toString();
                    if (!(Patterns.WEB_URL.matcher(comwebsite).matches())) {
                        comweb.setError("Invalid");
                    } else {

                        if (flag == 0 && formUrlFlag == 0) {
                            JobPostRecruiter(User_Email, EmployeeID, LocationValue, CountryValue, Designation, From_salary, To_salary, TotalVacancy, Aboutwork, qualdata, experience1, jobtype1, softwareknow, companyname, companyweb, StateValue, CityValue, category1, Currency, disabilities, streamdata, minexp, maxexp, Extraspec, FormURL);
                        } else if (flag == 0 && formUrlFlag == 1) {
                            if (formUrl.getText().toString().isEmpty()) {
                                Snackbar snackbar = Snackbar
                                        .make(frame3, "Please write form url", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            } else {
                                JobPostRecruiter(User_Email, EmployeeID, LocationValue, CountryValue, Designation, From_salary, To_salary, TotalVacancy, Aboutwork, qualdata, experience1, jobtype1, softwareknow, companyname, companyweb, StateValue, CityValue, category1, Currency, disabilities, streamdata, minexp, maxexp, Extraspec, FormURL);
                            }

                        }
                    }
                } else {

                    if (flag == 0 && formUrlFlag == 0) {
                        JobPostRecruiter(User_Email, EmployeeID, LocationValue, CountryValue, Designation, From_salary, To_salary, TotalVacancy, Aboutwork, qualdata, experience1, jobtype1, softwareknow, companyname, companyweb, StateValue, CityValue, category1, Currency, disabilities, streamdata, minexp, maxexp, Extraspec, FormURL);
                    } else if (flag == 0 && formUrlFlag == 1) {
                        if (formUrl.getText().toString().isEmpty()) {
                            Snackbar snackbar = Snackbar
                                    .make(frame3, "Please write form url", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else {
                            JobPostRecruiter(User_Email, EmployeeID, LocationValue, CountryValue, Designation, From_salary, To_salary, TotalVacancy, Aboutwork, qualdata, experience1, jobtype1, softwareknow, companyname, companyweb, StateValue, CityValue, category1, Currency, disabilities, streamdata, minexp, maxexp, Extraspec, FormURL);
                        }

                    }
                }
            }
        });
    }

    private void StateCityDialog() {

        Button submit, cancel, button_close;
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.citystate_layout);
        Sstate = dialog.findViewById(R.id.state);
        Button getcity_btn = dialog.findViewById(R.id.getcity);
        citySelected = dialog.findViewById(R.id.city);
        citystateFlag = 1;
        StateAPI(country_);
        getcity_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCity();
            }
        });
        button_close = dialog.findViewById(R.id.button_close);
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

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
                String statedata = "", citydata = "";
                if (!Sstate.getSelectedItem().toString().isEmpty()) {
                    statedata = Sstate.getSelectedItem().toString();
                }
                if (!(citySelected.getText().toString().isEmpty())) {
                    citydata = citySelected.getText().toString();
                }
                if (Tstate.getText().toString().isEmpty()) {
                    Tstate.setText(statedata);
                } else {
                    Tstate.setText(Tstate.getText().toString() + "," + statedata);
                }
                if (Tcity.getText().toString().isEmpty()) {
                    Tcity.setText(citydata);
                } else {
                    Tcity.setText(Tcity.getText().toString() + "," + citydata);
                }

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
        Button getstream_btn = dialog.findViewById(R.id.getstream);
        streamSelected = dialog.findViewById(R.id.stream);
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
                if (qualificationn.getText().toString().isEmpty()) {
                    qualificationn.setText(qualificationdata);
                    if (streamSpecialisationdata != null && !(streamSpecialisationdata.equals(""))) {
                        streamm.setText(streamSpecialisationdata);
                    }
                } else {

                    qualificationdata = qualificationn.getText().toString() + "," + qualificationdata;
                    if (!(streamm.getText().toString().isEmpty())) {

                        streamSpecialisationdata = streamm.getText().toString() + "," + streamSpecialisationdata;
                    }
                    qualificationn.setText(qualificationdata);
                    streamm.setText(streamSpecialisationdata);
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void getCity() {
        final ArrayList<Integer> mCityUserItems = new ArrayList<>();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddPost.this);
        mBuilder.setTitle("City");
        mBuilder.setMultiChoiceItems(citylistItems, citycheckItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                if (isChecked) {
                    mCityUserItems.add(position);
                } else {
                    mCityUserItems.remove((Integer.valueOf(position)));
                }
            }
        });

        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String item = "";
                for (int i = 0; i < mCityUserItems.size(); i++) {
                    item = item + citylistItems[mCityUserItems.get(i)];
                    if (i != mCityUserItems.size() - 1) {
                        item = item + ", ";
                    }
                }

                if (!item.isEmpty()) {
                    citySelected.setText(item);
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

    private void getStreams() {
        final ArrayList<Integer> mStreamUserItems = new ArrayList<>();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddPost.this);
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


    private void init() {
        progressDialog = new SpotsDialog(this, R.style.Custom);
        qualificationn = findViewById(R.id.qualificationn);
        streamm = findViewById(R.id.streamm);
        currency = findViewById(R.id.currency);
        nextDescription = findViewById(R.id.nextDes);
        llstate = findViewById(R.id.state1);
        llcity = findViewById(R.id.city1);
        Slocation = findViewById(R.id.location);
        Scountry = findViewById(R.id.country);
        Tstate = findViewById(R.id.Tstate);
        Tcity = findViewById(R.id.Tcity);
        explay = findViewById(R.id.experiencelay);
        designation = findViewById(R.id.post);
        from_salary = findViewById(R.id.fromsalary);
        to_salary = findViewById(R.id.tosalary);
        totalvacancy = findViewById(R.id.vacancy);
        aboutwork = findViewById(R.id.aboutwork);
        otherJobType = findViewById(R.id.otherjobtype);
        frame1 = findViewById(R.id.fragment_frame1);
        frame2 = findViewById(R.id.fragment_frame2);
        frame3 = findViewById(R.id.fragment_frame3);
        previousJobDes = findViewById(R.id.previousjobdes);
        handicap = findViewById(R.id.checkbox);
        nextcom = findViewById(R.id.nextcom);
        qualification = findViewById(R.id.qualification);
        experience = findViewById(R.id.experience);
        jobtype = findViewById(R.id.jobtype);
        category = findViewById(R.id.category);
        job = findViewById(R.id.job);
        internship = findViewById(R.id.internship);
        othersoft = findViewById(R.id.othersoftware);
        oSoft = findViewById(R.id.osoft);
        othercategory = findViewById(R.id.categoryed);
        previouscom = findViewById(R.id.previouscom);
        comname = findViewById(R.id.comname);
        comweb = findViewById(R.id.comweb);
        submit = findViewById(R.id.PostJob);
        radioGroup = (RadioGroup) findViewById(R.id.jobapply);
        chooseSoft_btn = (Button) findViewById(R.id.btnOrder);
        mItemSelected_soft = (TextView) findViewById(R.id.tvItemSelected);
        experience2 = findViewById(R.id.experience1);
        fresher = findViewById(R.id.fresher);
        arraylistQual = new ArrayList<>();
        arraylistQualID = new ArrayList<>();
        arrayListSpecialisation = new ArrayList<>();
        arrayListIndustry = new ArrayList<>();
        extraspec = findViewById(R.id.extraspec);
        jobProcess_RadioG = findViewById(R.id.jobprocess);
        seekerEmail_radio = findViewById(R.id.seekeremail);
        form_radio = findViewById(R.id.form);
        formUrl = findViewById(R.id.formurl);
    }

    private void JobPostRecruiter(final String user_email, String employeeID, String location, String country,
                                  final String designation, String from_salary, String to_salary,
                                  String totalVacancy, String aboutwork, String qualification1,
                                  String experience1, String jobtype1, String softwareknow,
                                  String companyname, String companyweb, String state,
                                  String city, String category, String Currency, String disabilities,
                                  String specializationData, String minexp, String maxexp, String Extraspec, String formurl) {
        progressDialog.show();
        String Url = "http://api.mymakeover.club/api/MepJobs/RecruiterJobPost?emailaddress=" + user_email;
        RequestQueue requestQueue = Volley.newRequestQueue(AddPost.this);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("HR_EmployeeId", employeeID);
            jsonBody.put("Job_Designation", designation);
            jsonBody.put("Job_Description", aboutwork);
            jsonBody.put("Job_Location", location);
            jsonBody.put("Country", country);
            jsonBody.put("State", state);
            jsonBody.put("City", city);
            jsonBody.put("Salary_from", from_salary);
            jsonBody.put("Salary_to", to_salary);
            jsonBody.put("Vacancy", totalVacancy);
            jsonBody.put("Qualification", qualification1);
            jsonBody.put("Experience", experience1);
            jsonBody.put("Job_Type", jobtype1);
            jsonBody.put("Software_Knowledge", softwareknow);
            jsonBody.put("Company_Name", companyname);
            jsonBody.put("Company_Website", companyweb);
            jsonBody.put("Postedby_email", user_email);
            jsonBody.put("Postedby_ID", employeeID);
            jsonBody.put("Category", category);
            jsonBody.put("Currency", Currency);
            jsonBody.put("Disabilities", disabilities);
            jsonBody.put("Specialization", specializationData);
            jsonBody.put("Maximum_Experience", maxexp);
            jsonBody.put("Minimum_Experience", minexp);
            jsonBody.put("Extra_Specialization", Extraspec);
            jsonBody.put("Column1", formurl);
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

                    String reg_Status = jsonObject.getString("RegistrationStatus");
                    if (reg_Status.equals("1")) {
                        openDialog();
                        Toast.makeText(AddPost.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                        mailtoHR(user_email, designation);
                        progressDialog.dismiss();
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
                errorLogs.AppErrorLog("jobpost", error);
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
        };stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    private void mailtoHR(String user_email, String designation) {

        String Url = "http://api.mymakeover.club/api/MepJobs/MailToHRJob?postedby_email=" + user_email + "&post=" + designation;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("done", "onResponse: " + response);
                JsonParser jsonParser = new JsonParser();
                String resstring = jsonParser.parse(response).getAsString();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("not done", "onResponse: " + error);
                progressDialog.dismiss();
                errorLogs.AppErrorLog("mailtohr", error);
                Toast.makeText(AddPost.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void openDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Your job has been posted.it will reflect to candidate after verfication");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(AddPost.this, MainActivity.class);
                        startActivity(i);
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddPost.this, android.R.layout.simple_spinner_item, arrayList_country);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Scountry.setAdapter(arrayAdapter);
                Scountry.setSelection(100);
                Scountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        country_ = arrayList_conID.get(position).toString();
                        String CountryName = arrayList_country.get(position).toString();
                        if (citystateFlag == 1) {
                            StateAPI(country_);
                        }
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
                errorLogs.AppErrorLog("getCountry", error);
                Toast.makeText(AddPost.this, "Connectivity issue", Toast.LENGTH_SHORT).show();

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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddPost.this, android.R.layout.simple_spinner_item, arrayList_state);
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
                errorLogs.AppErrorLog("getState", error);
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
                    citylistItems = arrayList_city.toArray(new String[0]);
                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorLogs.AppErrorLog("getCity", error);
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

                            String currency = jsonObject1.getString("code");

                            arrayList_currency.add(currency);

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddPost.this, android.R.layout.simple_spinner_item, arrayList_currency);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                currency.setAdapter(arrayAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorLogs.AppErrorLog("getcurrency", error);
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        requestQueue.add(stringRequest);

    }

    private void QualificationAPI() {

        arraylistQual = new ArrayList<>();
        arraylistQualID = new ArrayList<>();
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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddPost.this, android.R.layout.simple_spinner_item, arraylistQual);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                qual.setAdapter(arrayAdapter);
                qual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        qualification_id = arraylistQualID.get(position).toString();
                        qualification_name = arraylistQual.get(position).toString();
                        if (qualification_name.equals("10th")) {
                            streamlayout.setVisibility(View.GONE);
                            oQual.setVisibility(View.GONE);
                        } else if (qualification_name.equals("Other")) {
                            oQual.setVisibility(View.VISIBLE);
                            streamlayout.setVisibility(View.GONE);
                            editstreamlayout.setVisibility(View.VISIBLE);
                        } else {
                            oQual.setVisibility(View.GONE);
                            editstreamlayout.setVisibility(View.GONE);
                            streamlayout.setVisibility(View.VISIBLE);
                            GetQualSpecialisation(qualification_id);
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
                Log.e("error", "onErrorResponse: " + error);
                Toast.makeText(AddPost.this, "Connectivity issue", Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        requestQueue.add(stringRequest);

    }

    private void GetQualSpecialisation(String qualification_id) {
        String URL = "http://api.mymakeover.club/api/MepJobs/GetQualificationSpecialisation?qualId=" + qualification_id;
        final ArrayList<String> arrayListSpecialisation = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(AddPost.this);
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

    public void qualificationcheck() {

        if (qualification_name.equals("10th")) {
            streamlayout.setVisibility(View.GONE);
            oQual.setVisibility(View.GONE);
            qualificationdata = qualification_name;


        } else if (qualification_name.equals("Other")) {
            oQual.setVisibility(View.VISIBLE);
            streamlayout.setVisibility(View.GONE);
            editstreamlayout.setVisibility(View.VISIBLE);
            qualificationdata = otherqualification.getText().toString();
            streamSpecialisationdata = specializationOther.getText().toString();
        } else {

            oQual.setVisibility(View.GONE);
          //  editstreamlayout.setVisibility(View.GONE);
            streamlayout.setVisibility(View.VISIBLE);
            qualificationdata = qualification_name;
            GetQualSpecialisation(qualification_id);

        }

        if (editstreamlayout.getVisibility() == View.VISIBLE) {
            streamSpecialisationdata = streamSelected.getText().toString() + "," + specializationOther.getText().toString();

        }

    }

    private void GetIndustry() {
        String URL = "http://api.mymakeover.club/api/MepJobs/GetIndustryData?i=1";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddPost.this, android.R.layout.simple_spinner_item, arrayListIndustry);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category.setAdapter(arrayAdapter);
                category.setSelection(0);

                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String indus = parent.getItemAtPosition(position).toString();
                        if (indus.equals("Other")) {
                            catflag = 1;
                            othercategory.setVisibility(View.VISIBLE);
                            category1 = othercategory.getText().toString();
                        } else {
                            catflag = 0;
                            othercategory.setVisibility(View.GONE);
                            category1 = category.getSelectedItem().toString();
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
                errorLogs.AppErrorLog("getIndustry", error);
            }
        });
        requestQueue.add(stringRequest);

    }

    private void SoftwareAPI() {

        final ArrayList<String> softwarelist = new ArrayList<>();
        String URL = "http://api.mymakeover.club/api/MepJobs/ShowSoftwares?software={software}";
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
                        String softwarename = jsonObject1.getString("Software_Name");
                        // String qualID = jsonObject1.getString("Id");

                        softwarelist.add(softwarename);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

                listItems = softwarelist.toArray(new String[0]);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "onErrorResponse: " + error);
                errorLogs.AppErrorLog("getSoftware", error);
                Toast.makeText(AddPost.this, "Connectivity issue", Toast.LENGTH_SHORT).show();
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
