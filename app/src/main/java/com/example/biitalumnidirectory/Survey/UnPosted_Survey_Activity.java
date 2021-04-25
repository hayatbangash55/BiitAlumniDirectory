package com.example.biitalumnidirectory.Survey;

import android.os.Bundle;
import android.view.MenuItem;
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
import com.example.biitalumnidirectory.SJE_Detail;
import com.example.biitalumnidirectory.SharedReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class UnPosted_Survey_Activity extends AppCompatActivity {

    private UnPosted_Survey_Adapter adapter_survey;
    RecyclerView recyclerView;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unposted_survey);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("UnPosted Survey");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        queue = Volley.newRequestQueue(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerView);
        URL = MyIp.ip;
        SharedRef = new SharedReference(getApplicationContext());

        get_data();
    }

    public void get_data() {


        final String login_cnic = SharedRef.get_LoadUserDataByCNIC();
        String query = "SJE_Detail/Select_UnPosted_Surveys?cnic=" + login_cnic;

        final ArrayList<SJE_Detail> List_Survey = new ArrayList<SJE_Detail>();

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);

                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject obj = arr.getJSONObject(i);

                                String id_value = obj.getString("SJE_Detail_Id").trim();
                                String title_value = obj.getString("Title").trim();
                                String venue_value = obj.getString("Venue").trim();
                                String startingDate = obj.getString("Starting_Date").trim();
                                String ending_date = obj.getString("Ending_Date").trim();
                                String time_value = obj.getString("Time").trim();
                                String description_value = obj.getString("Description").trim();
                                String SJE_type_value = obj.getString("SJE_Type").trim();
                                String creatorCnic_value = obj.getString("Creator_CNIC").trim();
                                String creatorType_value = obj.getString("Creator_Type").trim();

                                SJE_Detail sjeDetail = new SJE_Detail(id_value, title_value, venue_value, startingDate,ending_date, time_value, description_value, SJE_type_value, creatorCnic_value, creatorType_value);
                                List_Survey.add(sjeDetail);
                            }

                            //Init adapter
                            adapter_survey = new UnPosted_Survey_Adapter(UnPosted_Survey_Activity.this, List_Survey);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter_survey);


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
