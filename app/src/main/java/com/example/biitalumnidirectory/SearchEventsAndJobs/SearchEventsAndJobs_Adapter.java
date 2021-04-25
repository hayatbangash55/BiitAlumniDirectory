package com.example.biitalumnidirectory.SearchEventsAndJobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;

import java.util.ArrayList;

public class SearchEventsAndJobs_Adapter extends RecyclerView.Adapter<SearchEventsAndJobs_Adapter.ViewHolder> implements Filterable {

    private ArrayList<Search_Job_Class> search_list = new ArrayList<Search_Job_Class>();
    private ArrayList<Search_Job_Class> fullsearch_list = new ArrayList<Search_Job_Class>();

    private Context mContext;
    SharedReference SharedRef;
    public String URL;
    public RequestQueue queue;
    FragmentManager manager;

    public SearchEventsAndJobs_Adapter(Context mContext, ArrayList<Search_Job_Class> search_list, FragmentManager manager) {
        this.search_list = search_list;
        fullsearch_list = new ArrayList<>(search_list);
        this.mContext = mContext;
        SharedRef = new SharedReference(mContext);
        queue = Volley.newRequestQueue(mContext);
        URL = MyIp.ip;
        this.manager = manager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumni_notification, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

         final Search_Job_Class s = search_list.get(position);
        holder.tv_title.setText(s.Title);
        holder.tv_Date.setText(s.Date);
        holder.tv_Time.setText(s.Time);
        holder.tv_NotificationType.setText(s.Type);

    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<Search_Job_Class> FilteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                FilteredList.clear();
                FilteredList.addAll(fullsearch_list);
            } else {

                String FilterPattern = constraint.toString().trim().toLowerCase();
                for (Search_Job_Class item : fullsearch_list) {
                    if (item.Title.toLowerCase().contains(FilterPattern)) {
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
            search_list.clear();
            search_list.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };


    @Override
    public int getItemCount() {
        return search_list.size();
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

    public ArrayList<Search_Job_Class> returnList(){
        return  fullsearch_list;
    }

}
