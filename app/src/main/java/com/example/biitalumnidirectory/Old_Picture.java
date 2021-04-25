package com.example.biitalumnidirectory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Old_Picture extends Activity {

    public String URL;
    public RequestQueue queue;
    ImageView update_img1;
    String base64String;
    SharedReference SharedRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_picture);

        queue = Volley.newRequestQueue(getApplicationContext());
        URL = MyIp.ip;
        update_img1 = findViewById(R.id.update_img1);
        SharedRef = new SharedReference(getApplicationContext());

        update_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(Old_Picture.this);
            }
        });
    }

    public void btn_upload(View view) {
        update_Data();
    }


    void update_Data() {

        final String temp = "8120344020925";

        String query = "Student_Detail/Update_Old_Image";

        Bitmap bm = ((BitmapDrawable) update_img1.getDrawable()).getBitmap();
        base64String = ImageUtil.convertToBase64(bm);

        String values = "{ \"cnic\":\"" + temp + "\"," +
                " \"img\":\"" + base64String + "\" }";


        JsonObjectRequest request = null;
        try {
            request = new JsonObjectRequest(
                    Request.Method.PUT, URL + query, new JSONObject(values),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String Msg = response.optString("Msg");
                            String Error = response.optString("Error");

                            if (Msg.equals("Image Uploaded")) {
                                Toast.makeText(getApplicationContext(), Msg, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), Error, Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });


        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        queue.add(request);

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
                        //   base64String = ImageUtil.convertToBase64(selectedImage);
                    }
                    break;

                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        try {
                            Bitmap bm = MediaStore.Images.Media.getBitmap(Old_Picture.this.getContentResolver(), selectedImage);
                            update_img1.setImageBitmap(bm);
                            //     base64String = ImageUtil.convertToBase64(bm);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }

    public void btn_mainActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}

