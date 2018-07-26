package org.atctech.parent.ApiRequest;

import org.atctech.parent.model.ClassInformation;
import org.atctech.parent.model.CoursesTeacher;
import org.atctech.parent.model.GetLocation;
import org.atctech.parent.model.LiveResults;
import org.atctech.parent.model.MessagesAll;
import org.atctech.parent.model.StudentDetails;
import org.atctech.parent.model.TeacherDetails;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Jacky on 2/28/2018.
 */

public interface ApiRequest {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> login(@Field("phone") String admin,@Field("password") String password);

    @FormUrlEncoded
    @POST("change_password.php")
    Call<ResponseBody> updatePassword(@Field("gphone") String id,@Field("password") String password);

    @FormUrlEncoded
    @POST("student_profile.php")
    Call<StudentDetails> getStudentDetails(@Field("phone") String id);

    @GET("all_teacher.php")
    Call<List<TeacherDetails>> getAllTeacher();

    @FormUrlEncoded
    @POST("student_results.php")
    Call<List<LiveResults>> getResult(@Field("id") String id);

    @FormUrlEncoded
    @POST("all_course_teachers.php")
    Call<List<CoursesTeacher>> getAllCourseTeacher(@Field("class") String classID);


    @FormUrlEncoded
    @POST("student_classes_subject.php")
    Call<ClassInformation> allClassesInformation(@Field("id") String id);


    @FormUrlEncoded
    @POST("teacher_student_chat.php")
    Call<List<MessagesAll>> getAllMessages(@Field("student_id") String studentId);


    @FormUrlEncoded
    @POST("getlocations.php")
    Call<GetLocation> getLoacation(@Field("u_id") String u_id);

    @FormUrlEncoded
    @POST("set_geofence.php")
    Call<ResponseBody> updateGeofence(@Field("latitude") String latitude,@Field("longitude") String longitude,
                                      @Field("radius") String radius,@Field("zoom") String zoom,
                                      @Field("u_id") String id);

}
