package com.example.biitalumnidirectory.MyProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.EditProfile.Add_FamilyInfo_Activity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class FamilyInfo_Fragment extends Fragment {

    private FamilyInfo_Adapter adapter_familyInfo;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;
    FloatingActionButton fAB_addFamily;
    Context context;
    public RecyclerView recyclerView;


    public FamilyInfo_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_family_info, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        queue = Volley.newRequestQueue(getContext());
        URL = MyIp.ip;
        SharedRef = new SharedReference(getContext());
        context = getContext();

        fAB_addFamily = view.findViewById(R.id.fAB_addFamily);
        fAB_addFamily.hide();
        if (SharedRef.get_EditProfile().equals(true)) {
            fAB_addFamily.show();
        }
        fAB_addFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Add_FamilyInfo_Activity.class);
                startActivity(intent);
            }
        });

        get_data();

        return view;
    }

    public void get_data() {

        final String temp = SharedRef.get_LoadUserDataByCNIC();

        String query = "Student_Family_Detail/Select_Student_Family_Detail?cnic=" + temp;

        final ArrayList<FamilyInfo> List_FamilyInfo = new ArrayList<>();

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arr = new JSONArray(response);

                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject obj = arr.getJSONObject(i);

                                String fName_value = obj.getString("F_Name").trim();
                                String relation_value = obj.getString("Relation").trim();
                                String id_value = obj.getString("Stu_Family_Id").trim();
                                String fimage_value = obj.getString("F_Image").trim();

                                FamilyInfo edu_obj = new FamilyInfo(id_value, fimage_value, fName_value, relation_value);
                                List_FamilyInfo.add(edu_obj);
                            }

                            adapter_familyInfo = new FamilyInfo_Adapter(context, List_FamilyInfo, getChildFragmentManager());
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter_familyInfo);


                        } catch (JSONException e) {
                            Toasty.success(context, response, Toast.LENGTH_SHORT, true).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.success(context, error.getMessage(), Toast.LENGTH_SHORT, true).show();

            }
        }
        );
        queue.add(request);
    }

}
