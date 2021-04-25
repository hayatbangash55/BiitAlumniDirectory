package com.example.biitalumnidirectory.EditProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.MainActivity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class EditAttributes_Activity extends AppCompatActivity {

    TextInputLayout et_attributes;
    public String URL;
    public RequestQueue queue;
    SharedReference ShredRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_attributes);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));

        getSupportActionBar().setTitle("Update Attribute");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        et_attributes = findViewById(R.id.et_attributes);

        queue = Volley.newRequestQueue(this);
        URL = MyIp.ip;
        ShredRef = new SharedReference(getApplicationContext());

        get_data();

    }

    public void get_data() {

        final String temp = ShredRef.get_LoadUserDataByCNIC();

        String query = "Student_Attributes/Select_For_StudentAttributes?cnic=" + temp;

        final ArrayList<String> List_attributes = new ArrayList<>();

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);
                            JSONObject obj = arr.getJSONObject(0);

                            String attributes = obj.getString("Attributes").trim();


                            et_attributes.getEditText().setText(attributes);
                            et_attributes.getEditText().setSelection(et_attributes.getEditText().getText().length());





                        } catch (JSONException e) {
                            Toasty.error(getApplicationContext(), response, Toast.LENGTH_LONG, false).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG, false).show();
            }
        }
        );
        queue.add(request);
    }

    void update_Data() {

        String title = et_attributes.getEditText().getText().toString();

        final String temp = ShredRef.get_LoadUserDataByCNIC();
        String query = "Student_Attributes/Update_Student_Attribute?cnic=" + temp;

        try {
            JSONObject obj = new JSONObject();
            obj.put("CNIC", temp);
            obj.put("Attributes", title);


            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.PUT, URL + query, obj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            String Msg = response.optString("Msg");
                            String Error = response.optString("Error");

                            if (Msg.equals("Record Updated")) {
                                Toasty.success(getApplicationContext(), Msg, Toast.LENGTH_SHORT, true).show();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            } else {
                                Toasty.error(getApplicationContext(), Error, Toast.LENGTH_LONG, true).show();

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toasty.error(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG, true).show();

                }
            });
            queue.add(request);

        } catch (JSONException e) {
            Toasty.error(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG, true).show();

        }

    }

    @Override
    public void onBackPressed() {
        finish();
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
                update_Data();
                break;
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}
