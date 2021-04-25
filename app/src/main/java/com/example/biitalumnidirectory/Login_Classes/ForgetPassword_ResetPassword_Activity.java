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
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class ForgetPassword_ResetPassword_Activity extends AppCompatActivity {

    TextInputLayout et_password, et_conformpassword;
    Intent intent;
    String cnic;
    public String URL;
    public RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_);

        queue = Volley.newRequestQueue(this);
        URL = MyIp.ip;
        et_password = findViewById(R.id.et_password);
        et_conformpassword = findViewById(R.id.et_conformpassword);
        intent = getIntent();
        cnic = intent.getStringExtra("cnic");
    }


    public void btn_onClick_Submit(View view) {
        update_Data();
    }


    void update_Data() {

        String password = et_password.getEditText().getText().toString().trim();
        String conformPassword = et_conformpassword.getEditText().getText().toString().trim();

        if (password.isEmpty() || conformPassword.isEmpty()) {
            if (password.isEmpty()) {
                et_password.setError("Enter the Password");
            }
            if (conformPassword.isEmpty()) {
                et_conformpassword.setError("Enter the Password to Confirm");
            }
        } else if (!password.equals(conformPassword)) {
            Toasty.error(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG, false).show();
        } else {

            String Query = "Login/Update_ResetPassword?cnic=" + cnic + "&password=" + password;

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.PUT, URL + Query, new JSONObject(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String Msg = response.optString("Msg");
                            String Error = response.optString("Error");

                            if (Msg.equals("Password Changed")) {
                                Toasty.success(getApplicationContext(), Msg, Toast.LENGTH_SHORT, true).show();

                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            } else {
                                Toasty.error(getApplicationContext(), Error, Toast.LENGTH_LONG, false).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toasty.error(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG, false).show();
                }
            });
            queue.add(request);
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
