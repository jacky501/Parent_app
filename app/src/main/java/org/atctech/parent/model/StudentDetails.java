package org.atctech.parent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jacky on 3/3/2018.
 */

public class StudentDetails {

    @SerializedName("fname")
    @Expose
    private String fname;

    @SerializedName("lname")
    @Expose
    private String lname;

    @SerializedName("u_id")
    @Expose
    private String u_id;

    @SerializedName("class")
    @Expose
    private String class_id;

    @SerializedName("fatname")
    @Expose
    private String fatname;

    @SerializedName("motname")
    @Expose
    private String motname;

    @SerializedName("foccu")
    @Expose
    private String foccu;

    @SerializedName("moccu")
    @Expose
    private String moccu;

    @SerializedName("paddress")
    @Expose
    private String paddress;

    @SerializedName("paraddress")
    @Expose
    private String paraddress;

    @SerializedName("blood")
    @Expose
    private String blood;

    @SerializedName("sex")
    @Expose
    private String sex;

    @SerializedName("details")
    @Expose
    private String details;

    @SerializedName("bdate")
    @Expose
    private String bdate;

    @SerializedName("gname")
    @Expose
    private String gname;

    @SerializedName("gphone")
    @Expose
    private String gphone;

    @SerializedName("file")
    @Expose
    private String file;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("className")
    @Expose
    private String className;


    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getU_id() {
        return u_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public String getFatname() {
        return fatname;
    }

    public String getMotname() {
        return motname;
    }

    public String getFoccu() {
        return foccu;
    }

    public String getMoccu() {
        return moccu;
    }

    public String getPaddress() {
        return paddress;
    }

    public String getParaddress() {
        return paraddress;
    }

    public String getBlood() {
        return blood;
    }

    public String getSex() {
        return sex;
    }

    public String getDetails() {
        return details;
    }

    public String getBdate() {
        return bdate;
    }

    public String getGname() {
        return gname;
    }

    public String getGphone() {
        return gphone;
    }

    public String getFile() {
        return file;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getClassName() {
        return className;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public void setFatname(String fatname) {
        this.fatname = fatname;
    }

    public void setMotname(String motname) {
        this.motname = motname;
    }

    public void setFoccu(String foccu) {
        this.foccu = foccu;
    }

    public void setMoccu(String moccu) {
        this.moccu = moccu;
    }

    public void setPaddress(String paddress) {
        this.paddress = paddress;
    }

    public void setParaddress(String paraddress) {
        this.paraddress = paraddress;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public void setGphone(String gphone) {
        this.gphone = gphone;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
