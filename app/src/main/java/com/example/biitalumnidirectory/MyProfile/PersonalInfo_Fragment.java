package com.example.biitalumnidirectory.MyProfile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.EditProfile.Edit_PersonalInfo_Activity;
import com.example.biitalumnidirectory.ImageUtil;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class PersonalInfo_Fragment extends Fragment {

    TextView tv_userName, tv_phone_title, tv_phone_value, tv_officePhoneNo_title, tv_officePhoneNo_value, tv_primaryEmail_title,
            tv_primaryEmail_value, tv_secondaryEmail_title, tv_secondaryEmail_value, tv_residence_title, tv_residence_value,
            tv_address_title, tv_address_value, tv_gender_title, tv_gender_value, tv_martital_Status_title, tv_martital_Status_value,
            tv_dateOfBirth_title, tv_dateOfBirth_value, tv_discipline_title, tv_discipline_value, tv_degree_Completion_title,
            tv_degree_Completion_value, tv_session_title, tv_session_value;

    ImageView edit_image, new_profile_Img, old_profile_Img;
    SharedReference SharedRef;
    public String URL;
    public RequestQueue queue;
    Context context;

    public PersonalInfo_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_personal_info, container, false);

        queue = Volley.newRequestQueue(getContext());
        SharedRef = new SharedReference(getContext());
        URL = MyIp.ip;
        context = getContext();

        tv_userName = v.findViewById(R.id.tv_userName);
        new_profile_Img = v.findViewById(R.id.new_profile_Img);
        old_profile_Img = v.findViewById(R.id.old_profile_Img);
        tv_phone_title = v.findViewById(R.id.tv_phone_title);
        tv_primaryEmail_title = v.findViewById(R.id.tv_primaryEmail_title);
        tv_residence_title = v.findViewById(R.id.tv_residence_title);
        tv_officePhoneNo_title = v.findViewById(R.id.tv_officePhoneNo_title);
        tv_secondaryEmail_title = v.findViewById(R.id.tv_secondaryEmail_title);
        tv_address_title = v.findViewById(R.id.tv_address_title);
        tv_gender_title = v.findViewById(R.id.tv_gender_title);
        tv_martital_Status_title = v.findViewById(R.id.tv_martital_Status_title);
        tv_dateOfBirth_title = v.findViewById(R.id.tv_dateOfBirth_title);
        tv_discipline_title = v.findViewById(R.id.tv_discipline_title);
        tv_degree_Completion_title = v.findViewById(R.id.tv_degree_Completion_title);
        tv_session_title = v.findViewById(R.id.tv_session_title);

        tv_phone_value = v.findViewById(R.id.tv_phone_value);
        tv_primaryEmail_value = v.findViewById(R.id.tv_primaryEmail_value);
        tv_residence_value = v.findViewById(R.id.tv_residence_value);
        tv_officePhoneNo_value = v.findViewById(R.id.tv_officePhoneNo_value);
        tv_secondaryEmail_value = v.findViewById(R.id.tv_secondaryEmail_value);
        tv_address_value = v.findViewById(R.id.tv_address_value);
        tv_gender_value = v.findViewById(R.id.tv_gender_value);
        tv_martital_Status_value = v.findViewById(R.id.tv_martital_Status_value);
        tv_dateOfBirth_value = v.findViewById(R.id.tv_dateOfBirth_value);
        tv_discipline_value = v.findViewById(R.id.tv_discipline_value);
        tv_degree_Completion_value = v.findViewById(R.id.tv_degree_Completion_value);
        tv_session_value = v.findViewById(R.id.tv_session_value);

        tv_officePhoneNo_value.setVisibility(View.GONE);
        tv_officePhoneNo_title.setVisibility(View.GONE);
        tv_secondaryEmail_title.setVisibility(View.GONE);
        tv_secondaryEmail_value.setVisibility(View.GONE);
        tv_martital_Status_title.setVisibility(View.GONE);
        tv_martital_Status_value.setVisibility(View.GONE);
        tv_degree_Completion_title.setVisibility(View.GONE);
        tv_degree_Completion_value.setVisibility(View.GONE);


        edit_image = (ImageView) v.findViewById(R.id.edit_image);
        edit_image.setVisibility(View.GONE);
        if (SharedRef.get_EditProfile().equals(true)) {
            edit_image.setVisibility(View.VISIBLE);
        }
        edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Edit_PersonalInfo_Activity.class);
                startActivity(intent);
            }
        });

        get_data();
        return v;
    }


    public void get_data() {

        final String temp = SharedRef.get_LoadUserDataByCNIC();

        String query = "Student_Detail/Select_For_PersonalInfo?cnic="+temp;

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);
                            JSONObject obj = arr.getJSONObject(0);

                            String studentName_value = obj.getString("Student_Name").trim();
                            String phoneNumber_value = obj.getString("Phone_No").trim();
                            String city_value = obj.getString("City").trim();
                            String primaryEmail_value = obj.getString("Primary_Email").trim();
                            String officePhoneNo_value = obj.getString("Office_No").trim();
                            String secondaryEmail_value = obj.getString("Secondary_Email").trim();
                            String address_value = obj.getString("Address").trim();
                            String gender_value = obj.getString("Gender").trim();
                            String maritialStatus_value = obj.getString("Maritial_Status").trim();
                            String dateOfBirth_value = obj.getString("DOB").trim();
                            String discipline_value = obj.getString("Discipline").trim();
                            String section_value = obj.getString("Section").trim();
                            String degreeCompletion_value = obj.getString("Degree_Completion").trim();
                            String session_value = obj.getString("Session").trim();
                            String newImage_value = obj.getString("New_Image");
                            String oldImage_value = obj.getString("Old_Image");

                            Bitmap bitmap = ImageUtil.convertToImage(newImage_value);
                            new_profile_Img.setImageBitmap(bitmap);
                            bitmap = ImageUtil.convertToImage(oldImage_value);
                            old_profile_Img.setImageBitmap(bitmap);


                            if (!officePhoneNo_value.equals("") && !officePhoneNo_value.equals("null")) {
                                tv_officePhoneNo_title.setVisibility(View.VISIBLE);
                                tv_officePhoneNo_value.setVisibility(View.VISIBLE);
                                tv_officePhoneNo_value.setText(officePhoneNo_value);
                            }
                            if (!secondaryEmail_value.equals("") && !secondaryEmail_value.equals("null")) {
                                tv_secondaryEmail_value.setText(secondaryEmail_value);
                                tv_secondaryEmail_title.setVisibility(View.VISIBLE);
                                tv_secondaryEmail_value.setVisibility(View.VISIBLE);
                            }

                            if (!maritialStatus_value.equals("") && !maritialStatus_value.equals("null")) {
                                tv_martital_Status_value.setText(maritialStatus_value);
                                tv_martital_Status_title.setVisibility(View.VISIBLE);
                                tv_martital_Status_value.setVisibility(View.VISIBLE);

                            }
                            if (!degreeCompletion_value.equals("") && !degreeCompletion_value.equals("null")) {
                                tv_degree_Completion_value.setText(degreeCompletion_value);
                                tv_degree_Completion_title.setVisibility(View.VISIBLE);
                                tv_degree_Completion_value.setVisibility(View.VISIBLE);
                            }

                            tv_userName.setText(studentName_value);
                            tv_primaryEmail_value.setText(primaryEmail_value);
                            tv_residence_value.setText(city_value);
                            tv_phone_value.setText(phoneNumber_value);
                            tv_gender_value.setText(gender_value);
                            tv_dateOfBirth_value.setText(dateOfBirth_value);
                            tv_discipline_value.setText(discipline_value);
                            tv_degree_Completion_value.setText(degreeCompletion_value);
                            tv_address_value.setText(address_value);
                            tv_session_value.setText(session_value);


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