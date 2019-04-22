package camelcase.technovation.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

import camelcase.technovation.BaseActivity;
import camelcase.technovation.R;
import camelcase.technovation.chat.object_classes.Constants;

public class AnalysisActivity extends BaseActivity
{
    EntryStorage entryStorage;
    Analysis analysis;

    public static final int NUMBER_OF_MOOD_TYPES = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.analysis_activity, (ViewGroup) findViewById(R.id.contents));

        entryStorage = (EntryStorage) getIntent().getSerializableExtra(Constants.ENTRY_STORAGE_KEY);
        analysis = new Analysis(entryStorage);

        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();

        for (int count = 1; count <= NUMBER_OF_MOOD_TYPES; count++)
        {
            String key = Integer.toString(count);
            data.add(new ValueDataEntry(key, analysis.findNoOfSelectedMood(count)));
        }

        pie.data(data);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view_pie);
        anyChartView.setChart(pie);

    }
}