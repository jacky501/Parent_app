package org.atctech.parent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jacky on 3/19/2018.
 */

public class CoursesTeacher {

    @SerializedName("u_id")
    @Expose
    private String u_id;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("lname")
    @Expose
    private String  lname;
    @SerializedName("email")
    @Expose
    private String  email;
    @SerializedName("phone")
    @Expose
    private String  phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("details")
    @Expose
    private String  details;
    @SerializedName("bdate")
    @Expose
    private String bdate;
    @SerializedName("blood")
    @Expose
    private String blood;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("pro_pic")
    @Expose
    private String pro_pic;
    @SerializedName("paddress")
    @Expose
    private String paddress;
    @SerializedName("name")
    @Expose
    private String   name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("class")
    @Expose
    private String  class_id;
    @SerializedName("teacher")
    @Expose
    private String  teacher;
    @SerializedName("sub1")
    @Expose
    private String  sub1;

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPro_pic() {
        return pro_pic;
    }

    public void setPro_pic(String pro_pic) {
        this.pro_pic = pro_pic;
    }

    public String getPaddress() {
        return paddress;
    }

    public void setPaddress(String paddress) {
        this.paddress = paddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSub1() {
        return sub1;
    }

    public void setSub1(String sub1) {
        this.sub1 = sub1;
    }
}


