package org.atctech.parent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jacky on 3/21/2018.
 */

public class ClassInformation {



    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("shift")
    @Expose
    private String shift;
    @SerializedName("gp")
    @Expose
    private String gp;
    @SerializedName("routine")
    @Expose
    private String routine;





    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getGp() {
        return gp;
    }

    public void setGp(String gp) {
        this.gp = gp;
    }

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }
}
