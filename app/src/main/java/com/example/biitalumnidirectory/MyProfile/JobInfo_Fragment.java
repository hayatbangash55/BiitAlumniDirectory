package com.example.biitalumnidirectory.MyProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.EditProfile.Add_JobInfo_Activity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class JobInfo_Fragment extends Fragment {

    public RecyclerView recyclerView;
    private JobInfo_Adapter adapter_jobInfo;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;
    FloatingActionButton fAB_addJob;
    Context context;


    public JobInfo_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_job_info, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        queue = Volley.newRequestQueue(getContext());
        URL = MyIp.ip;
        SharedRef = new SharedReference(getContext());
        context = getContext();

        fAB_addJob = view.findViewById(R.id.fAB_addJob);
        fAB_addJob.hide();
        if (SharedRef.get_EditProfile().equals(true)) {
            fAB_addJob.show();
        }
        fAB_addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Add_JobInfo_Activity.class);
                startActivity(intent);
            }
        });


        get_data();

        return view;
    }

    public void get_data() {

        final String temp = SharedRef.get_LoadUserDataByCNIC();

        String query = "Student_Job_Detail/Select_Job_Detail?cnic=" + temp;

        final ArrayList<JobInfo> List_jobInfo = new ArrayList<>();

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);

                                String id_value = obj.getString("Stu_Job_Id").trim();
                                String designation_value = obj.getString("Designation").trim();
                                String organization_value = obj.getString("Organization").trim();
                                String startingYear_value = obj.getString("Starting_Year").trim();
                                String endingYear_value = obj.getString("Ending_Year").trim();

                                JobInfo edu_obj = new JobInfo(id_value, designation_value, organization_value, startingYear_value, endingYear_value);
                                List_jobInfo.add(edu_obj);
                            }

                            //Init adapter
                            adapter_jobInfo = new JobInfo_Adapter(context, List_jobInfo, getChildFragmentManager());
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter_jobInfo);


                        } catch (JSONException e) {
                            Toasty.error(context, response, Toast.LENGTH_LONG, false).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(context, error.getMessage(), Toast.LENGTH_LONG, false).show();
            }
        }
        );
        queue.add(request);
    }

}
