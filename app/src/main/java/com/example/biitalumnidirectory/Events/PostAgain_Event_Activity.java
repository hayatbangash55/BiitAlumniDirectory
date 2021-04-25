package com.example.biitalumnidirectory.Events;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.Filter_Admin_Post_SJENotification_Activity;
import com.example.biitalumnidirectory.Filter_Alumni_Post_SJENotification_Activity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class PostAgain_Event_Activity extends AppCompatActivity {

    DatePickerDialog datePicker;
    TimePickerDialog timePicker;
    TextInputLayout et_startingDate, et_endingDate, et_eventTime, et_eventTitle, et_eventDescription, et_eventVenue;
    TextView tv_startingDate, tv_endingDate, tv_eventTime, tv_eventTitle, tv_eventVenue;
    public String URL;
    public RequestQueue queue;
    String SJE_Detail_Id = "";
    Button btn_submit;
    SharedReference SharedRef;
    String id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Post Event Again");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        et_eventTime = findViewById(R.id.et_eventTime);
        et_startingDate = findViewById(R.id.et_startingDate);
        et_endingDate = findViewById(R.id.et_endingDate);
        et_eventTitle = findViewById(R.id.et_eventTitle);
        et_eventDescription = findViewById(R.id.et_eventDescription);
        et_eventVenue = findViewById(R.id.et_eventVenue);
        tv_eventTime = findViewById(R.id.tv_eventTime);
        tv_startingDate = findViewById(R.id.tv_startingDate);
        tv_endingDate = findViewById(R.id.tv_endingDate);
        tv_eventTitle = findViewById(R.id.tv_eventTitle);
        tv_eventVenue = findViewById(R.id.tv_eventVenue);
        btn_submit = findViewById(R.id.btn_submit);
        SharedRef = new SharedReference(getApplicationContext());
        queue = Volley.newRequestQueue(this);
        URL = MyIp.ip;


        Intent intent = getIntent();
        id = intent.getStringExtra("EventId");
        get_data();

        SpannableStringBuilder ssb;
        ForegroundColorSpan fcsRed = new ForegroundColorSpan(Color.parseColor("#FF0000"));

        String Title = "Event Title *";
        ssb = new SpannableStringBuilder(Title);
        ssb.setSpan(fcsRed, 12, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_eventTitle.setText(ssb);

        String Location = "Venue Detail *";
        ssb = new SpannableStringBuilder(Location);
        ssb.setSpan(fcsRed, 13, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_eventVenue.setText(ssb);

        String startingDate = "Starting Date *";
        ssb = new SpannableStringBuilder(startingDate);
        ssb.setSpan(fcsRed, 14, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_startingDate.setText(ssb);

        String endingDate = "Ending Date *";
        ssb = new SpannableStringBuilder(endingDate);
        ssb.setSpan(fcsRed, 12, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_endingDate.setText(ssb);

        String Time = "Time *";
        ssb = new SpannableStringBuilder(Time);
        ssb.setSpan(fcsRed, 5, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_eventTime.setText(ssb);


        et_startingDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                datePicker = new DatePickerDialog(PostAgain_Event_Activity.this,
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
                                et_startingDate.getEditText().setText(f);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });

        et_endingDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                datePicker = new DatePickerDialog(PostAgain_Event_Activity.this,
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
                                et_endingDate.getEditText().setText(f);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });


        et_eventTime.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                final int hour = cldr.get(Calendar.HOUR_OF_DAY);
                final int minutes = cldr.get(Calendar.MINUTE);

                // time picker dialog
                timePicker = new TimePickerDialog(PostAgain_Event_Activity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {

                                Calendar c = Calendar.getInstance();
                                c.add(Calendar.HOUR, sHour);
                                c.add(Calendar.MINUTE, sMinute);
                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                                String f = sdf.format(c.getTime());
                                et_eventTime.getEditText().setText(f);
                            }
                        }, hour, minutes, false);
                timePicker.show();


            }
        });

    }


    public void btn_onClick_Submit(View view) {
        save_Data();
    }

    public void get_data() {

        String query = "SJE_Detail/Select_Event_Data?id=" + id;

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);
                            JSONObject obj = arr.getJSONObject(0);

                            String title_value = obj.getString("Title").trim();
                            String venue_value = obj.getString("Venue").trim();
                            String startingDate = obj.getString("Starting_Date").trim();
                            String endingDate = obj.getString("Ending_Date").trim();
                            String time_value = obj.getString("Time").trim();
                            String description_value = obj.getString("Description").trim();


                            et_eventTitle.getEditText().setText(title_value);
                            et_eventTitle.getEditText().setSelection(et_eventTitle.getEditText().getText().length());
                            et_eventVenue.getEditText().setText(venue_value);
                            et_eventVenue.getEditText().setSelection(et_eventVenue.getEditText().getText().length());
                            et_eventTime.getEditText().setText(time_value);
                            et_eventTime.getEditText().setSelection(et_eventTime.getEditText().getText().length());
                            et_startingDate.getEditText().setText(startingDate);
                            et_startingDate.getEditText().setSelection(et_startingDate.getEditText().getText().length());
                            et_endingDate.getEditText().setText(endingDate);
                            et_endingDate.getEditText().setSelection(et_endingDate.getEditText().getText().length());
                            et_eventDescription.getEditText().setText(description_value);
                            et_eventDescription.getEditText().setSelection(et_eventDescription.getEditText().getText().length());

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


    public void save_Data() {

        String eventTitle = et_eventTitle.getEditText().getText().toString().trim();
        String eventVenue = et_eventVenue.getEditText().getText().toString().trim();
        String eventTime = et_eventTime.getEditText().getText().toString().trim();
        String startingDate = et_startingDate.getEditText().getText().toString().trim();
        String endingDate = et_endingDate.getEditText().getText().toString().trim();
        String eventDescription = et_eventDescription.getEditText().getText().toString().trim();
        final String SJE_type = "Event";

        if (eventTitle.equals("") || eventVenue.equals("") || eventTime.equals("") || startingDate.equals("") || endingDate.equals("")) {

            if (eventTitle.equals("")) {
                et_eventTitle.setError("Field Required");
            } else {
                et_eventTitle.setError(null);
            }

            if (eventVenue.equals("")) {
                et_eventVenue.setError("Field Required");
            } else {
                et_eventVenue.setError(null);
            }

            if (eventTime.equals("")) {
                et_eventTime.setError("Field Required");
            } else {
                et_eventTime.setError(null);
            }

            if (startingDate.equals("")) {
                et_startingDate.setError("Field Required");
            } else {
                et_startingDate.setError(null);
            }

            if (endingDate.equals("")) {
                et_endingDate.setError("Field Required");
            } else {
                et_endingDate.setError(null);
            }

        } else {

            try {
                final String login_cnic = SharedRef.get_LoadUserDataByCNIC();
                final String login_type = SharedRef.get_UserType();
                String query = "SJE_Detail/Insert_SJE_Detail";

                JSONObject obj = new JSONObject();
                obj.put("Title", eventTitle);
                obj.put("Venue", eventVenue);
                obj.put("Time", eventTime);
                obj.put("Starting_Date", startingDate);
                obj.put("Ending_Date", endingDate);
                obj.put("Description", eventDescription);
                obj.put("SJE_Type", SJE_type);
                obj.put("Creator_CNIC", login_cnic);
                obj.put("Creator_Type", login_type);


                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST, URL + query, obj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                String Msg = response.optString("Msg");
                                String Error = response.optString("Error");

                                if (!Msg.equals("")) {

                                    SJE_Detail_Id = Msg;

                                    AlertDialog.Builder builder = new AlertDialog.Builder(PostAgain_Event_Activity.this);
                                    builder.setTitle("Post Event");
                                    builder.setCancelable(false);
                                    builder.setMessage("Do you want to post event Now or Later?");
                                    builder.setPositiveButton("Now", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            if (login_type.equalsIgnoreCase("admin")) {

                                                Intent intent = new Intent(getApplicationContext(), Filter_Admin_Post_SJENotification_Activity.class);
                                                intent.putExtra("SJE_Detail_Id", SJE_Detail_Id);
                                                intent.putExtra("SJEDetail_Type", SJE_type);
                                                intent.putExtra("SJEDetail_Title", et_eventTitle.getEditText().getText().toString().trim());
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Intent intent = new Intent(getApplicationContext(), Filter_Alumni_Post_SJENotification_Activity.class);
                                                intent.putExtra("SJE_Detail_Id", SJE_Detail_Id);
                                                intent.putExtra("SJEDetail_Type", SJE_type);
                                                intent.putExtra("SJEDetail_Title", et_eventTitle.getEditText().getText().toString().trim());
                                                startActivity(intent);
                                                finish();
                                            }

                                            dialog.dismiss();
                                        }
                                    });
                                    builder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    });
                                    builder.show();


                                } else {
                                    Toasty.error(getApplicationContext(), Error, Toast.LENGTH_SHORT, true).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT, true).show();
                    }
                }
                );

                request.setRetryPolicy(new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                queue.add(request);

            } catch (JSONException e) {
                Toasty.error(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
            }

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
