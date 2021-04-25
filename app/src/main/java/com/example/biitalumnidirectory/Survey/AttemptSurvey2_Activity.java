package com.example.biitalumnidirectory.Survey;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class AttemptSurvey2_Activity extends AppCompatActivity {

    TextView tv_question, tv_title, tvQuestion_No;
    RadioGroup rg_options;
    Button btn_submit;
    RadioButton rb_option1, rb_option2, rb_option3, rb_option4, rb_option5;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;
    String SJEDetail_Id = "";
    String SJEDetail_Title = "";
    String Posted_SJE_Id = "";
    final List<Survey_Question> questions = new ArrayList<>();
    int index = 0;
    String myAnswer = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_survey);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Show Survey");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        tv_title = findViewById(R.id.tv_title);
        tv_question = findViewById(R.id.tv_question);
        rg_options = findViewById(R.id.rg_options);
        rb_option1 = findViewById(R.id.rb_option1);
        rb_option2 = findViewById(R.id.rb_option2);
        rb_option3 = findViewById(R.id.rb_option3);
        rb_option4 = findViewById(R.id.rb_option4);
        rb_option5 = findViewById(R.id.rb_option5);
        tvQuestion_No = findViewById(R.id.tvQuestion_No);
        btn_submit = findViewById(R.id.btn_submit);
        queue = Volley.newRequestQueue(this);
        URL = MyIp.ip;
        SharedRef = new SharedReference(getApplicationContext());

        Intent intent = getIntent();
        SJEDetail_Id = intent.getStringExtra("SJE_Detail_Id");
        SJEDetail_Title = "(" + intent.getStringExtra("SJEDetail_Title") + ")";
        Posted_SJE_Id = intent.getStringExtra("Posted_SJE_Id");
        tv_title.setText(SJEDetail_Title);


        rg_options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (R.id.rb_option1 == checkedId) {
                    myAnswer = rb_option1.getText().toString();
                }
                if (R.id.rb_option2 == checkedId) {
                    myAnswer = rb_option2.getText().toString();
                }
                if (R.id.rb_option3 == checkedId) {
                    myAnswer = rb_option3.getText().toString();
                }
                if (R.id.rb_option4 == checkedId) {
                    myAnswer = rb_option4.getText().toString();
                }
                if (R.id.rb_option5 == checkedId) {
                    myAnswer = rb_option5.getText().toString();
                }
            }
        });

        get_data();
    }

    public void get_data() {

        String query = "Survey_Questions/Select_Survey_Questions?sje_detail_id=" + SJEDetail_Id;

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);

                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject obj = arr.getJSONObject(i);

                                Survey_Question obj1 = new Survey_Question();

                                obj1.Question_No = obj.getString("Question_No").trim();
                                obj1.Question = obj.getString("Question").trim();
                                obj1.Option1 = obj.getString("Option_1").trim();
                                obj1.Option2 = obj.getString("Option_2").trim();
                                obj1.Option3 = obj.getString("Option_3").trim();
                                obj1.Option4 = obj.getString("Option_4").trim();
                                obj1.Option5 = obj.getString("Option_5").trim();
                                obj1.Answer = "";

                                questions.add(obj1);
                            }
                            LoadQuestions();

                        } catch (JSONException e) {
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

    public void LoadQuestions() {

        rg_options.clearCheck();

        Survey_Question q = questions.get(index);
        String question_no = "Question No. " + q.Question_No + "/" + questions.size();
        tvQuestion_No.setText(question_no);
        tv_question.setText(q.Question);
        rb_option1.setText(q.Option1);
        rb_option2.setText(q.Option2);
        rb_option3.setText(q.Option3);
        rb_option4.setText(q.Option4);
        rb_option5.setText(q.Option5);


        if (!q.Option3.equals("")) {
            rb_option3.setVisibility(View.VISIBLE);
            rb_option3.setText(q.Option3);
        } else {
            rb_option3.setVisibility(View.GONE);
        }

        if (!q.Option4.equals("")) {
            rb_option4.setVisibility(View.VISIBLE);
            rb_option4.setText(q.Option4);
        } else {
            rb_option4.setVisibility(View.GONE);
        }

        if (!q.Option5.equals("")) {
            rb_option5.setVisibility(View.VISIBLE);
            rb_option5.setText(q.Option5);
        } else {
            rb_option5.setVisibility(View.GONE);
        }
    }

    public void btn_onClick_Submit(View view) {

        if (myAnswer.equalsIgnoreCase("")) {
            Toasty.error(getApplicationContext(), "Please Select Answer", Toasty.LENGTH_LONG, true).show();
        } else {

            //continuing question
            if (index < (questions.size() - 1) ){
                save_Answer();
                index++;
                LoadQuestions();
                myAnswer = "";

                if (index == (questions.size() - 1) ){
                    btn_submit.setText("Finish");
                }
            }

            //for last question
            else if (index == (questions.size() - 1) ){
                save_Answer();
                Insert_Attempted_Survey();
            }
        }



    }

    void save_Answer() {

        Survey_Question q = questions.get(index);
        q.Answer = myAnswer;
        questions.set(index, q);

        myAnswer = "";
    }

    void Insert_Attempted_Survey() {

        try {

            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String date = dateFormat.format(c.getTime());

            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
            String time = timeFormat.format(c.getTime());

            String query = "Attempt_Survey/Insert_Attempt_Survey";

            JSONObject obj = new JSONObject();
            obj.put("SJE_Detail_Id", SJEDetail_Id);
            obj.put("CNIC", SharedRef.get_LoadUserDataByCNIC());
            obj.put("Attempt_Date", date);
            obj.put("Attempt_Time", time);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST, MyIp.ip+query,
                    obj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String Msg = response.optString("Msg");
                    String Error = response.optString("Error");

                    if (!Msg.equals("")) {

                        for (int i = 0; i < questions.size(); i++) {
                            Insert_Attempted_suervey_Answer(Msg, i);
                        }

                        Toasty.success(getApplicationContext(), "Survey Submitted", Toasty.LENGTH_LONG, true).show();
                        Intent intent = new Intent(AttemptSurvey2_Activity.this, MainActivity.class);
                        startActivity(intent);

                    } else {
                        Toasty.error(getApplicationContext(), Error, Toasty.LENGTH_LONG, true).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //    Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (JSONException e) {
            //
        }
    }

    void Insert_Attempted_suervey_Answer(String Attempt_Survey_Id, int Question_No) {

        Survey_Question q = questions.get(Question_No);
        try {

            JSONObject obj = new JSONObject();
            obj.put("Attempt_Survey_Id", Attempt_Survey_Id);
            obj.put("Question_No", q.Question_No);
            obj.put("Answer", q.Answer);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST, MyIp.ip + "Attempt_Survey/Insert_Attempt_Survey_Answer",
                    obj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    delete_UserNotification(Posted_SJE_Id, SharedRef.get_LoadUserDataByCNIC());
                    Toast.makeText(getApplicationContext(), "inserted", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //     Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (JSONException e) {
            //
        }
    }


    void delete_UserNotification(String val_id, String cnic) {

        String query = "Attempt_Survey/Remove_User_SurveyNotification?id=" + val_id+"&cnic="+cnic;


        StringRequest request = new StringRequest(
                Request.Method.DELETE, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.replace("\"", "").equals("Record Deleted")) {

//                            event_list.remove(position);
//                            notifyItemRemoved(position);
//                            notifyDataSetChanged();
//                            Toast.makeText(AttemptSurvey2_Activity.class, response, Toast.LENGTH_SHORT).show();
//                            Toasty.success(AttemptSurvey2_Activity.class, response, Toast.LENGTH_SHORT, true).show();

                        } else {
                            //Toasty.error(AttemptSurvey2_Activity.class, response, Toast.LENGTH_SHORT, true).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
    //            Toasty.error(AttemptSurvey2_Activity.class, error.getMessage(), Toast.LENGTH_SHORT, true).show();

            }
        }
        );
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


//    public void btn_onClick_Back(View view) {
//        btn_submit.setText("Next");
//        save_Answer();
//        index--;
//        if (index < 1) {
//            btn_Back.setVisibility(View.INVISIBLE);
//        }
//        LoadQuestions();
//    }
}
