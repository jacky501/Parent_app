package org.atctech.parent;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import org.atctech.parent.ApiRequest.ApiRequest;
import org.atctech.parent.preferences.Session;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    EditText login,loginPassword;
    Button signIn;
    ApiRequest service;
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signIn = findViewById(R.id.submit);
        login= findViewById(R.id.loginID);
        loginPassword = findViewById(R.id.loginPassword);
        session = Session.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        session.setFirstTimeLaunch(false);

        if (session.isLoggedIn())
        {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiRequest.class);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String admin = login.getText().toString();
                final String password = loginPassword.getText().toString();

                if(TextUtils.isEmpty(admin)) {
                    login.setError("Field Must Not Empty");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    loginPassword.setError("Field Must Not Empty");
                    return;
                }

                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setTitle("Signing in");
                progressDialog.setMessage("Please wait....");
                progressDialog.show();

                Call<ResponseBody> loginCall = service.login(admin,password);
                loginCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful())
                        {
                            progressDialog.dismiss();
                            session.setLoggedIn(true);
                            SharedPreferences prefs = getApplicationContext().getSharedPreferences("PREFS",
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor ed = prefs.edit();
                            ed.putString("PHONE", login.getText().toString());
                            ed.apply();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }else {
                            progressDialog.dismiss();
                            showError();
                            loginPassword.setText("");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressDialog.dismiss();
                     final AlertDialog alertDialog =   new AlertDialog.Builder(LoginActivity.this)
                                .setMessage("Login failed.\nplease try again.")
                                .setNeutralButton("OKAY!!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create();

                     alertDialog.show();

                    }
                });
            }
        });

    }

    private void showError() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        login.setError("Email OR Password is Incorrect");
        loginPassword.setError("Email OR Password is Incorrect");
        login.startAnimation(shake);
        loginPassword.startAnimation(shake);
    }

}