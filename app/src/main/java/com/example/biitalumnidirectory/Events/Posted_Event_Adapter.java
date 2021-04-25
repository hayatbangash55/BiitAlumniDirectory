package com.example.biitalumnidirectory.Events;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SJE_Detail;
import com.example.biitalumnidirectory.SharedReference;

import java.util.ArrayList;

public class Posted_Event_Adapter extends RecyclerView.Adapter<Posted_Event_Adapter.ViewHolder> {

    private ArrayList<SJE_Detail> event_list = new ArrayList<SJE_Detail>();
    private Context mContext;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;


    public Posted_Event_Adapter(Context mContext, ArrayList<SJE_Detail> event_list) {
        this.event_list = event_list;
        this.mContext = mContext;
        queue = Volley.newRequestQueue(mContext);
        URL = MyIp.ip;
        SharedRef = new SharedReference(mContext);
    }

    @NonNull
    @Override
    public Posted_Event_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_events, parent, false);
        Posted_Event_Adapter.ViewHolder holder = new Posted_Event_Adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final Posted_Event_Adapter.ViewHolder holder, final int position) {
        final SJE_Detail s = event_list.get(position);
        holder.tv_Event_Title.setText(s.Title);
        holder.tv_Date.setText(s.Starting_Date);
        holder.tv_Location.setText(s.Venue);
        holder.tv_Time.setText(s.Time);

        holder.edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PostAgain_Event_Activity.class);
                intent.putExtra("EventId", s.SJE_Detail_Id);
                mContext.startActivity(intent);
            }
        });

        holder.delete_image.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return event_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Event_Title, tv_Date, tv_Location, tv_Time;
        ImageView edit_image, delete_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Event_Title = itemView.findViewById(R.id.tv_Event_Title);
            tv_Date = itemView.findViewById(R.id.tv_Date);
            tv_Location = itemView.findViewById(R.id.tv_Location);
            tv_Time = itemView.findViewById(R.id.tv_Time);

            edit_image = itemView.findViewById(R.id.edit_image);
            delete_image = itemView.findViewById(R.id.delete_image);
        }
    }

}
