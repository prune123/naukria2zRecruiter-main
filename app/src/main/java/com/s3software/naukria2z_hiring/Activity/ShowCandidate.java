package com.s3software.naukria2z_hiring.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.s3software.naukria2z_hiring.Adapter.ShowCandidateAdapter;
import com.s3software.naukria2z_hiring.Model.CandidatesModel;
import com.s3software.naukria2z_hiring.Model.ViewCandidate;
import com.s3software.naukria2z_hiring.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ShowCandidate extends AppCompatActivity {
RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_candidate);
        recyclerView=findViewById(R.id.recyclerview);
        Intent intent=getIntent();

        int jobPostID=Integer.parseInt(getIntent().getExtras().get("jobPostID").toString());
        String HRPostID=intent.getStringExtra("HRPostID");
        ViewCandidateAPI( jobPostID,  HRPostID);

    }

    private void callAdapter(ShowCandidate showCandidate, ArrayList<ViewCandidate> datumPost) {

        ShowCandidateAdapter showPostAdapter = new ShowCandidateAdapter(showCandidate, datumPost);
        recyclerView.setAdapter(showPostAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
    }

    private void ViewCandidateAPI(int jobPostID, String HRPostID) {
        String URL="http://api.mymakeover.club/api/MepJobs/ViewCandidate?jobpostID="+jobPostID+"&RecruiterID="+HRPostID;
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JsonParser jsonParser=new JsonParser();
                String resstring=jsonParser.parse(response).getAsString();

                Log.e("viewCandidates", "onResponse: "+resstring );

                try {
                    JSONObject jsonObject=new JSONObject(resstring);
                    String viewCandidates=jsonObject.getString("viewCandidates");
                    if(!(viewCandidates.equals("null"))){

                        Gson gson=new Gson();
                        CandidatesModel candidatesModel = gson.fromJson(resstring.toString(), CandidatesModel.class);

                        ArrayList<ViewCandidate> datumPost = new ArrayList<>();
                        for (int i = 0; i < candidatesModel.getViewCandidates().size(); i++) {
                            datumPost.add(candidatesModel.getViewCandidates().get(i));
                        }
                        callAdapter(ShowCandidate.this,datumPost);
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
        requestQueue.add(stringRequest);

    }

}
