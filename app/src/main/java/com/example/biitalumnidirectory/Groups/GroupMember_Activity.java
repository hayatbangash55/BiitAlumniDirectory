package com.example.biitalumnidirectory.Groups;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.MainActivity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.MyProfile.ShowProfile_Fragment;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class GroupMember_Activity extends AppCompatActivity {

    public String URL;
    public RequestQueue queue;
    RecyclerView recyclerView;
    String groupId = "";
    String Status, Group_Name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        queue = Volley.newRequestQueue(getApplicationContext());
        URL = MyIp.ip;

        Intent intent = getIntent();
        groupId = intent.getStringExtra("GroupId");
        Status = intent.getStringExtra("Status");
        Group_Name = intent.getStringExtra("Group_Name");
        getSupportActionBar().setTitle("Group Members of ("+Group_Name+")");
        get_data(false);

    }

    public void get_data(final Boolean deleteCheck) {

        final ArrayList<GroupMember> groupMember_list = new ArrayList<>();
        groupMember_list.clear();

        String query = "GroupMember/Select_GroupMembers?id=" + groupId;

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);

                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);

                                String groupMemberCnic = obj.getString("GroupMember_CNIC").trim();
                                String reg_no = obj.getString("Reg_No").trim();
                                String name = obj.getString("Student_Name").trim();
                                String discipline = obj.getString("Discipline").trim();
                                String semester = obj.getString("Semester").trim();
                                String section = obj.getString("Section").trim();
                                String newImage = obj.getString("New_Image").trim();
                                String oldImage = obj.getString("Old_Image").trim();
                                String Status = obj.getString("Status").trim();

                                GroupMember obj1 = new GroupMember(groupMemberCnic, reg_no, name, discipline, section, semester, newImage, oldImage,Status);
                                groupMember_list.add(obj1);
                            }


                            GroupMember_Adapter adapter = new GroupMember_Adapter(GroupMember_Activity.this, groupMember_list, groupId, deleteCheck);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(GroupMember_Activity.this));


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
    public boolean onCreateOptionsMenu(Menu menu) {
        if(Status.equals("Admin")) {
            getMenuInflater().inflate(R.menu.group_member_option_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.Add_Group_Member){
            Intent intent = new Intent(GroupMember_Activity.this, Add_GroupMembers_Activity.class);
            intent.putExtra("GroupId", groupId);
            startActivity(intent);
        }

        if(item.getItemId() == R.id.Delete_group_Member) {
            get_data(true);
        }

            if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }


}
