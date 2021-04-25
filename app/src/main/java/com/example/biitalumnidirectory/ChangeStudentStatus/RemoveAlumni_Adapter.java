package com.example.biitalumnidirectory.ChangeStudentStatus;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.Groups.Add_Group_Member;
import com.example.biitalumnidirectory.ImageUtil;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import java.util.ArrayList;

public class RemoveAlumni_Adapter extends RecyclerView.Adapter<RemoveAlumni_Adapter.ViewHolder>  {

    ArrayList<Add_Group_Member> Student_list;
    public Context mContext;
    SharedReference SharedRef;
    public String URL;
    public RequestQueue queue;

    public RemoveAlumni_Adapter(Context mContext, ArrayList<Add_Group_Member> Student_list) {
        this.Student_list = Student_list;
        this.mContext = mContext;
        SharedRef = new SharedReference(mContext);
        queue = Volley.newRequestQueue(mContext);
        URL = MyIp.ip;
    }


    @NonNull
    @Override
    public RemoveAlumni_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_remove_alumni, parent, false);
        RemoveAlumni_Adapter.ViewHolder holder = new RemoveAlumni_Adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final RemoveAlumni_Adapter.ViewHolder holder, final int position) {

        final Add_Group_Member s = Student_list.get(position);

        holder.tv_userName.setText(s.Name);
        holder.tv_aridNo.setText(s.Arid_No);

        String sec = s.Dicipline + "-" + s.Semester + s.Section;
        holder.tv_class.setText(sec);

        Bitmap bitmap = ImageUtil.convertToImage(s.NewImage);
        holder.search_Img1.setImageBitmap(bitmap);

        bitmap = ImageUtil.convertToImage(s.OldImage);
        holder.search_Img2.setImageBitmap(bitmap);

        //must for check boxes work
        if(s.isChecked){
            holder.cb_Selection.setChecked(true);
        }else if(!s.isChecked){
            holder.cb_Selection.setChecked(false);
        }


        holder.cb_Selection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    s.isChecked = true;
                } else if (!isChecked) {
                    s.isChecked = false;
                }
                Student_list.set(position,s);
            }
        });


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
        return Student_list.size();
    }



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
        return Student_list;
    }

}


