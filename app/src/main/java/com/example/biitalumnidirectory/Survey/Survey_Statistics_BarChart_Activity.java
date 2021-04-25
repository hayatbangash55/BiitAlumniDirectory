package com.example.biitalumnidirectory.Survey;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class Survey_Statistics_BarChart_Activity extends AppCompatActivity {

    LinearLayout parentLayout;
    RecyclerView survey_statistics_recycleView;
    String URL;
    RequestQueue queue;
    final ArrayList<Survey_Statistics> Statistics_List = new ArrayList<>();
    String SJE_Detail_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_statistics_barchart);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Survey Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        parentLayout = findViewById(R.id.parentLayout);
        URL = MyIp.ip;
        queue = Volley.newRequestQueue(this);
        survey_statistics_recycleView = findViewById(R.id.survey_statistics_recycleView);

        Intent intent = getIntent();
        SJE_Detail_Id = intent.getStringExtra("SJE_Detail_Id");
        getData();
    }

    public void getData() {

        String query = "Attempt_Survey/Select_Survey_Statistics?SJE_Detail_Id=" + SJE_Detail_Id;
        final ArrayList<Survey_Statistics> Statistics_List = new ArrayList<>();
        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);
                                String question_no_value = obj.getString("Question_No").trim();
                                String question_value = obj.getString("Question").trim();
                                String option1_value = obj.getString("Option1").trim();
                                String option2_value = obj.getString("Option2").trim();
                                String option3_value = obj.getString("Option3").trim();
                                String option4_value = obj.getString("Option4").trim();
                                String option5_value = obj.getString("Option5").trim();
                                String Total_Option1 = obj.getString("Total_Option1").trim();
                                String Total_Option2 = obj.getString("Total_Option2").trim();
                                String Total_Option3 = obj.getString("Total_Option3").trim();
                                String Total_Option4 = obj.getString("Total_Option4").trim();
                                String Total_Option5 = obj.getString("Total_Option5").trim();

                                Statistics_List.add(new Survey_Statistics(question_no_value, question_value, option1_value, option2_value, option3_value, option4_value, option5_value, Total_Option1, Total_Option2, Total_Option3, Total_Option4, Total_Option5));
                            }
                            Survey_Statistics_BarChart_Adapter adapter = new Survey_Statistics_BarChart_Adapter(Survey_Statistics_BarChart_Activity.this,Statistics_List);
                            RecyclerView.LayoutManager manager = new LinearLayoutManager(Survey_Statistics_BarChart_Activity.this);
                            survey_statistics_recycleView.setLayoutManager(manager);
                            survey_statistics_recycleView.setAdapter(adapter);
                           // drawChart(Statistics_List);
                        } catch (JSONException e) {
                            Toasty.error(getApplicationContext(), response, Toast.LENGTH_LONG, true).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG, true).show();
            }
        });
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
