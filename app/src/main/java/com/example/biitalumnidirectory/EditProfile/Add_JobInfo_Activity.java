package com.example.biitalumnidirectory.EditProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class Add_JobInfo_Activity extends AppCompatActivity {

    LinearLayout endingDate_layout;
    Spinner spinner_startMonth, spinner_startYear, spinner_endMonth, spinner_endYear;
    String selected_startMonth, selected_startYear, selected_endMonth, selected_endYear;
    ArrayAdapter<CharSequence> startMonth_Adapter, endMonth_Adapter;
    ArrayAdapter<String> startYear_Adapter, endYear_Adapter;
    public String URL;
    public RequestQueue queue;
    RadioGroup rg_jobEnding;
    RadioButton rb_continue, rb_endingYear;
    TextView tv_endingYear_Title;
    TextInputLayout et_designation, et_organization;
    SharedReference SharedRef;
    String id;
    String job_ending_Value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job_info);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));

        getSupportActionBar().setTitle("Add Job Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        endingDate_layout = findViewById(R.id.endingDate_layout);
        tv_endingYear_Title = findViewById(R.id.tv_endingYear_Title);
        rb_endingYear = findViewById(R.id.rb_endingYear);
        rb_continue = findViewById(R.id.rb_continue);
        rg_jobEnding = findViewById(R.id.rg_jobEnding);
        et_designation = findViewById(R.id.et_designation);
        et_organization = findViewById(R.id.et_organization);
        SharedRef = new SharedReference(getApplicationContext());
        queue = Volley.newRequestQueue(getApplicationContext());
        URL = MyIp.ip;

        tv_endingYear_Title.setVisibility(View.GONE);
        endingDate_layout.setVisibility(View.GONE);


        rg_jobEnding.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rb_endingYear) {
                    tv_endingYear_Title.setVisibility(View.VISIBLE);
                    endingDate_layout.setVisibility(View.VISIBLE);
                } else {
                    tv_endingYear_Title.setVisibility(View.GONE);
                    endingDate_layout.setVisibility(View.GONE);
                }
            }
        });


        spinner_startMonth = findViewById(R.id.spinner_startMonth);
        startMonth_Adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Month, android.R.layout.simple_spinner_dropdown_item);
        spinner_startMonth.setAdapter(startMonth_Adapter);
        spinner_startMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_startMonth = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Calendar c = Calendar.getInstance();
        final int yearValue = c.get(Calendar.YEAR);

        ArrayList<String> arr = new ArrayList<>();
        for (int i = 1980; i <= yearValue; i++) {
            arr.add(i + "");
        }

        spinner_startYear = findViewById(R.id.spinner_startYear);
        startYear_Adapter = new ArrayAdapter<String>(Add_JobInfo_Activity.this, android.R.layout.simple_spinner_dropdown_item, arr);
        spinner_startYear.setAdapter(startYear_Adapter);
        spinner_startYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_startYear = parent.getItemAtPosition(position).toString();

                ArrayList<String> arr2 = new ArrayList<>();
                for (int i = Integer.parseInt(selected_startYear); i <= yearValue; i++) {
                    arr2.add(i + "");
                }

                spinner_endYear = findViewById(R.id.spinner_endYear);
                endYear_Adapter = new ArrayAdapter<String>(Add_JobInfo_Activity.this, android.R.layout.simple_spinner_dropdown_item, arr2);
                spinner_endYear.setAdapter(endYear_Adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner_endMonth = findViewById(R.id.spinner_endMonth);
        endMonth_Adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Month, android.R.layout.simple_spinner_dropdown_item);
        spinner_endMonth.setAdapter(endMonth_Adapter);
        spinner_endMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_endMonth = parent.getItemAtPosition(position).toString();


                if(selected_startYear.equals(selected_endYear)){
                    String b = selected_startMonth;
                    int spinnerPosition3 = endMonth_Adapter.getPosition(b);
                    if (spinnerPosition3 > position) {
                        spinner_endMonth.setSelection(spinnerPosition3);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        spinner_endYear = findViewById(R.id.spinner_endYear);
        endYear_Adapter = new ArrayAdapter<String>(Add_JobInfo_Activity.this, android.R.layout.simple_spinner_dropdown_item, arr);
        spinner_endYear.setAdapter(endYear_Adapter);
        spinner_endYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_endYear = parent.getItemAtPosition(position).toString();

                if(selected_startYear.equals(selected_endYear)){
                    String b = selected_startMonth;
                    int spinnerPosition3 = endMonth_Adapter.getPosition(b);
                    if (spinnerPosition3 > -1) {
                        spinner_endMonth.setSelection(spinnerPosition3);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    public void save_Data() {

        if (rb_continue.isChecked()) {
            job_ending_Value = "Continue";
        } else {
            job_ending_Value = selected_endMonth + " " + selected_endYear;
        }

        String designation = et_designation.getEditText().getText().toString().trim();
        String organization = et_organization.getEditText().getText().toString().trim();


        if (designation.equals("") || organization.equals("")) {

            Toasty.error(getApplicationContext(), "Fill all the Fields", Toast.LENGTH_SHORT, true).show();

            if (designation.equals("")) {
                et_designation.setError("Field Required");
            } else {
                et_designation.setError(null);
            }

            if (organization.equals("")) {
                et_organization.setError("Field Required");
            } else {
                et_organization.setError(null);
            }

        } else {

            String query = "Student_Job_Detail/Insert_Student_Job_Detail";
            final String temp = SharedRef.get_LoadUserDataByCNIC();

            try {
                JSONObject obj = new JSONObject();

                obj.put("CNIC", temp);
                obj.put("Designation", designation);
                obj.put("Organization", organization);
                obj.put("Starting_Year", selected_startMonth + " " + selected_startYear);
                obj.put("Ending_Year", job_ending_Value);

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
