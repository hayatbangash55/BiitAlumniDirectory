package com.example.biitalumnidirectory.Survey;

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

public class Posted_Survey_Adapter extends RecyclerView.Adapter<UnPosted_Survey_Adapter.ViewHolder> {

    private ArrayList<SJE_Detail> survey_list = new ArrayList<SJE_Detail>();
    private Context mContext;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;


    public Posted_Survey_Adapter(Context mContext, ArrayList<SJE_Detail> survey_list) {
        this.survey_list = survey_list;
        this.mContext = mContext;
        queue = Volley.newRequestQueue(mContext);
        URL = MyIp.ip;
        SharedRef = new SharedReference(mContext);

    }

    @NonNull
    @Override
    public UnPosted_Survey_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_survey, parent, false);
        UnPosted_Survey_Adapter.ViewHolder holder = new UnPosted_Survey_Adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final UnPosted_Survey_Adapter.ViewHolder holder, final int position) {

        final SJE_Detail s = survey_list.get(position);
        holder.tv_Survey_Title.setText(s.Title);
        holder.tv_Date.setText(s.Starting_Date);
        holder.tv_Time.setText(s.Time);


    //    holder.delete_image.setVisibility(View.GONE);
    //    holder.edit_image.setVisibility(View.GONE);

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Survey_Statistics_Activity.class);
                intent.putExtra("SJE_Detail_Id", s.SJE_Detail_Id);
                intent.putExtra("Title", s.Title);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return survey_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Survey_Title, tv_Date, tv_Time;
        ImageView edit_image, delete_image;
        CardView parent_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Survey_Title = itemView.findViewById(R.id.tv_Survey_Title);
            tv_Date = itemView.findViewById(R.id.tv_Date);
            tv_Time = itemView.findViewById(R.id.tv_Time);

            parent_layout = itemView.findViewById(R.id.parent_layout);
   //         edit_image = itemView.findViewById(R.id.edit_image);
  //          delete_image = itemView.findViewById(R.id.delete_image);
        }
    }

}

