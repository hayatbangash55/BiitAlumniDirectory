package com.example.biitalumnidirectory;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class Filter_Admin_Post_SJENotification_Activity extends AppCompatActivity {

    Spinner spinner_gender, spinner_discipline, spinner_session, spinner_audience, spinner_studentType;
    String selected_discipline, selected_gender, selected_session, selected_audience, selected_studentType;
    LinearLayout privateItems_Parent;
    TextView tv_Title;
    EditText et_sessionYear,et_attributes;
    public String URL;
    public RequestQueue queue;
    SharedReference sharedRef;

    String SJEDetail_Id = "";
    String SJEDetail_Type = "";
    String SJEDetail_Title = "";

    String System_Date = "";
    String System_Time = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_admin_post_sje_notification);

        Intent intent = getIntent();
        SJEDetail_Id = intent.getStringExtra("SJE_Detail_Id");
        SJEDetail_Type = intent.getStringExtra("SJEDetail_Type");
        SJEDetail_Title = "(" + intent.getStringExtra("SJEDetail_Title") + ")";
        sharedRef = new SharedReference(getApplicationContext());

        String pageTitle = "Post " + SJEDetail_Type;
        getSupportActionBar().setTitle(pageTitle);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tv_Title = findViewById(R.id.tv_Title);
        tv_Title.setText(SJEDetail_Title);
        et_sessionYear = findViewById(R.id.et_sessionYear);
        et_attributes = findViewById(R.id.et_attributes);
        privateItems_Parent = findViewById(R.id.privateItems_Parent);

        privateItems_Parent.setVisibility(View.GONE);

        queue = Volley.newRequestQueue(getApplicationContext());
        URL = MyIp.ip;

        if(!SJEDetail_Type.equals("Job")){
            et_attributes.setVisibility(View.GONE);
        }


        spinner_audience = findViewById(R.id.spinner_audience);
        ArrayAdapter<CharSequence> audience_Adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.audience, android.R.layout.simple_spinner_dropdown_item);
        spinner_audience.setAdapter(audience_Adapter);
        spinner_audience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_audience = parent.getItemAtPosition(position).toString();

                if (selected_audience.equals("Public")) {
                    privateItems_Parent.setVisibility(View.GONE);
                } else if (selected_audience.equals("Private")) {
                    privateItems_Parent.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner_studentType = findViewById(R.id.spinner_studentType);
        ArrayAdapter<CharSequence> studentType_Adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.studentType, android.R.layout.simple_spinner_dropdown_item);
        spinner_studentType.setAdapter(studentType_Adapter);
        spinner_studentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_studentType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner_gender = findViewById(R.id.spinner_gender);
        ArrayAdapter<CharSequence> gender_Adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Gender, android.R.layout.simple_spinner_dropdown_item);
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


        spinner_discipline = findViewById(R.id.spinner_discipline);
        ArrayAdapter<CharSequence> discipline_Adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Discipline, android.R.layout.simple_spinner_dropdown_item);
        spinner_discipline.setAdapter(discipline_Adapter);
        spinner_discipline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_discipline = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner_session = findViewById(R.id.spinner_session);
        ArrayAdapter<CharSequence> session_Adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Session, android.R.layout.simple_spinner_dropdown_item);
        spinner_session.setAdapter(session_Adapter);
        spinner_session.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_session = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    public void save_DataPrivately() {

        try {

            String sessionYear = et_sessionYear.getText().toString().trim();
            String Attributes = et_attributes.getText().toString().trim();

            if (selected_studentType.equals("Current Student")) {
                selected_studentType = "InComplete";
            }
            if (selected_studentType.equals("Alumni")) {
                selected_studentType = "Completed";
            }

            if (sessionYear.equals("")) {
                sessionYear = "SelectSessionYear";
            }

            if (selected_session.equals("Select Session")) {
                selected_session = "SelectSession";
            }

            if (selected_discipline.equals("Select Discipline")) {
                selected_discipline = "SelectDiscipline";
            }

            if (selected_gender.equals("Select Gender")) {
                selected_gender = "SelectGender";
            }

            if (Attributes.equals("")) {
                Attributes = "NoAttributes";
            }

            String query = "Post_SJE/Insert_SJE_Notification_Privately";

            JSONObject obj = new JSONObject();
            obj.put("SJE_Detail_Id", SJEDetail_Id);
            obj.put("Admin_Approval", "Approved");
            obj.put("Posted_Time", System_Time);
            obj.put("Posted_Date", System_Date);
            obj.put("Student_Type", selected_studentType);
            obj.put("Gender", selected_gender);
            obj.put("Posted_Privacy","Private");
            obj.put("Discipline", selected_discipline);
            obj.put("Session_Year", sessionYear);
            obj.put("Session", selected_session);
            obj.put("Attributes",Attributes);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST, URL + query, obj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            String Msg = response.optString("Msg");
                            String Error = response.optString("Error");

                            if (Msg.equals("Record Saved")) {
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
            }
            );

            queue.add(request);

        } catch (JSONException e) {
            Toasty.error(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
        }

    }

    void saveData_Publically() {

        String query = "Post_SJE/Insert_SJE_Notification_Publically";

        try {

            JSONObject obj = new JSONObject();
            obj.put("SJE_Detail_Id", SJEDetail_Id);
            obj.put("Admin_Approval", "Approved");
            obj.put("Posted_Time", System_Time);
            obj.put("Posted_Date", System_Date);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST, URL + query, obj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            String Msg = response.optString("Msg");
                            String Error = response.optString("Error");

                            if (Msg.equals("Record Saved")) {
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

    void saveSurvey_Publically() {
        String cnic = sharedRef.get_LoadUserDataByCNIC();

        String query = "Post_SJE/Insert_Admin_Survey_Publically?mycnic="+cnic;

        try {

            JSONObject obj = new JSONObject();
            obj.put("SJE_Detail_Id", SJEDetail_Id);
            obj.put("Admin_Approval", "Approved");
            obj.put("Posted_Time", System_Time);
            obj.put("Posted_Date", System_Date);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST, URL + query, obj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            String Msg = response.optString("Msg");
                            String Error = response.optString("Error");

                            if (Msg.equals("Record Saved")) {
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

    public void btn_onClick_Submit(View view) {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        System_Date = dateFormat.format(c.getTime());

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        System_Time = timeFormat.format(c.getTime());


        if (selected_audience.equals("Public")) {
            if(SJEDetail_Type.equalsIgnoreCase("Survey")){
                saveSurvey_Publically();
            }else {
                saveData_Publically();
            }
        } else if (selected_audience.equals("Private")) {
            save_DataPrivately();
        } else {
            Toast.makeText(getApplicationContext(), "Select One Option Please", Toast.LENGTH_LONG).show();
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

