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
import com.example.biitalumnidirectory.EditProfile.Edit_EducationInfo_Activity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class EducationInfo_Adapter extends RecyclerView.Adapter<EducationInfo_Adapter.ViewHolder> {

    private ArrayList<EducationInfo> list_educationInfo = new ArrayList<EducationInfo>();
    private Context mContext;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;
    FragmentManager manager;

    public EducationInfo_Adapter(Context mContext, ArrayList<EducationInfo> list_educationInfo, FragmentManager manager) {

        this.mContext = mContext;
        this.list_educationInfo = list_educationInfo;
        this.manager = manager;
        SharedRef = new SharedReference(mContext);
        queue = Volley.newRequestQueue(mContext);
        URL = MyIp.ip;
    }

    @NonNull
    @Override
    public EducationInfo_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_education_info, parent, false);
        EducationInfo_Adapter.ViewHolder holder = new EducationInfo_Adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final EducationInfo_Adapter.ViewHolder holder, final int position) {

        final EducationInfo s = list_educationInfo.get(position);

        holder.tv_degreeName.setText(s.Degree_name);
        holder.tv_institutionName.setText(s.Instituation_name);
        holder.tv_completetionYear.setText(s.Completion_year);
        holder.tv_majorSubject.setText(s.Major_Subject);

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
                Intent intent = new Intent(mContext, Edit_EducationInfo_Activity.class);
                intent.putExtra("id", s.id);
                mContext.startActivity(intent);
            }
        });

        holder.delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = list_educationInfo.get(position).id;
                delete_Data(id,position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list_educationInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_degreeName, tv_institutionName, tv_completetionYear, tv_majorSubject;
        ImageView edit_image, delete_image;
        LinearLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_degreeName = itemView.findViewById(R.id.tv_degreeName);
            tv_institutionName = itemView.findViewById(R.id.tv_institutionName);
            tv_completetionYear = itemView.findViewById(R.id.tv_completetionYear);
            tv_majorSubject = itemView.findViewById(R.id.tv_majorSubject);
            edit_image = itemView.findViewById(R.id.edit_image);
            delete_image = itemView.findViewById(R.id.delete_image);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }


    void delete_Data(String val_id, final int position) {

        final String temp = SharedRef.get_LoadUserDataByCNIC();

        String query = "Student_Educational_Detail/Remove_Educational_Detail?cnic=" + temp + "&id=" + val_id;

        StringRequest request = new StringRequest(
                Request.Method.DELETE, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.replace("\"", "").equals("Record Deleted")) {
                            list_educationInfo.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                            Toasty.success(mContext, response, Toast.LENGTH_SHORT, true).show();

                        } else {
                            Toasty.error(mContext, response, Toast.LENGTH_LONG, false).show();
                        }
                        ;

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
