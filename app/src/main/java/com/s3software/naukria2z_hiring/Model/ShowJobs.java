package com.s3software.naukria2z_hiring.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowJobs {

    @SerializedName("PostStatus")
    @Expose
    private Boolean postStatus;
    @SerializedName("PostData")
    @Expose
    private List<PostDatum> postData = null;

    public Boolean getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(Boolean postStatus) {
        this.postStatus = postStatus;
    }

    public List<PostDatum> getPostData() {
        return postData;
    }

    public void setPostData(List<PostDatum> postData) {
        this.postData = postData;
    }

}
