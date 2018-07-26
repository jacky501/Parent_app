package org.atctech.parent.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.atctech.parent.ApiRequest.ApiRequest;
import org.atctech.parent.R;
import org.atctech.parent.model.ClassInformation;
import org.atctech.parent.preferences.Session;
import org.atctech.parent.utils.ConstantValue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeTableFragment extends Fragment {

    ImageView routineImageView;
    Session session;
    ApiRequest service;
    ClassInformation classInformation;

    public TimeTableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_table, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Class Routine");

        session = Session.getInstance(getContext().getSharedPreferences("prefs", MODE_PRIVATE));

        routineImageView = view.findViewById(R.id.routineImage);

        session = Session.getInstance(getActivity().getSharedPreferences("prefs",getContext().MODE_PRIVATE));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiRequest.class);

        String classId = session.getUser().getClass_id();

        Call<ClassInformation> classInformationCall = service.allClassesInformation(classId);

        classInformationCall.enqueue(new Callback<ClassInformation>() {
            @Override
            public void onResponse(Call<ClassInformation> call, Response<ClassInformation> response) {

                if (response.isSuccessful())
                {

                   classInformation  = response.body();

                    if (classInformation.getRoutine().equalsIgnoreCase("") && classInformation.getRoutine().isEmpty())
                    {
                        Picasso.with(getContext()).load(R.drawable.placeholder).into(routineImageView);
                    }else
                    {
                        Picasso.with(getContext()).load(ConstantValue.IMAGE_URL+classInformation.getRoutine()).placeholder(R.drawable.placeholder).into(routineImageView);

                    }
                }

            }

            @Override
            public void onFailure(Call<ClassInformation> call, Throwable t) {

            }
        });

        
        routineImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog localDialog = new Dialog(getContext());
                localDialog.requestWindowFeature(1);
                localDialog.setContentView(R.layout.dialog_book_img);
                localDialog.getWindow().setLayout(-1, -1);
                localDialog.show();
                ImageView localImageView1 = localDialog.findViewById(R.id.iv_dialog_img);
                ImageView localImageView2 = localDialog.findViewById(R.id.iv_dialog_cancle);


                if (classInformation.getRoutine().equalsIgnoreCase("") && classInformation.getRoutine().isEmpty())
                {
                    Picasso.with(getContext()).load(R.drawable.placeholder).into(localImageView1);
                }else
                {
                    Picasso.with(getContext()).load(ConstantValue.IMAGE_URL+classInformation.getRoutine()).placeholder(R.drawable.placeholder).into(localImageView1);

                }

                localImageView2.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View paramAnonymousView)
                    {
                        localDialog.dismiss();
                    }
                });
            }
        });

        return view;
    }


}
