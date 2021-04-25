package com.example.biitalumnidirectory.EditProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.MainActivity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class Add_EducationInfo_Activity extends AppCompatActivity {

    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;
    TextInputLayout et_degree,et_majorSubjects, et_instituation, et_completionYear;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_education_info);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));

        getSupportActionBar().setTitle("Add Education Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        et_degree = findViewById(R.id.et_degree);
        et_majorSubjects = findViewById(R.id.et_majorSubjects);
        et_completionYear = findViewById(R.id.et_completetionYear);
        et_instituation = findViewById(R.id.et_instuation);
        SharedRef = new SharedReference(getApplicationContext());
        queue = Volley.newRequestQueue(getApplicationContext());
        URL = MyIp.ip;


    }


    public void save_Data() {

        String degree_name = et_degree.getEditText().getText().toString().trim();
        String majorSubject = et_majorSubjects.getEditText().getText().toString().trim();
        String completion_year = et_completionYear.getEditText().getText().toString().trim();
        String institute_name = et_instituation.getEditText().getText().toString().trim();

        if (degree_name.equals("") || majorSubject.equals("") || institute_name.equals("") || completion_year.equals("")) {

            Toasty.error(getApplicationContext(), "Fill all the Fields", Toast.LENGTH_SHORT, true).show();

            if (degree_name.equals("")) {
                et_degree.setError("Field Required");
            } else {
                et_degree.setError(null);
            }

            if (majorSubject.equals("")) {
                et_degree.setError("Field Required");
            } else {
                et_degree.setError(null);
            }

            if (institute_name.equals("")) {
                et_instituation.setError("Field Required");
            } else {
                et_instituation.setError(null);
            }

            if (completion_year.equals("")) {
                et_completionYear.setError("Field Required");
            } else {
                et_completionYear.setError(null);
            }
        } else {

            final String temp = SharedRef.get_LoadUserDataByCNIC();
            String query = "Student_Educational_Detail/Insert_Educational_Detail";

            try {
                JSONObject obj = new JSONObject();
                obj.put("CNIC", temp);
                obj.put("degree_name", degree_name);
                obj.put("Major_Subject", majorSubject);
                obj.put("institute_name", institute_name);
                obj.put("completion_year", completion_year);

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST, URL + query, obj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String Msg = response.optString("Msg");
                                String Error = response.optString("Error");

                                if (Msg.equals("Record Saved")) {
                                    Toasty.success(getApplicationContext(), Msg, Toast.LENGTH_SHORT, true).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                }
                );

                queue.add(request);
            } catch (JSONException e) {
                Toasty.error(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG, true).show();
            }

        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saveButton:
                save_Data();
                break;
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}
