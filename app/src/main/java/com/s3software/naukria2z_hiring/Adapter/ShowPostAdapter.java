package com.s3software.naukria2z_hiring.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonParser;
import com.s3software.naukria2z_hiring.Activity.EditActivity;
import com.s3software.naukria2z_hiring.Activity.MainActivity;
import com.s3software.naukria2z_hiring.Activity.ShowCandidate;
import com.s3software.naukria2z_hiring.ErrorLogs;
import com.s3software.naukria2z_hiring.Model.PostDatum;
import com.s3software.naukria2z_hiring.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ShowPostAdapter extends RecyclerView.Adapter<ShowPostAdapter.ViewHolder> {
  private   Context context;
    private ArrayList<PostDatum> postData;
    private int candidate;
    private String satisfied,flag="0";
    private  RadioGroup r1, r2;
    private EditText sugg;
    ErrorLogs errorLogs;

    private ProgressDialog progressDialog;

    public ShowPostAdapter(MainActivity context, ArrayList<PostDatum> datumPost) {
        this.context = context;
        this.postData = datumPost;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.showjobs, parent, false);
        ViewHolder mh = new ViewHolder(v);
        errorLogs=new ErrorLogs(context);
        progressDialog=new ProgressDialog(context);
        progressDialog.setMax(100);
        progressDialog.setMessage("Please Wait..");
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final String jobtitle = postData.get(position).getJobDesignation();
        final String location = postData.get(position).getJobLocation();
        final String qualification = postData.get(position).getQualification();
        String email = postData.get(position).getPostedbyEmail();
        final String salaryTo = postData.get(position).getSalaryTo();
        final String salaryFrom = postData.get(position).getSalaryFrom();
        final String experience = postData.get(position).getExperience();
        final String vacancy = postData.get(position).getVacancy();
        final String Country =postData.get(position).getCountry();
        final String State=postData.get(position).getState();
        final String city=postData.get(position).getCity();
        final String AboutWork=postData.get(position).getJobDescription();
        final String jobType=postData.get(position).getJobType();
        final String software=postData.get(position).getSoftwareKnowledge();
        final String Category=postData.get(position).getCategory();
        final String CompanyName=postData.get(position).getCompanyName();
        final String CompanyWebsite=postData.get(position).getCompanyWebsite();
        int AppliedUserCount=postData.get(position).getApplied_User_Count();
        final String specialisation=postData.get(position).getSpecialization();
        final String currency=postData.get(position).getCurrency();
        final String extra_spec=postData.get(position).getExtra_Specialization();
        final String FormUrl=postData.get(position).getColumn1();

        String date=postData.get(position).getJob_PostDate();
        holder.viewCandidate.setText("Response "+"("+String.valueOf(AppliedUserCount)+")");
        if(date !=null){

        holder.date.setText("Posted on :"+date.substring(0, 9));
        }
        holder.jobTitle.setText(jobtitle);
        holder.location.setText(location);
        holder.salary.setText(salaryFrom + "-" + salaryTo);
        holder.qualification.setText(qualification);
        holder.experience.setText(experience);
        holder.email.setText(email);
        holder.vacancy.setText(vacancy);
        holder.specialization.setText(specialisation);
        holder.exspec.setText(extra_spec);
        final int jobPostID = postData.get(position).getId();
        final String HRPostID = postData.get(position).getHREmployeeId();
        final String HREmail = postData.get(position).getPostedbyEmail();
        String Status = postData.get(position).getStatus();
        if (Status.equals("0")){
            holder.deactivate.setBackgroundColor(Color.WHITE);
            holder.deactivate.setTextColor(Color.BLACK);
            holder.deactivate.setText("Active");
            holder.status.setText("Inprocess");
        } else if (Status.equals("1")) {
          //  holder.deactivate.setBackgroundColor(Color.RED);

           // holder.deactivate.setTextColor(Color.WHITE);
            holder.status.setText("Active");
        }
        else if(Status.equals("3")){
            holder.status.setText("Deactivated");
            holder.status.setTextColor(Color.parseColor("#C4F44336"));
            holder.deactivate.setText("Deactivated");
        }

        holder.deactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.deactivate.getText().toString().equals("Deactivate Job")){
                Button cancel,closecancel;
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_dialog);

                dialog.setTitle("Title...");

                r1 = dialog.findViewById(R.id.radio1);
                r2 = dialog.findViewById(R.id.radio2);
                sugg=dialog.findViewById(R.id.suggestion);

                cancel=dialog.findViewById(R.id.cancel);
                closecancel=dialog.findViewById(R.id.button_close);
                Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
                r1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch(checkedId){
                            case R.id.radiob1:
                                // do operations specific to this selection candidate = 1;
                                candidate = 1;
                                break;
                            case R.id.radiob2:
                                // do operations specific to this selection
                                candidate = 0;
                                break;
                        }
                    }
                });
                r2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch(checkedId){
                            case R.id.radiob3:
                                sugg.setVisibility(View.VISIBLE);
                                flag="1";
                                break;
                            case R.id.radiob4:
                                sugg.setVisibility(View.GONE);
                                satisfied = "1";
                                break;
                        }
                    }
                });
                dialogButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if(flag.equals("1")) {
                            satisfied = sugg.getText().toString();
                        }
                        else{
                            callDeactivateAPI(jobPostID, HRPostID, HREmail,holder, candidate, satisfied);
                            dialog.dismiss();}
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                closecancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
            }
        });

        holder.removejob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button cancel,closecancel;
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_dialog);

                dialog.setTitle("Title...");

                r1 = dialog.findViewById(R.id.radio1);
                r2 = dialog.findViewById(R.id.radio2);
                sugg=dialog.findViewById(R.id.suggestion);
                cancel=dialog.findViewById(R.id.cancel);
                closecancel=dialog.findViewById(R.id.button_close);
                Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
                r1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch(checkedId){
                            case R.id.radiob1:
                                // do operations specific to this selection candidate = 1;
                                candidate = 1;
                                break;
                            case R.id.radiob2:
                                // do operations specific to this selection
                                candidate = 0;
                                break;
                        }
                    }
                });
                r2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch(checkedId){
                            case R.id.radiob3:
                               sugg.setVisibility(View.VISIBLE);
                               flag="1";
                                break;
                            case R.id.radiob4:
                                sugg.setVisibility(View.GONE);
                                satisfied = "1";
                                break;
                        }
                    }
                });
                dialogButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if(flag.equals("1")) {
                            satisfied = sugg.getText().toString();
                        }
                   else{
                        callRemovePostAPI(jobPostID, HRPostID, HREmail, candidate, satisfied,holder,position);
                        dialog.dismiss();}
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                closecancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        holder.viewCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                ViewCandidateAPI(jobPostID,HRPostID);
            }
        });
        holder.editPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, EditActivity.class);
                i.putExtra("HREmail",HREmail) ;
                i.putExtra("PostID",jobPostID);
                i.putExtra("PostedbyID",HRPostID);
                i.putExtra("location",location);
                i.putExtra("jobtitle",jobtitle);
                i.putExtra("salaryFrom",salaryFrom);
                i.putExtra("salaryTo",salaryTo);
                i.putExtra("vacancy",vacancy);
                i.putExtra("experience",experience);
                i.putExtra("qualification",qualification);
                i.putExtra("Specialisation",specialisation);
                i.putExtra("Country",Country);
                i.putExtra("State",State);
                i.putExtra("city",city);
                i.putExtra("AboutWork",AboutWork);
                i.putExtra("jobType",jobType);
                i.putExtra("software",software);
                i.putExtra("Category",Category);
                i.putExtra("CompanyName",CompanyName);
                i.putExtra("CompanyWebsite",CompanyWebsite);
                i.putExtra("Currency",currency);
                i.putExtra("extra_spec",extra_spec);
                i.putExtra("FormUrl",FormUrl);

                context.startActivity(i);

            }
        });
        holder.viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.viewless.setVisibility(View.VISIBLE);
                holder.viewmore.setVisibility(View.GONE);
                holder.frameLayout.setVisibility(View.VISIBLE);
                holder.country.setText(postData.get(position).getCountry());
                holder.state.setText(postData.get(position).getState());
                holder.city.setText(postData.get(position).getCity());
               // holder.aboutwork.setText(postData.get(position).getJobDescription());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.aboutwork.setText(Html.fromHtml(postData.get(position).getJobDescription(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    holder.aboutwork.setText(Html.fromHtml(postData.get(position).getJobDescription()));
                }
                holder.jobtype.setText(postData.get(position).getJobType());
                holder.softwareknowledge.setText(postData.get(position).getSoftwareKnowledge());
                holder.category.setText(postData.get(position).getCategory());
                holder.companyname.setText(postData.get(position).getCompanyName());
                holder.companywebsite.setText(postData.get(position).getCompanyWebsite());

            }
        });
        holder.viewless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.viewless.setVisibility(View.GONE);
                holder.frameLayout.setVisibility(View.GONE);
                holder.viewmore.setVisibility(View.VISIBLE);
            }
        });

    }

    private void ViewCandidateAPI(final int jobPostID, final String HRPostID) {
        String URL="http://api.mymakeover.club/api/MepJobs/ViewCandidate?jobpostID="+jobPostID+"&RecruiterID="+HRPostID;
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JsonParser jsonParser=new JsonParser();
                String resstring=jsonParser.parse(response).getAsString();

                Log.e("viewCandidates", "onResponse: "+resstring );
                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(resstring);
                    JSONArray viewCandidates=jsonObject.getJSONArray("viewCandidates");
                    if(viewCandidates.length()==0){
                        progressDialog.dismiss();
                        Toast.makeText(context, " No Candidate have applied for this job yet", Toast.LENGTH_SHORT).show();
                    }
                    else{  progressDialog.dismiss();
                        Intent i=new Intent(context, ShowCandidate.class);
                        i.putExtra("jobPostID",jobPostID);
                        i.putExtra("HRPostID",HRPostID);
                        context.startActivity(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                errorLogs.AppErrorLog("viewCandidate",error);
            }
        });
        requestQueue.add(stringRequest);

    }

    @Override
    public int getItemCount() {
        return postData.size();
    }

    private void callDeactivateAPI(int jobPostID, String HRPostID, String HREmail, final ViewHolder holder, int candidate, String satisfied) {
        progressDialog.show();
        String URL = "http://api.mymakeover.club/api/MepJobs/DeactivateJob?jobPostID=" + jobPostID + "&HRPostID=" + HRPostID + "&HREmail=" + HREmail+"&satisfied="+satisfied+"&candidate="+candidate;
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JsonParser jsonParser = new JsonParser();
                String resstring = jsonParser.parse(response).getAsString();
                try {
                    JSONObject jsonObject=new JSONObject(resstring);
                    String Status=jsonObject.getString("DStatus");
                    if(Status.equals("1")){
                        holder.deactivate.setBackgroundColor(Color.GREEN);
                        holder.deactivate.setText("Activate Job");
                        holder.deactivate.setTextColor(Color.WHITE);
                        holder.status.setText("Inprocess");
                        progressDialog.dismiss();
                    }progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorLogs.AppErrorLog("Deactivatejob",error);
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        requestQueue.add(stringRequest);

    }

    private void callRemovePostAPI(int jobPostID, String HRPostID, String HREmail, int candidate, String satisfied, final ViewHolder holder, final int position) {
        progressDialog.show();
        String Url = "http://api.mymakeover.club/api/MepJobs/RemoveJob?jobPostID=" + jobPostID + "&HRPostID=" + HRPostID + "&HREmail=" + HREmail + "&candidate=" + candidate+ "&satisfied=" + satisfied;
        final RequestQueue requestQueue = Volley.newRequestQueue(context);

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JsonParser jsonParser = new JsonParser();
                String resting = jsonParser.parse(response).getAsString();
                Log.e("RemovePost", "onResponse: "+resting );
                try {
                    JSONObject jsonObject=new JSONObject(resting);
                    String status=jsonObject.getString("Status");
                    if(status.equals("1")){
                        postData.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, postData.size());
                        progressDialog.dismiss();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    progressDialog.dismiss();
                    Toast.makeText(context, "No Network", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorLogs.AppErrorLog("Deletejob",error);
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        requestQueue.add(stringRequest);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView exspec,jobTitle,date, location,specialization, salary, qualification, email, experience, vacancy, status,editPost,viewmore,viewless,country,state,city,aboutwork,jobtype,softwareknowledge,category,companyname,companywebsite;
        Button deactivate, removejob, viewCandidate;
        FrameLayout frameLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            jobTitle = itemView.findViewById(R.id.jobtitle);
           // userResponse = itemView.findViewById(R.id.userresponse);
            location = itemView.findViewById(R.id.location);
            salary = itemView.findViewById(R.id.salary);
            qualification = itemView.findViewById(R.id.qualification);
            email = itemView.findViewById(R.id.email);
            experience = itemView.findViewById(R.id.experience);
            vacancy = itemView.findViewById(R.id.vacancy);
            status = itemView.findViewById(R.id.status);
            deactivate = itemView.findViewById(R.id.deactivate);
            removejob = itemView.findViewById(R.id.removejob);
            viewCandidate = itemView.findViewById(R.id.viewcandidates);
            editPost=itemView.findViewById(R.id.edit);
            viewmore=itemView.findViewById(R.id.viewmore);
            viewless=itemView.findViewById(R.id.viewless);
            frameLayout=itemView.findViewById(R.id.frame1);
            country=itemView.findViewById(R.id.country);
            state=itemView.findViewById(R.id.state);
            city=itemView.findViewById(R.id.city);
            aboutwork=itemView.findViewById(R.id.aboutwork);
            jobtype=itemView.findViewById(R.id.jobtype);
            softwareknowledge=itemView.findViewById(R.id.softwareknowledge);
            category=itemView.findViewById(R.id.category);
            companyname=itemView.findViewById(R.id.comname);
            companywebsite=itemView.findViewById(R.id.companywebsite);
            date=itemView.findViewById(R.id.date);
            specialization=itemView.findViewById(R.id.specialization);
            exspec=itemView.findViewById(R.id.exspec);

        }
    }
}
