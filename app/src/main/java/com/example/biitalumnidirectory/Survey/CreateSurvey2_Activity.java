package com.example.biitalumnidirectory.Survey;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.Filter_Admin_Post_SJENotification_Activity;
import com.example.biitalumnidirectory.Filter_Alumni_Post_SJENotification_Activity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class CreateSurvey2_Activity extends AppCompatActivity {

    RequestQueue queue;
    String URL;
    Spinner spinner_noOfOptions;
    TextView tv_op3, tv_op4, tv_op5, tv_questionNo;
    TextInputLayout et_question, et_Option1, et_Option2, et_Option3, et_Option4, et_Option5;
    Button btn_next;
    SharedReference SharedRef;
    String SJE_Detail_Id;

    int totalQuestion, currentQuestionNumber;
    String Survey_Title, selected_noOfOptions, surveyDescription;
    ArrayList<Survey_Question> questionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_survey_questions);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Create Survey");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        questionList = new ArrayList<>();
        queue = Volley.newRequestQueue(this);
        URL = MyIp.ip;
        SharedRef = new SharedReference(getApplicationContext());
        tv_questionNo = findViewById(R.id.tv_questionNo);
        et_question = findViewById(R.id.et_question);
        et_Option1 = findViewById(R.id.et_Option1);
        et_Option2 = findViewById(R.id.et_Option2);
        et_Option3 = findViewById(R.id.et_Option3);
        et_Option4 = findViewById(R.id.et_Option4);
        et_Option5 = findViewById(R.id.et_Option5);
        tv_op3 = findViewById(R.id.tv_op3);
        tv_op4 = findViewById(R.id.tv_op4);
        tv_op5 = findViewById(R.id.tv_op5);
        btn_next = findViewById(R.id.buNext);

        et_Option3.setVisibility(View.GONE);
        et_Option4.setVisibility(View.GONE);
        et_Option5.setVisibility(View.GONE);
        tv_op3.setVisibility(View.GONE);
        tv_op4.setVisibility(View.GONE);
        tv_op5.setVisibility(View.GONE);

        Intent i = getIntent();
        Survey_Title = i.getStringExtra("surveyTitle");
        totalQuestion = i.getIntExtra("totalQuestion", 1);
        surveyDescription = i.getStringExtra("surveyDescription");




        currentQuestionNumber = 1;
        //if totalQuestion=1 (sent from previous activity)
        if (totalQuestion == 1) {
            btn_next.setText("Finish");
        }

        spinner_noOfOptions = findViewById(R.id.spinner_noOfOptions);
        ArrayAdapter<CharSequence> semester_Adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spinner_noOfOptions, android.R.layout.simple_spinner_dropdown_item);
        spinner_noOfOptions.setAdapter(semester_Adapter);
        spinner_noOfOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selected_noOfOptions = parent.getItemAtPosition(position).toString();

                et_Option3.setVisibility(View.GONE);
                et_Option4.setVisibility(View.GONE);
                et_Option5.setVisibility(View.GONE);
                tv_op3.setVisibility(View.GONE);
                tv_op4.setVisibility(View.GONE);
                tv_op5.setVisibility(View.GONE);

                switch (selected_noOfOptions) {

                    case "3":
                        et_Option3.setVisibility(View.VISIBLE);
                        tv_op3.setVisibility(View.VISIBLE);
                        break;
                    case "4":
                        et_Option3.setVisibility(View.VISIBLE);
                        et_Option4.setVisibility(View.VISIBLE);
                        tv_op3.setVisibility(View.VISIBLE);
                        tv_op4.setVisibility(View.VISIBLE);
                        break;
                    case "5":
                        et_Option3.setVisibility(View.VISIBLE);
                        et_Option4.setVisibility(View.VISIBLE);
                        et_Option5.setVisibility(View.VISIBLE);
                        tv_op3.setVisibility(View.VISIBLE);
                        tv_op4.setVisibility(View.VISIBLE);
                        tv_op5.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }//onCreate





    public void buNextClick(View view) {

        if (currentQuestionNumber <= totalQuestion) {

            Survey_Question question = new Survey_Question();
            question.Question = et_question.getEditText().getText().toString().trim();
            question.Option1 = et_Option1.getEditText().getText().toString().trim();
            question.Option2 = et_Option2.getEditText().getText().toString().trim();
            question.Option3 = et_Option3.getEditText().getText().toString().trim();
            question.Option4 = et_Option4.getEditText().getText().toString().trim();
            question.Option5 = et_Option5.getEditText().getText().toString().trim();

            if (question.Question.equals("") || question.Option1.equals("") || question.Option2.equals("") || (question.Option3.equals("") && selected_noOfOptions.equals("3")) || (question.Option4.equals("") && selected_noOfOptions.equals("4")) || (question.Option5.equals("") && selected_noOfOptions.equals("5"))) {

                if (question.Question.equals("")) {
                    et_question.setError("Field Required");
                }{
                    et_question.setError(null);
                }

                if (question.Option1.equals("")) {
                    et_Option1.setError("Field Required");
                }else {
                    et_Option1.setError(null);
                }

                if (question.Option2.equals("")) {
                    et_Option2.setError("Field Required");
                }else {
                    et_Option2.setError(null);
                }

                if (question.Option3.equals("")) {
                    et_Option3.setError("Field Required");
                }else {
                    et_Option3.setError(null);
                }

                if (question.Option4.equals("")) {
                    et_Option4.setError("Field Required");
                }else {
                    et_Option4.setError(null);
                }

                if (question.Option5.equals("")) {
                    et_Option5.setError("Field Required");
                }else {
                    et_Option5.setError(null);
                }


            }
            else {

                questionList.add(question);

                //continuing questions
                currentQuestionNumber++;
                if (currentQuestionNumber <= totalQuestion) {
                    tv_questionNo.setText("Question No. # " + currentQuestionNumber);
                    resetFields();
                }

                // For Last Question
                if (currentQuestionNumber == totalQuestion) {
                    btn_next.setText("Finish");
                }
            }

        }


        if (currentQuestionNumber > totalQuestion) {
            Insert_Survey_Detail();
        }

    }//btnNext

    public void resetFields() {

        spinner_noOfOptions.setSelection(0);

        et_Option3.setVisibility(View.GONE);
        et_Option4.setVisibility(View.GONE);
        et_Option5.setVisibility(View.GONE);
        tv_op3.setVisibility(View.GONE);
        tv_op4.setVisibility(View.GONE);
        tv_op5.setVisibility(View.GONE);

        et_question.getEditText().setText("");
        et_Option1.getEditText().setText("");
        et_Option2.getEditText().setText("");
        et_Option3.getEditText().setText("");
        et_Option4.getEditText().setText("");
        et_Option5.getEditText().setText("");

        et_question.setError(null);
        et_Option1.setError(null);
        et_Option2.setError(null);
        et_Option3.setError(null);
        et_Option4.setError(null);
        et_Option5.setError(null);
    }

    void Insert_Survey_Detail() {
        try {

            final String login_cnic = SharedRef.get_LoadUserDataByCNIC();
            final String login_type = SharedRef.get_UserType();
            String query = "SJE_Detail/Insert_SJE_Detail";
            final String SJE_type = "Survey";

            JSONObject obj = new JSONObject();
            obj.put("Title", Survey_Title);
            obj.put("Venue", null);
            obj.put("Time", null);
            obj.put("Starting_Date", null);
            obj.put("Ending_Date", null);
            obj.put("Description", surveyDescription);
            obj.put("SJE_Type", SJE_type);
            obj.put("Creator_CNIC", login_cnic);
            obj.put("Creator_Type", login_type);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST, URL + query,
                    obj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    String Msg = response.optString("Msg");
                    String Error = response.optString("Error");

                    if (!Msg.equals("")) {

                        SJE_Detail_Id = Msg;

                        for (int i = 0; i < totalQuestion; i++) {
                            Insert_Survey_Question(SJE_Detail_Id, i);
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateSurvey2_Activity.this);
                        builder.setCancelable(false);
                        builder.setTitle("Post Survey");
                        builder.setMessage("So you want to post Survey...?");
                        builder.setPositiveButton("Now", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();

                                if (login_type.equalsIgnoreCase("admin")) {
                                    finish();
                                    Intent intent = new Intent(getApplicationContext(), Filter_Admin_Post_SJENotification_Activity.class);
                                    intent.putExtra("SJE_Detail_Id", SJE_Detail_Id);
                                    intent.putExtra("SJEDetail_Type", SJE_type);
                                    intent.putExtra("SJEDetail_Title", Survey_Title);
                                    startActivity(intent);
                                } else {
                                    finish();
                                    Intent intent = new Intent(getApplicationContext(), Filter_Alumni_Post_SJENotification_Activity.class);
                                    intent.putExtra("SJE_Detail_Id", SJE_Detail_Id);
                                    intent.putExtra("SJEDetail_Type", SJE_type);
                                    intent.putExtra("SJEDetail_Title", Survey_Title);
                                    startActivity(intent);

                                }
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
            });

            request.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(request);

        } catch (JSONException e) {
            Toasty.error(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    void Insert_Survey_Question(String Survey_Id, int Question_No) {

        try {
            String query = "Survey_Questions/Insert_Survey_Questions";

            JSONObject obj = new JSONObject();
            obj.put("SJE_Detail_Id", Survey_Id);
            obj.put("Question_No", (Question_No + 1));
            obj.put("Question", questionList.get(Question_No).Question);
            obj.put("Option1", questionList.get(Question_No).Option1);
            obj.put("Option2", questionList.get(Question_No).Option2);
            obj.put("Option3", questionList.get(Question_No).Option3);
            obj.put("Option4", questionList.get(Question_No).Option4);
            obj.put("Option5", questionList.get(Question_No).Option5);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST, URL + query,
                    obj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String Msg = response.optString("Msg");
                    String Error = response.optString("Error");

                    if (Msg.equals("Record Saved")) {
                        Toasty.success(getApplicationContext(), Msg, Toast.LENGTH_SHORT, true).show();

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

    //Back Button
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
