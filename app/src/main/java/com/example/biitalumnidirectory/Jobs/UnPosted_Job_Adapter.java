package com.example.biitalumnidirectory.Jobs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.Filter_Admin_Post_SJENotification_Activity;
import com.example.biitalumnidirectory.Filter_Alumni_Post_SJENotification_Activity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SJE_Detail;
import com.example.biitalumnidirectory.SharedReference;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class UnPosted_Job_Adapter extends RecyclerView.Adapter<UnPosted_Job_Adapter.ViewHolder> {

    private ArrayList<SJE_Detail> job_list = new ArrayList<SJE_Detail>();
    private Context mContext;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;


    public UnPosted_Job_Adapter(Context mContext, ArrayList<SJE_Detail> job_list) {
        this.job_list = job_list;
        this.mContext = mContext;
        queue = Volley.newRequestQueue(mContext);
        URL = MyIp.ip;
        SharedRef = new SharedReference(mContext);
    }


    @NonNull
    @Override
    public UnPosted_Job_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_job,parent,false);
        UnPosted_Job_Adapter.ViewHolder holder = new UnPosted_Job_Adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final UnPosted_Job_Adapter.ViewHolder holder, final int position) {

        final SJE_Detail s = job_list.get(position);

        holder.tv_jobTitle.setText(s.Title);
        holder.tv_Date.setText(s.Ending_Date);
        holder.tv_Venue.setText(s.Venue);

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Post Event");
                builder.setMessage("Are you sure you want to post the \n Job ("+s.Title+") ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        String login_type = SharedRef.get_UserType();
                        if (login_type.equalsIgnoreCase("admin")){

                            Intent intent = new Intent(mContext, Filter_Admin_Post_SJENotification_Activity.class);
                            intent.putExtra("SJE_Detail_Id", s.SJE_Detail_Id);
                            intent.putExtra("SJEDetail_Type", s.SJE_Type);
                            intent.putExtra("SJEDetail_Title", s.Title);
                            mContext.startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(mContext, Filter_Alumni_Post_SJENotification_Activity.class);
                            intent.putExtra("SJE_Detail_Id", s.SJE_Detail_Id);
                            intent.putExtra("SJEDetail_Type", s.SJE_Type);
                            intent.putExtra("SJEDetail_Title", s.Title);
                            mContext.startActivity(intent);
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        holder.edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, EditJob_Activity.class);
                intent.putExtra("JobId", s.SJE_Detail_Id);
                mContext.startActivity(intent);
            }
        });

        holder.delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delete_Data(s.SJE_Detail_Id);
            }
        });
    }


    @Override
    public int getItemCount() {
        return job_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

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



    void delete_Data(String val_id) {

        String query = "SJE_Detail/Remove_SJE_Detail?id=" + val_id+"&type=Job";


        StringRequest request = new StringRequest(
                Request.Method.DELETE, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.replace("\"", "").equals("Record Deleted")) {
                            Toasty.success(mContext, response, Toast.LENGTH_SHORT, true).show();

                        } else {
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
