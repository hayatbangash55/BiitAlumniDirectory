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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class ForgetPassword1_Activity extends AppCompatActivity {

    TextInputLayout et_cnic;
    public String URL;
    public RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        et_cnic = findViewById(R.id.et_cnic);
        queue = Volley.newRequestQueue(this);
        URL = MyIp.ip;
    }


    public void btn_onClick_Submit(View view) {
        get_data();
    }


    public void get_data() {

        final String cnic = et_cnic.getEditText().getText().toString().trim();

        if (cnic.isEmpty()) {
            et_cnic.setError("Enter the CNIC");
        } else {
            et_cnic.setError(null);


            String query = "Login/Select_For_ForgetPassword?cnic=" + cnic;

            StringRequest request = new StringRequest(
                    Request.Method.GET, URL + query,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONArray arr = new JSONArray(response);
                                JSONObject obj = arr.getJSONObject(0);
                                String primary_email = obj.getString("Primary_Email").trim();
                                String phone_no = obj.getString("Phone_No").trim();


                                String first2Chars = primary_email.substring(0, 2);
                                String []a = primary_email.split("@");
                                a[0] = a[0].substring(2);
                                a[0] = a[0].replaceAll("[a-zA-Z0-9\\!\\#\\$\\%\\^\\&\\(\\)\\-\\_\\+\\=\\~\\`\\;\\'\\\"\\<\\>\\,\\?\\/\\{\\}\\[\\]\\|\\\\]","*");
                                primary_email = first2Chars+a[0]+"@"+a[1];

                                first2Chars = phone_no.substring(0, 3);
                                String b = phone_no.substring(5);
                                String c = phone_no.substring(phone_no.length()-2);
                                b = b.replaceAll("[0-9\\-\\_\\+]","*");
                                phone_no = first2Chars+b+c;

                                Intent intent = new Intent(getApplicationContext(), ForgetPassword2_Activity.class);
                                intent.putExtra("primary_email", primary_email);
                                intent.putExtra("phoneNo", phone_no);
                                intent.putExtra("cnic", cnic);
                                startActivity(intent);

                            } catch (Exception e) {
                                Toasty.error(getApplicationContext(), response, Toast.LENGTH_LONG, false).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toasty.error(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG, false).show();
                }
            }
            );
            queue.add(request);
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }
}

