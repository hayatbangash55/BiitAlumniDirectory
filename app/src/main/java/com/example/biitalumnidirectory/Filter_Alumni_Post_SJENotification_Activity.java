package com.example.biitalumnidirectory;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.Groups.Select_Groups;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class Filter_Alumni_Post_SJENotification_Activity extends AppCompatActivity {

    Spinner spinner_audience;
    String selected_audience;
    public String URL;
    public RequestQueue queue;
    RecyclerView recyclerView;
    SharedReference sharedRef;
    Filter_Alumni_Post_SJENotification_Adapter adapter;

    String SJEDetail_Id = "";
    String SJEDetail_Type = "";
    String SJEDetail_Title = "";
    String System_Date = "";
    String System_Time = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_alumni_post_sje_notification);

        Intent intent = getIntent();
        SJEDetail_Id = intent.getStringExtra("SJE_Detail_Id");
        SJEDetail_Type = intent.getStringExtra("SJEDetail_Type");
        SJEDetail_Title = "(" + intent.getStringExtra("SJEDetail_Title") + ")";

        String pageTitle = "Post " + SJEDetail_Type;
        getSupportActionBar().setTitle(pageTitle);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        queue = Volley.newRequestQueue(getApplicationContext());
        URL = MyIp.ip;
        sharedRef = new SharedReference(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.INVISIBLE);


        spinner_audience = findViewById(R.id.spinner_audience);
        ArrayAdapter<CharSequence> gender_Adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.audience, android.R.layout.simple_spinner_dropdown_item);
        spinner_audience.setAdapter(gender_Adapter);
        spinner_audience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_audience = parent.getItemAtPosition(position).toString();

                if (selected_audience.equals("Public")) {
                    recyclerView.setVisibility(View.INVISIBLE);
                } else if (selected_audience.equals("Private")) {
                    recyclerView.setVisibility(View.VISIBLE);
                    get_data();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    public void get_data() {

        final ArrayList<Select_Groups> groups_list = new ArrayList<>();
        groups_list.clear();

        String query = "Groups/Select_Groups?CNIC=" + sharedRef.get_LoadUserDataByCNIC();

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);

                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);

                                String groups_id = obj.getString("Groups_Id").trim();
                                String group_name = obj.getString("Group_Name").trim();

                                Select_Groups obj1 = new Select_Groups(groups_id, null, group_name, false);
                                groups_list.add(obj1);
                            }


                            adapter = new Filter_Alumni_Post_SJENotification_Adapter(getApplicationContext(), groups_list);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


                        } catch (JSONException e) {
                            adapter = new Filter_Alumni_Post_SJENotification_Adapter(getApplicationContext(), groups_list);
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        );
        queue.add(request);
    }

    public void saveData_Privately() {

        ArrayList<Select_Groups> list = adapter.return_List();
        String SelectedGroups = "";

        for (int i = 0; i < list.size(); i++) {

            Select_Groups group = list.get(i);
            if (group.isSelected) {
                SelectedGroups += group.Groups_Id + ",";
            }
        }

        try {
            String query = "Post_SJE/Insert_Alumni_SJE_Notification_Privately?groupId=" + SelectedGroups;

            JSONObject obj = new JSONObject();
            obj.put("SJE_Detail_Id", SJEDetail_Id);
            obj.put("Admin_Approval", "Approved");
            obj.put("Posted_Time", System_Time);
            obj.put("Posted_Date", System_Date);
            obj.put("Posted_Privacy", "Private");
            obj.put("GroupMember_CNIC", sharedRef.get_LoadUserDataByCNIC());


            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST, URL + query, obj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            String Msg = response.optString("Msg");
                            String Error = response.optString("Error");

                            if (Msg.equals("Record Saved")) {
                                Toast.makeText(getApplicationContext(), Msg, Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), Error, Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            );

            queue.add(request);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    void saveData_Publically() {

        String query = "Post_SJE/Insert_SJE_Notification_Publically";

        try {

            JSONObject obj = new JSONObject();
            obj.put("SJE_Detail_Id", SJEDetail_Id);
            obj.put("Admin_Approval", "Not Approved");
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
            obj.put("Admin_Approval", "Not Approved");
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

    public void btn_onClick_addGroups(View view) {

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
            saveData_Privately();
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
