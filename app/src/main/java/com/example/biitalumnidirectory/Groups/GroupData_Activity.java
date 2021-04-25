package com.example.biitalumnidirectory.Groups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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

public class GroupData_Activity extends AppCompatActivity {

    String Groups_Id, Group_Name, Status;
    public String URL;
    public RequestQueue queue;
    SharedReference sharedRef;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_data);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        queue = Volley.newRequestQueue(getApplicationContext());
        URL = MyIp.ip;
        sharedRef = new SharedReference(this);

        Intent intent = getIntent();
        Groups_Id = intent.getStringExtra("GroupId");
        Status = intent.getStringExtra("Status");
        Group_Name = intent.getStringExtra("Group_Name");
        getSupportActionBar().setTitle("Group Data of ("+Group_Name+")");

        get_data();
    }

    public void get_data() {

        final ArrayList<GroupData> groups_list = new ArrayList<>();
        groups_list.clear();

        String query = "GroupData/Select_GroupData?groupId=" + Groups_Id;

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            int total_date = 1;
                            String date = "";
                            JSONArray arr = new JSONArray(response);
                            date = arr.getJSONObject(0).getString("Posted_Date");

                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);

                                String SJE_Detail_Id = obj.getString("SJE_Detail_Id").trim();
                                String Student_Name = obj.getString("Student_Name").trim();
                                String Posted_Date = obj.getString("Posted_Date").trim();
                                String Posted_Time = obj.getString("Posted_Time").trim();
                                String Title = obj.getString("Title").trim();
                                String SJE_Type = obj.getString("SJE_Type").trim();
                                if(!date.equals(Posted_Date)){
                                    total_date++;
                                    date = Posted_Date;
                                }
                                GroupData obj1 = new GroupData(SJE_Detail_Id, Student_Name, Posted_Date,Posted_Time,Title,SJE_Type);
                                groups_list.add(obj1);
                            }


                            GroupData_Adapter adapter = new GroupData_Adapter(GroupData_Activity.this, groups_list,total_date);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(GroupData_Activity.this));


                        } catch (JSONException e) {
                            Toasty.error(GroupData_Activity.this, response, Toast.LENGTH_SHORT, true).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(GroupData_Activity.this, error.getMessage(), Toast.LENGTH_SHORT, true).show();

            }
        }
        );
        queue.add(request);
    }


    void Delete_Group(String id){
        String query="Groups/Remove_Group?id="+id;

        StringRequest request = new StringRequest(
                Request.Method.DELETE,URL+query , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.replace("\"", "").equals("Record Deleted")) {
                    finish();
                    Intent intent = new Intent(GroupData_Activity.this,Groups_Activity.class);
                    startActivity(intent);
                    Toasty.success(GroupData_Activity.this, response, Toast.LENGTH_SHORT, true).show();
                } else {
                    Toasty.error(GroupData_Activity.this, response, Toast.LENGTH_SHORT, true).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(GroupData_Activity.this, error.getMessage(), Toast.LENGTH_SHORT, true).show();

            }
        });

        queue.add(request);
    }


    void Delete_LeaveGroup(String id, String membercnic) {
        String query = "GroupMember/Remove_GroupMember?id=" + id + "&membercnic=" + membercnic;

        StringRequest request = new StringRequest(
                Request.Method.DELETE, URL + query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.replace("\"", "").equals("Record Deleted")) {
                    finish();
                    Intent intent = new Intent(GroupData_Activity.this,Groups_Activity.class);
                    startActivity(intent);
                    Toasty.success(GroupData_Activity.this, response, Toast.LENGTH_SHORT, true).show();

                } else {
                    Toasty.error(GroupData_Activity.this, response, Toast.LENGTH_SHORT, true).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(GroupData_Activity.this, error.getMessage(), Toast.LENGTH_SHORT, true).show();

            }
        });

        queue.add(request);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(Status.equals("Admin")){
            menu.findItem(R.id.leave_group).setEnabled(false);
        }
        else {
            menu.findItem(R.id.delete_group).setEnabled(false);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.group_member){
            Intent intent = new Intent(GroupData_Activity.this,GroupMember_Activity.class);
            intent.putExtra("GroupId", Groups_Id);
            intent.putExtra("Status", Status);
            intent.putExtra("Group_Name",Group_Name);
            startActivity(intent);
        }




        if(item.getItemId() == R.id.leave_group) {
            Delete_LeaveGroup(Groups_Id,sharedRef.get_LoadUserDataByCNIC());
        }


            if(item.getItemId() == R.id.delete_group) {
            AlertDialog.Builder builder = new AlertDialog.Builder(GroupData_Activity.this);
            builder.setTitle("Delete Group");
            builder.setCancelable(false);
            builder.setMessage("Do you really want to delete group ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Delete_Group(Groups_Id);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();

        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent intent = new Intent(GroupData_Activity.this, Groups_Activity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(GroupData_Activity.this, Groups_Activity.class);
        startActivity(intent);
    }
}
