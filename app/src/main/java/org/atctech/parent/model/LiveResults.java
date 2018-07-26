package org.atctech.parent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jacky on 3/13/2018.
 */

public class LiveResults {

    @SerializedName("file_name")
    @Expose
    private String file_name;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("size")
    @Expose
    private int size;
    @SerializedName("date")
    @Expose
    private String date;


    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
