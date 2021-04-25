package com.example.biitalumnidirectory.EditProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.example.biitalumnidirectory.MainActivity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class NumberVerification2 extends AppCompatActivity {

    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;
    String phoneNumber;
    TextInputLayout et_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_verification2);

        getSupportActionBar().setTitle("Number Verification");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        queue = Volley.newRequestQueue(getApplicationContext());
        SharedRef = new SharedReference(getApplicationContext());
        et_code = findViewById(R.id.et_code);
        URL = MyIp.ip;

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("Number");


        delete_Code1();
    }


    public void btn_onClick_Submit(View view) {
        String etcode = et_code.getEditText().getText().toString().trim();
        if (etcode.isEmpty()) {
            et_code.setError("Enter the received Code");
        } else {
            et_code.setError(null);
            get_AuthenticationCode();
        }
    }


    public void get_AuthenticationCode() {

        final String cnic = SharedRef.get_LoadUserDataByCNIC();
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
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                update_phoneNumber(phoneNumber);
                                startActivity(intent);

                            } else {
                                Toasty.error(getApplicationContext(), "Incorrect Code", Toast.LENGTH_LONG, true).show();
                            }

                        } catch (Exception e) {
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

    void delete_Code2() {

        final String cnic = SharedRef.get_LoadUserDataByCNIC();
        String query = "Authentication/Remove_Authentication_Code?cnic=" + cnic;

        StringRequest request = new StringRequest(
                Request.Method.DELETE, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.replace("\"", "").equals("Record Deleted")) {
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

    void update_phoneNumber(String val) {

        final String temp = SharedRef.get_LoadUserDataByCNIC();
        String Query = "Student_Detail/Update_Student_PhoneNo?cnic=" + temp + "&phone=" + val;


        JsonObjectRequest request = null;
        request = new JsonObjectRequest(
                Request.Method.PUT, URL + Query, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String Msg = response.optString("Msg");
                        String Error = response.optString("Error");

                        if (Msg.equals("Record Updated")) {
                            Toasty.success(getApplicationContext(), Msg, Toast.LENGTH_SHORT, true).show();
                            delete_Code2();
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


    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    public void save_AuthenticationCode() {

        int randomValue = getRandomNumber(999, 9999);
        String cnic = SharedRef.get_LoadUserDataByCNIC();
        String query = "Authentication/Insert_Authentication_Code?code=" + randomValue + "&cnic=" + cnic;

        JsonObjectRequest request = null;
        request = new JsonObjectRequest(
                Request.Method.POST, URL + query, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String Msg = response.optString("Msg");
                        String Error = response.optString("Error");

                        if (Msg.equals("Record Saved")) {

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


    void delete_Code1() {

        final String cnic = SharedRef.get_LoadUserDataByCNIC();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
