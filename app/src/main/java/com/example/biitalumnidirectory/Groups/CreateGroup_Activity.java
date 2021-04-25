package com.example.biitalumnidirectory.Groups;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class CreateGroup_Activity extends AppCompatActivity {

    public String URL;
    public RequestQueue queue;
    EditText et_groupName;
    SharedReference SharedRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);


        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Create Group");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        SharedRef = new SharedReference(getApplicationContext());
        queue = Volley.newRequestQueue(getApplicationContext());
        URL = MyIp.ip;

        et_groupName = findViewById(R.id.et_groupName);


    }


    public void save_Data() {

        final String temp = SharedRef.get_LoadUserDataByCNIC();
        String groupName = et_groupName.getText().toString().trim();

        String query = "Groups/Insert_createGroup?cnic=" + temp + "&groupName=" + groupName;


        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, URL + query, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String Msg = response.optString("Msg");
                        String Error = response.optString("Error");

                        if (Msg.equals("Group Created")) {
                            Toasty.success(CreateGroup_Activity.this, Msg, Toast.LENGTH_SHORT, true).show();
                            finish();
                            Intent intent = new Intent(CreateGroup_Activity.this, Groups_Activity.class);
                            startActivity(intent);
                        } else {
                            Toasty.error(CreateGroup_Activity.this, Error, Toast.LENGTH_SHORT, true).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(CreateGroup_Activity.this, error.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
        });
        queue.add(request);
    }


    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(CreateGroup_Activity.this, Groups_Activity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saveButton:
                save_Data();
                break;
            case android.R.id.home:
                finish();
                Intent intent = new Intent(CreateGroup_Activity.this, Groups_Activity.class);
                startActivity(intent);

                break;
        }

        return true;
    }
}
