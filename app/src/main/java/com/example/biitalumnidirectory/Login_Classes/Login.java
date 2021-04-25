package com.example.biitalumnidirectory.Login_Classes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.MainActivity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class Login extends AppCompatActivity {

    TextInputLayout et_email, et_password;
    CheckBox cb_rememberMe;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.textInputLayout_email);
        et_password = findViewById(R.id.textInputLayout_password);
        cb_rememberMe = findViewById(R.id.cb_rememberMe);
        SharedRef = new SharedReference(this);
        queue = Volley.newRequestQueue(this);
        URL = MyIp.ip;


        //if the user applied for verification code & close app
        //then go to verification activity on starting of app
        if (SharedRef.get_registrationStatus().equals(true)) {
            Intent i = new Intent(getApplicationContext(), Registration_EmailVerification1.class);
            startActivity(i);
            finish();
//            Toast.makeText(getApplicationContext(), "Go to Verification", Toast.LENGTH_LONG).show();
        }


        //if remember me option is checked than go towards MainActivity on starting of app
        if (SharedRef.get_RememberMe().equals(true)) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }

    }


    public void btn_onClick_Login(View view) {

        final String email = et_email.getEditText().getText().toString();
        final String password = et_password.getEditText().getText().toString();

        String query = "login/Select_Login_Data?email=" + email + "&password=" + password;

        //validation
        if (email.isEmpty() || password.isEmpty()) {
            if (email.isEmpty()) {
                et_email.setError("Enter your Email");
            } else {
                et_email.setError(null);
            }

            if (password.isEmpty()) {
                et_password.setError("Enter your Password");
            } else {
                et_password.setError(null);
            }

        } else {

            StringRequest request = new StringRequest(
                    Request.Method.GET, URL + query,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONArray arr = new JSONArray(response);
                                JSONObject obj = arr.getJSONObject(0);
                                String Cnic_value = obj.getString("CNIC").trim();
                                String email_value = obj.getString("Email").trim();
                                String password_value = obj.getString("Password").trim();
                                String Role = obj.getString("Role").trim();

                                if (email_value.equalsIgnoreCase(email) && password_value.equals(password)) {

                                    SharedRef.save_UserType(Role);
                                    SharedRef.save_UserLoginCNIC(Cnic_value);


                                        if (cb_rememberMe.isChecked()) {
                                            SharedRef.save_RememberMe(true);

                                        } else {
                                            SharedRef.save_RememberMe(false);
                                        }

                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                        finish();


                                }
                            } catch (JSONException e) {
                                Toasty.error(getApplicationContext(), response, Toast.LENGTH_LONG, true).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toasty.error(getApplicationContext(), error.toString(), Toast.LENGTH_LONG, true).show();
                }
            }
            );
            queue.add(request);
        }
    }

    public void onClick_forgetPassword(View view) {
        Intent i = new Intent(getApplicationContext(), ForgetPassword1_Activity.class);
        startActivity(i);
        finish();
    }

    public void tv_onClick_newUser(View view) {
        Intent i = new Intent(getApplicationContext(), Registration.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
