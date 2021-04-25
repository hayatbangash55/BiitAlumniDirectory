package com.example.biitalumnidirectory.Groups;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.biitalumnidirectory.SearchAlumni.Show_Searched_Clicked_Profile_Activity;
import com.example.biitalumnidirectory.SharedReference;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class GroupMember_Adapter extends RecyclerView.Adapter<GroupMember_Adapter.ViewHolder> {

    private ArrayList<GroupMember> groupMember_list = new ArrayList<GroupMember>();
    private Context mContext;
    SharedReference SharedRef;
    public String URL;
    public RequestQueue queue;
    String id;
    Boolean deleteCheck;

    public GroupMember_Adapter(Context mContext, ArrayList<GroupMember> groupMember_list, String id, Boolean deleteCheck) {
        this.groupMember_list = groupMember_list;
        this.mContext = mContext;
        SharedRef = new SharedReference(mContext);
        queue = Volley.newRequestQueue(mContext);
        URL = MyIp.ip;
        this.id = id;
        this.deleteCheck = deleteCheck;
    }

    @NonNull
    @Override
    public GroupMember_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_member, parent, false);
        GroupMember_Adapter.ViewHolder holder = new GroupMember_Adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final GroupMember_Adapter.ViewHolder holder, final int position) {

        final GroupMember s = groupMember_list.get(position);

        holder.tv_userName.setText(s.Name);
        holder.tv_aridNo.setText(s.Arid_No);

        String sec = s.Dicipline + "-" + s.Semester + s.Section;
        holder.tv_class.setText(sec);

        Bitmap bitmap = ImageUtil.convertToImage(s.NewImage);
        holder.search_Img1.setImageBitmap(bitmap);

        bitmap = ImageUtil.convertToImage(s.OldImage);
        holder.search_Img2.setImageBitmap(bitmap);

        if (deleteCheck) {
            holder.delete_image.setVisibility(View.VISIBLE);
        } else {
            holder.delete_image.setVisibility(View.GONE);
        }

        if(s.Status.equals("Group Member")){
            holder.tv_adminStatus.setVisibility(View.GONE);
        }

        holder.delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Delete Group");
                builder.setCancelable(false);
                builder.setMessage("Do you really want to delete group member \"" + s.Name + "\"?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Delete_data(id, s.GroupMember_CNIC);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });

        //search
        holder.item_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedRef.save_LoadUserDataByCNIC(s.GroupMember_CNIC);
                Intent intent = new Intent(mContext, Show_Searched_Clicked_Profile_Activity.class);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return groupMember_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_userName, tv_aridNo, tv_class,tv_adminStatus;
        CardView item_cardView;
        ImageView search_Img1, search_Img2, delete_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_aridNo = itemView.findViewById(R.id.tv_aridNo);
            tv_userName = itemView.findViewById(R.id.tv_user_name);
            tv_class = itemView.findViewById(R.id.tv_class);
            tv_adminStatus = itemView.findViewById(R.id.tv_adminStatus);
            item_cardView = itemView.findViewById(R.id.parent_layout);
            search_Img1 = itemView.findViewById(R.id.search_Img1);
            search_Img2 = itemView.findViewById(R.id.search_Img2);
            delete_image = itemView.findViewById(R.id.delete_image);
        }


    }

    void Delete_data(String id, String membercnic) {
        String query = "GroupMember/Remove_GroupMember?id=" + id + "&membercnic=" + membercnic;

        StringRequest request = new StringRequest(
                Request.Method.DELETE, URL + query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.replace("\"", "").equals("Record Deleted")) {
                    Toasty.success(mContext, response, Toast.LENGTH_SHORT, true).show();

                } else {
                    Toasty.error(mContext, response, Toast.LENGTH_SHORT, true).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(mContext, error.getMessage(), Toast.LENGTH_SHORT, true).show();

            }
        });

        queue.add(request);
    }

}

