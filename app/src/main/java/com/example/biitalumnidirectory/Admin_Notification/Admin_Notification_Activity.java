package com.example.biitalumnidirectory.Admin_Notification;

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
import com.example.biitalumnidirectory.SharedReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class Admin_Notification_Activity extends AppCompatActivity {

    private Admin_Notification_Adapter adapter_notification;
    RecyclerView recyclerView;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Admin Notification");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        SharedRef = new SharedReference(getApplicationContext());
        queue = Volley.newRequestQueue(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerView);
        URL = MyIp.ip;

        get_data();
    }



    public void get_data() {

        String query = "Post_SJE/Select_Admin_Notification";

        final ArrayList<Admin_Notification> list_Admin_notification = new ArrayList<Admin_Notification>();

        StringRequest request = new StringRequest(
                Request.Method.GET, URL+query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);

                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject obj = arr.getJSONObject(i);

                                String sje_detail_id = obj.getString("SJE_Detail_Id").trim();
                                String posted_sje_id = obj.getString("Posted_SJE_Id").trim();
                                String creator_cnic = obj.getString("Creator_CNIC").trim();
                                String sje_type = obj.getString("SJE_Type").trim();
                                String title_value = obj.getString("Title").trim();
                                String posted_time = obj.getString("Posted_Time").trim();
                                String posted_date = obj.getString("Posted_Date").trim();
                                String studentName= obj.getString("Student_Name").trim();

                                Admin_Notification adminNotification = new Admin_Notification(sje_detail_id,posted_sje_id, creator_cnic, sje_type, title_value, posted_time, posted_date, studentName);
                                list_Admin_notification.add(adminNotification);
                            }

                            //Init adapter
                            adapter_notification = new Admin_Notification_Adapter(Admin_Notification_Activity.this, list_Admin_notification);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter_notification);


                        } catch (JSONException e) {
                            Toasty.success(getApplicationContext(), response, Toast.LENGTH_SHORT, true).show();
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
