package com.example.myapplication.ui.Generate_Report;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.common.HttpCall;
import com.example.myapplication.model.SessionManager;
import com.example.myapplication.ui.Update_Asset.AssetDetail;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Generate_Report_Fragment extends Fragment {
    private DecoView mDecoView;
    private int mBackIndex;
    private int mSeries1Index;
    private int mSeries2Index;
    private int mSeries3Index;
    private int mSeries4Index;

    private int disposeValue;
    private int faultyValue;
    private int unassignValue;
    private int assignedValue;
    private int totalValue;
    private float mSeriesMax = 0;

    private HttpCall httpCall = new HttpCall();
    private String commonUrl = httpCall.getCommonUrl();
    public String getReportUrl = commonUrl + "/api/reportData";

    private String reportType;
    LinearLayout pieChartView;
    Button monitorButton;
    Button cpuButton;
    Button mouseButton;
    Button keyboardButton;
    Button phoneButton;
    Button headsetButton;
    Button projectorButton;
    Button tvButton;
    Button modemButton;
    Button printerButton;
    Button reportGenerateButton;
    SessionManager sessionManager;
    List<String> typeCategory = new ArrayList<String>();
    ProgressBar reportProgressbar;
    JSONArray reportArray = new JSONArray();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate (R.layout.fragment_generate__report, container, false);
        mDecoView = (DecoView)root.findViewById(R.id.dynamicArcView);
        reportProgressbar = (ProgressBar)root.findViewById(R.id.report_progressbar);
        reportProgressbar.setVisibility(ProgressBar.GONE);

        // Create required data series on the DecoView

        sessionManager = new SessionManager(getContext());
        pieChartView = (LinearLayout)root.findViewById(R.id.pie_chat_view);
        monitorButton = (Button)root.findViewById(R.id.report_monitor);
        cpuButton = (Button)root.findViewById(R.id.report_cpu);
        mouseButton = (Button)root.findViewById(R.id.report_mouse);
        keyboardButton = (Button)root.findViewById(R.id.report_keyboard);
        phoneButton = (Button)root.findViewById(R.id.report_phone);
        headsetButton = (Button)root.findViewById(R.id.report_headset);
        projectorButton = (Button)root.findViewById(R.id.report_projector);
        tvButton = (Button)root.findViewById(R.id.report_tv);
        modemButton = (Button)root.findViewById(R.id.report_modem);
        printerButton = (Button)root.findViewById(R.id.report_printer);
        reportGenerateButton = (Button)root.findViewById(R.id.report_generate_button);

        reportType = "";
        pieChartView.setVisibility(LinearLayout.GONE);

        typeCategory.add("Monitor");
        typeCategory.add("CPU");
        typeCategory.add("Mouse");
        typeCategory.add("Keyboard");
        typeCategory.add("Phone");
        typeCategory.add("Headset");
        typeCategory.add("Projector");
        typeCategory.add("TV");
        typeCategory.add("Modem");
        typeCategory.add("Printer");
        reportItemHandle(monitorButton, cpuButton, mouseButton, keyboardButton, phoneButton, headsetButton, projectorButton, tvButton, modemButton, printerButton);
        reportGenerateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(reportType.equals("")){
                    Toast.makeText(getActivity(), "Select the device type.", Toast.LENGTH_SHORT).show();
                }else{
                    pieChartView.setVisibility(LinearLayout.VISIBLE);
                    getReportData(root);
                }
            }
        });
        return root;
    }

    private void createBackSeries() {
        SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                .setRange(0, mSeriesMax, 0)
                .setInitialVisibility(true)
                .build();

        mBackIndex = mDecoView.addSeries(seriesItem);
    }

    private void createDataSeries1(View view) {
        final SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor("#d3dce6"))
                .setRange(0, mSeriesMax , 0)
                .setInitialVisibility(false)
                .build();

        final TextView assignedView = (TextView)view.findViewById(R.id.assigned_view);
        seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
//                assignedView.setText(String.format("%.0f", currentPosition));
                assignedView.setText(String.valueOf(assignedValue));

            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });

        mSeries1Index = mDecoView.addSeries(seriesItem);
    }

    private void createDataSeries2(View view) {
        final SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor("#c0cbdd"))
                .setRange(0, mSeriesMax , 0)
                .setInitialVisibility(false)
                .build();

        final TextView unassignedView = (TextView)view.findViewById(R.id.unassigned_view);

        seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
//                unassignedView.setText(String.format("%.0f", currentPosition));
                unassignedView.setText(String.valueOf(unassignValue));
            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });

        mSeries2Index = mDecoView.addSeries(seriesItem);
    }

    private void createDataSeries3(View view) {
        final SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor("#7989a0"))
                .setRange(0, mSeriesMax, 0 )
                .setInitialVisibility(false)
                .build();

        final TextView faultyView= (TextView)view.findViewById(R.id.faulty_view);

        seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
//                faultyView.setText(String.format("%.0f", currentPosition));
                faultyView.setText(String.format(String.valueOf(faultyValue)));
            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });

        mSeries3Index = mDecoView.addSeries(seriesItem);
    }

    private void createDataSeries4(View view) {
        final SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor("#273444"))
                .setRange(0, mSeriesMax, 0)
                .setInitialVisibility(false)
                .build();

        final TextView disposeView = (TextView)view.findViewById(R.id.dispose_view);
        final TextView total = (TextView)view.findViewById(R.id.total);

        seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                disposeView.setText(String.valueOf(disposeValue));
