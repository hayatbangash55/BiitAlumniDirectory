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
import com.example.biitalumnidirectory.EditProfile.Add_EducationInfo_Activity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class EducationInfo_Fragment extends Fragment {

    public RecyclerView recyclerView;
    private EducationInfo_Adapter adapter_educationInfo;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;
    FloatingActionButton fAB_addEducation;
    Context context;


    public EducationInfo_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_education_info, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);
        queue = Volley.newRequestQueue(getContext());
        URL = MyIp.ip;
        SharedRef = new SharedReference(getContext());
        context = getContext();

        fAB_addEducation = view.findViewById(R.id.fAB_addEducation);
        fAB_addEducation.hide();
        if (SharedRef.get_EditProfile().equals(true)) {
            fAB_addEducation.show();
        }
        fAB_addEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Add_EducationInfo_Activity.class);
                startActivity(intent);
            }
        });

        get_data();

        return view;
    }


    public void get_data(){

        final String temp = SharedRef.get_LoadUserDataByCNIC();

        String query = "Student_Educational_Detail/Select_Education_Detail?cnic=" + temp;

        final ArrayList<EducationInfo> List_EducationInfo = new ArrayList<>();

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);

                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject obj = arr.getJSONObject(i);

                                String id_value = obj.getString("Stu_Edu_Id").trim();
                                String degreeName_value = obj.getString("Degree_Name").trim();
                                String instituteName_value = obj.getString("Institute_Name").trim();
                                String completionYear_value = obj.getString("Completion_Year").trim();
                                String majorSubject = obj.getString("Major_Subject").trim();

                                EducationInfo edu_obj = new EducationInfo(id_value, degreeName_value, instituteName_value, completionYear_value, majorSubject);
                                List_EducationInfo.add(edu_obj);
                            }

                            //Init adapter
                            adapter_educationInfo = new EducationInfo_Adapter(context, List_EducationInfo, getChildFragmentManager());
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter_educationInfo);


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
