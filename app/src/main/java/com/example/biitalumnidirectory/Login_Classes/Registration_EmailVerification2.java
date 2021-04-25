package com.example.biitalumnidirectory.Login_Classes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

public class Registration_EmailVerification2 extends AppCompatActivity {

    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;

    TextInputLayout et_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification2);

        SharedRef = new SharedReference(this);
        queue = Volley.newRequestQueue(this);
        et_code = findViewById(R.id.et_code);
        URL = MyIp.ip;

        delete_Code1();

    }

    public void btn_Submit(View view) {

        String code = et_code.getEditText().getText().toString();

        if (code.isEmpty()) {
            et_code.setError("Enter the Code");
        } else {
            et_code.setError(null);
            get_AuthenticationCode();
        }
    }

    public final void save_Data() {

        User_RegistrationData shrd_obj = SharedRef.get_UserRegistrationData();
        String query = "login/Insert_Login_Data";
        try {
            JSONObject obj = new JSONObject();
            obj.put("CNIC", shrd_obj.CNIC);
            obj.put("Email", shrd_obj.Email);
            obj.put("Password", shrd_obj.Password);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST, URL + query, obj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            String Msg = response.optString("Msg");
                            String Error = response.optString("Error");

                            if (Msg.equals("Registration Successful")) {

                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                delete_Code2();
                                startActivity(intent);

                            } else {
                                Toasty.error(getApplicationContext(), Error, Toast.LENGTH_LONG, true).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toasty.error(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG, true).show();
                }
            });

            queue.add(request);

        } catch (JSONException e) {
            Toasty.error(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG, true).show();
        }


        SharedRef.save_registrationStatus(false);
    }

    public void get_AuthenticationCode() {

        String cnic = SharedRef.get_UserRegistrationData().CNIC;
        String query = "Authentication/Select_Authentication_Code?cnic=" + cnic;

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);
                            JSONObject obj = arr.getJSONObject(0);
                            String receivedCode = obj.getString("Code").trim();

                            if (receivedCode.equals(et_code.getEditText().getText().toString())) {
                                save_Data();

                            } else {
                                Toasty.error(getApplicationContext(), "Incorrect Code", Toast.LENGTH_LONG, true).show();
                            }

                        } catch (Exception e) {
                            // Toast.makeText(getApplicationContext(), "Code Expired", Toast.LENGTH_LONG).show();
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

    void delete_Code2() {

        String cnic = SharedRef.get_UserRegistrationData().CNIC;
        String query = "Authentication/Remove_Authentication_Code?cnic=" + cnic;

        StringRequest request = new StringRequest(
                Request.Method.DELETE, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.replace("\"", "").equals("Record Deleted")) {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        );
        queue.add(request);

    }


    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void save_AuthenticationCode() {

        final int randomValue = getRandomNumber(999, 9999);
        String cnic = SharedRef.get_UserRegistrationData().CNIC;
        String query = "Authentication/Insert_Authentication_Code?code=" + randomValue + "&cnic=" + cnic;


        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, URL + query, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String Msg = response.optString("Msg");
                        String Error = response.optString("Error");


                        if (Msg.equals("Record Saved")) {
                            save_SendCodeToEmail(randomValue);
                        } else {
                            Toasty.error(getApplicationContext(), Error, Toast.LENGTH_LONG, true).show();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG, true).show();
            }
        });

        queue.add(request);
    }

    public void save_SendCodeToEmail(int code) {

        String query = "Authentication/SendCodeToEmail?code="+code;


        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, URL + query, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Toasty.error(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG, true).show();
            }
        });

        queue.add(request);
    }

    void delete_Code1() {

        String cnic = SharedRef.get_UserRegistrationData().CNIC;
        String query = "Authentication/Remove_Authentication_Code?cnic=" + cnic;

        StringRequest request = new StringRequest(
                Request.Method.DELETE, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.replace("\"", "").equals("Record Deleted")) {
                            Toasty.error(getApplicationContext(), response, Toast.LENGTH_LONG, true).show();
                        }
                        save_AuthenticationCode();
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


    @Override
    public void onBackPressed() {
        finish();
    }
}
