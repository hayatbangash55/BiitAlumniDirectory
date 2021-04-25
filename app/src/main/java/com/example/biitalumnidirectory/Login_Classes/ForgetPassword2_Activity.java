package com.example.biitalumnidirectory.Login_Classes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class ForgetPassword2_Activity extends AppCompatActivity {

    public String URL;
    public RequestQueue queue;
    RadioGroup radioGroup;
    RadioButton rb_email, rb_phoneNo;
    Intent intent;

    String email;
    String phoneNO;
    String cnic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password2);

        queue = Volley.newRequestQueue(this);
        URL = MyIp.ip;
        rb_email = findViewById(R.id.rb_email);
        rb_phoneNo = findViewById(R.id.rb_phoneNo);
        radioGroup = findViewById(R.id.rg_option);

        intent = getIntent();
        email = intent.getStringExtra("primary_email");
        phoneNO = intent.getStringExtra("phoneNo");
        cnic = intent.getStringExtra("cnic");

        rb_email.setText(email);
        rb_phoneNo.setText(phoneNO);
        rb_email.setChecked(true);
    }


    public void btn_onClick_Submit(View view) {

        if (rb_phoneNo.isChecked()){
            intent = new Intent(getApplicationContext(), ForgetPassword_ByNumber_Activity.class);
            intent.putExtra("cnic", cnic);
            startActivity(intent);
        }
        else {
            intent = new Intent(getApplicationContext(), ForgetPassword_ByEmail_Activity.class);
            intent.putExtra("cnic", cnic);
            startActivity(intent);
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
