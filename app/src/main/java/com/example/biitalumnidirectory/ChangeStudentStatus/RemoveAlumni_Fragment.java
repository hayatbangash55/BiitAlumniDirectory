package com.example.biitalumnidirectory.ChangeStudentStatus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.Groups.Add_Group_Member;
import com.example.biitalumnidirectory.MainActivity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class RemoveAlumni_Fragment extends Fragment {

    SharedReference SharedRef;
    public String URL;
    public RequestQueue queue;
    RecyclerView recyclerView;
    Spinner spinner_session, spinner_discipline, spinner_section;
    ArrayAdapter<String> discipline_Adapter;
    ArrayAdapter<String> session_Adapter;
    ArrayAdapter<String> section_Adapter;

    String selected_discipline, selected_session, selected_section;
    RemoveAlumni_Adapter adapter;
    Button btn_Change_Status;
    CheckBox selectAll;




    public RemoveAlumni_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_remove_alumni, container, false);


        ((MainActivity) getActivity()).setActionBarTitle("Remove Alumni");

        selectAll = v.findViewById(R.id.selectAll);
        spinner_session = v.findViewById(R.id.spinner_session);
        spinner_discipline = v.findViewById(R.id.spinner_discipline);
        spinner_section = v.findViewById(R.id.spinner_section);
        btn_Change_Status = v.findViewById(R.id.btn_Change_Status);
        recyclerView = v.findViewById(R.id.recyclerView);
        queue = Volley.newRequestQueue(getContext());
        URL = MyIp.ip;
        SharedRef = new SharedReference(getContext());
        setSpinner_session();

        btn_Change_Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Add_Group_Member> list = adapter.returnList();
                String cnics = "";
                for (Add_Group_Member i : list) {
                    if (i.isChecked) {
                        cnics += i.CNIC + ",";
                    }
                }
                update_data(cnics);
            }
        });




        selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    load_Students(true);
                }else {
                    load_Students(false);
                }
            }
        });

        return v;
    }



    void setSpinner_session() {
        String query = "Student_Detail/Select_Session_Remove_Alumni";

        final ArrayList<String> List_Session = new ArrayList<>();

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arr = new JSONArray(response);

                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject obj = arr.getJSONObject(i);

                                String session_value = obj.getString("Session").trim();
                                List_Session.add(session_value);
                            }

                            session_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, List_Session);
                            spinner_session.setAdapter(session_Adapter);
                            spinner_session.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selected_session = parent.getItemAtPosition(position).toString();
                                    //                                   selected_session = selected_session.replace(" ","");
                                    setSpinner_discipline();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });

                        } catch (JSONException e) {
                            Toasty.error(getContext(), response, Toast.LENGTH_SHORT, true).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(getContext(), error.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
        });
        queue.add(request);
    }

    void setSpinner_discipline() {
        String query = "Student_Detail/Select_Discipline_Remove_Alumni?Session=" + selected_session;

        final ArrayList<String> List_Discipline = new ArrayList<>();

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arr = new JSONArray(response);

                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject obj = arr.getJSONObject(i);

                                String discipline_value = obj.getString("Discipline").trim();
                                List_Discipline.add(discipline_value);
                            }

                            discipline_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, List_Discipline);
                            spinner_discipline.setAdapter(discipline_Adapter);
                            spinner_discipline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selected_discipline = parent.getItemAtPosition(position).toString();
                                    setSpinner_section();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });

                        } catch (JSONException e) {
                            Toasty.error(getContext(), response, Toast.LENGTH_SHORT, true).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(getContext(), error.getMessage(), Toast.LENGTH_SHORT, true).show();

            }
        }
        );
        queue.add(request);
    }

    void setSpinner_section() {
        String query = "Student_Detail/Select_Section_Remove_Alumni?Session=" + selected_session + "&Discipline=" + selected_discipline;

        final ArrayList<String> List_Section = new ArrayList<>();

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arr = new JSONArray(response);

                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject obj = arr.getJSONObject(i);

                                String section_value = obj.getString("Section").trim();
                                List_Section.add(section_value);
                            }

                            section_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, List_Section);
                            spinner_section.setAdapter(section_Adapter);
                            spinner_section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selected_section = parent.getItemAtPosition(position).toString();
                                    load_Students(false);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });

                        } catch (JSONException e) {
                            Toasty.error(getContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(getContext(), error.getMessage(), Toast.LENGTH_SHORT, true).show();

            }
        }
        );
        queue.add(request);
    }

    void load_Students(final Boolean selectAll_check) {
        String query = "Student_Detail/Select_Student_For_Change_Status_Remove_Alumni?Session=" + selected_session + "&Discipline=" + selected_discipline + "&Section=" + selected_section;

        final ArrayList<Add_Group_Member> List_Students = new ArrayList<>();

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


                                if(selectAll_check) {
                                    Add_Group_Member obj1 = new Add_Group_Member(reg_no, name, discipline, section, semester, cnic, newImage, oldImage, true);
                                    List_Students.add(obj1);
                                }
                                else {
                                    Add_Group_Member obj1 = new Add_Group_Member(reg_no, name, discipline, section, semester, cnic, newImage, oldImage, false);
                                    List_Students.add(obj1);
                                }

                            }

                            adapter = new RemoveAlumni_Adapter(getContext(), List_Students);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        } catch (JSONException e) {
                            List_Students.clear(); //it is in catch becasue if no record comes
                            adapter = new RemoveAlumni_Adapter(getContext(), List_Students);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            Toasty.error(getContext(), response, Toast.LENGTH_SHORT, true).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(getContext(), error.getMessage(), Toast.LENGTH_SHORT, true).show();

            }
        }
        );
        queue.add(request);
    }

    void update_data(String cnics) {
        String query = "Student_Detail/update_Student_Degree_Status_Remove_Alumni?cnics=" + cnics;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT, URL + query, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String Msg = response.optString("Msg");
                            String Error = response.optString("Error");

                            if (Msg.equals("Record Updated")) {
                                Toast.makeText(getContext(), Msg, Toast.LENGTH_LONG).show();
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                        new RemoveAlumni_Fragment()).commit();
                            } else {
                                Toast.makeText(getContext(), Error, Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }
}
