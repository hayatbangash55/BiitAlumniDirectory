package com.example.biitalumnidirectory.Survey;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.biitalumnidirectory.MyIp;
import com.example.biitalumnidirectory.R;
import com.example.biitalumnidirectory.SharedReference;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class Survey_Statistics_Adapter extends RecyclerView.Adapter<Survey_Statistics_Adapter.ViewHolder> {

    private ArrayList<Survey_Statistics> survey_statistics_list = new ArrayList<Survey_Statistics>();
    private Context mContext;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;


    public Survey_Statistics_Adapter(Context mContext, ArrayList<Survey_Statistics> survey_statistics_list) {
        this.survey_statistics_list = survey_statistics_list;
        this.mContext = mContext;
        queue = Volley.newRequestQueue(mContext);
        URL = MyIp.ip;
        SharedRef = new SharedReference(mContext);

    }

    @NonNull
    @Override
    public Survey_Statistics_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_survey_statistics, parent, false);
        Survey_Statistics_Adapter.ViewHolder holder = new Survey_Statistics_Adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final Survey_Statistics_Adapter.ViewHolder holder, final int position) {

        final Survey_Statistics s = survey_statistics_list.get(position);

        holder.pieChart.setUsePercentValues(true);
        holder.question.setText(s.Question_No+") "+s.Question);

        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        int count = 1;

        int val = Integer.parseInt(s.Total_Option1);
        yvalues.add(new PieEntry(val, s.Option1, count++));

        val = Integer.parseInt(s.Total_Option2);
        yvalues.add(new PieEntry(val, s.Option2, count++));

        if (!s.Option3.equals("")) {
            val = Integer.parseInt(s.Total_Option3);
            yvalues.add(new PieEntry(val, s.Option3, count++));
        }

        if (!s.Option4.equals("")) {
            val = Integer.parseInt(s.Total_Option4);
            yvalues.add(new PieEntry(val, s.Option4, count++));
        }

        if (!s.Option5.equals("")) {
            val = Integer.parseInt(s.Total_Option5);
            yvalues.add(new PieEntry(val, s.Option5, count++));
        }

        PieDataSet dataSet = new PieDataSet(yvalues, "");
        PieData data = new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter());
        holder.pieChart.setData(data);
        Description description = new Description();
        description.setText("");
        holder.pieChart.setDescription(description);
        holder.pieChart.setDrawHoleEnabled(false);
//        holder.pieChart.setTransparentCircleRadius(90f);
 //       holder.pieChart.setHoleRadius(58f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        data.setValueTextSize(30f);
        data.setValueTextColor(Color.WHITE);

    }

    @Override
    public int getItemCount() {
        return survey_statistics_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView question;
        PieChart pieChart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.question);
            pieChart = itemView.findViewById(R.id.mpchart);
        }
    }

}

