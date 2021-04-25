package com.example.biitalumnidirectory;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.ChangeStudentStatus.AddAlumni_Fragment;
import com.example.biitalumnidirectory.Events.EventMenu_Fragment;
import com.example.biitalumnidirectory.Jobs.JobMenu_Fragment;
import com.example.biitalumnidirectory.MyProfile.FamilyInfo;
import com.example.biitalumnidirectory.MyProfile.FamilyInfo_Adapter;
import com.example.biitalumnidirectory.Survey.SurveyMenu_Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class Admin_DashBoard_Fragment extends Fragment {

    CardView event_Menu, jobMenu, surveyMenu, changeStudentStatusMenu;
    ImageView edit_image;
    TextView admin_name;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;
    ImageView profile_image;

    public Admin_DashBoard_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_admin_dash_board, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Admin DashBoard");

        admin_name = v.findViewById(R.id. admin_name);
        profile_image = v.findViewById(R.id.profile_image);
        event_Menu = v.findViewById(R.id.event_Menu);
        jobMenu = v.findViewById(R.id.jobMenu);
        surveyMenu = v.findViewById(R.id.surveyMenu);
        edit_image = v.findViewById(R.id.edit_image);
        changeStudentStatusMenu = v.findViewById(R.id.changeStudentStatusMenu);
        queue = Volley.newRequestQueue(getContext());
        URL = MyIp.ip;
        SharedRef = new SharedReference(getContext());

        edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Admin_Image_Upload.class);
                startActivity(intent);
            }
        });

        event_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EventMenu_Fragment()).commit();
            }
        });


        jobMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new JobMenu_Fragment()).commit();
            }
        });

        surveyMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SurveyMenu_Fragment()).commit();
            }
        });


        changeStudentStatusMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddAlumni_Fragment()).commit();
            }
        });

        get_data();

        return v;
    }

    public void get_data() {

        final String temp = SharedRef.get_LoadUserDataByCNIC();

        String query = "Admin_Detail/Select_Admin_Detail?cnic=" + temp;

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arr = new JSONArray(response);

                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject obj = arr.getJSONObject(i);

                                String Name = obj.getString("Name").trim();
                                String Image = obj.getString("Image").trim();

                                Bitmap bitmap = ImageUtil.convertToImage(Image);
                                profile_image.setImageBitmap(bitmap);
                                admin_name.setText(Name);

                            }


                        } catch (JSONException e) {
                            Toasty.success(getContext(), response, Toast.LENGTH_SHORT, true).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.success(getContext(), error.getMessage(), Toast.LENGTH_SHORT, true).show();

            }
        }
        );
        queue.add(request);
    }
}
