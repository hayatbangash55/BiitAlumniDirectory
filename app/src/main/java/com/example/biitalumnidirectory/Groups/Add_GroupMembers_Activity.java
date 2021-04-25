package com.example.biitalumnidirectory.Groups;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

public class Add_GroupMembers_Activity extends AppCompatActivity {

    SearchView searchView;
    Add_GroupMembers_Adapter adapter;
    SharedReference SharedRef;
    public String URL;
    public RequestQueue queue;
    RecyclerView recyclerView;
    final ArrayList<Add_Group_Member> list = new ArrayList<>();
    String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_members);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Add Group Members");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        queue = Volley.newRequestQueue(getApplicationContext());
        URL = MyIp.ip;
        SharedRef = new SharedReference(getApplicationContext());
        searchView = findViewById(R.id.search_view);
        searchView.setIconified(false);

        Intent intent = getIntent();
        groupId = intent.getStringExtra("GroupId");


        get_data();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

               try {
                   ArrayList<Add_Group_Member> members = adapter.returnList();
                   if (members.size() > 0 || newText.trim().equals("")) {
                       adapter.getFilter().filter(newText);
                   }
               }catch (Exception ex){
                   Toasty.error(Add_GroupMembers_Activity.this,"No Member",Toasty.LENGTH_LONG,true).show();
               }

                return false;
            }
        });

    }


    public void get_data() {

        String mycnic = SharedRef.get_LoadUserDataByCNIC();
        String query = "GroupMember/Select_Add_Group_Member?CNIC=" + mycnic + "&Group_Id=" + groupId;
        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);

                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);

                                String reg_no = obj.getString("Reg_No").trim();
                                String name = obj.getString("Student_Name").trim();
                                String discipline = obj.getString("Discipline").trim();
                                String semester = obj.getString("Semester").trim();
                                String section = obj.getString("Section").trim();
                                String cnic = obj.getString("CNIC").trim();
                                String newImage = obj.getString("New_Image").trim();
                                String oldImage = obj.getString("Old_Image").trim();


                                Add_Group_Member obj1 = new Add_Group_Member(reg_no, name, discipline, section, semester, cnic, newImage, oldImage, false);
                                list.add(obj1);
                            }

                            adapter = new Add_GroupMembers_Adapter(getApplicationContext(), list);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                        } catch (JSONException e) {

                            Add_GroupMembers_Adapter adapter = new Add_GroupMembers_Adapter(Add_GroupMembers_Activity.this, list);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(Add_GroupMembers_Activity.this));

                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
    }

    public void btn_onClick_addMembers(View view) {
        save_Data();
    }

    public void save_Data() {

        ArrayList<Add_Group_Member> members = adapter.returnList();
        String cnics = "";
        for (int i = 0; i < members.size(); i++) {
            Add_Group_Member member = members.get(i);
            if (member.isChecked) {
                cnics += member.CNIC + ",";
            }
        }

        String query = "GroupMember/Insert_Add_Group_Member?groupId=" + groupId + "&friend_cnic=" + cnics;

        JsonObjectRequest request = null;
        try {
            request = new JsonObjectRequest(
                    Request.Method.POST, URL + query, new JSONObject(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            String Msg = response.optString("Msg");
                            String Error = response.optString("Error");

                            if (Msg.equals("Record Saved")) {
                                Toasty.success(Add_GroupMembers_Activity.this, Msg, Toast.LENGTH_SHORT,true).show();
                                finish();
                            } else {
                                Toasty.error(Add_GroupMembers_Activity.this, Error, Toast.LENGTH_SHORT,true).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toasty.error(Add_GroupMembers_Activity.this, error.getMessage(), Toast.LENGTH_SHORT,true).show();
                }
            });
        } catch (Exception e) {
            Toasty.error(Add_GroupMembers_Activity.this, e.getMessage(), Toast.LENGTH_SHORT,true).show();
        }
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
