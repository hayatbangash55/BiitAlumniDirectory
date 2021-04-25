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
import com.example.biitalumnidirectory.EditProfile.Add_PublicationInfo_Activity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class PublicationInfo_Fragment extends Fragment {

    public RecyclerView recyclerView;
    private PublicationInfo_Adapter adapter_publicationInfo;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;
    FloatingActionButton fAB_addPublication;
    Context context;


    public PublicationInfo_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_publication_info, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);
        queue = Volley.newRequestQueue(getContext());
        URL = MyIp.ip;
        SharedRef = new SharedReference(getContext());
        context = getContext();

        fAB_addPublication = view.findViewById(R.id.fAB_addEducation);
        fAB_addPublication.hide();
        if (SharedRef.get_EditProfile().equals(true)) {
            fAB_addPublication.show();
        }
        fAB_addPublication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Add_PublicationInfo_Activity.class);
                startActivity(intent);
            }
        });

        get_data();

        return view;
    }


    public void get_data() {

        final String temp = SharedRef.get_LoadUserDataByCNIC();

        String query = "Student_Publication_Detail/Select_Publication_Detail?cnic=" + temp;

        final ArrayList<PublicationInfo> List_PublicationInfo = new ArrayList<>();

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);

                                String id_value = obj.getString("Stu_Pub_Id").trim();
                                String title = obj.getString("Title").trim();
                                String publisher = obj.getString("Publisher").trim();
                                String publicationDate = obj.getString("Publication_Date").trim();
                                String publicationUrl = obj.getString("Publication_URL").trim();
                                String description = obj.getString("Description").trim();

                                PublicationInfo pub_obj = new PublicationInfo(id_value, title, publisher, publicationDate, publicationUrl, description);
                                List_PublicationInfo.add(pub_obj);
                            }

                            //Init adapter
                            adapter_publicationInfo = new PublicationInfo_Adapter(context, List_PublicationInfo, getChildFragmentManager());
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter_publicationInfo);


                        } catch (JSONException e) {
                            Toasty.error(context, response, Toast.LENGTH_LONG, true).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(context, error.getMessage(), Toast.LENGTH_LONG, true).show();
            }
        }
        );
        queue.add(request);
    }
}
