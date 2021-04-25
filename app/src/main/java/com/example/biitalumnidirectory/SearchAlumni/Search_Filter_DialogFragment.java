package com.example.biitalumnidirectory.SearchAlumni;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

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

import es.dmoral.toasty.Toasty;

public class Search_Filter_DialogFragment extends AppCompatDialogFragment {

    Spinner spinner_discipline, spinner_session,spinner_studentType;
    String selected_discipline, selected_session,selected_studentType;
    EditText et_sessionYear;
    CheckBox cb_myClass;
    SharedReference SharedRef;
    String myClass = "false", mySession = "", myDiscipline = "", mySection = "";
    public String URL;
    public RequestQueue queue;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.search_view_filter_dialog, null);

        et_sessionYear = view.findViewById(R.id.et_sessionYear);
        cb_myClass = view.findViewById(R.id.cb_myClass);
        SharedRef = new SharedReference(getContext());
        queue = Volley.newRequestQueue(getContext());
        URL = MyIp.ip;


        spinner_discipline = view.findViewById(R.id.spinner_discipline);
        ArrayAdapter<CharSequence> discipline_Adapter = ArrayAdapter.createFromResource(getContext(), R.array.Discipline, android.R.layout.simple_spinner_dropdown_item);
        spinner_discipline.setAdapter(discipline_Adapter);
        spinner_discipline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_discipline = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner_session = view.findViewById(R.id.spinner_session);
        ArrayAdapter<CharSequence> semester_Adapter = ArrayAdapter.createFromResource(getContext(), R.array.Session, android.R.layout.simple_spinner_dropdown_item);
        spinner_session.setAdapter(semester_Adapter);
        spinner_session.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_session = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_studentType = view.findViewById(R.id.spinner_studentType);
        ArrayAdapter<CharSequence> studentType_Adapter = ArrayAdapter.createFromResource(getContext(), R.array.studentType, android.R.layout.simple_spinner_dropdown_item);
        spinner_studentType.setAdapter(studentType_Adapter);
        spinner_studentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_studentType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        get_data();


        builder.setView(view)
                .setTitle("Filter Search")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getDialog().dismiss();

                    }
                })
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        search();
                        getDialog().dismiss();
                    }
                });
        return builder.create();
    }

    void search() {
        String sessionYear = et_sessionYear.getText().toString().trim();
        SearchAlumni_Fragment fragment = (SearchAlumni_Fragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (selected_studentType.equals("Current Student")){
            selected_studentType = "Incomplete";
        }
        if (selected_studentType.equals("Alumni")){
            selected_studentType = "Completed";
        }

        if (sessionYear.equals("")) {
            sessionYear = "SelectSessionYear";
        }
        if (selected_discipline.equals("Select Discipline")) {
            selected_discipline = "SelectDiscipline";
        }
        if (selected_session.equals("Select Session")) {
            selected_session = "SelectSession";
        }
        if (cb_myClass.isChecked()) {
            get_data();
            myClass = "true";
        }

        // SelectSectionYearSpring

        fragment.get_data(selected_discipline, sessionYear, selected_session, myClass, mySession, myDiscipline, mySection,selected_studentType);
    }


    // GET MY CURRENT CLASS
    public void get_data() {

        String mycnic = SharedRef.get_LoadUserDataByCNIC();

        String query = "Student_Detail/Select_For_Search_for_MyClass?cnic=" + mycnic;

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);
                            JSONObject obj = arr.getJSONObject(0);

                            myDiscipline = obj.getString("Discipline").trim();
                            mySection = obj.getString("Section").trim();
                            mySession = obj.getString("Session").trim();
                        } catch (JSONException ex) {
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
