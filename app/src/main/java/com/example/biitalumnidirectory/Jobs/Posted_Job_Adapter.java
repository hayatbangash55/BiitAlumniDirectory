package com.example.biitalumnidirectory.Jobs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SJE_Detail;
import com.example.biitalumnidirectory.SharedReference;

import java.util.ArrayList;

public class Posted_Job_Adapter extends RecyclerView.Adapter<Posted_Job_Adapter.ViewHolder> {


    private ArrayList<SJE_Detail> job_list = new ArrayList<SJE_Detail>();
    private Context mContext;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;


    public Posted_Job_Adapter(Context mContext, ArrayList<SJE_Detail> job_list) {
        this.job_list = job_list;
        this.mContext = mContext;
        queue = Volley.newRequestQueue(mContext);
        SharedRef = new SharedReference(mContext);
        URL = MyIp.ip;
    }


    @NonNull
    @Override
    public Posted_Job_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_job, parent, false);
        Posted_Job_Adapter.ViewHolder holder = new Posted_Job_Adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final Posted_Job_Adapter.ViewHolder holder, final int position) {

        final SJE_Detail s = job_list.get(position);

        holder.tv_jobTitle.setText(s.Title);
        holder.tv_Date.setText(s.Ending_Date);
        holder.tv_Venue.setText(s.Venue);

        holder.edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PostJob_Again_Activity.class);
                intent.putExtra("JobId", s.SJE_Detail_Id);
                mContext.startActivity(intent);
            }
        });

        holder.delete_image.setVisibility(View.GONE);
    }


    @Override
    public int getItemCount() {
        return job_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_jobTitle, tv_Date, tv_Venue;
        ImageView edit_image, delete_image;
        CardView parent_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_jobTitle = itemView.findViewById(R.id.tv_jobTitle);
            tv_Date = itemView.findViewById(R.id.tv_Date);
            tv_Venue = itemView.findViewById(R.id.tv_Venue);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            edit_image = itemView.findViewById(R.id.edit_image);
            delete_image = itemView.findViewById(R.id.delete_image);
        }


    }
}

