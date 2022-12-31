package com.s3software.naukria2z_hiring.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ViewCandidate {

    @SerializedName("EmployeeId")
    @Expose
    private String employeeId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("User_Email")
    @Expose
    private String userEmail;
    @SerializedName("Mobile_No")
    @Expose
    private String mobileNo;
    @SerializedName("Alternate_MobileNo")
    @Expose
    private String alternateMobileNo;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("User_Country")
    @Expose
    private String userCountry;
    @SerializedName("User_State")
    @Expose
    private String userState;
    @SerializedName("User_City")
    @Expose
    private String userCity;
    @SerializedName("User_Pincode")
    @Expose
    private String userPincode;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Qualification")
    @Expose
    private String qualification;
    @SerializedName("User_Industry")
    @Expose
    private String userIndustry;
    @SerializedName("Experience")
    @Expose
    private String experience;
    @SerializedName("Salary_Expected")
    @Expose
    private String salaryExpected;
    @SerializedName("User_Designation")
    @Expose
    private String userDesignation;
    @SerializedName("User_CompanyName")
    @Expose
    private String userCompanyName;
    @SerializedName("User_CompanyLocation")
    @Expose
    private String userCompanyLocation;
    @SerializedName("Time_Period")
    @Expose
    private String timePeriod;
    @SerializedName("Salary")
    @Expose
    private String salary;
    @SerializedName("Job_Type")
    @Expose
    private String jobType;
    @SerializedName("User_AboutWork")
    @Expose
    private String userAboutWork;
    @SerializedName("Resume")
    @Expose
    private String resume;
    @SerializedName("key_Flag")
    @Expose
    private String keyFlag;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAlternateMobileNo() {
        return alternateMobileNo;
    }

    public void setAlternateMobileNo(String alternateMobileNo) {
        this.alternateMobileNo = alternateMobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserPincode() {
        return userPincode;
    }

    public void setUserPincode(String userPincode) {
        this.userPincode = userPincode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getUserIndustry() {
        return userIndustry;
    }

    public void setUserIndustry(String userIndustry) {
        this.userIndustry = userIndustry;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSalaryExpected() {
        return salaryExpected;
    }

    public void setSalaryExpected(String salaryExpected) {
        this.salaryExpected = salaryExpected;
    }

    public String getUserDesignation() {
        return userDesignation;
    }

    public void setUserDesignation(String userDesignation) {
        this.userDesignation = userDesignation;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getUserCompanyLocation() {
        return userCompanyLocation;
    }

    public void setUserCompanyLocation(String userCompanyLocation) {
        this.userCompanyLocation = userCompanyLocation;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getUserAboutWork() {
        return userAboutWork;
    }

    public void setUserAboutWork(String userAboutWork) {
        this.userAboutWork = userAboutWork;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getKeyFlag() {
        return keyFlag;
    }

    public void setKeyFlag(String keyFlag) {
        this.keyFlag = keyFlag;
    }

}