package com.example.biitalumnidirectory.MyProfile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.biitalumnidirectory.EditProfile.Full_Screen_Image_Activity;
import com.example.biitalumnidirectory.ImageUtil;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class Gallery_Adapter extends ArrayAdapter<Gallery> {

    private Context mContext;
    private ImageView ivGallery;
    ArrayList<Gallery> list_image;
    SharedReference SharedRef;

    public Gallery_Adapter(Context mContext, ArrayList<Gallery> list_image) {
        super(mContext, R.layout.item_gallery_view,list_image);

        this.mContext = mContext;
        this.list_image = list_image;
        SharedRef = new SharedReference(mContext);
    }

    @Override
    public int getCount() {
        return list_image.size();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_gallery_view, parent, false);
        ivGallery = itemView.findViewById(R.id.ivGallery);
        String image = list_image.get(position).Image;

        try {
            Bitmap bitmap = ImageUtil.convertToImage(image);
            ivGallery.setImageBitmap(bitmap);
        }catch (Exception e)
        {
            Toasty.error(mContext, e.getMessage(), Toast.LENGTH_LONG, false).show();
        }


        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(mContext, Full_Screen_Image_Activity.class);
                    SharedRef.save_Image(list_image.get(position).Image);
                    intent.putExtra("Image_Gallery_Id",list_image.get(position).Image_Gallery_Id);
                    mContext.startActivity(intent);
                }catch (Exception e){
                    Toasty.error(mContext,e.getMessage(),Toasty.LENGTH_LONG,true).show();
                }
            }
        });


        return itemView;
    }
}
