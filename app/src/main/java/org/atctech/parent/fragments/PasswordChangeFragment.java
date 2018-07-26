package org.atctech.parent.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.atctech.parent.ApiRequest.ApiRequest;
import org.atctech.parent.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordChangeFragment extends Fragment {

    EditText newPassword,confirmPassword;
    ApiRequest apiRequest;
    Button submitBtn;
    AwesomeValidation validation;

    public PasswordChangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_password_change, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Change Password");


        newPassword = view.findViewById(R.id.newPassword);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        submitBtn = view.findViewById(R.id.changePasswordSubmit);

        validation = new AwesomeValidation(ValidationStyle.COLORATION);
        validation.setColor(R.color.textInputErrorColor);


        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(getString(R.string.base_url_api)).
                addConverterFactory(GsonConverterFactory.create()).
                build();

        apiRequest = retrofit.create(ApiRequest.class);


            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    validation.clear();
                    if (validation.validate()) {

                        String password = newPassword.getText().toString();
                        String conPassword = confirmPassword.getText().toString();

                        if(TextUtils.isEmpty(password)) {
                            newPassword.setError("Field Must Not Empty");
                            return;
                        }
                        if(TextUtils.isEmpty(conPassword)) {
                            confirmPassword.setError("Field Must Not Empty");
                            return;
                        }

                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);

                        String id = sharedPreferences.getString("UID", null);

                        Call<ResponseBody> responseBodyCall = apiRequest.updatePassword(id, password);

                        responseBodyCall.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    CustomToastGreen();
                                    HomeFragment homeFragment = new HomeFragment();
                                    getFragmentManager().beginTransaction().replace(R.id.main_fragment, homeFragment).commitAllowingStateLoss();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                }
            });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupRules();

    }

    public void CustomToastGreen()
    {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast_layout,null);

        Toast toast = new Toast(getContext());
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP,0,50);
        toast.show();
    }

    public void setupRules()
    {
        validation.addValidation(getActivity(),R.id.newPassword, "[a-zA-Z0-9]{4,}",R.string.errPassword);
        validation.addValidation(getActivity(),R.id.confirmPassword, R.id.newPassword ,R.string.errConPassword);
    }
}
