package com.s3software.naukria2z_hiring.RetrofitAPICall;

import androidx.annotation.Nullable;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiConfig {



    @Multipart
    @POST("api/MepJobs/UpdateRecruiterProfileImage")
    Call<ResponseBody> uploadProfileImage(

            @Part("EmployeeId") RequestBody employeeID,
            @Nullable @Part MultipartBody.Part file);
}


