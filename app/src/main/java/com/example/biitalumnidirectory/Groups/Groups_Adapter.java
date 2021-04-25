package com.example.biitalumnidirectory.Groups;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import java.util.ArrayList;

public class Groups_Adapter extends RecyclerView.Adapter<Groups_Adapter.ViewHolder> {

    private ArrayList<Groups> groups_list = new ArrayList<Groups>();
    private Context mContext;
    SharedReference SharedRef;
    public String URL;
    public RequestQueue queue;

    public Groups_Adapter(Context mContext, ArrayList<Groups> groups_list) {
        this.groups_list = groups_list;
        this.mContext = mContext;
        SharedRef = new SharedReference(mContext);
        queue = Volley.newRequestQueue(mContext);
        URL = MyIp.ip;
    }

    @NonNull
    @Override
    public Groups_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_groups, parent, false);
        Groups_Adapter.ViewHolder holder = new Groups_Adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final Groups_Adapter.ViewHolder holder, final int position) {

        final Groups s = groups_list.get(position);

        holder.tv_groupName.setText(s.Group_Name);

        holder.item_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)mContext).finish();
                Intent intent = new Intent(mContext, GroupData_Activity.class);
                intent.putExtra("GroupId", s.Groups_Id);
                intent.putExtra("Status",s.Status);
                intent.putExtra("Group_Name",s.Group_Name);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_groupName;
        CardView item_cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_groupName = itemView.findViewById(R.id.tv_groupName);
            item_cardView = itemView.findViewById(R.id.parent_layout);
        }


    }


}


