package com.example.biitalumnidirectory.Friends;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.ImageUtil;
import com.example.biitalumnidirectory.MainActivity;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.MyProfile.ShowProfile_Fragment;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SearchAlumni.Show_Searched_Clicked_Profile_Activity;
import com.example.biitalumnidirectory.SharedReference;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class RequestSent_Adapter extends RecyclerView.Adapter<RequestSent_Adapter.ViewHolder> {

    private ArrayList<Friend> friend_list = new ArrayList<Friend>();
    private Context mContext;
    SharedReference SharedRef;
    public String URL;
    public RequestQueue queue;
    FragmentManager manager;

    public RequestSent_Adapter(Context mContext, ArrayList<Friend> friend_list, FragmentManager manager) {
        this.friend_list = friend_list;
        this.mContext = mContext;
        SharedRef = new SharedReference(mContext);
        queue = Volley.newRequestQueue(mContext);
        URL = MyIp.ip;
        this.manager = manager;
    }

    @NonNull
    @Override
    public RequestSent_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_list_view, parent, false);
        RequestSent_Adapter.ViewHolder holder = new RequestSent_Adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final RequestSent_Adapter.ViewHolder holder, final int position) {

        final Friend s = friend_list.get(position);
        holder.tv_userName.setText(s.Name);
        holder.tv_aridNo.setText(s.Arid_No);

        String sec = s.Dicipline + "-" + s.Semester + s.Section;
        holder.tv_class.setText(sec);

        Bitmap bitmap = ImageUtil.convertToImage(s.NewImage);
        holder.search_Img1.setImageBitmap(bitmap);

        bitmap = ImageUtil.convertToImage(s.OldImage);
        holder.search_Img2.setImageBitmap(bitmap);
        holder.btn_friend.setText("Request Sent");



        //search
        holder.item_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedRef.save_LoadUserDataByCNIC(s.Friend_CNIC);
                Intent intent = new Intent(mContext, Show_Searched_Clicked_Profile_Activity.class);
                mContext.startActivity(intent);

            }
        });


        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = SharedRef.get_LoginCNIC();
                delete_Data(val,s.Friend_CNIC, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return friend_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_userName, tv_aridNo, tv_class;
        CardView item_cardView;
        ImageView search_Img1, search_Img2;
        Button btn_friend,btn_delete;

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

        }


    }

    void delete_Data(String CNIC, String Friend_CNIC, final int position) {

        String query = "Friends/Remove_Friends?CNIC=" + CNIC+"&Friend_CNIC="+Friend_CNIC;

        StringRequest request = new StringRequest(
                Request.Method.DELETE, URL + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.replace("\"","").equals("Friend Removed")){
                            friend_list.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
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

