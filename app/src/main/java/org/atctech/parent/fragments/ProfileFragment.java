package org.atctech.parent.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.atctech.parent.ApiRequest.ApiRequest;
import org.atctech.parent.R;
import org.atctech.parent.customs.CircularImageView;
import org.atctech.parent.model.ClassInformation;
import org.atctech.parent.preferences.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    Session session;
    ApiRequest service;

    CircularImageView profileImage;
    TextView fname,uid,className,fatName,motName,foccu,moccu,paaddress,blood,sex,details,bdate,gName,gPhone,section,shift,group;

    public ProfileFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Profile");

        session = Session.getInstance(getActivity().getSharedPreferences("prefs",getContext().MODE_PRIVATE));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiRequest.class);

        String classId = session.getUser().getClass_id();



        profileImage = v.findViewById(R.id.top_image);
        fname = v.findViewById(R.id.fullname);
        uid = v.findViewById(R.id.studentID);
        className = v.findViewById(R.id.className);
        fatName = v.findViewById(R.id.fName);
        motName = v.findViewById(R.id.mName);
        foccu = v.findViewById(R.id.fPro);
        moccu = v.findViewById(R.id.mPro);
        paaddress = v.findViewById(R.id.fullAddress);
        blood = v.findViewById(R.id.bloodGroup);
        sex = v.findViewById(R.id.sSex);
        details = v.findViewById(R.id.sDetails);
        bdate = v.findViewById(R.id.bDate);
        gName = v.findViewById(R.id.gName);
        gPhone = v.findViewById(R.id.gPhone);

        section = v.findViewById(R.id.secName);
        shift = v.findViewById(R.id.cShift);
        group = v.findViewById(R.id.groupName);

        if (!session.getUser().getFile().isEmpty() && session.getUser().getFile().equalsIgnoreCase("")) {
            Picasso.with(getContext()).load(session.getUser().getFile()).placeholder(R.drawable.placeholder).into(profileImage);
        }else
        {
            Picasso.with(getContext()).load(R.drawable.profile).into(profileImage);
        }
        fname.setText(session.getUser().getFname()+" "+session.getUser().getLname());
        uid.setText(session.getUser().getU_id());
        className.setText(session.getUser().getClassName());
        fatName.setText(session.getUser().getFatname());
        motName.setText(session.getUser().getMotname());
        foccu.setText(session.getUser().getFoccu());
        moccu.setText(session.getUser().getMoccu());
        paaddress.setText(session.getUser().getPaddress()+","+session.getUser().getParaddress());
        blood.setText(session.getUser().getBlood());
        sex.setText(session.getUser().getSex());
        details.setText(session.getUser().getDetails());
        bdate.setText(session.getUser().getBdate());
        gName.setText(session.getUser().getGname());
        gPhone.setText(session.getUser().getGphone());


        Call<ClassInformation> classInformationCall = service.allClassesInformation(classId);

        classInformationCall.enqueue(new Callback<ClassInformation>() {
            @Override
            public void onResponse(Call<ClassInformation> call, Response<ClassInformation> response) {

                if (response.isSuccessful())
                {

                    ClassInformation classInformation = response.body();

                    if (classInformation!=null) {
                        section.setText(classInformation.getSection());
                        shift.setText(classInformation.getShift());
                        group.setText(classInformation.getGp());

                    }

                }

            }

            @Override
            public void onFailure(Call<ClassInformation> call, Throwable t) {

            }
        });


       return v;
    }

}
