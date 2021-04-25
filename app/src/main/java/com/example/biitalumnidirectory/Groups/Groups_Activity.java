package com.example.biitalumnidirectory.Groups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class Groups_Activity extends AppCompatActivity {

    public String URL;
    public RequestQueue queue;
    RecyclerView recyclerView;
    SharedReference sharedRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Groups");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        sharedRef = new SharedReference(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerView);
        queue = Volley.newRequestQueue(getApplicationContext());
        URL = MyIp.ip;

        get_data();

    }



    public void get_data() {

        final ArrayList<Groups> groups_list = new ArrayList<>();
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
                                String cnic = obj.getString("GroupMember_CNIC").trim();
                                String group_name = obj.getString("Group_Name").trim();
                                String Status = obj.getString("Status").trim();

                                Groups obj1 = new Groups(groups_id, cnic, group_name,Status);
                                groups_list.add(obj1);
                            }


                            Groups_Adapter adapter = new Groups_Adapter(Groups_Activity.this, groups_list);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(Groups_Activity.this));


                        } catch (JSONException e) {
                            Toasty.error(Groups_Activity.this, response, Toast.LENGTH_SHORT, true).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(Groups_Activity.this, error.getMessage(), Toast.LENGTH_SHORT, true).show();

            }
        }
        );
        queue.add(request);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.groups_option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.create_group){
            finish();
            Intent intent = new Intent(Groups_Activity.this, CreateGroup_Activity.class);
            startActivity(intent);
        }

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }




}
