package com.example.biitalumnidirectory.Alumni_Notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

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

public class Alumni_Notification_Activity extends AppCompatActivity {

    private Alumni_Notification_Adapter adapter_notification;
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
        getSupportActionBar().setTitle("Alumni Notification");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        SharedRef = new SharedReference(getApplicationContext());
        queue = Volley.newRequestQueue(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerView);
        URL = MyIp.ip;

        get_data();
    }



    public void get_data() {

        final String temp = SharedRef.get_LoadUserDataByCNIC();

        String query = "Post_SJE/Select_MyProfile_Notification?cnic="+temp;

        final ArrayList<Alumni_Notification> list_Alumni_notification = new ArrayList<Alumni_Notification>();

        StringRequest request = new StringRequest(
                Request.Method.GET, URL+query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);

                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject obj = arr.getJSONObject(i);

                                String id_value = obj.getString("SJE_Detail_Id").trim();
                                String title_value = obj.getString("Title").trim();
                                String PostedSJEId_Value = obj.getString("Posted_SJE_Id").trim();
                                String date_value = obj.getString("Posted_Date").trim();
                                String time_value = obj.getString("Posted_Time").trim();
                                String SeenStatus_value= obj.getString("Seen_Status").trim();
                                String SJEtype_value = obj.getString("SJE_Type").trim();


                                Alumni_Notification alumniNotification = new Alumni_Notification(id_value, title_value,PostedSJEId_Value,date_value, time_value, SeenStatus_value, SJEtype_value);
                                list_Alumni_notification.add(alumniNotification);
                            }

                            //Init adapter
                            adapter_notification = new Alumni_Notification_Adapter(getApplicationContext(), list_Alumni_notification);
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