//                disposeView.setText(String.format("%.0f", currentPosition));
                total.setText (String.valueOf(totalValue));

            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });

        mSeries4Index = mDecoView.addSeries(seriesItem);
    }

    private void createEvents() {

        mDecoView.executeReset();

        mDecoView.addEvent(new DecoEvent.Builder(mSeriesMax)   .setIndex(mBackIndex)     .setDuration(3000)   .setDelay(100)  .build());

        mDecoView.addEvent(new DecoEvent.Builder(disposeValue + faultyValue + unassignValue + assignedValue) .setIndex(mSeries1Index)  .setDelay(3250)   .build());

        mDecoView.addEvent(new DecoEvent.Builder(faultyValue + unassignValue + assignedValue)  .setIndex(mSeries2Index)   .setDelay(5500)     .build());


        mDecoView.addEvent(new DecoEvent.Builder(unassignValue + assignedValue).setIndex(mSeries3Index).setDelay(7500).build());

        mDecoView.addEvent(new DecoEvent.Builder(assignedValue).setIndex(mSeries4Index).setDelay(10500).build());


    }

    private void reportItemHandle(Button monitorButton, Button cpuButton, Button mouseButton, Button keyboardButton, Button phoneButton, Button headsetButton, Button projectorButton, Button tvButton, Button modemButton, Button printerButton){
        monitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportType = typeCategory.get(0);
                Toast.makeText(getActivity(), reportType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
        cpuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportType = typeCategory.get(1);
                Toast.makeText(getActivity(), reportType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
        mouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportType = typeCategory.get(2);
                Toast.makeText(getActivity(), reportType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
        keyboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportType = typeCategory.get(3);
                Toast.makeText(getActivity(), reportType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportType = typeCategory.get(4);
                Toast.makeText(getActivity(), reportType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
        headsetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportType = typeCategory.get(5);
                Toast.makeText(getActivity(), reportType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
        projectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportType = typeCategory.get(6);
                Toast.makeText(getActivity(), reportType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
        tvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportType = typeCategory.get(7);
                Toast.makeText(getActivity(), reportType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
        modemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportType = typeCategory.get(8);
                Toast.makeText(getActivity(), reportType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
        printerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportType = typeCategory.get(9);
                Toast.makeText(getActivity(), reportType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getReportData(final View view){
        disposeValue = 0;
        assignedValue = 0;
        unassignValue = 0;
        faultyValue = 0;
        totalValue = 0;
        reportProgressbar.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getReportUrl + "/" + reportType)
                .addHeader("Authorization", "Bearer " + sessionManager.getToken())
                .method("GET", null)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "Exception", e);
                call.cancel();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                ResponseBody responseBody = response.body();
                final String resp = responseBody.string();
                getActivity().runOnUiThread(new Runnable() { public void run() {
                    try {
                        JSONObject jr = new JSONObject(resp);
                        if (response.code() == 200) {
                            reportProgressbar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "get report data successfully ", Toast.LENGTH_SHORT).show();
                            reportArray = jr.getJSONArray("data");
                            for (int i = 0; i < reportArray.length(); i++) {
                                JSONObject  attachment = (JSONObject) reportArray.get(i);
                                if(i == 0){
                                    int assigned = Integer.parseInt(attachment.getString("Assigned")) ;
                                    Log.e("temp1", String.valueOf(assigned));
                                    mSeries1Index = assigned;
                                    assignedValue = assigned;
                                    totalValue += assigned;
                                }
                                if(i == 1){
                                    int unassign = Integer.parseInt(attachment.getString("Unassign")) ;
                                    mSeries2Index = unassign;
                                    unassignValue = unassign;
                                    Log.e("temp2", String.valueOf(unassign));
                                    totalValue += unassign;
                                }
                                if(i == 2){
                                    int faulty = Integer.parseInt(attachment.getString("Faulty")) ;
                                    Log.e("temp3", String.valueOf(faulty));
                                    mSeries3Index = faulty;
                                    faultyValue = faulty;
                                    totalValue += faulty;
                                }
                                if(i == 3){
                                    int dispose = Integer.parseInt(attachment.getString("Dispose")) ;
                                    Log.e("temp4", String.valueOf(dispose));
                                    mSeries4Index = dispose;
                                    disposeValue = dispose;
                                    totalValue += dispose;
                                }

                                mSeriesMax = totalValue;
//                                int unassign = Integer.parseInt(attachment.getString("Unassign"));
//                                int faulty = Integer.parseInt(attachment.getString("Faulty"));
//                                int dispose = Integer.parseInt(attachment.getString("Dispose"));
//                                mSeries1Index = assigned;
//                                mSeries2Index = 1;
//                                mSeries3Index = 1;
//                                mSeries4Index = 1;




                            }
                            createBackSeries();
                            createDataSeries1(view);
                            createDataSeries2(view);
                            createDataSeries3(view);
                            createDataSeries4(view);
                            createEvents();
                        } else {
                        }
                    } catch (JSONException e) {
                        Log.e("TAG", "Exception", e);
                        reportProgressbar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }});
            }
        });
    }


}