package com.example.biitalumnidirectory.MyProfile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.EditProfile.Edit_FamilyInfo_Activity;
import com.example.biitalumnidirectory.ImageUtil;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class FamilyInfo_Adapter extends RecyclerView.Adapter<FamilyInfo_Adapter.ViewHolder> {

    private ArrayList<FamilyInfo> list_familyInfo = new ArrayList<FamilyInfo>();
    private Context mContext;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;
    FragmentManager manager;


    public FamilyInfo_Adapter(Context mContext, ArrayList<FamilyInfo> list_familyInfo, FragmentManager manager) {
        this.list_familyInfo = list_familyInfo;
        this.mContext = mContext;
        URL = MyIp.ip;
        queue = Volley.newRequestQueue(mContext);
        this.manager = manager;
        SharedRef = new SharedReference(mContext);
    }

    @NonNull
    @Override
    public FamilyInfo_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_family_info, parent, false);
        FamilyInfo_Adapter.ViewHolder holder = new FamilyInfo_Adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final FamilyInfo_Adapter.ViewHolder holder, final int position) {

        final FamilyInfo s = list_familyInfo.get(position);
        holder.tv_name.setText(s.Name);
        holder.tv_relation.setText(s.Relation);

        holder.parent_layout.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.parseColor("#d4f8d4"));

        Bitmap bitmap = ImageUtil.convertToImage(list_familyInfo.get(position).Image);
        holder.new_profile_Img.setImageBitmap(bitmap);


        holder.edit_image.setVisibility(View.GONE);
        holder.delete_image.setVisibility(View.GONE);
        if (SharedRef.get_EditProfile().equals(true)) {
            holder.edit_image.setVisibility(View.VISIBLE);
            holder.delete_image.setVisibility(View.VISIBLE);
        }

        holder.edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Edit_FamilyInfo_Activity.class);
                intent.putExtra("id", s.id);
                mContext.startActivity(intent);
            }
        });

        holder.delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = list_familyInfo.get(position).id;
                delete_Data(id, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list_familyInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_relation;
        LinearLayout parent_layout;
        ImageView new_profile_Img, edit_image, delete_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_relation = itemView.findViewById(R.id.tv_relation);
            new_profile_Img = itemView.findViewById(R.id.new_profile_Img);
            edit_image = itemView.findViewById(R.id.edit_image);
            delete_image = itemView.findViewById(R.id.delete_image);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }


    }


    void delete_Data(String val_id, final int position ) {

        final String temp = SharedRef.get_LoadUserDataByCNIC();

        String query = "Student_Family_Detail/Remove_Select_Student_Family_Detail?cnic=" + temp + "&id=" + val_id;


        StringRequest request = new StringRequest(
                Request.Method.DELETE, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.replace("\"","").equals("Record Deleted")) {
                            list_familyInfo.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                            Toasty.success(mContext, response, Toast.LENGTH_SHORT, true).show();
                        }else {
                            Toasty.error(mContext, response, Toast.LENGTH_LONG, false).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toasty.error(mContext, error.getMessage(), Toast.LENGTH_LONG, false).show();

            }
        }
        );
        queue.add(request);

    }

}
