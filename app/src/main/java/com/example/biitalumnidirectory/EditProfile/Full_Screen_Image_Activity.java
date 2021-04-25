package com.example.biitalumnidirectory.EditProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.ImageUtil;
import com.example.biitalumnidirectory.MainActivity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import es.dmoral.toasty.Toasty;

public class Full_Screen_Image_Activity extends AppCompatActivity {

    SharedReference SharedRef;
    public String URL;
    public RequestQueue queue;
    Button btn_delete;
    ImageView img;
    String Image_Gallery_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gradient));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Full Screen Image");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        SharedRef = new SharedReference(this);
        queue = Volley.newRequestQueue(this);
        URL = MyIp.ip;
        img = findViewById(R.id.img);

        btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setVisibility(View.GONE);
        if (SharedRef.get_EditProfile().equals(true)) {
            btn_delete.setVisibility(View.VISIBLE);
        }

        Intent intent = getIntent();
        String image = SharedRef.get_Image();
        Image_Gallery_Id = intent.getStringExtra("Image_Gallery_Id");
        Bitmap bm = ImageUtil.convertToImage(image);
        img.setImageBitmap(bm);
    }

    public void btn_onClick_Delete(View view) {
        delete_Data(Image_Gallery_Id);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    void delete_Data(String val_id) {

        final String temp = SharedRef.get_LoadUserDataByCNIC();

        String query = "Image_Gallery/Remove_Image_Gallery?cnic=" + temp + "&id=" + val_id;


        StringRequest request = new StringRequest(
                Request.Method.DELETE, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.replace("\"","").equals("Image Deleted")) {
                            Toasty.success(getApplicationContext(), response, Toast.LENGTH_SHORT, true).show();
                        }else {
                            Toasty.error(getApplicationContext(), response, Toast.LENGTH_LONG, false).show();
                        };

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);

    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
