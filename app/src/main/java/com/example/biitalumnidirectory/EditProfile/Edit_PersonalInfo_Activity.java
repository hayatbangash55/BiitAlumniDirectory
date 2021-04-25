package com.example.biitalumnidirectory.EditProfile;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.ImageUtil;
import com.example.biitalumnidirectory.MainActivity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class Edit_PersonalInfo_Activity extends AppCompatActivity {

    DatePickerDialog datePicker;
    Spinner spinner_maritialStatus;
    String selected_maritialStatus;
    ArrayAdapter<CharSequence> maritialStatus_Adapter;
    TextInputLayout et_userName, et_City, et_officePhoneNo, et_secondaryEmail, et_address, et_dateOfBirth;
    ImageView update_img1;
    Button btn_updateImage, btnupdate_email, btnupdate_number;
    TextView tv_phoneNo, tv_Email;
    String base64String;
    Context mContext;
    SharedReference SharedRef;
    public String URL;
    public RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setTitle("Update Personal Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        update_img1 = findViewById(R.id.update_img1);
        et_userName = findViewById(R.id.et_userName);
        tv_phoneNo = findViewById(R.id.tv_phoneNo);
        tv_Email = findViewById(R.id.tv_email);
        et_City = findViewById(R.id.et_City);
        et_officePhoneNo = findViewById(R.id.et_officePhoneNo);
        et_secondaryEmail = findViewById(R.id.et_secondaryEmail);
        et_address = findViewById(R.id.et_address);
        et_dateOfBirth = findViewById(R.id.et_dateOfBirth);

        queue = Volley.newRequestQueue(getApplicationContext());
        URL = MyIp.ip;
        SharedRef = new SharedReference(getApplicationContext());
        mContext = getApplicationContext();


        btn_updateImage = findViewById(R.id.btnupdate_image);
        btn_updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(Edit_PersonalInfo_Activity.this);
            }
        });

        btnupdate_email = findViewById(R.id.btnupdate_email);
        btnupdate_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EmailVerification1_forPersonalInfo_Activity.class);
                startActivity(intent);
            }
        });
        btnupdate_number = findViewById(R.id.btnupdate_number);
        btnupdate_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NumberVerification1.class);
                startActivity(intent);
            }
        });


        et_dateOfBirth.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                datePicker = new DatePickerDialog(Edit_PersonalInfo_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //   eText.setText(dayOfMonth + "/" + (month) + "/" + year);
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.MONTH, monthOfYear);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                String f = df.format(c.getTime());
                                et_dateOfBirth.getEditText().setText(f);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });


        spinner_maritialStatus = findViewById(R.id.spinner_maritialStatus);
        maritialStatus_Adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.maritialStatus, android.R.layout.simple_spinner_dropdown_item);
        spinner_maritialStatus.setAdapter(maritialStatus_Adapter);
        spinner_maritialStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_maritialStatus = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        get_data();
    }


    public void get_data() {

        final String temp = SharedRef.get_LoadUserDataByCNIC();

        String query = "Student_Detail/Select_For_PersonalInfo?cnic=" + temp;

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);
                            JSONObject obj = arr.getJSONObject(0);

                            String maritialStatus_value = obj.getString("Maritial_Status").trim();
                            String phoneNumber_value = obj.getString("Phone_No").trim();
                            String primaryEmail_value = obj.getString("Primary_Email").trim();
                            String studentName_value = obj.getString("Student_Name").trim();
                            String city_value = obj.getString("City").trim();
                            String officePhoneNo_value = obj.getString("Office_No").trim();
                            String secondaryEmail_value = obj.getString("Secondary_Email").trim();
                            String address_value = obj.getString("Address").trim();
                            String dateOfBirth_value = obj.getString("DOB").trim();
                            String newImage_value = obj.getString("New_Image");


                            String v2 = maritialStatus_value;
                            int spinnerPosition2 = maritialStatus_Adapter.getPosition(v2);
                            if (spinnerPosition2 > 0) {
                                spinner_maritialStatus.setSelection(spinnerPosition2);
                            }

                            tv_phoneNo.setText(phoneNumber_value);
                            tv_Email.setText(primaryEmail_value);
                            et_userName.getEditText().setText(studentName_value);
                            et_userName.getEditText().setSelection(et_userName.getEditText().getText().length());
                            et_City.getEditText().setText(city_value);
                            et_City.getEditText().setSelection(et_City.getEditText().getText().length());
                            et_officePhoneNo.getEditText().setText(officePhoneNo_value);
                            et_officePhoneNo.getEditText().setSelection(et_officePhoneNo.getEditText().getText().length());
                            et_secondaryEmail.getEditText().setText(secondaryEmail_value);
                            et_secondaryEmail.getEditText().setSelection(et_secondaryEmail.getEditText().getText().length());
                            et_address.getEditText().setText(address_value);
                            et_address.getEditText().setSelection(et_address.getEditText().getText().length());
                            et_dateOfBirth.getEditText().setText(dateOfBirth_value);
                            et_dateOfBirth.getEditText().setSelection(et_dateOfBirth.getEditText().getText().length());

                            Bitmap bitmap = ImageUtil.convertToImage(newImage_value);
                            update_img1.setImageBitmap(bitmap);


                        } catch (JSONException e) {
                            Toasty.error(getApplicationContext(), response, Toast.LENGTH_LONG, false).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG, false).show();
            }
        }
        );
        queue.add(request);
    }

    void update_Data() {

        final String temp = SharedRef.get_LoadUserDataByCNIC();

        String Query = "Student_Detail/Update_PersonalInfo";

        try {
            Bitmap bm = ((BitmapDrawable) update_img1.getDrawable()).getBitmap();
            base64String = ImageUtil.convertToBase64(bm);
        } catch (Exception e) {
            base64String = "";
        }

        String name = et_userName.getEditText().getText().toString();
        String city = et_City.getEditText().getText().toString();
        String OfficeNo = et_officePhoneNo.getEditText().getText().toString();
        String secondaryEmail = et_secondaryEmail.getEditText().getText().toString();
        String Address = et_address.getEditText().getText().toString();
        String dateOfBirth = et_dateOfBirth.getEditText().getText().toString();

        if (selected_maritialStatus.equals("Select Maritial Status")) {
            selected_maritialStatus = "";
        }

        if (name.equals("") || city.equals("") || Address.equals("") || dateOfBirth.equals("") ||  base64String.equals("")) {

            Toasty.error(getApplicationContext(), "Fill all the Fields", Toast.LENGTH_SHORT, true).show();

            if (base64String.equals("")) {
                Toasty.error(getApplicationContext(), "Upload Your profile pic", Toast.LENGTH_LONG, true).show();
            }

            if (name.equals("")) {
                et_userName.setError("Field Required");
            } else {
                et_userName.setError(null);
            }

            if (city.equals("")) {
                et_City.setError("Field Required");
            } else {
                et_City.setError(null);
            }

            if (Address.equals("")) {
                et_address.setError("Field Required");
            } else {
                et_address.setError(null);
            }

            if (dateOfBirth.equals("")) {
                et_dateOfBirth.setError("Field Required");
            } else {
                et_dateOfBirth.setError(null);
            }

        } else {

            JSONObject obj = new JSONObject();
            try {
                obj.put("CNIC", temp);
                obj.put("Name", name);
                obj.put("Secondary_Email", secondaryEmail);
                obj.put("Office_No", OfficeNo);
                obj.put("Address", Address);
                obj.put("Maritial_Status", selected_maritialStatus);
                obj.put("City", city);
                obj.put("DOB", dateOfBirth);
                obj.put("NewImage", base64String);


                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.PUT, URL + Query, obj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String Msg = response.optString("Msg");
                                String Error = response.optString("Error");

                                if (Msg.equals("Record Updated")) {
                                    Toasty.success(getApplicationContext(), Msg, Toast.LENGTH_SHORT, true).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                } else {
                                    Toasty.error(getApplicationContext(), Error, Toast.LENGTH_LONG, true).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG, true).show();
                    }
                });
                queue.add(request);

            } catch (JSONException e) {
                Toasty.error(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG, true).show();
            }


        }
    }

    private void selectImage(Context context) {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {

                    Intent gallery = new Intent();
                    gallery.setType("image/*");
                    gallery.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(gallery, "Select Picture"), 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        update_img1.setImageBitmap(selectedImage);
                        //              base64String = ImageUtil.convertToBase64(selectedImage);
                    }
                    break;

                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        try {
                            Bitmap bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                            update_img1.setImageBitmap(bm);
                            base64String = ImageUtil.convertToBase64(bm);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_button, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saveButton:
                update_Data();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}
