package com.example.biitalumnidirectory.Jobs;


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
public class JobMenu_Fragment extends Fragment {

    Intent intent;
    CardView postedJobs, newjob, unPostedJob;


    public JobMenu_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_job_menu, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Job Menu");

        postedJobs = v.findViewById(R.id.postedJobs);
        postedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), Posted_Job_Activity.class);
                startActivity(intent);
            }
        });

        newjob = v.findViewById(R.id.newJob);
        newjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), CreateJob_Activity.class);
                startActivity(intent);
            }
        });

        unPostedJob = v.findViewById(R.id.unPostedJob);
        unPostedJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), UnPosted_Job_Activity.class);
                startActivity(intent);
            }
        });

        return v;


    }
}
