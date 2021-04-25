package com.example.biitalumnidirectory.Alumni_Notification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.Survey.AttemptSurvey1_Activity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.Events.ShowEvent_Activity;
import com.example.biitalumnidirectory.Jobs.ShowJob_Activity;
import com.example.biitalumnidirectory.SharedReference;

import org.json.JSONObject;

import java.util.ArrayList;

public class Alumni_Notification_Adapter extends RecyclerView.Adapter<Alumni_Notification_Adapter.ViewHolder> {

    private ArrayList<Alumni_Notification> alumniNotification_list = new ArrayList<Alumni_Notification>();
    private Context mContext;
    public String URL;
    SharedReference SharedRef;

    public RequestQueue queue;

    public Alumni_Notification_Adapter(Context mContext, ArrayList<Alumni_Notification> alumniNotification_list) {
        this.alumniNotification_list = alumniNotification_list;
        this.mContext = mContext;
        URL = MyIp.ip;
        queue = Volley.newRequestQueue(mContext);
        SharedRef = new SharedReference(mContext);
    }

    @NonNull
    @Override
    public Alumni_Notification_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumni_notification, parent, false);
        Alumni_Notification_Adapter.ViewHolder holder = new Alumni_Notification_Adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final Alumni_Notification_Adapter.ViewHolder holder, final int position) {

        final Alumni_Notification s = alumniNotification_list.get(position);
        holder.tv_title.setText(s.Title);
        holder.tv_Date.setText(s.Posted_Date);
        holder.tv_Time.setText(s.Posted_Time);
        holder.tv_NotificationType.setText(s.SJE_Type);

        if (s.Seen_Status.equalsIgnoreCase("UnSeen")) {
            holder.parent_layout.setBackgroundColor(Color.parseColor("#d4f8d4"));
        }

        //search
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s.Seen_Status.equalsIgnoreCase("UnSeen")) {
                   update_Data(s.Posted_SJE_Id, SharedRef.get_LoadUserDataByCNIC());
                }
                if(s.SJE_Type.equalsIgnoreCase("Event")) {
                    Intent intent = new Intent(mContext, ShowEvent_Activity.class);
                    intent.putExtra("SJE_Detail_Id", s.SJE_Detail_Id);
                    intent.putExtra("SJEDetail_Title", s.Title);
//                    intent.putExtra("SJEDetail_Type", s.SJE_Type);
                    mContext.startActivity(intent);
                }

                else if(s.SJE_Type.equalsIgnoreCase("Job")) {
                    Intent intent = new Intent(mContext, ShowJob_Activity.class);
                    intent.putExtra("SJE_Detail_Id", s.SJE_Detail_Id);
                    intent.putExtra("SJEDetail_Title", s.Title);
//                    intent.putExtra("SJEDetail_Type", s.SJE_Type);
                    mContext.startActivity(intent);
                }

                else if(s.SJE_Type.equalsIgnoreCase("Survey")) {
                    Intent intent = new Intent(mContext, AttemptSurvey1_Activity.class);
                    intent.putExtra("SJE_Detail_Id", s.SJE_Detail_Id);
                    intent.putExtra("SJEDetail_Title", s.Title);
                    intent.putExtra("Posted_SJE_Id", s.Posted_SJE_Id);
//                    intent.putExtra("SJEDetail_Type", s.SJE_Type);
                    mContext.startActivity(intent);
                }

                else {
                    Toast.makeText(mContext, "Page Removed", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return alumniNotification_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Date, tv_Time,tv_Time1, tv_NotificationType, tv_title;
        CardView parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_Date = itemView.findViewById(R.id.tv_Date);
            tv_Time = itemView.findViewById(R.id.tv_Time);
            tv_Time1 = itemView.findViewById(R.id.tv_Time1);
            tv_NotificationType = itemView.findViewById(R.id.tv_NotificationType);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }


    }


    void update_Data(String id, String myCNIC) {

        String Query = "Post_SJE/Update_Notification_SeenStatus?id=" + id +"&cnic="+myCNIC;

        JsonObjectRequest request = null;

        request = new JsonObjectRequest(
                Request.Method.PUT, URL + Query, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String Msg = response.optString("Msg");
                        String Error = response.optString("Error");

                        if (Msg.equals("Record Updated")) {
//                            Toasty.success(mContext, Msg, Toast.LENGTH_SHORT, true).show();
                        } else {
//                            Toasty.error(mContext, Error, Toast.LENGTH_LONG, false).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toasty.error(mContext, error.getMessage(), Toast.LENGTH_LONG, false).show();
            }
        });
        queue.add(request);
    }

}
