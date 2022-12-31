package com.s3software.naukria2z_hiring;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ErrorLogs {
   private Context mContext;

    // constructor
    public ErrorLogs(Context context){
        this.mContext = context;
    }


    public  void  AppErrorLog(  String pagename, VolleyError message)
    {  SharedPreferences userdata = mContext.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String  EmployeeID = userdata.getString("EmployeeID", "");
        String Name = userdata.getString("First_Name", "");
        String User_Email = userdata.getString("User_Email", "");
        String number=userdata.getString("Phone_No", "");
        String appname="jobseekerApp";
        String Url="http://api.mymakeover.club/api/MepJobs/AppErrorLog?name="+Name+"&userid="+EmployeeID+"&email="+User_Email+"&number="+number+"&appname="+appname+"&pagename="+pagename+"&message="+message;
        RequestQueue requestQueue= Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", "onResponse: "+response );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });requestQueue.add(stringRequest);
    }
}
