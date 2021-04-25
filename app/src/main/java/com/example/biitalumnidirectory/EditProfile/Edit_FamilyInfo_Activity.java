package com.example.biitalumnidirectory.EditProfile;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.biitalumnidirectory.MyProfile.FamilyInfo;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class Edit_FamilyInfo_Activity extends AppCompatActivity {


    public String URL;
    public RequestQueue queue;
    TextInputLayout et_fName, et_relation;
    SharedReference SharedRef;
    ImageView update_img1;
    Button btn_updateImage;
    String base64String;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_family_info);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));

        getSupportActionBar().setTitle("Edit Family Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        update_img1 = findViewById(R.id.update_img1);
        et_fName = findViewById(R.id.et_name);
        et_relation = findViewById(R.id.et_relation);
        SharedRef = new SharedReference(getApplicationContext());
        queue = Volley.newRequestQueue(getApplicationContext());
        URL = MyIp.ip;


        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        get_data(id);

        btn_updateImage = findViewById(R.id.btnupdate_image);
        btn_updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(Edit_FamilyInfo_Activity.this);
            }
        });
    }


    public void get_data(String id) {

        String query = "Student_Family_Detail/Select_Student_Family_Detail_For_Update?id=" + id;

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
                                String fimage_value = obj.getString("F_Image").trim();

                                et_fName.getEditText().setText(fName_value);
                                et_fName.getEditText().setSelection(et_fName.getEditText().getText().length());
                                et_relation.getEditText().setText(relation_value);
                                et_relation.getEditText().setSelection(et_relation.getEditText().getText().length());

                                Bitmap bitmap = ImageUtil.convertToImage(fimage_value);
                                update_img1.setImageBitmap(bitmap);
                            }


                        } catch (JSONException e) {
                            Toasty.success(getApplicationContext(), response, Toast.LENGTH_SHORT, true).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.success(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
        }
        );
        queue.add(request);
    }


    void update_Data() {

        String name = et_fName.getEditText().getText().toString();
        String relation = et_relation.getEditText().getText().toString();

        try {
            Bitmap bm = ((BitmapDrawable) update_img1.getDrawable()).getBitmap();
            base64String = ImageUtil.convertToBase64(bm);
        } catch (Exception e) {
            base64String = "";
        }


        if (name.equals("") || relation.equals("")) {

            Toasty.error(getApplicationContext(), "Fill all the Fields", Toast.LENGTH_SHORT, true).show();

            if (name.equals("")) {
                et_fName.setError("Field Required");
            } else {
                et_fName.setError(null);
            }

            if (relation.equals("")) {
                et_relation.setError("Field Required");
            } else {
                et_relation.setError(null);
            }

        } else {

            final String temp = SharedRef.get_LoadUserDataByCNIC();
            String Query = "Student_Family_Detail/Update_Select_Student_Family_Detail";

            try {
                JSONObject obj = new JSONObject();
                obj.put("id", id);
                obj.put("CNIC", temp);
                obj.put("F_Name", name);
                obj.put("Relation", relation);
                obj.put("F_Image", base64String);



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
