package com.example.biitalumnidirectory.Admin_Notification;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.Events.ShowEvent_Activity;
import com.example.biitalumnidirectory.Jobs.ShowJob_Activity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SearchAlumni.Show_Searched_Clicked_Profile_Activity;
import com.example.biitalumnidirectory.SharedReference;
import com.example.biitalumnidirectory.Survey.AttemptSurvey2_Activity;

import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class Admin_Notification_Adapter extends RecyclerView.Adapter<Admin_Notification_Adapter.ViewHolder> {

    private ArrayList<Admin_Notification> adminNotification_list = new ArrayList<Admin_Notification>();
    private Context mContext;
    public String URL;
    SharedReference SharedRef;

    public RequestQueue queue;

    public Admin_Notification_Adapter(Context mContext, ArrayList<Admin_Notification> adminNotification_list) {
        this.adminNotification_list = adminNotification_list;
        this.mContext = mContext;
        URL = MyIp.ip;
        queue = Volley.newRequestQueue(mContext);
        SharedRef = new SharedReference(mContext);
    }

    @NonNull
    @Override
    public Admin_Notification_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_notification, parent, false);
        Admin_Notification_Adapter.ViewHolder holder = new Admin_Notification_Adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final Admin_Notification_Adapter.ViewHolder holder, final int position) {

        final Admin_Notification s = adminNotification_list.get(position);
        holder.tv_title.setText(s.Title);
        holder.tv_Date.setText(s.Posted_Date);
        holder.tv_Time.setText(s.Posted_Time);
        holder.tv_NotificationType.setText(s.SJE_Type);
        holder.tv_createdBy.setText(s.Student_Name);


        //search
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final CharSequence[] options = {"Approve", "Not Approve", "View" + s.SJE_Type, "View Creator Profile", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Post Options");

                builder.setItems(options, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (options[item].equals("Approve")) {
                            update_Approve_PostedSJE(s.Posted_SJE_Id);
                            dialog.dismiss();
                        }

                        else if (options[item].equals("Not Approve")) {
                            delete_Data(s.Posted_SJE_Id);
                        }

                        else if (options[item].equals("View" + s.SJE_Type)) {
                            if (s.SJE_Type.equalsIgnoreCase("Event")) {
                                Intent intent = new Intent(mContext, ShowEvent_Activity.class);
                                intent.putExtra("SJE_Detail_Id", s.SJE_Detail_Id);
                                intent.putExtra("SJEDetail_Title", s.Title);
//                    intent.putExtra("SJEDetail_Type", s.SJE_Type);
                                mContext.startActivity(intent);
                            } else if (s.SJE_Type.equalsIgnoreCase("Job")) {
                                Intent intent = new Intent(mContext, ShowJob_Activity.class);
                                intent.putExtra("SJE_Detail_Id", s.SJE_Detail_Id);
                                intent.putExtra("SJEDetail_Title", s.Title);
//                    intent.putExtra("SJEDetail_Type", s.SJE_Type);
                                mContext.startActivity(intent);
                            } else if (s.SJE_Type.equalsIgnoreCase("Survey")) {
                                Intent intent = new Intent(mContext, AttemptSurvey2_Activity.class);
                                intent.putExtra("SJE_Detail_Id", s.SJE_Detail_Id);
                                intent.putExtra("SJEDetail_Title", s.Title);
//                    intent.putExtra("SJEDetail_Type", s.SJE_Type);
                                mContext.startActivity(intent);
                            } else {
                                Toast.makeText(mContext, "Page Removed", Toast.LENGTH_LONG).show();
                            }
                            dialog.dismiss();
                        }

                        else if (options[item].equals("View Creator Profile")) {
                            SharedRef.save_LoadUserDataByCNIC(s.Creator_CNIC);
                            Intent intent = new Intent(mContext, Show_Searched_Clicked_Profile_Activity.class);
                            mContext.startActivity(intent);
                            dialog.dismiss();
                        }

                        else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return adminNotification_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Date, tv_Time, tv_NotificationType, tv_title, tv_createdBy;
        CardView parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_Date = itemView.findViewById(R.id.tv_Date);
            tv_Time = itemView.findViewById(R.id.tv_Time);
            tv_createdBy = itemView.findViewById(R.id.tv_createdBy);
            tv_NotificationType = itemView.findViewById(R.id.tv_NotificationType);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }


    }


    void update_Approve_PostedSJE(String id) {

        String Query = "Post_SJE/Update_Approve_PostedSJE?sjeDetail_Id=" + id;

        JsonObjectRequest request = null;

        request = new JsonObjectRequest(
                Request.Method.PUT, URL + Query, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String Msg = response.optString("Msg");
                        String Error = response.optString("Error");

                        if (Msg.equals("Record Updated")) {
                            Toasty.success(mContext, Msg, Toast.LENGTH_SHORT, true).show();
                        } else {
                            Toasty.error(mContext, Error, Toast.LENGTH_LONG, true).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(mContext, error.getMessage(), Toast.LENGTH_LONG, false).show();
            }
        });
        queue.add(request);
    }

    void delete_Data(String Posted_SJE_Id) {

        String query = "Post_SJE/Remove_Post?Posted_SJE_Id=" + Posted_SJE_Id;

        StringRequest request = new StringRequest(
                Request.Method.DELETE, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.replace("\"","").equals("Record Deleted")){
                            Toasty.success(mContext, response, Toast.LENGTH_SHORT, true).show();

                        }
                        else{
                            Toasty.error(mContext, response, Toast.LENGTH_SHORT, true).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(mContext, error.getMessage(), Toast.LENGTH_SHORT, true).show();

            }
        }
        );
        queue.add(request);

    }

}
