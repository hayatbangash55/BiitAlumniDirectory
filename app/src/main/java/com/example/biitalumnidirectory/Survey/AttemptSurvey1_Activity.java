package com.example.biitalumnidirectory.Survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class AttemptSurvey1_Activity extends AppCompatActivity {

    TextView tv_title, tv_description;
    String SJE_Detail_Id,SJEDetail_Title,Posted_SJE_Id;
    String URL;
    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attempt_survey1);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Attempt Survey");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        URL = MyIp.ip;
        queue = Volley.newRequestQueue(this);
        tv_title = findViewById(R.id.tv_title);
        tv_description = findViewById(R.id.tv_description);


        Intent intent = getIntent();
        SJE_Detail_Id = intent.getStringExtra("SJE_Detail_Id");
        SJEDetail_Title = intent.getStringExtra("SJEDetail_Title");
        Posted_SJE_Id = intent.getStringExtra("Posted_SJE_Id");
        tv_title.setText(SJEDetail_Title);
        get_data();
    }

    public void btn_onClick_Start(View view) {
        Intent intent = new Intent(AttemptSurvey1_Activity.this, AttemptSurvey2_Activity.class);
        intent.putExtra("SJE_Detail_Id",SJE_Detail_Id);
        intent.putExtra("SJEDetail_Title",SJEDetail_Title);
        intent.putExtra("Posted_SJE_Id", Posted_SJE_Id);
        startActivity(intent);
    }


    public void get_data() {

        String query = "SJE_Detail/Select_Survey_Data?id=" + SJE_Detail_Id;


        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);
                            JSONObject obj = arr.getJSONObject(0);

                            String description_value = obj.getString("Description").trim();

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
