package com.example.biitalumnidirectory.MyProfile;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import java.util.ArrayList;

public class Attributes_Adapter extends RecyclerView.Adapter<Attributes_Adapter.ViewHolder> {

    private ArrayList<String> list_data = new ArrayList<String>();
    private Context mContext;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;
    FragmentManager manager;



    public Attributes_Adapter(Context mContext, ArrayList<String> list_data, FragmentManager manager) {
        this.list_data = list_data;
        this.mContext = mContext;
        URL = MyIp.ip;
        queue = Volley.newRequestQueue(mContext);
        this.manager = manager;
        SharedRef = new SharedReference(mContext);

    }

    @NonNull
    @Override
    public Attributes_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attribute_info, parent, false);
        Attributes_Adapter.ViewHolder holder = new Attributes_Adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final Attributes_Adapter.ViewHolder holder, final int position) {

        final String s = list_data.get(position);
        holder.attribute.setText(s);

        holder.parent_layout.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.parseColor("#d4f8d4"));



    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView attribute;
        LinearLayout parent_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            attribute = itemView.findViewById(R.id.attribute);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
