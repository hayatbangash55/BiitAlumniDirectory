package com.example.biitalumnidirectory.Jobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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
import com.example.biitalumnidirectory.SharedReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class ShowJob_Activity extends AppCompatActivity {

    TextView tv_title, tv_location, tv_date,tv_gender,tv_shift,tv_experience,tv_degreeLevel,tv_maxAge,tv_industry, tv_description;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;
    String SJEDetail_Id = "";
    String SJEDetail_Title = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_job);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Show Job");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        Intent intent = getIntent();
        SJEDetail_Id = intent.getStringExtra("SJE_Detail_Id");
        SJEDetail_Title = "(" + intent.getStringExtra("SJEDetail_Title") + ")";

        tv_title = findViewById(R.id.tv_title);
        tv_location = findViewById(R.id.tv_location);
        tv_date = findViewById(R.id.tv_date);
        tv_gender = findViewById(R.id.tv_gender);
        tv_location = findViewById(R.id.tv_location);
        tv_shift = findViewById(R.id.tv_shift);
        tv_experience = findViewById(R.id.tv_experience);
        tv_degreeLevel = findViewById(R.id.tv_degreeLevel);
        tv_maxAge = findViewById(R.id.tv_maxAge);
        tv_industry = findViewById(R.id.tv_industry);
        tv_description = findViewById(R.id.tv_description);
        queue = Volley.newRequestQueue(this);
        URL = MyIp.ip;
        SharedRef = new SharedReference(getApplicationContext());




        tv_title.setText(SJEDetail_Title);


        get_data();
    }

    public void btn_onClick_Submit(View view) {
        finish();
    }


    public void get_data() {

        String query = "SJE_Detail/Select_Job_Data?id=" + SJEDetail_Id;

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


                            if(!gender.equals("")){
                                tv_gender.setText(gender);
                            }
                            if(!shift.equals("")){
                                tv_shift.setText(shift);
                            }

                            if(!experience.equals("")){
                                tv_experience.setText(experience);
                            }

                            if(!degree_level.equals("")){
                                tv_degreeLevel.setText(degree_level);
                            }

                            if(!age.equals("")){
                                tv_maxAge.setText(age);
                            }

                            if(!industry.equals("")){
                                tv_industry.setText(industry);
                            }


                            tv_title.setText(title_value);
                            tv_location.setText(venue_value);
                            tv_date.setText(date_value);
                            tv_description.setText(description_value);

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
