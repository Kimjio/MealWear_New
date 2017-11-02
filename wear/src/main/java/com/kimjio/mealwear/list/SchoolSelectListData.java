package com.kimjio.mealwear.list;

import com.kimjio.mealwear.school.SchoolListData;

/**
 * Created by kimji on 2017-10-15.
 */

public class SchoolSelectListData {
    private String countryUrl;
    private String schoolName;
    private String zipAddress;
    private String orgCode;
    private String schulKndScCode;
    private String schulCrseScCode;

    public String getCountryUrl() {
        return countryUrl;
    }

    public void setCountryUrl(String countryUrl) {
        this.countryUrl = countryUrl;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getZipAddress() {
        return zipAddress;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public String getSchulKndScCode() {
        return schulKndScCode;
    }

    public String getSchulCrseScCode() {
        return schulCrseScCode;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public void setZipAddress(String zipAddress) {
        this.zipAddress = zipAddress;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public void setSchulKndScCode(String schulKndScCode) {
        this.schulKndScCode = schulKndScCode;
    }

    public void setSchulCrseScCode(String schulCrseScCode) {
        this.schulCrseScCode = schulCrseScCode;
    }
}
