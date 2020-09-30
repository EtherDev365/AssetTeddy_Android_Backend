package com.example.myapplication.ui.Track;

import android.os.Bundle;

import com.example.myapplication.common.HttpCall;
import com.example.myapplication.model.SessionManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TrackView extends AppCompatActivity {

    private HttpCall httpCall = new HttpCall();
    private String commonUrl = httpCall.getCommonUrl();
    public String getAssetBarcodeUrl = commonUrl + "/api/getAssetByBarcode";
    TextView serialNumberView;
    TextView assetTagView;
    TextView typeView;
    TextView descriptionView;
    TextView locationView;
    TextView departmentView;
    TextView statusView;
    TextView usernameView;
    TextView remarkView;
    ProgressBar trackAssetProgressbar;
    SessionManager sessionManager;
    JSONArray assetArray = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_view);

        sessionManager = new SessionManager(getApplicationContext());
        trackAssetProgressbar = (ProgressBar)findViewById(R.id.track_asset_progressbar) ;
        trackAssetProgressbar.setVisibility(View.GONE);

        serialNumberView = (TextView)findViewById(R.id.serial_number_by_track);
        assetTagView = (TextView)findViewById(R.id.asset_tag_by_track);
        typeView = (TextView)findViewById(R.id.type_by_track);
        descriptionView = (TextView)findViewById(R.id.description_by_track);
        locationView = (TextView)findViewById(R.id.location_by_track);
        departmentView = (TextView)findViewById(R.id.department_by_track);
        statusView = (TextView)findViewById(R.id.status_by_track);
        usernameView = (TextView)findViewById(R.id.username_by_track);
        remarkView = (TextView)findViewById(R.id.remark_by_track);

        Button trackBackButton = (Button)findViewById(R.id.track_asset_back);
        trackBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Log.e("barcdoevalue",sessionManager.getTrackBarcode());
        getAssetByBarcode();
    }

    private void getAssetByBarcode(){
        trackAssetProgressbar.setVisibility(View.VISIBLE);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getAssetBarcodeUrl + "/" + sessionManager.getBarcode() )
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
                runOnUiThread(new Runnable() { public void run() {
                    try {
                        JSONObject jr = new JSONObject(resp);
                        if (response.code() == 200) {
                            assetArray = jr.getJSONArray("data");
                            JSONObject  attachment = (JSONObject) assetArray.get(0);
                            setAssetData(attachment);
                            trackAssetProgressbar.setVisibility(View.GONE);
                            Toast.makeText(TrackView.this, "get asset successfully ", Toast.LENGTH_SHORT).show();                        } else {
                        }
                    } catch (JSONException e) {
                        Log.e("TAG", "Exception", e);
                        trackAssetProgressbar.setVisibility(View.GONE);
                        Toast.makeText(TrackView.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }});
            }
        });
    }

    private void setAssetData(JSONObject attachment) throws JSONException{
        serialNumberView.setText(attachment.getString("serial_number"));
        assetTagView.setText(attachment.getString("barcode"));
        typeView.setText(attachment.getString("asset_type"));
        descriptionView.setText(attachment.getString("description"));
        locationView.setText(attachment.getString("location"));
        departmentView.setText(attachment.getString("department"));
        statusView.setText(attachment.getString("status"));
        usernameView.setText(attachment.getString("updated_by_user"));
        remarkView.setText(attachment.getString("remark"));
    }
}