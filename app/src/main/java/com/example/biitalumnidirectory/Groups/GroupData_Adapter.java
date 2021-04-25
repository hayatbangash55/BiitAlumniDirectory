package com.example.biitalumnidirectory.Groups;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.Survey.AttemptSurvey1_Activity;
import com.example.biitalumnidirectory.Events.ShowEvent_Activity;
import com.example.biitalumnidirectory.Jobs.ShowJob_Activity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import java.util.ArrayList;

public class GroupData_Adapter extends RecyclerView.Adapter {

    private ArrayList<GroupData> groupData_list = new ArrayList<GroupData>();
    private Context mContext;
    SharedReference SharedRef;
    public String URL;
    public RequestQueue queue;
    String date;
    boolean isFirst = true;
    int viewtype;
    int index = 0;
    int total_date;

    public GroupData_Adapter(Context mContext, ArrayList<GroupData> groupData_list,int total_date) {
        this.groupData_list = groupData_list;
        this.mContext = mContext;
        SharedRef = new SharedReference(mContext);
        queue = Volley.newRequestQueue(mContext);
        URL = MyIp.ip;
        date = groupData_list.get(0).Posted_Date;
        this.total_date = total_date;
    }

    @Override
    public int getItemViewType(int position) {
        //use for first time only
        if (isFirst) {
            isFirst = false;
            viewtype = 0;
            return viewtype;
        }

        if (date.equals(groupData_list.get(index).Posted_Date)) {
            viewtype = 1;
            return viewtype;
        } else {
            viewtype = 0;
            date = groupData_list.get(index).Posted_Date;
            return viewtype;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_group_data_date, parent, false);
            return new ViewHolderDate(v);
        } else {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_group_data_post, parent, false);
            return new ViewHolderPost(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (viewtype == 0) {
            ViewHolderDate holderDate = (ViewHolderDate) holder;
            holderDate.tv_date.setText(groupData_list.get(index).Posted_Date);
        }

       else {
            ViewHolderPost holderPost = (ViewHolderPost) holder;
            final GroupData s = groupData_list.get(index);
            holderPost.tv_title.setText(s.Title);
            holderPost.tv_posted_date.setText(s.Posted_Date);
            holderPost.tv_posted_time.setText(s.Posted_Time);
            holderPost.tv_type.setText(s.Type);
            holderPost.tv_student_name.setText(s.Student_Name);

            //search
            holderPost.item_cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (s.Seen_Status.equalsIgnoreCase("UnSeen")) {
//                        update_Data(s.Posted_SJE_Id, SharedRef.get_LoadUserDataByCNIC());
//                    }
                    if(s.Type.equalsIgnoreCase("Event")) {
                        Intent intent = new Intent(mContext, ShowEvent_Activity.class);
                        intent.putExtra("SJE_Detail_Id", s.SJE_Detail_Id);
                        intent.putExtra("SJEDetail_Title", s.Title);
//                    intent.putExtra("SJEDetail_Type", s.SJE_Type);
                        mContext.startActivity(intent);
                    }

                    else if(s.Type.equalsIgnoreCase("Job")) {
                        Intent intent = new Intent(mContext, ShowJob_Activity.class);
                        intent.putExtra("SJE_Detail_Id", s.SJE_Detail_Id);
                        intent.putExtra("SJEDetail_Title", s.Title);
//                    intent.putExtra("SJEDetail_Type", s.SJE_Type);
                        mContext.startActivity(intent);
                    }

                    else if(s.Type.equalsIgnoreCase("Survey")) {
                        Intent intent = new Intent(mContext, AttemptSurvey1_Activity.class);
                        intent.putExtra("SJE_Detail_Id", s.SJE_Detail_Id);
                        intent.putExtra("SJEDetail_Title", s.Title);
//                    intent.putExtra("SJEDetail_Type", s.SJE_Type);
                        mContext.startActivity(intent);
                    }

                    else {
                        Toast.makeText(mContext, "Page Removed", Toast.LENGTH_LONG).show();
                    }
                }
            });

            index++;
        }
    }

    @Override
    public int getItemCount() {
        return groupData_list.size()+total_date;
    }


    public class ViewHolderPost extends RecyclerView.ViewHolder {

        TextView tv_title, tv_posted_date, tv_posted_time, tv_student_name, tv_type;
        CardView item_cardView;

        public ViewHolderPost(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_posted_date = itemView.findViewById(R.id.tv_posted_date);
            tv_posted_time = itemView.findViewById(R.id.tv_posted_time);
            tv_student_name = itemView.findViewById(R.id.tv_student_name);
            tv_type = itemView.findViewById(R.id.tv_type);
            item_cardView = itemView.findViewById(R.id.parent_layout);
        }
    }

    public class ViewHolderDate extends RecyclerView.ViewHolder {

        TextView tv_date;

        public ViewHolderDate(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }
}

