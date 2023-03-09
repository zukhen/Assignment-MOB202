package com.example.myapplication.fragment.Analysis;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Database.Expenses.ExpensesDtb;
import com.example.myapplication.Database.Revenue.RevenueDtb;
import com.example.myapplication.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;


public class AnalysisFragment extends Fragment implements OnChartValueSelectedListener {
private PieChart mPieChart;

    public AnalysisFragment() {

    }


    public static AnalysisFragment newInstance() {

        return new AnalysisFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_analysis, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        setUpChartPie();
    }

    private void setUpChartPie() {
        mPieChart.setRotationEnabled(true);
        mPieChart.setDescription(null);
        mPieChart.setHoleRadius(35f);
        mPieChart.setTransparentCircleAlpha(0);
        mPieChart.setCenterText(getString(R.string.Analysis));
        mPieChart.setCenterTextSize(10);
        mPieChart.setDrawEntryLabels(true);
        addDataSet(mPieChart);
        mPieChart.setOnChartValueSelectedListener(this);
    }

    private void addDataSet(PieChart mPieChart) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        final float a = RevenueDtb.getInstance(getContext()).revenueDAO().AnalysisRevenue();
        final float b = ExpensesDtb.getInstance(getContext()).expensesDAO().AnalysisExpenses();
        final float[] yData ={a,b} ;
        String[] xData = {getString(R.string.revenue),getString(R.string.expenses)};

        for (int i = 0; i < yData.length; i++) {
            entries.add(new PieEntry(yData[i],xData[i]));
        }
        PieDataSet pieDataSet = new PieDataSet(entries,"");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(15);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getContext().getResources().getColor(R.color.yellow));
        colors.add(getContext().getResources().getColor(R.color.yellow_900));
        pieDataSet.setColors(colors);

        Legend legend = mPieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);


        PieData pieData = new PieData(pieDataSet);
        mPieChart.setData(pieData);
        mPieChart.invalidate();
//        animation
        mPieChart.animateY(1500);
        mPieChart.getLegend().setEnabled(false);
    }

    private void initUi(View view) {
        mPieChart =view.findViewById(R.id.pieChart);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
      }

    @Override
    public void onNothingSelected() {

    }
}