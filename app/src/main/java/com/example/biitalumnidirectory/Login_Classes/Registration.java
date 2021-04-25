package com.example.biitalumnidirectory.Login_Classes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class Registration extends AppCompatActivity {

    TextInputLayout et_cnic, et_password, et_conformPassword;
    TextView tv_alredayUser;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        et_cnic = findViewById(R.id.textInputLayout_cnic);
        et_password = findViewById(R.id.textInputLayout_email);
        et_conformPassword = findViewById(R.id.textInputLayout_conformpassword);
        tv_alredayUser = findViewById(R.id.tv_codeVerification);

        queue = Volley.newRequestQueue(this);
        URL = MyIp.ip;
        SharedRef = new SharedReference(this);
    }


    public void btn_onClick_Register(View view) {

        getData_FromLogin();
    }


    public void getData_FromLogin() {

        final String cnic = et_cnic.getEditText().getText().toString();
        final String password = et_password.getEditText().getText().toString();
        final String conformPassword = et_conformPassword.getEditText().getText().toString();

        String query = "Login/Select_Check_AlreadyUser?cnic=" + cnic;

        //validation
        if (cnic.isEmpty() || password.isEmpty() || conformPassword.isEmpty()) {
            if (cnic.isEmpty()) {
                et_cnic.setError("Field is required");
            } else {
                et_cnic.setError(null);
            }

            if (password.isEmpty()) {
                et_password.setError("Field is required");
            } else {
                et_password.setError(null);
            }

            if (conformPassword.isEmpty()) {
                et_conformPassword.setError("Field is required");
            } else {
                et_conformPassword.setError(null);
            }

        } else if (!password.equals(conformPassword)) {
            Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
        } else {
            StringRequest request = new StringRequest(
                    Request.Method.GET, URL + query,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.replace("\"", "").equals("No Record")) {
                                getData_FromStudentDetail();
                            } else {
                                Toasty.error(getApplicationContext(), response, Toast.LENGTH_LONG, true).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toasty.error(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG, true).show();
                }
            }
            );
            queue.add(request);
        }
    }


    public void getData_FromStudentDetail() {

        final String cnic = et_cnic.getEditText().getText().toString();
        final String password = et_password.getEditText().getText().toString();

        String query = "Student_Detail/Select_Registration?cnic=" + cnic;


        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);
                            JSONObject obj = arr.getJSONObject(0);
                            String cnic_value = obj.getString("CNIC").trim();
                            String email_value = obj.getString("Primary_Email").trim();

                            if (cnic_value.equals(cnic)) {

                                Intent i = new Intent(getApplicationContext(), Registration_EmailVerification1.class);
                                SharedRef.save_UserRegistrationData(new User_RegistrationData(cnic_value, email_value, password));
                                startActivity(i);
                            }

                        } catch (JSONException e) {
                            Toasty.error(getApplicationContext(), response, Toast.LENGTH_LONG, true).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG, true).show();
            }
        }
        );
        queue.add(request);
    }


    public void tv_onClick_alreadyUser(View view) {
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
        finish();
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }
}