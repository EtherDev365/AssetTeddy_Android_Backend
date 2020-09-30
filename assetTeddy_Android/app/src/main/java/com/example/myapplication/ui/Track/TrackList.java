package com.example.myapplication.ui.Track;

import android.os.Bundle;

import com.example.myapplication.common.HttpCall;
import com.example.myapplication.model.SessionManager;
import com.example.myapplication.ui.Update_Asset.AssetList;
import com.example.myapplication.ui.Update_Asset.NewAssetArrayAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.R;

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

public class TrackList extends AppCompatActivity {

    private HttpCall httpCall = new HttpCall();
    private String commonUrl = httpCall.getCommonUrl();
    ProgressBar trackListProgressbar;
    SessionManager sessionManager ;
    JSONArray userArray = new JSONArray();
    ArrayList<String> assetList = new ArrayList<String >();
    ArrayList<String> idList = new ArrayList<String >();
    ListView trackAssetList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_list);
        sessionManager = new SessionManager(getApplicationContext());
        trackListProgressbar = (ProgressBar) findViewById(R.id.track_list_progressbar);
        trackListProgressbar.setVisibility(ProgressBar.GONE);
        trackAssetList = (ListView) findViewById(R.id.track_list_view);
    }

//    private void getAssetTrackList() {
//
//        final String trackLocationValue = sessionManager.getTrackLocation();
//        String  temp = sessionManager.getDeviceType();
//
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(commonUrl + "/api/trackAsset/" + trackLocationValue + "/" + trackDeviceTypeValue)
//                .addHeader("Authorization", "Bearer " + sessionManager.getToken())
//                .method("GET", null)
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("TAG", "Exception", e);
//                call.cancel();
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//                ResponseBody responseBody = response.body();
//                final String resp = responseBody.string();
//                AssetList.this.runOnUiThread(new Runnable() { public void run() {
//                    try {
//                        JSONObject jr = new JSONObject(resp);
//                        if (response.code() == 200) {
//                            userArray = jr.getJSONArray("data");
//                            for (int i = 0; i < userArray.length(); i++) {
//                                JSONObject  attachment = (JSONObject) userArray.get(i);
//                                String assetTag = attachment.getString("barcode");
//                                String id = attachment.getString("id");
//                                barcodeList.add(assetTag);
//                                idList.add(id);
//                            }
//                            NewAssetArrayAdapter adapter = new NewAssetArrayAdapter(AssetList.this, barcodeList, idList);
//                            trackAssetList.setAdapter(adapter);
//
//                            Toast.makeText(AssetList.this, " tracked assets successfully ", Toast.LENGTH_SHORT).show();
//                            getTrackAssetProgressbar.setVisibility(ProgressBar.GONE);
//
//                        } else {
//                            Toast.makeText(AssetList.this, " not exist", Toast.LENGTH_SHORT).show();
//                            getTrackAssetProgressbar.setVisibility(ProgressBar.GONE);
//                        }
//                    } catch (JSONException e) {
//                        Log.e("TAG", "Exception", e);
//                        getTrackAssetProgressbar.setVisibility(ProgressBar.GONE);
//                        Toast.makeText(AssetList.this, "Server Error", Toast.LENGTH_SHORT).show();
//                    }
//                }});
//            }
//        });
//    }
//
//    public void onPause() {
//        super.onPause();
//        isBackFromB = true;
//    }
//
//    public void onResume() {
//        super.onResume();
//        if (isBackFromB){
//            barcodeList.clear();
//            idList.clear();
//            getTrackAssets();
//        }
//
//    }
}