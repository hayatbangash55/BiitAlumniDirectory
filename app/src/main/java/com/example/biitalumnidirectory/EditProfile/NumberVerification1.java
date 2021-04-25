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

import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class NumberVerification1 extends AppCompatActivity {


    public String URL;
    public RequestQueue queue;
    TextInputLayout et_phoneNo;
    SharedReference SharedRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_verification1);

        getSupportActionBar().setTitle("Update Number");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        queue = Volley.newRequestQueue(this);
        URL = MyIp.ip;
        SharedRef = new SharedReference(this);
        et_phoneNo = findViewById(R.id.et_phoneNo);
    }


    public void btn_onClick_Submit(View view) {
        String phoneNumber = et_phoneNo.getEditText().getText().toString().trim();
        if (phoneNumber.isEmpty()) {
            et_phoneNo.setError("Enter your phone Number");
        } else {
            et_phoneNo.setError(null);
            Intent intent = new Intent(getApplicationContext(), NumberVerification2.class);
            intent.putExtra("Number", phoneNumber);
            startActivity(intent);
        }
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
