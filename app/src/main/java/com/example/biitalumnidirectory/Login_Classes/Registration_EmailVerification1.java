package com.example.biitalumnidirectory.Login_Classes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.google.android.material.textfield.TextInputEditText;

public class Registration_EmailVerification1 extends AppCompatActivity {

    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;
    TextInputEditText et_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification1);


        SharedRef = new SharedReference(this);
        queue = Volley.newRequestQueue(this);
        et_email = findViewById(R.id.et_email);
        et_email.setEnabled(false);
        URL = MyIp.ip;

        SharedRef.save_registrationStatus(true);
        String email = SharedRef.get_UserRegistrationData().Email;

        String first2Chars = email.substring(0, 2);
        String []a = email.split("@");
        a[0] = a[0].substring(2);
        a[0] = a[0].replaceAll("[a-zA-Z0-9\\!\\#\\$\\%\\^\\&\\(\\)\\-\\_\\+\\=\\~\\`\\;\\;\\'\\\"\\<\\>\\,\\?\\/\\{\\}\\[\\]\\|\\\\]","*");
        email = first2Chars+a[0]+"@"+a[1];

        et_email.setText(email);

    }

    public void btn_onClick_sendCode(View view) {
        Intent intent = new Intent(getApplicationContext(), Registration_EmailVerification2.class);
        startActivity(intent);
        finish();
    }





    @Override
    public void onBackPressed() {
        finish();
    }
}
