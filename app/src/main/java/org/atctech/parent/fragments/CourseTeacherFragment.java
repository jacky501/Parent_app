package org.atctech.parent.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.atctech.parent.ApiRequest.ApiRequest;
import org.atctech.parent.R;
import org.atctech.parent.adapter.CourseTeacherAdapter;
import org.atctech.parent.model.CoursesTeacher;
import org.atctech.parent.preferences.Session;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseTeacherFragment extends Fragment {

    Session session;
    ApiRequest service;
    RecyclerView recyclerView;

    public CourseTeacherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_teacher, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Course Teacher");

        session = Session.getInstance(getActivity().getSharedPreferences("prefs", getContext().MODE_PRIVATE));
        String classID = session.getUser().getClass_id();
        recyclerView = view.findViewById(R.id.courseTeacherRecyclerView);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiRequest.class);

        Call<List<CoursesTeacher>> listCall = service.getAllCourseTeacher(classID);

        listCall.enqueue(new Callback<List<CoursesTeacher>>() {
            @Override
            public void onResponse(Call<List<CoursesTeacher>> call, Response<List<CoursesTeacher>> response) {
                if (response.isSuccessful())
                {
                    List<CoursesTeacher> coursesTeachers = response.body();
                    CourseTeacherAdapter adapter = new CourseTeacherAdapter(getContext(),coursesTeachers);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CoursesTeacher>> call, Throwable t) {

            }
        });

        return view;
    }

}
