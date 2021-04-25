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
import com.example.biitalumnidirectory.EditProfile.Edit_JobInfo_Activity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class JobInfo_Adapter extends RecyclerView.Adapter<JobInfo_Adapter.ViewHolder> {

    private ArrayList<JobInfo> list_jobInfo = new ArrayList<JobInfo>();
    private Context mContext;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;
    FragmentManager manager;



    public JobInfo_Adapter(Context mContext, ArrayList<JobInfo> list_jobInfo, FragmentManager manager) {
        this.list_jobInfo = list_jobInfo;
        this.mContext = mContext;
        URL = MyIp.ip;
        queue = Volley.newRequestQueue(mContext);
        this.manager = manager;
        SharedRef = new SharedReference(mContext);

    }

    @NonNull
    @Override
    public JobInfo_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_info, parent, false);
        JobInfo_Adapter.ViewHolder holder = new JobInfo_Adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final JobInfo_Adapter.ViewHolder holder, final int position) {

        final JobInfo s = list_jobInfo.get(position);
        holder.tv_designation.setText(s.Designation);
        holder.tv_organization.setText(s.Organization);
        holder.tv_startingDate.setText(s.Starting_Date);
        holder.tv_endingDate.setText(s.Ending_Date);

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
                Intent intent = new Intent(mContext, Edit_JobInfo_Activity.class);
                intent.putExtra("id", s.id);
                mContext.startActivity(intent);
            }
        });

        holder.delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = list_jobInfo.get(position).id;
                delete_Data(id, position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list_jobInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_designation, tv_organization, tv_startingDate, tv_endingDate;
        LinearLayout parent_layout;
        ImageView edit_image, delete_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_designation = itemView.findViewById(R.id.tv_designation);
            tv_organization = itemView.findViewById(R.id.tv_organization);
            tv_startingDate = itemView.findViewById(R.id.tv_startingDate);
            tv_endingDate = itemView.findViewById(R.id.tv_endingDate);
            edit_image = itemView.findViewById(R.id.edit_image);
            delete_image = itemView.findViewById(R.id.delete_image);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }


    }


    void delete_Data(String val_id, final int position) {

        final String temp = SharedRef.get_LoadUserDataByCNIC();

        String query = "Student_Job_Detail/Remove_Student_Job_Detail?cnic=" + temp + "&id=" + val_id;

        StringRequest request = new StringRequest(
                Request.Method.DELETE, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.replace("\"", "").equals("Record Deleted")) {
                            list_jobInfo.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                            Toasty.success(mContext, response, Toast.LENGTH_SHORT, true).show();
                        } else {
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
