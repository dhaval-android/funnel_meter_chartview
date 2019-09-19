package com.db.funnelmeterchart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.db.funnel_meterchartview.FunnelChartData;
import com.db.funnel_meterchartview.FunnelChartView;
import com.db.funnel_meterchartview.MeterChartView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private FunnelChartView funnelChart;
    private MeterChartView meterChart;
    private AppCompatEditText mEdtVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        updateFunnelChartData();
    }

    private void initView() {
        funnelChart = findViewById(R.id.funnelChart);
        meterChart = findViewById(R.id.meterChart);
        mEdtVal = findViewById(R.id.mEdtVal);
    }

    private void initListener() {
        mEdtVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mEdtVal.getText() != null && mEdtVal.getText().toString().length() > 0) {
                    meterChart.rotateHand(Float.parseFloat(mEdtVal.getText().toString().trim()));
                } else {
                    meterChart.rotateHand(0f);
                }
            }
        });
    }


    //Dummy Data for funnel Chart
    private void updateFunnelChartData() {
        ArrayList<FunnelChartData> mDataSet = new ArrayList<>();
        mDataSet.add(new FunnelChartData("#FF5733", "Chart Data 1"));
        mDataSet.add(new FunnelChartData("#DAF7A6", "Chart Data 2"));
        mDataSet.add(new FunnelChartData("#CE93D8", "Chart Data 3"));
        mDataSet.add(new FunnelChartData("#4DB6AC", "Chart Data 4"));
        funnelChart.setmDataSet(mDataSet);
    }

}
