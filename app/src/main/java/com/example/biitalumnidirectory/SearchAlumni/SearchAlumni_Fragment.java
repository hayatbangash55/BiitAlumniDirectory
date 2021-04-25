package com.example.biitalumnidirectory.SearchAlumni;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.MainActivity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class SearchAlumni_Fragment extends Fragment {

    ImageView img_filter;
    public String URL;
    public RequestQueue queue;
    SearchView searchView;
    RecyclerView recyclerView;
    String value;
    SharedReference SharedRef;

    public SearchAlumni_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_search_alumni, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Search Alumni");

        recyclerView = v.findViewById(R.id.recyclerView);
        queue = Volley.newRequestQueue(getContext());
        URL = MyIp.ip;
        SharedRef = new SharedReference(getContext());

        searchView = v.findViewById(R.id.search_view);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                value = query;
                get_data("SelectDiscipline", "SelectSessionYear", "SelectSession", "false", "", "", "","ALL");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //  get_data(newText);
                img_filter.setVisibility(View.GONE);
                return false;
            }
        });

        img_filter = v.findViewById(R.id.filter_icon);
        img_filter.setVisibility(View.GONE);
        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search_Filter_DialogFragment obj = new Search_Filter_DialogFragment();
                obj.show(getChildFragmentManager(), "Search dialog");
            }
        });

        return v;
    }


    public void get_data(String discipline, String sessionYear, String session, String myClass, String mySession, String myDiscipline, String mySection, String myStudentType) {

        String mycnic = SharedRef.get_LoadUserDataByCNIC();

        String query = "Student_Detail/Select_SearchAlumni?value=" + value + "&cnic=" + mycnic + "&discipline=" + discipline + "&sessionYear=" + sessionYear+"&session=" + session + "&myClass=" + myClass + "&mySession=" + mySession + "&myDiscipline=" + myDiscipline + "&mySection=" + mySection + "&myStudentType=" + myStudentType;

        final ArrayList<Search_Class> search_list = new ArrayList<>();
        search_list.clear();

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);

                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);

                                String reg_no = obj.getString("Reg_No").trim();
                                String name = obj.getString("Student_Name").trim();
                                String discipline = obj.getString("Discipline").trim();
                                String semester = obj.getString("Semester").trim();
                                String section = obj.getString("Section").trim();
                                String cnic = obj.getString("CNIC").trim();
                                String newImage = obj.getString("New_Image").trim();
                                String oldImage = obj.getString("Old_Image").trim();
                                String status = obj.getString("Status").trim();


                                Search_Class obj1 = new Search_Class(reg_no, name, discipline, section, semester, cnic, newImage, oldImage, status);
                                search_list.add(obj1);
                                img_filter.setVisibility(View.VISIBLE);

                            }


                            Search_RecycleView_Adapter adapter = new Search_RecycleView_Adapter(getContext(), search_list, getFragmentManager());
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


                        } catch (JSONException e) {

                            Search_RecycleView_Adapter adapter = new Search_RecycleView_Adapter(getContext(), search_list, getFragmentManager());
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            Toasty.error(getContext(), response, Toast.LENGTH_LONG, false).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(getContext(), error.toString(), Toast.LENGTH_LONG, false).show();
            }
        }
        );
        queue.add(request);
    }

}
