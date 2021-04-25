package com.example.biitalumnidirectory.MyProfile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.ImageUtil;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class Gallery_Fragment extends Fragment {

    private GridView Gridview_Gallery;
    private Gallery_Adapter adapter_gallery;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;
    FloatingActionButton fAB_addEducation;
    Context context;

    public Gallery_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gallery, container, false);

        Gridview_Gallery = v.findViewById(R.id.gridLayout);
        queue = Volley.newRequestQueue(getContext());
        URL = MyIp.ip;
        SharedRef = new SharedReference(getContext());
        context = getContext();

        fAB_addEducation = v.findViewById(R.id.fAB_addEducation);
        fAB_addEducation.hide();
        if (SharedRef.get_EditProfile().equals(true)) {
            fAB_addEducation.show();
        }
        fAB_addEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(getContext());
            }
        });

        get_data();

        return v;
    }

    public void get_data() {

        final String temp = SharedRef.get_LoadUserDataByCNIC();

        String query = "Image_Gallery/Select_Image_Gallery?cnic=" + temp;

        final ArrayList<Gallery> List_Image = new ArrayList<>();

        StringRequest request = new StringRequest(
                Request.Method.GET, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arr = new JSONArray(response);


                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject obj = arr.getJSONObject(i);

                                String image_value = obj.getString("Image").trim();
                                String id_value = obj.getString("Image_Gallery_Id").trim();

                                List_Image.add(new Gallery(id_value, image_value));
                            }

                            //Init adapter
                            adapter_gallery = new Gallery_Adapter(getActivity().getApplicationContext(), List_Image);
                            Gridview_Gallery.setAdapter(adapter_gallery);


                        } catch (JSONException e) {
                            Toasty.error(context, response, Toast.LENGTH_LONG, false).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(context, error.getMessage(), Toast.LENGTH_LONG, false).show();
            }
        });
        queue.add(request);
    }

    private void selectImage(Context context) {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
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

                    //   Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //   startActivityForResult(pickPhoto, 1);//one can be replaced with any action code

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        String base64String = ImageUtil.convertToBase64(selectedImage);
                        insert_Data(base64String);
                    }
                    break;

                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        try {
                            Bitmap bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                            String base64String = ImageUtil.convertToBase64(bm);
                            insert_Data(base64String);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                    break;
            }
        }
    }

    void insert_Data(String Image) {

        final String temp = SharedRef.get_LoadUserDataByCNIC();

        String query = "Image_Gallery/Insert_Image_Gallery?cnic=" + temp;
        String values = "{ \"CNIC\":\"" + temp + "\"," +
                " \"Image\":\"" + Image + "\"}";

        JsonObjectRequest request = null;
        try {
            request = new JsonObjectRequest(
                    Request.Method.POST, URL+query, new JSONObject(values),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            String Msg = response.optString("Msg");
                            String Error = response.optString("Error");

                            if (Msg.equals("Image Uploaded")) {
                                Toasty.success(context, Msg, Toast.LENGTH_SHORT, true).show();

                            } else {
                                Toasty.error(context, Error, Toast.LENGTH_LONG, true).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toasty.error(context, error.getMessage(), Toast.LENGTH_LONG, true).show();
                }
            });
        } catch (JSONException e) {
            Toasty.error(context, e.getMessage(), Toast.LENGTH_LONG, true).show();
        }
        queue.add(request);
    }

}
