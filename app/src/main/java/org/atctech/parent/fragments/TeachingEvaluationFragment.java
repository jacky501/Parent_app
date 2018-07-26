package org.atctech.parent.fragments;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.atctech.parent.ApiRequest.ApiRequest;
import org.atctech.parent.R;
import org.atctech.parent.model.CoursesTeacher;
import org.atctech.parent.preferences.Session;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeachingEvaluationFragment extends Fragment {

    private Spinner spinner;
    private ApiRequest service;
    private Session session;
    private RadioGroup radioGroupTeaching,radioGroupListening,radioGroupBehaviour,radioGroupWriting,radioGroupAttaining;
    private RadioButton radioButtonTeaching,radioButtonListening,radioButtonBehaviour,radioButtonWriting,radioButtonAttaining;
    private Button submit;
    private EditText comment;
    List<CoursesTeacher> coursesTeachers;

    public TeachingEvaluationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =inflater.inflate(R.layout.fragment_teaching_evaluation, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Teaching Evaluation");



        return view;
    }

}
