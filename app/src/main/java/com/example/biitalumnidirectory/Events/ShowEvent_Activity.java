package com.example.biitalumnidirectory.Events;

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

public class ShowEvent_Activity extends AppCompatActivity {

    TextView tv_title, tv_venueDetail, tv_startingDate,tv_endingDate, tv_time, tv_description;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;

    String SJEDetail_Id = "";
    String SJEDetail_Title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Show Event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        Intent intent = getIntent();
        SJEDetail_Id = intent.getStringExtra("SJE_Detail_Id");
        SJEDetail_Title = "(" + intent.getStringExtra("SJEDetail_Title") + ")";


        tv_title = findViewById(R.id.tv_title);
        tv_venueDetail = findViewById(R.id.tv_venueDetail);
        tv_startingDate = findViewById(R.id.tv_startingDate);
        tv_endingDate = findViewById(R.id.tv_endingDate);
        tv_time = findViewById(R.id.tv_time);
        tv_description = findViewById(R.id.tv_description);

        tv_title.setText(SJEDetail_Title);

        queue = Volley.newRequestQueue(this);
        URL = MyIp.ip;
        SharedRef = new SharedReference(getApplicationContext());

        get_data();
    }


    public void btn_onClick_Submit(View view) {
        finish();
    }


    public void get_data() {

        String query = "SJE_Detail/Select_Event_Data?id=" + SJEDetail_Id;


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
                            String starting_date = obj.getString("Starting_Date").trim();
                            String ending_date = obj.getString("Ending_Date").trim();
                            String time_value = obj.getString("Time").trim();
                            String description_value = obj.getString("Description").trim();

                            tv_title.setText(title_value);
                            tv_venueDetail.setText(venue_value);
                            tv_time.setText(time_value);
                            tv_startingDate.setText(starting_date);
                            tv_endingDate.setText(ending_date);
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
