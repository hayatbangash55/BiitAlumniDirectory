package com.example.biitalumnidirectory.SearchAlumni;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.ImageUtil;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.MyProfile.ShowProfile_Fragment;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class Search_RecycleView_Adapter extends RecyclerView.Adapter<Search_RecycleView_Adapter.ViewHolder> {

    private ArrayList<Search_Class> search_list = new ArrayList<Search_Class>();
    private Context mContext;
    SharedReference SharedRef;
    public String URL;
    public RequestQueue queue;
    FragmentManager manager;

    public Search_RecycleView_Adapter(Context mContext, ArrayList<Search_Class> search_list, FragmentManager manager) {
        this.search_list = search_list;
        this.mContext = mContext;
        SharedRef = new SharedReference(mContext);
        queue = Volley.newRequestQueue(mContext);
        URL = MyIp.ip;
        this.manager = manager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Search_Class s = search_list.get(position);
        holder.tv_userName.setText(s.Name);
        holder.tv_aridNo.setText(s.Arid_No);
        String sec = s.Dicipline + "-" + s.Semester + s.Section;
        holder.tv_class.setText(sec);

        Bitmap bitmap = ImageUtil.convertToImage(s.NewImage);
        holder.search_Img1.setImageBitmap(bitmap);

        bitmap = ImageUtil.convertToImage(s.OldImage);
        holder.search_Img2.setImageBitmap(bitmap);

        holder.btnFriend.setText(s.Status);

        if (s.Status.equals("Friend")) {
            holder.btnFriend.setText("UnFriend");
        }

        holder.btn_deleteRequest.setVisibility(View.GONE);

        if (s.Status.equals("Request Sent") || s.Status.equals("Accept Request")) {
            holder.btn_deleteRequest.setVisibility(View.VISIBLE);
        }


        holder.btnFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.btnFriend.getText().toString().equals("Add Friend")) {
                    add_Friend(SharedRef.get_LoadUserDataByCNIC(), s.CNIC, holder);
                } else if (holder.btnFriend.getText().toString().equals("UnFriend")) {
                    delete_Data(SharedRef.get_LoadUserDataByCNIC(), s.CNIC, holder);
                } else if (holder.btnFriend.getText().toString().equals("Accept Request")) {
                    confirm_Request(SharedRef.get_LoadUserDataByCNIC(), s.CNIC, holder);
                }
            }
        });


        holder.btn_deleteRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_Data(SharedRef.get_LoadUserDataByCNIC(), s.CNIC, holder);
            }
        });


        //search
        holder.item_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedRef.save_LoadUserDataByCNIC(s.CNIC);
                Intent intent = new Intent(mContext, Show_Searched_Clicked_Profile_Activity.class);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return search_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_userName, tv_aridNo, tv_class;
        CardView item_cardView;
        ImageView search_Img1, search_Img2;
        Button btnFriend, btn_deleteRequest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_aridNo = itemView.findViewById(R.id.tv_aridNo);
            tv_userName = itemView.findViewById(R.id.tv_user_name);
            tv_class = itemView.findViewById(R.id.tv_class);
            item_cardView = itemView.findViewById(R.id.parent_layout);
            search_Img1 = itemView.findViewById(R.id.search_Img1);
            search_Img2 = itemView.findViewById(R.id.search_Img2);
            btnFriend = itemView.findViewById(R.id.btn_friend);
            btn_deleteRequest = itemView.findViewById(R.id.btn_deleteRequest);

        }


    }

    public void add_Friend(String userCNIC, String friendCNIC, final ViewHolder holder) {

        try {
            String query = "Friends/Insert_Make_Friends";

            JSONObject obj = new JSONObject();
            obj.put("CNIC", userCNIC);
            obj.put("Friend_CNIC", friendCNIC);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST, URL + query, obj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            String Msg = response.optString("Msg");
                            String Error = response.optString("Error");

                            if (Msg.equals("Request Sent")) {
                                holder.btnFriend.setText("Request Sent");
                                holder.btn_deleteRequest.setVisibility(View.VISIBLE);

                                Toasty.success(mContext, Msg, Toast.LENGTH_SHORT, true).show();

                            } else {
                                Toasty.error(mContext, Error, Toast.LENGTH_LONG, true).show();

                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toasty.error(mContext, error.getMessage(), Toast.LENGTH_LONG, true).show();

                }
            }
            );

            queue.add(request);

        } catch (JSONException e) {
            Toasty.error(mContext, e.getMessage(), Toast.LENGTH_LONG, true).show();

        }
    }

    void delete_Data(String CNIC, String Friend_CNIC, final ViewHolder holder) {

        String query = "Friends/Remove_Friends?CNIC=" + CNIC + "&Friend_CNIC=" + Friend_CNIC;

        StringRequest request = new StringRequest(
                Request.Method.DELETE, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.replace("\"", "").equals("Friend Removed")) {
                            Toasty.success(mContext, response, Toast.LENGTH_SHORT, true).show();

                            holder.btnFriend.setText("Add Friend");
                            holder.btn_deleteRequest.setVisibility(View.GONE);
                        } else {
                            Toasty.error(mContext, response, Toast.LENGTH_LONG, true).show();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(mContext, error.toString(), Toast.LENGTH_LONG, true).show();

            }
        }
        );
        queue.add(request);

    }

    void confirm_Request(String userCNIC, String friendCNIC, final ViewHolder holder) {

        try {
            String query = "Friends/Confirm_Request";

            JSONObject obj = new JSONObject();
            obj.put("CNIC", userCNIC);
            obj.put("Friend_CNIC", friendCNIC);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.PUT, URL + query, obj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            String Msg = response.optString("Msg");
                            String Error = response.optString("Error");

                            if (Msg.equals("Friend Added")) {
                                holder.btnFriend.setText("UnFriend");
                                holder.btn_deleteRequest.setVisibility(View.GONE);

                                Toasty.success(mContext, Msg, Toast.LENGTH_SHORT, true).show();

                            } else {
                                Toasty.error(mContext, Error, Toast.LENGTH_LONG, true).show();

                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toasty.error(mContext, error.getMessage(), Toast.LENGTH_LONG, true).show();

                        }
                    });

            queue.add(request);

        } catch (JSONException e) {
            Toasty.error(mContext, e.getMessage(), Toast.LENGTH_LONG, true).show();

        }

    }

}
