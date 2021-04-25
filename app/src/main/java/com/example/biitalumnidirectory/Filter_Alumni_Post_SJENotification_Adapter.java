package com.example.biitalumnidirectory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.Groups.GroupMember_Activity;
import com.example.biitalumnidirectory.Groups.Select_Groups;

import java.util.ArrayList;

public class Filter_Alumni_Post_SJENotification_Adapter extends RecyclerView.Adapter<Filter_Alumni_Post_SJENotification_Adapter.ViewHolder> {

    private ArrayList<Select_Groups> groups_list = new ArrayList<Select_Groups>();
    private Context mContext;
    SharedReference SharedRef;
    public String URL;
    public RequestQueue queue;

    public Filter_Alumni_Post_SJENotification_Adapter(Context mContext, ArrayList<Select_Groups> groups_list) {
        this.groups_list = groups_list;
        this.mContext = mContext;
        SharedRef = new SharedReference(mContext);
        queue = Volley.newRequestQueue(mContext);
        URL = MyIp.ip;
    }

    @NonNull
    @Override
    public Filter_Alumni_Post_SJENotification_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_groups_for_post_sje, parent, false);
        Filter_Alumni_Post_SJENotification_Adapter.ViewHolder holder = new Filter_Alumni_Post_SJENotification_Adapter.ViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull final Filter_Alumni_Post_SJENotification_Adapter.ViewHolder holder, final int position) {

        final Select_Groups s = groups_list.get(position);

        holder.tv_groupName.setText(s.Group_Name);


        holder.cb_selection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    s.isSelected = true;
                    groups_list.set(position,s);
                }
                else {
                    s.isSelected = false;
                    groups_list.set(position,s);
                }
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

        CheckBox cb_selection;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_groupName = itemView.findViewById(R.id.tv_groupName);
            item_cardView = itemView.findViewById(R.id.parent_layout);
            cb_selection = itemView.findViewById(R.id.cb_Selection);
        }


    }


    ArrayList<Select_Groups> return_List(){
        return groups_list;
    }

}


