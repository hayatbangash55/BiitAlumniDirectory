package com.example.biitalumnidirectory.Survey;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.google.android.material.textfield.TextInputLayout;

public class CreateSurvey1_Activity extends AppCompatActivity {

    TextInputLayout et_totalQuestions, et_SurveyTitle, et_surveyDescription;
    SharedReference SharedRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_survey);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Create Survey");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        SharedRef = new SharedReference(this);
        et_SurveyTitle = findViewById(R.id.et_SurveyTitle);
        et_totalQuestions = findViewById(R.id.et_totalQuestions);
        et_surveyDescription = findViewById(R.id.et_surveyDescription);
    }

    public void btn_onClick_Next(View view) {

        String surveyTitle = et_SurveyTitle.getEditText().getText().toString().trim();
        String totalQuestion = et_totalQuestions.getEditText().getText().toString().trim();
        String surveyDescription = et_surveyDescription.getEditText().getText().toString().trim();

        if (surveyTitle.equals("") || totalQuestion.equals("")) {
            if (surveyTitle.equals("")) {
                et_SurveyTitle.setError("Field Required");
            } else {
                et_SurveyTitle.setError(null);
            }

            if (totalQuestion.equals("")) {
                et_totalQuestions.setError("Field Required");
            } else {
                et_totalQuestions.setError(null);
            }

        } else {
            finish();
            Intent i = new Intent(getApplicationContext(), CreateSurvey2_Activity.class);
            i.putExtra("surveyTitle", surveyTitle);
            i.putExtra("totalQuestion", Integer.parseInt(totalQuestion));
            i.putExtra("surveyDescription", surveyDescription);
            startActivity(i);
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

    public void btn_onClick_Cancel(View view) {
        finish();
    }
}
