package com.example.biitalumnidirectory.MyProfile;

import android.content.Context;
import android.content.Intent;
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
import com.example.biitalumnidirectory.EditProfile.Edit_PublicationInfo_Activity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class PublicationInfo_Adapter extends RecyclerView.Adapter<PublicationInfo_Adapter.ViewHolder> {

    private ArrayList<PublicationInfo> list_publicationInfo = new ArrayList<PublicationInfo>();
    private Context mContext;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;
    FragmentManager manager;

    public PublicationInfo_Adapter(Context mContext, ArrayList<PublicationInfo> list_publicationInfo, FragmentManager manager) {

        this.mContext = mContext;
        this.list_publicationInfo = list_publicationInfo;
        this.manager = manager;
        SharedRef = new SharedReference(mContext);
        queue = Volley.newRequestQueue(mContext);
        URL = MyIp.ip;
    }

    @NonNull
    @Override
    public PublicationInfo_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_publication_info, parent, false);
        PublicationInfo_Adapter.ViewHolder holder = new PublicationInfo_Adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final PublicationInfo_Adapter.ViewHolder holder, final int position) {

        final PublicationInfo s = list_publicationInfo.get(position);


        holder.tv_Publisher_Title.setVisibility(View.GONE);
        holder.tv_Publisher_Value.setVisibility(View.GONE);
        holder.tv_PublicationDate_Title.setVisibility(View.GONE);
        holder.tv_PublicationDate_Value.setVisibility(View.GONE);
        holder.tv_PublicationURL_Title.setVisibility(View.GONE);
        holder.tv_PublicationURL_Value.setVisibility(View.GONE);
        holder.tv_Description_Title.setVisibility(View.GONE);
        holder.tv_Description_Value.setVisibility(View.GONE);


            holder.tv_Title_Value.setText(s.Title);

        if(!s.Publisher.equals("") && !s.Publisher.equals(null)){
            holder.tv_Publisher_Title.setVisibility(View.VISIBLE);
            holder.tv_Publisher_Value.setVisibility(View.VISIBLE);
            holder.tv_Publisher_Value.setText(s.Publisher);
        }

        if(!s.Publication_Date.equals("") && !s.Publication_Date.equals(null)){
            holder.tv_PublicationDate_Title.setVisibility(View.VISIBLE);
            holder.tv_PublicationDate_Value.setVisibility(View.VISIBLE);
            holder.tv_PublicationDate_Value.setText(s.Publication_Date);
        }

        if(!s.Publication_URL.equals("") && !s.Publication_URL.equals(null)){
            holder.tv_PublicationURL_Title.setVisibility(View.VISIBLE);
            holder.tv_PublicationURL_Value.setVisibility(View.VISIBLE);
            holder.tv_PublicationURL_Value.setText(s.Publication_URL);
        }

        if(!s.Description.equals("") && !s.Description.equals(null)){
            holder.tv_Description_Title.setVisibility(View.VISIBLE);
            holder.tv_Description_Value.setVisibility(View.VISIBLE);
            holder.tv_Description_Value.setText(s.Description);
        }


        holder.parent_layout.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.parseColor("#d4f8d4"));

        holder.edit_image.setVisibility(View.GONE);
        holder.delete_image.setVisibility(View.GONE);

        if (SharedRef.get_EditProfile().equals(true)) {
            holder.edit_image.setVisibility(View.VISIBLE);
            holder.delete_image.setVisibility(View.VISIBLE);
        }


        holder.edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Edit_PublicationInfo_Activity.class);
                intent.putExtra("id", s.Stu_Pub_Id);
                mContext.startActivity(intent);
            }
        });

        holder.delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = list_publicationInfo.get(position).Stu_Pub_Id;
                delete_Data(id, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list_publicationInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Title_Title,tv_Title_Value,tv_Publisher_Title, tv_Publisher_Value, tv_PublicationDate_Title,
                tv_PublicationDate_Value, tv_PublicationURL_Title, tv_PublicationURL_Value, tv_Description_Title,
                tv_Description_Value;
        ImageView edit_image, delete_image;
        LinearLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Title_Title = itemView.findViewById(R.id.tv_Title_Title);
            tv_Title_Value = itemView.findViewById(R.id.tv_Title_Value);
            tv_Publisher_Title = itemView.findViewById(R.id.tv_Publisher_Title);
            tv_Publisher_Value = itemView.findViewById(R.id.tv_Publisher_Value);
            tv_PublicationDate_Title = itemView.findViewById(R.id.tv_PublicationDate_Title);
            tv_PublicationDate_Value = itemView.findViewById(R.id.tv_PublicationDate_Value);
            tv_PublicationURL_Title = itemView.findViewById(R.id.tv_PublicationURL_Title);
            tv_PublicationURL_Value = itemView.findViewById(R.id.tv_PublicationURL_Value);
            tv_Description_Title = itemView.findViewById(R.id.tv_Description_Title);
            tv_Description_Value = itemView.findViewById(R.id.tv_Description_Value);
            edit_image = itemView.findViewById(R.id.edit_image);
            delete_image = itemView.findViewById(R.id.delete_image);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }


    void delete_Data(String val_id, final int position) {

        final String temp = SharedRef.get_LoadUserDataByCNIC();

        String query = "Student_Publication_Detail/Remove_Publication_Detail?cnic=" + temp + "&id=" + val_id;

        StringRequest request = new StringRequest(
                Request.Method.DELETE, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.replace("\"", "").equals("Record Deleted")) {
                            list_publicationInfo.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                            Toasty.success(mContext, response, Toast.LENGTH_SHORT, true).show();

                        } else {
                            Toasty.error(mContext, response, Toast.LENGTH_LONG, true).show();
                        }
                        ;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(mContext, error.getMessage(), Toast.LENGTH_LONG, true).show();
            }
        }
        );
        queue.add(request);

    }

}
