package com.example.mapapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Hazard {


    @SerializedName("hz_id")
@Expose
private String hzId;
@SerializedName("hz_location")
@Expose
private String hzLocation;
@SerializedName("hz_state")
@Expose
private String hzState;
@SerializedName("hz_repname")
@Expose
private String hzRepname;
@SerializedName("hz_type")
@Expose
private String hzType;
@SerializedName("hz_desc")
@Expose
private String hzDesc;
@SerializedName("hz_time")
@Expose
private String hzTime;
@SerializedName("hz_date")
@Expose
private String hzDate;
@SerializedName("hz_lat")
@Expose
private String hzLat;
@SerializedName("hz_lng")
@Expose
private String hzLng;
@SerializedName("hz_news")
@Expose
private String hzNews;

/**
 * No args constructor for use in serialization
 *
 */
public Hazard() {
        }

/**
 *
 * @param hzState
 * @param hzLng
 * @param hzNews
 * @param hzRepname
 * @param hzTime
 * @param hzLocation
 * @param hzType
 * @param hzId
 * @param hzDesc
 * @param hzDate
 * @param hzLat
 */
public Hazard(String hzId, String hzLocation, String hzState, String hzRepname, String hzType, String hzDesc, String hzTime, String hzDate, String hzLat, String hzLng, String hzNews) {
        super();
        this.hzId = hzId;
        this.hzLocation = hzLocation;
        this.hzState = hzState;
        this.hzRepname = hzRepname;
        this.hzType = hzType;
        this.hzDesc = hzDesc;
        this.hzTime = hzTime;
        this.hzDate = hzDate;
        this.hzLat = hzLat;
        this.hzLng = hzLng;
        this.hzNews = hzNews;
        }

public String getHzId() {
        return hzId;
        }

public void setHzId(String hzId) {
        this.hzId = hzId;
        }

public String getHzLocation() {
        return hzLocation;
        }

public void setHzLocation(String hzLocation) {
        this.hzLocation = hzLocation;
        }

public String getHzState() {
        return hzState;
        }

public void setHzState(String hzState) {
        this.hzState = hzState;
        }

public String getHzRepname() {
        return hzRepname;
        }

public void setHzRepname(String hzRepname) {
        this.hzRepname = hzRepname;
        }

public String getHzType() {
        return hzType;
        }

public void setHzType(String hzType) {
        this.hzType = hzType;
        }

public String getHzDesc() {
        return hzDesc;
        }

public void setHzDesc(String hzDesc) {
        this.hzDesc = hzDesc;
        }

public String getHzTime() {
        return hzTime;
        }

public void setHzTime(String hzTime) {
        this.hzTime = hzTime;
        }

public String getHzDate() {
        return hzDate;
        }

public void setHzDate(String hzDate) {
        this.hzDate = hzDate;
        }

public String getHzLat() {
        return hzLat;
        }

public void setHzLat(String hzLat) {
        this.hzLat = hzLat;
        }

public String getHzLng() {
        return hzLng;
        }

public void setHzLng(String hzLng) {
        this.hzLng = hzLng;
        }

public String getHzNews() {
        return hzNews;
        }

public void setHzNews(String hzNews) {
        this.hzNews = hzNews;
        }

}