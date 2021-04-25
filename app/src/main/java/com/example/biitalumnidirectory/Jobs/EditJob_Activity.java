package com.example.biitalumnidirectory.Jobs;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class EditJob_Activity extends AppCompatActivity {

    DatePickerDialog datePicker;
    Spinner spinner_gender, spinner_shift, spinner_experience, spinner_degreeLevel;
    String selected_gender, selected_shift, selected_experience, selected_degreeLevel;
    ArrayAdapter<CharSequence> gender_Adapter, shift_Adapter, experience_Adapter, degreeLevel_Adapter;
    TextInputLayout et_jobTitle, et_jobLocation, et_jobLastDate, et_age, et_industry, et_jobDescription;
    TextView tv_jobTitle, tv_jobLocation, tv_jobLastDate;
    public String URL;
    public RequestQueue queue;

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__job);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Update Job");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        URL = MyIp.ip;
        queue = Volley.newRequestQueue(this);
        et_jobTitle = findViewById(R.id.et_jobTitle);
        et_age = findViewById(R.id.et_age);
        et_industry = findViewById(R.id.et_industry);
        et_jobDescription = findViewById(R.id.et_jobDescription);
        et_jobLocation = findViewById(R.id.et_jobLocation);
        et_jobLastDate = findViewById(R.id.et_jobLastDate);
        tv_jobTitle = findViewById(R.id.tv_jobTitle);
        tv_jobLastDate = findViewById(R.id.tv_jobLastDate);
        tv_jobLocation = findViewById(R.id.tv_jobLocation);

        Intent intent = getIntent();
        id = intent.getStringExtra("JobId");
        get_data();


        SpannableStringBuilder ssb;
        ForegroundColorSpan fcsRed = new ForegroundColorSpan(Color.parseColor("#FF0000"));

        String jobTitle = "Job Title *";
        ssb = new SpannableStringBuilder(jobTitle);
        ssb.setSpan(fcsRed, 10, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_jobTitle.setText(ssb);

        String jobLocation = "Job Location *";
        ssb = new SpannableStringBuilder(jobLocation);
        ssb.setSpan(fcsRed, 13, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_jobLocation.setText(ssb);

        String jobLastDate = "Last Date *";
        ssb = new SpannableStringBuilder(jobLastDate);
        ssb.setSpan(fcsRed, 10, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_jobLastDate.setText(ssb);


        et_jobLastDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                datePicker = new DatePickerDialog(EditJob_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //   eText.setText(dayOfMonth + "/" + (month) + "/" + year);
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.MONTH, monthOfYear);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                String f = df.format(c.getTime());
                                et_jobLastDate.getEditText().setText(f);
                            }
                        }, year, month, day);
                datePicker.show();

            }
        });


        spinner_gender = findViewById(R.id.spinner_gender);
        gender_Adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Gender, android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(gender_Adapter);
        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_gender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner_shift = findViewById(R.id.spinner_shift);
        shift_Adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Shift, android.R.layout.simple_spinner_dropdown_item);
        spinner_shift.setAdapter(shift_Adapter);
        spinner_shift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_shift = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner_degreeLevel = findViewById(R.id.spinner_degreeLevel);
        degreeLevel_Adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.DegreeLevel, android.R.layout.simple_spinner_dropdown_item);
        spinner_degreeLevel.setAdapter(degreeLevel_Adapter);
        spinner_degreeLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_degreeLevel = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner_experience = findViewById(R.id.spinner_experience);
        experience_Adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Experience, android.R.layout.simple_spinner_dropdown_item);
        spinner_experience.setAdapter(experience_Adapter);
        spinner_experience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_experience = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void btn_onClick_Submit(View view) {
        update_Data();
    }


    public void get_data() {

        String query = "SJE_Detail/Select_Job_Data?id=" + id;

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);
                            JSONObject obj = arr.getJSONObject(0);

                            String title_value = obj.getString("Title").trim();
                            String venue_value = obj.getString("Venue").trim();
                            String date_value = obj.getString("Ending_Date").trim();
                            String description_value = obj.getString("Description").trim();
                            String gender = obj.getString("Gender").trim();
                            String shift = obj.getString("Shift").trim();
                            String experience = obj.getString("Experience").trim();
                            String degree_level = obj.getString("Degree_Level").trim();
                            String age = obj.getString("Age").trim();
                            String industry = obj.getString("Industry").trim();


                            String v1 = gender;
                            int spinnerPosition1 = gender_Adapter.getPosition(v1);
                            if (spinnerPosition1 > 0) {
                                spinner_gender.setSelection(spinnerPosition1);
                            }

                            String v2 = shift;
                            int spinnerPosition2 = shift_Adapter.getPosition(v2);
                            if (spinnerPosition2 > 0) {
                                spinner_shift.setSelection(spinnerPosition2);
                            }

                            String v3 = experience;
                            int spinnerPosition3 = experience_Adapter.getPosition(v3);
                            if (spinnerPosition3 > 0) {
                                spinner_experience.setSelection(spinnerPosition3);
                            }

                            String v4 = degree_level;
                            int spinnerPosition4 = degreeLevel_Adapter.getPosition(v4);
                            if (spinnerPosition4 > 0) {
                                spinner_degreeLevel.setSelection(spinnerPosition4);
                            }

                            et_jobTitle.getEditText().setText(title_value);
                            et_jobTitle.getEditText().setSelection(et_jobTitle.getEditText().getText().length());
                            et_jobLocation.getEditText().setText(venue_value);
                            et_jobLocation.getEditText().setSelection(et_jobLocation.getEditText().getText().length());
                            et_jobLastDate.getEditText().setText(date_value);
                            et_jobLastDate.getEditText().setSelection(et_jobLastDate.getEditText().getText().length());
                            et_jobDescription.getEditText().setText(description_value);
                            et_jobDescription.getEditText().setSelection(et_jobDescription.getEditText().getText().length());
                            et_age.getEditText().setText(age);
                            et_age.getEditText().setSelection(et_age.getEditText().getText().length());
                            et_industry.getEditText().setText(industry);
                            et_industry.getEditText().setSelection(et_industry.getEditText().getText().length());


                        } catch (JSONException e) {
                            Toasty.error(getApplicationContext(), response, Toast.LENGTH_SHORT, true).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
        }
        );
        queue.add(request);
    }

    void update_Data() {

        String query = "SJE_Detail/Update_UnPosted_Events";

        String title_value = et_jobTitle.getEditText().getText().toString();
        String venue_value = et_jobLocation.getEditText().getText().toString();
        String date_value = et_jobLastDate.getEditText().getText().toString();
        String description_value = et_jobDescription.getEditText().getText().toString();
        String age = et_age.getEditText().getText().toString().trim();
        String industry = et_industry.getEditText().getText().toString().trim();

        if (title_value.equals("") || venue_value.equals("") || date_value.equals("")) {
            if (title_value.equals("")) {
                et_jobTitle.setError("Field Required");
            } else {
                et_jobTitle.setError(null);
            }

            if (venue_value.equals("")) {
                et_jobLocation.setError("Field Required");
            } else {
                et_jobLocation.setError(null);
            }

            if (date_value.equals("")) {
                et_jobLastDate.setError("Field Required");
            } else {
                et_jobLastDate.setError(null);
            }
        } else {
            try {


                if (selected_gender.equals("Select Gender")) {
                    selected_gender = "";
                }
                if (selected_shift.equals("Select Shift")) {
                    selected_shift = "";
                }
                if (selected_experience.equals("Select Experience")) {
                    selected_experience = "";
                }
                if (selected_degreeLevel.equals("Select Degree Level")) {
                    selected_degreeLevel = "";
                }

                JSONObject obj = new JSONObject();
                obj.put("SJE_Detail_Id", id);
                obj.put("Title", title_value);
                obj.put("Venue", venue_value);
                obj.put("Time", null);
                obj.put("Starting_Date", null);
                obj.put("Ending_Date", date_value);
                obj.put("Description", description_value);
                obj.put("SJE_Type", "Job");
                obj.put("Gender", selected_gender);
                obj.put("Shift", selected_shift);
                obj.put("Experience", selected_experience);
                obj.put("Degree_Level", selected_degreeLevel);
                obj.put("Age", age);
                obj.put("Industry", industry);


                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.PUT, URL + query, obj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String Msg = response.optString("Msg");
                                String Error = response.optString("Error");

                                if (Msg.equals("Record Updated")) {
                                    Toasty.success(getApplicationContext(), Msg, Toast.LENGTH_SHORT, true).show();
                                    finish();
                                } else {
                                    Toasty.error(getApplicationContext(), Error, Toast.LENGTH_SHORT, true).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT, true).show();
                    }
                });
                queue.add(request);
            } catch (JSONException e) {
                Toasty.error(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
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
