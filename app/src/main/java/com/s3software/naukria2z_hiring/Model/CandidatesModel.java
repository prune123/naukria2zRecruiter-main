package com.s3software.naukria2z_hiring.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CandidatesModel {

    @SerializedName("viewCandidates")
    @Expose
    private List<ViewCandidate> viewCandidates = null;

    public List<ViewCandidate> getViewCandidates() {
        return viewCandidates;
    }

    public void setViewCandidates(List<ViewCandidate> viewCandidates) {
        this.viewCandidates = viewCandidates;
    }

}
