package com.example.biitalumnidirectory.Groups;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.ImageUtil;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import java.util.ArrayList;

public class Add_GroupMembers_Adapter extends RecyclerView.Adapter<Add_GroupMembers_Adapter.ViewHolder> implements Filterable {

    ArrayList<Add_Group_Member> groupMember_list;
    ArrayList<Add_Group_Member> fullgroupMember_list;
    public Context mContext;
    SharedReference SharedRef;
    public String URL;
    public RequestQueue queue;


    public Add_GroupMembers_Adapter(Context mContext, ArrayList<Add_Group_Member> groupMember_list) {
        this.groupMember_list = groupMember_list;
        fullgroupMember_list = new ArrayList<>(groupMember_list);
        this.mContext = mContext;
        SharedRef = new SharedReference(mContext);
        queue = Volley.newRequestQueue(mContext);
        URL = MyIp.ip;
    }


    @NonNull
    @Override
    public Add_GroupMembers_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_group_member, parent, false);
        Add_GroupMembers_Adapter.ViewHolder holder = new Add_GroupMembers_Adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final Add_GroupMembers_Adapter.ViewHolder holder, final int position) {

        final Add_Group_Member s = groupMember_list.get(position);

        holder.tv_userName.setText(s.Name);
        holder.tv_aridNo.setText(s.Arid_No);

        String sec = s.Dicipline + "-" + s.Semester + s.Section;
        holder.tv_class.setText(sec);

        Bitmap bitmap = ImageUtil.convertToImage(s.NewImage);
        holder.search_Img1.setImageBitmap(bitmap);

        bitmap = ImageUtil.convertToImage(s.OldImage);
        holder.search_Img2.setImageBitmap(bitmap);


        holder.cb_Selection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    s.isChecked = true;
                } else if (!isChecked) {
                    s.isChecked = false;
                }


                //update object from orignal list using object Add_Group_Member s
                for (int i = 0; i < fullgroupMember_list.size(); i++) {
                    Add_Group_Member m = fullgroupMember_list.get(i);
                    if (m.CNIC == s.CNIC) {
                        fullgroupMember_list.set(i, s);
                        break;
                    }
                }
            }
        });

        if (s.isChecked) {
            holder.cb_Selection.setChecked(true);
        } else if (!s.isChecked) {
            holder.cb_Selection.setChecked(false);
        }

        //search
        holder.item_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // selectedFriend.add(s.CNIC);
            }
        });

    }

    @Override
    public int getItemCount() {
        return groupMember_list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<Add_Group_Member> FilteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                FilteredList.clear();
                FilteredList.addAll(fullgroupMember_list);
            } else {

                String FilterPattern = constraint.toString().trim().toLowerCase();
                for (Add_Group_Member item : fullgroupMember_list) {
                    if (item.Name.toLowerCase().contains(FilterPattern)) {
                        FilteredList.add(item);
                    } else if (item.isChecked) {
                        FilteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = FilteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            groupMember_list.clear();
            groupMember_list.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_userName, tv_aridNo, tv_class;
        CardView item_cardView;
        ImageView search_Img1, search_Img2;
        Button btn_friend, btn_delete;
        CheckBox cb_Selection;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_aridNo = itemView.findViewById(R.id.tv_aridNo);
            tv_userName = itemView.findViewById(R.id.tv_user_name);
            tv_class = itemView.findViewById(R.id.tv_class);
            item_cardView = itemView.findViewById(R.id.parent_layout);
            search_Img1 = itemView.findViewById(R.id.search_Img1);
            search_Img2 = itemView.findViewById(R.id.search_Img2);
            btn_friend = itemView.findViewById(R.id.btn_friend);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            cb_Selection = itemView.findViewById(R.id.cb_Selection);
        }
    }

    public ArrayList<Add_Group_Member> returnList() {
        return fullgroupMember_list;
    }

}

