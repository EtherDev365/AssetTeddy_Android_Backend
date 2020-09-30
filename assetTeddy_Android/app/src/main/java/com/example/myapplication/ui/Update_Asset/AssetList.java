package com.example.myapplication.ui.Update_Asset;

import android.content.Context;
import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.common.HttpCall;
import com.example.myapplication.model.SessionManager;
import com.example.myapplication.ui.Remove_User.MySimpleArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AssetList extends AppCompatActivity {

    private HttpCall httpCall = new HttpCall();
    private String commonUrl = httpCall.getCommonUrl();
    ProgressBar getTrackAssetProgressbar;
    SessionManager sessionManager ;
    JSONArray userArray = new JSONArray();
    ArrayList<String> barcodeList = new ArrayList<String >();
    ArrayList<String> idList = new ArrayList<String >();
    ListView trackAssetList;
    String trackDeviceTypeValue;
    private boolean isBackFromB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_list);

        sessionManager = new SessionManager(getApplicationContext());
        getTrackAssetProgressbar = (ProgressBar) findViewById(R.id.get_track_asset_progressbar);
        getTrackAssetProgressbar.setVisibility(ProgressBar.GONE);
        trackAssetList = (ListView) findViewById(R.id.track_assets_list);
        isBackFromB=false;
        getTrackAssets();
    }

    private void getTrackAssets() {

        final String trackLocationValue = sessionManager.getTrackLocation();
        String  temp = sessionManager.getDeviceType();
        Log.e("valuedata", trackLocationValue + ":" + temp);
        if(temp == null){
            trackDeviceTypeValue = "Monitor";
        }else {
            trackDeviceTypeValue = sessionManager.getDeviceType();
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(commonUrl + "/api/trackAsset/" + trackLocationValue + "/" + trackDeviceTypeValue)
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
                AssetList.this.runOnUiThread(new Runnable() { public void run() {
                    try {
                        JSONObject jr = new JSONObject(resp);
                        if (response.code() == 200) {
                            userArray = jr.getJSONArray("data");
                            for (int i = 0; i < userArray.length(); i++) {
                                JSONObject  attachment = (JSONObject) userArray.get(i);
                                String assetTag = attachment.getString("barcode");
                                String id = attachment.getString("id");
                                barcodeList.add(assetTag);
                                idList.add(id);
                            }
                            NewAssetArrayAdapter adapter = new NewAssetArrayAdapter(AssetList.this, barcodeList, idList);
                            trackAssetList.setAdapter(adapter);

                            Toast.makeText(AssetList.this, " tracked assets successfully ", Toast.LENGTH_SHORT).show();
                            getTrackAssetProgressbar.setVisibility(ProgressBar.GONE);

                        } else {
                            Toast.makeText(AssetList.this, " not exist", Toast.LENGTH_SHORT).show();
                            getTrackAssetProgressbar.setVisibility(ProgressBar.GONE);
                        }
                    } catch (JSONException e) {
                        Log.e("TAG", "Exception", e);
                        getTrackAssetProgressbar.setVisibility(ProgressBar.GONE);
                        Toast.makeText(AssetList.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }});
            }
        });
    }

    public void onPause() {
        super.onPause();
        isBackFromB = true;
    }

    public void onResume() {
        super.onResume();
        if (isBackFromB){
            barcodeList.clear();
            idList.clear();
            getTrackAssets();
        }

    }
}