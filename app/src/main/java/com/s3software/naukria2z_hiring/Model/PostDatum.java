package com.s3software.naukria2z_hiring.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostDatum {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("HR_EmployeeId")
    @Expose
    private String hREmployeeId;
    @SerializedName("Job_Designation")
    @Expose
    private String jobDesignation;
    @SerializedName("Job_Description")
    @Expose
    private String jobDescription;
    @SerializedName("Job_Location")
    @Expose
    private String jobLocation;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Salary_from")
    @Expose
    private String salaryFrom;
    @SerializedName("Salary_to")
    @Expose
    private String salaryTo;
    @SerializedName("Vacancy")
    @Expose
    private String vacancy;
    @SerializedName("Qualification")
    @Expose
    private String qualification;
    @SerializedName("Experience")
    @Expose
    private String experience;
    @SerializedName("Job_Type")
    @Expose
    private String jobType;
    @SerializedName("Software_Knowledge")
    @Expose
    private String softwareKnowledge;
    @SerializedName("Company_Name")
    @Expose
    private String companyName;
    @SerializedName("Company_Website")
    @Expose
    private String companyWebsite;
    @SerializedName("Postedby_email")
    @Expose
    private String postedbyEmail;
    @SerializedName("Postedby_ID")
    @Expose
    private Object postedbyID;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Applied_User_Count")
    @Expose
    private int Applied_User_Count;


    @SerializedName("Job_PostDate")
    @Expose
    private String Job_PostDate;

    @SerializedName("Specialization")
    @Expose
    private String Specialization;
    @SerializedName("Disabilities")
    @Expose
    private String Disabilities;

    @SerializedName("Currency")
    @Expose
    private String Currency;


    @SerializedName("Maximum_Experience")
    @Expose
    private String Maximum_Experience;

    @SerializedName("Extra_Specialization")
    @Expose
    private String Extra_Specialization;

    @SerializedName("Column1")
    @Expose
    private String Column1;

    public String getColumn1() {
        return Column1;
    }

    public void setColumn1(String column1) {
        Column1 = column1;
    }

    public String getExtra_Specialization() {
        return Extra_Specialization;
    }

    public void setExtra_Specialization(String extra_Specialization) {
        Extra_Specialization = extra_Specialization;
    }

    public String getMaximum_Experience() {
        return Maximum_Experience;
    }

    public void setMaximum_Experience(String maximum_Experience) {
        Maximum_Experience = maximum_Experience;
    }

    public String getMinimum_Experience() {
        return Minimum_Experience;
    }

    public void setMinimum_Experience(String minimum_Experience) {
        Minimum_Experience = minimum_Experience;
    }

    @SerializedName("Minimum_Experience")
    @Expose
    private String Minimum_Experience;

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public String getDisabilities() {
        return Disabilities;
    }

    public void setDisabilities(String disabilities) {
        Disabilities = disabilities;
    }

    public String getJob_PostDate() {
        return Job_PostDate;
    }

    public void setJob_PostDate(String job_PostDate) {
        Job_PostDate = job_PostDate;
    }

    public int getApplied_User_Count() {
        return Applied_User_Count;
    }

    public void setApplied_User_Count(int applied_User_Count) {
        Applied_User_Count = applied_User_Count;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @SerializedName("Status")
    @Expose
    private String Status;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHREmployeeId() {
        return hREmployeeId;
    }

    public void setHREmployeeId(String hREmployeeId) {
        this.hREmployeeId = hREmployeeId;
    }

    public String getJobDesignation() {
        return jobDesignation;
    }

    public void setJobDesignation(String jobDesignation) {
        this.jobDesignation = jobDesignation;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSalaryFrom() {
        return salaryFrom;
    }

    public void setSalaryFrom(String salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public String getSalaryTo() {
        return salaryTo;
    }

    public void setSalaryTo(String salaryTo) {
        this.salaryTo = salaryTo;
    }

    public String getVacancy() {
        return vacancy;
    }

    public void setVacancy(String vacancy) {
        this.vacancy = vacancy;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getSoftwareKnowledge() {
        return softwareKnowledge;
    }

    public void setSoftwareKnowledge(String softwareKnowledge) {
        this.softwareKnowledge = softwareKnowledge;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getPostedbyEmail() {
        return postedbyEmail;
    }

    public void setPostedbyEmail(String postedbyEmail) {
        this.postedbyEmail = postedbyEmail;
    }

    public Object getPostedbyID() {
        return postedbyID;
    }

    public void setPostedbyID(Object postedbyID) {
        this.postedbyID = postedbyID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
