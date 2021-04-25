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

public class Edit_EducationInfo_Activity extends AppCompatActivity {

    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;
    TextInputLayout et_degree, et_instituation, et_completionYear, et_majorSubjects;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_education_info);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));

        getSupportActionBar().setTitle("Update Education Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        et_degree = findViewById(R.id.et_degree);
        et_completionYear = findViewById(R.id.et_completetionYear);
        et_instituation = findViewById(R.id.et_instuation);
        et_majorSubjects = findViewById(R.id.et_majorSubjects);
        SharedRef = new SharedReference(getApplicationContext());
        queue = Volley.newRequestQueue(getApplicationContext());
        URL = MyIp.ip;


        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        get_data(id);

    }


    public void get_data(String id) {

        String query = "Student_Educational_Detail/Select_Education_Detail_To_Edit?id=" + id;

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);

                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject obj = arr.getJSONObject(i);

                                String degreeName_value = obj.getString("Degree_Name").trim();
                                String instituteName_value = obj.getString("Institute_Name").trim();
                                String completionYear_value = obj.getString("Completion_Year").trim();
                                String majorSubject = obj.getString("Major_Subject").trim();

                                et_degree.getEditText().setText(degreeName_value);
                                et_degree.getEditText().setSelection(et_degree.getEditText().getText().length());
                                et_majorSubjects.getEditText().setText(majorSubject);
                                et_majorSubjects.getEditText().setSelection(et_majorSubjects.getEditText().getText().length());
                                et_completionYear.getEditText().setText(completionYear_value);
                                et_completionYear.getEditText().setSelection(et_completionYear.getEditText().getText().length());
                                et_instituation.getEditText().setText(instituteName_value);
                                et_instituation.getEditText().setSelection(et_instituation.getEditText().getText().length());


                            }


                        } catch (JSONException e) {
                            Toasty.error(getApplicationContext(), response, Toast.LENGTH_LONG, false).show();
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

    void update_Data() {

        String degree_name = et_degree.getEditText().getText().toString();
        String majorSubject = et_majorSubjects.getEditText().getText().toString();
        String institute_name = et_instituation.getEditText().getText().toString();
        String completion_year = et_completionYear.getEditText().getText().toString();


        if (degree_name.equals("") || institute_name.equals("") || completion_year.equals("") || majorSubject.equals("")) {

            Toasty.error(getApplicationContext(), "Fill all the Fields", Toast.LENGTH_SHORT, true).show();

            if (degree_name.equals("")) {
                et_degree.setError("Field Required");
            } else {
                et_degree.setError(null);
            }

            if (majorSubject.equals("")) {
                et_majorSubjects.setError("Field Required");
            } else {
                et_majorSubjects.setError(null);
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
            String Query = "Student_Educational_Detail/Update_Educational_Detail";

            try {
                JSONObject obj = new JSONObject();
                obj.put("id", id);
                obj.put("CNIC", temp);
                obj.put("degree_name", degree_name);
                obj.put("Major_Subject", majorSubject);
                obj.put("institute_name", institute_name);
                obj.put("completion_year", completion_year);

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.PUT, URL + Query, obj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                String Msg = response.optString("Msg");
                                String Error = response.optString("Error");

                                if (Msg.equals("Record Updated")) {
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
                });
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
                update_Data();
                break;
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }

}
