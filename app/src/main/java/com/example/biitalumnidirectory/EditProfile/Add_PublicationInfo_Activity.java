package com.example.biitalumnidirectory.EditProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.MainActivity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class Add_PublicationInfo_Activity extends AppCompatActivity {

    DatePickerDialog datePicker;
    TextInputLayout et_Title, et_publisher, et_publicationDate, et_publicationURL, et_description;
    public String URL;
    public RequestQueue queue;
    String id = "";
    SharedReference ShredRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_publication_activity);


        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));


        getSupportActionBar().setTitle("Add Publication Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        et_Title = findViewById(R.id.et_Title);
        et_publisher = findViewById(R.id.et_publisher);
        et_publicationDate = findViewById(R.id.et_publicationDate);
        et_publicationURL = findViewById(R.id.et_publicationURL);
        et_description = findViewById(R.id.et_description);
        queue = Volley.newRequestQueue(this);
        URL = MyIp.ip;
        ShredRef = new SharedReference(getApplicationContext());

        et_publicationDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                datePicker = new DatePickerDialog(Add_PublicationInfo_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //   eText.setText(dayOfMonth + "/" + (month) + "/" + year);
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.MONTH, monthOfYear);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                String f = df.format(c.getTime());
                                et_publicationDate.getEditText().setText(f);
                            }
                        }, year, month, day);
                datePicker.show();

            }
        });


    }



    public void save_Data() {

        String title = et_Title.getEditText().getText().toString();
        String publisher = et_publisher.getEditText().getText().toString();
        String publicationDate = et_publicationDate.getEditText().getText().toString();
        String publicationURL = et_publicationURL.getEditText().getText().toString();
        String description = et_description.getEditText().getText().toString();

        if (title.equals("")) {

            if (title.equals("")) {
                et_Title.setError("Field Required");
            } else {
                et_Title.setError(null);
            }

        } else {

            final String temp = ShredRef.get_LoadUserDataByCNIC();
            String query = "Student_Publication_Detail/Insert_Publication_Detail";

            try {
                JSONObject obj = new JSONObject();
                obj.put("Stu_Pub_Id", id);
                obj.put("CNIC", temp);
                obj.put("Title", title);
                obj.put("Publisher", publisher);
                obj.put("Publication_Date", publicationDate);
                obj.put("Publication_URL", publicationURL);
                obj.put("Description", description);

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST, URL + query, obj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String Msg = response.optString("Msg");
                                String Error = response.optString("Error");

                                if (Msg.equals("Record Saved")) {
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
                }
                );

                queue.add(request);
            } catch (JSONException e) {
                Toasty.error(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG, true).show();
            }

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
                save_Data();
                break;
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }

}
