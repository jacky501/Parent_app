package org.atctech.parent.preferences;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import org.atctech.parent.model.ClassInformation;
import org.atctech.parent.model.StudentDetails;

/**
 * Created by Jacky on 3/3/2018.
 */

public class Session {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private static Session INSTANCE = null;

    private Session(SharedPreferences prefs) {
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public static synchronized Session getInstance(SharedPreferences prefs)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new Session(prefs);
        }
        return INSTANCE;
    }



    public void setLoggedIn(boolean isLogged)
    {
        editor.putBoolean("loggedIn",isLogged);
        editor.commit();
    }

    public boolean isLoggedIn()
    {
        return prefs.getBoolean("loggedIn",false);
    }

    public void saveUser(StudentDetails studentDetails)
    {
        editor.putString("F_NAME",studentDetails.getFname()).apply();
        editor.putString("L_NAME",studentDetails.getLname()).apply();
        editor.putString("U_ID",studentDetails.getU_id()).apply();
        editor.putString("CLASS_ID",studentDetails.getClass_id()).apply();
        editor.putString("FAT_NAME",studentDetails.getFatname()).apply();
        editor.putString("MOT_NAME",studentDetails.getMotname()).apply();
        editor.putString("FOCCU",studentDetails.getFoccu()).apply();
        editor.putString("MOCCU",studentDetails.getMoccu()).apply();
        editor.putString("PAADDRESS",studentDetails.getPaddress()).apply();
        editor.putString("PARADDRESS",studentDetails.getParaddress()).apply();
        editor.putString("BLOOD",studentDetails.getBlood()).apply();
        editor.putString("SEX",studentDetails.getSex()).apply();
        editor.putString("DETAILS",studentDetails.getDetails()).apply();
        editor.putString("BDATE",studentDetails.getBdate()).apply();
        editor.putString("GNAME",studentDetails.getGname()).apply();
        editor.putString("GPHONE",studentDetails.getGphone()).apply();
        editor.putString("FILE",studentDetails.getFile()).apply();
        editor.putString("LATITUDE",studentDetails.getLatitude()).apply();
        editor.putString("LONGITUDE",studentDetails.getLongitude()).apply();
        editor.putString("CLASS",studentDetails.getClassName()).apply();
    }

    public StudentDetails getUser()
    {
       StudentDetails studentDetails = new StudentDetails();
        studentDetails.setFname(prefs.getString("F_NAME",null));
        studentDetails.setLname(prefs.getString("L_NAME",null));
        studentDetails.setU_id(prefs.getString("U_ID",null));
        studentDetails.setClass_id(prefs.getString("CLASS_ID",null));
        studentDetails.setFatname(prefs.getString("FAT_NAME",null));
        studentDetails.setMotname(prefs.getString("MOT_NAME",null));
        studentDetails.setFoccu(prefs.getString("FOCCU",null));
        studentDetails.setMoccu(prefs.getString("MOCCU",null));
        studentDetails.setPaddress(prefs.getString("PAADDRESS",null));
        studentDetails.setParaddress(prefs.getString("PARADDRESS",null));
        studentDetails.setBlood(prefs.getString("BLOOD",null));
        studentDetails.setSex(prefs.getString("SEX",null));
        studentDetails.setDetails(prefs.getString("DETAILS",null));
        studentDetails.setBdate(prefs.getString("BDATE",null));
        studentDetails.setGname(prefs.getString("GNAME",null));
        studentDetails.setGphone(prefs.getString("GPHONE",null));
        studentDetails.setFile(prefs.getString("FILE",null));
        studentDetails.setLatitude(prefs.getString("LATITUDE",null));
        studentDetails.setLongitude(prefs.getString("LONGITUDE",null));
        studentDetails.setClassName(prefs.getString("CLASS",null));


        return studentDetails;
    }
    public void deleteUser()
    {
        editor.remove("F_NAME").apply();
        editor.remove("L_NAME").apply();
        editor.remove("U_ID").apply();
        editor.remove("CLASS_ID").apply();
        editor.remove("FAT_NAME").apply();
        editor.remove("MOT_NAME").apply();
        editor.remove("FOCCU").apply();
        editor.remove("MOCCU").apply();
        editor.remove("PAADDRESS").apply();
        editor.remove("PARADDRESS").apply();
        editor.remove("BLOOD").apply();
        editor.remove("SEX").apply();
        editor.remove("DETAILS").apply();
        editor.remove("BDATE").apply();
        editor.remove("GNAME").apply();
        editor.remove("GPHONE").apply();
        editor.remove("FILE").apply();
        editor.remove("LATITUDE").apply();
        editor.remove("LONGITUDE").apply();
        editor.remove("CLASS").apply();
    }


    public void saveClasses(@Nullable ClassInformation classInformation)
    {

        editor.putString("SECTION_NAME",classInformation.getSection()).apply();
        editor.putString("SHIFT_CLASS",classInformation.getShift()).apply();
        editor.putString("CLASS_GROUP",classInformation.getGp()).apply();
        editor.putString("CLASS_ROUTINE",classInformation.getRoutine()).apply();

    }

    public ClassInformation getAllClasses()
    {
        ClassInformation classInformation = new ClassInformation();

        classInformation.setSection(prefs.getString("SECTION_NAME",null));
        classInformation.setShift(prefs.getString("SHIFT_CLASS",null));
        classInformation.setGp(prefs.getString("CLASS_GROUP",null));
        classInformation.setRoutine(prefs.getString("CLASS_ROUTINE",null));

        return classInformation;
    }

    public void deleteClasses() {
        editor.remove("SECTION_NAME").apply();
        editor.remove("SHIFT_CLASS").apply();
        editor.remove("CLASS_GROUP").apply();
        editor.remove("CLASS_ROUTINE").apply();
    }

    public void setFirstTimeLaunch(boolean isFirstTimeLaunch) {
        editor.putBoolean("IS_FIRST_TIME_LAUNCH", isFirstTimeLaunch);
        editor.apply();
    }

    public boolean isFirstTimeLaunch() {
        return prefs.getBoolean("IS_FIRST_TIME_LAUNCH", true);
    }
}
