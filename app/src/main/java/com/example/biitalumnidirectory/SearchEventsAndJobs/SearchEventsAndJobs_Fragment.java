package com.example.biitalumnidirectory.SearchEventsAndJobs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class SearchEventsAndJobs_Fragment extends Fragment {

    ImageView img_filter;
    public String URL;
    public RequestQueue queue;
    SearchView searchView;
    RecyclerView recyclerView;
    SharedReference SharedRef;
    String value;
    Spinner spinner;
    String selected_choice;
    SearchEventsAndJobs_Adapter adapter;


    public SearchEventsAndJobs_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_search_events_and_jobs, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Search Events And Jobs");

        recyclerView = v.findViewById(R.id.recyclerView);
        queue = Volley.newRequestQueue(getContext());
        URL = MyIp.ip;
        SharedRef = new SharedReference(getContext());

        spinner = v.findViewById(R.id.spinner_audience);
        ArrayAdapter<CharSequence> gender_Adapter = ArrayAdapter.createFromResource(getContext(), R.array.JobEventSearch, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(gender_Adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_choice = parent.getItemAtPosition(position).toString();
                get_data();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        recyclerView = v.findViewById(R.id.recyclerView);
        queue = Volley.newRequestQueue(getContext());
        URL = MyIp.ip;
        SharedRef = new SharedReference(getContext());

        get_data();

        searchView = v.findViewById(R.id.search_view);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                value = query;
                //   get_data("SelectDiscipline", "SelectSessionYear", "SelectSession", "false", "", "", "");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //  get_data(newText);
                //  img_filter.setVisibility(View.GONE);

                ArrayList<Search_Job_Class> members = adapter.returnList();
                if (members.size() > 0 || newText.trim().equals("")) {
                    adapter.getFilter().filter(newText);
                }
                return false;
            }
        });

//        img_filter = v.findViewById(R.id.filter_icon);
//        img_filter.setVisibility(View.GONE);
//        img_filter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Search_Filter_DialogFragment obj = new Search_Filter_DialogFragment();
//                obj.show(getFragmentManager(), "Search dialog");
//            }
//        });
        return v;

    }


    public void get_data() {

        String query = "Post_SJE/Select_Public_JobEvents?Type=" + selected_choice;

        final ArrayList<Search_Job_Class> search_list = new ArrayList<>();
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

                                String SJE_Detail_Id = obj.getString("SJE_Detail_Id").trim();
                                String Title = obj.getString("Title").trim();
                                String Date = obj.getString("Posted_Date").trim();
                                String Time = obj.getString("Posted_Time").trim();
                                String sjeType = obj.getString("SJE_Type").trim();

                                Search_Job_Class obj1 = new Search_Job_Class(SJE_Detail_Id, Title, Date, Time,sjeType);
                                search_list.add(obj1);
//                                img_filter.setVisibility(View.VISIBLE);

                            }


                            adapter = new SearchEventsAndJobs_Adapter(getContext(), search_list, getFragmentManager());
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


                        } catch (JSONException e) {

                            adapter = new SearchEventsAndJobs_Adapter(getContext(), search_list, getFragmentManager());
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            Toasty.error(getContext(), response, Toast.LENGTH_SHORT, false).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(getContext(), error.toString(), Toast.LENGTH_SHORT, false).show();
            }
        });
        queue.add(request);
    }

}
