package com.example.biitalumnidirectory.Survey;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biitalumnidirectory.MainActivity;
import com.example.biitalumnidirectory.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyMenu_Fragment extends Fragment {

    Intent intent;
    CardView postedSurvey, createSurvey, unpostedSurvey;

    public SurveyMenu_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_survey_menu, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Survey Menu");

        createSurvey = v.findViewById(R.id.createSurvey);
        createSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), CreateSurvey1_Activity.class);
                startActivity(intent);
            }
        });

        unpostedSurvey = v.findViewById(R.id.unpostedSurvey);
        unpostedSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), UnPosted_Survey_Activity.class);
                startActivity(intent);
            }
        });


        postedSurvey = v.findViewById(R.id.postedSurvey);
        postedSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), Posted_Survey_Activity.class);
                startActivity(intent);
            }
        });




        return v;

    }

}
