package com.example.biitalumnidirectory.Friends;

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
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class FriendRequests_Fragment extends Fragment {


    public String URL;
    public RequestQueue queue;
    RecyclerView recyclerView;
    SharedReference sharedRef;


    public FriendRequests_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_friend_requests, container, false);

        sharedRef = new SharedReference(getContext());
        recyclerView = v.findViewById(R.id.recyclerView);
        queue = Volley.newRequestQueue(getContext());
        URL = MyIp.ip;

        get_data();

        return v;
    }


    public void get_data() {
        final ArrayList<Friend> friend_list = new ArrayList<>();
        friend_list.clear();
        String query = "Friends/Select_FriendRequests?CNIC="+sharedRef.get_LoginCNIC();

        StringRequest request = new StringRequest(
                Request.Method.GET, URL+query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);

                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);

                                String Friend_CNIC = obj.getString("CNIC").trim();
                                String reg_no = obj.getString("Reg_No").trim();
                                String name = obj.getString("Student_Name").trim();
                                String discipline = obj.getString("Discipline").trim();
                                String semester = obj.getString("Semester").trim();
                                String section = obj.getString("Section").trim();
                                String newImage = obj.getString("New_Image").trim();
                                String oldImage = obj.getString("Old_Image").trim();

                                Friend obj1 = new Friend(Friend_CNIC, reg_no, name, discipline, section, semester, newImage, oldImage);
                                friend_list.add(obj1);
                            }


                            FriendRequests_Adapter adapter = new FriendRequests_Adapter(getContext(), friend_list, getActivity().getSupportFragmentManager());
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


                        } catch (JSONException e) {
//                            Toasty.error(getContext(), response, Toast.LENGTH_LONG, true).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(getContext(), error.getMessage(), Toast.LENGTH_LONG, true).show();

            }
        }
        );
        queue.add(request);
    }
}
