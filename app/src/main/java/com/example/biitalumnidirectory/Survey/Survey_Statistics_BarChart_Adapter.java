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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Survey_Statistics_BarChart_Adapter extends RecyclerView.Adapter<Survey_Statistics_BarChart_Adapter.ViewHolder> {

    private ArrayList<Survey_Statistics> survey_statistics_list = new ArrayList<Survey_Statistics>();
    private Context mContext;
    public String URL;
    public RequestQueue queue;
    SharedReference SharedRef;


    public Survey_Statistics_BarChart_Adapter(Context mContext, ArrayList<Survey_Statistics> survey_statistics_list) {
        this.survey_statistics_list = survey_statistics_list;
        this.mContext = mContext;
        queue = Volley.newRequestQueue(mContext);
        URL = MyIp.ip;
        SharedRef = new SharedReference(mContext);

    }

    @NonNull
    @Override
    public Survey_Statistics_BarChart_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_survey_statistics_barchart, parent, false);
        Survey_Statistics_BarChart_Adapter.ViewHolder holder = new Survey_Statistics_BarChart_Adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final Survey_Statistics_BarChart_Adapter.ViewHolder holder, final int position) {

        final Survey_Statistics s = survey_statistics_list.get(position);

        //holder.pieChart.setUsePercentValues(true);
        holder.question.setText(s.Question_No +") "+s.Question);

        ArrayList<BarEntry> yvalues = new ArrayList<BarEntry>();

        int val = Integer.parseInt(s.Total_Option1);
        yvalues.add(new BarEntry(0, val));

        val = Integer.parseInt(s.Total_Option2);
        yvalues.add(new BarEntry(1,val));

        if (!s.Option3.equals("")) {
            val = Integer.parseInt(s.Total_Option3);
            yvalues.add(new BarEntry(2,val));
        }

        if (!s.Option4.equals("")) {
            val = Integer.parseInt(s.Total_Option4);
            yvalues.add(new BarEntry(3,val));
        }

        if (!s.Option5.equals("")) {
            val = Integer.parseInt(s.Total_Option5);
            yvalues.add(new BarEntry(4, val));
        }
        ArrayList<String> BarEntryLabels = new ArrayList<>() ;
        BarEntryLabels.add("January");
        BarEntryLabels.add("February");
        BarEntryLabels.add("March");
        BarEntryLabels.add("April");
        BarEntryLabels.add("May");
        BarEntryLabels.add("June");

        BarDataSet dataSet = new BarDataSet(yvalues, "");
        BarData data = new BarData(dataSet);

        data.setValueFormatter(new PercentFormatter());
        holder.pieChart.setData(data);
        Description description = new Description();
        description.setText("");
        holder.pieChart.setDescription(description);
        //holder.pieChart.setDrawHoleEnabled(false);
//        holder.pieChart.setTransparentCircleRadius(90f);
 //       holder.pieChart.setHoleRadius(58f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setDrawValues(true);
        data.setValueTextSize(23f);
        data.setValueTextColor(Color.WHITE);
        holder.pieChart.invalidate();
        holder.pieChart.animateY(500);
    }

    @Override
    public int getItemCount() {
        return survey_statistics_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView question;
        BarChart pieChart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.question);
            pieChart = itemView.findViewById(R.id.mpchart);
        }
    }

}

