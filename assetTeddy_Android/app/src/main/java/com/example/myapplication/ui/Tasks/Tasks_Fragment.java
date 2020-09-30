package com.example.myapplication.ui.Tasks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

public class Tasks_Fragment extends Fragment {

    private HttpCall httpCall = new HttpCall();
    private String commonUrl = httpCall.getCommonUrl();
    public String getTaskListUrl = commonUrl + "/api/currentTaskAssets";
    private  SessionManager sessionManager;
    private boolean isBackFromB;
    JSONArray assetArray = new JSONArray();
    ArrayList<String> typeList = new ArrayList<String >();
    ArrayList<String> usernameList = new ArrayList<String >();
    ArrayList<String> dateList = new ArrayList<String >();
    ArrayList<String> barcodeList = new ArrayList<String >();
    ArrayList<String> idList = new ArrayList<String >();
    ProgressBar taskListProgressbar;
    ListView taskListView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate (R.layout.fragment_tasks, container, false);
        sessionManager = new SessionManager(getContext());
        taskListProgressbar = (ProgressBar)root.findViewById(R.id.task_list_progressbar);
        taskListProgressbar.setVisibility(ProgressBar.GONE);

        taskListView = (ListView) root.findViewById(R.id.task_list);
        isBackFromB=false;
        getLatestAsset();
        return root;
    }

    public void onPause() {
        super.onPause();
        isBackFromB = true;
    }

    public void onResume() {
        super.onResume();
        if (isBackFromB){
            typeList.clear();
            usernameList.clear();
            dateList.clear();
            barcodeList.clear();
            idList.clear();
            getLatestAsset();
        }
    }

    private void getLatestAsset() {
        taskListProgressbar.setVisibility(ProgressBar.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getTaskListUrl)
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
                            assetArray = jr.getJSONArray("data");
                            for (int i = 0; i < assetArray.length(); i++) {
                                JSONObject  attachment = (JSONObject) assetArray.get(i);
                                String type = attachment.getString("asset_type");
                                String barcode = attachment.getString("barcode");
                                String username = attachment.getString("updated_by_user");
                                String updated_at = attachment.getString("updated_at").substring(0, 19);
                                String id = attachment.getString("id");
                                typeList.add(type);
                                usernameList.add(username);
                                dateList.add(updated_at);
                                barcodeList.add(barcode);
                                idList.add(id);
                            }

                            TaskArrayAdapter adapter = new TaskArrayAdapter(getActivity(), typeList, usernameList, dateList,barcodeList, idList);
                            taskListView.setAdapter(adapter);

                            Toast.makeText(getActivity(), "get latest activities successfully ", Toast.LENGTH_SHORT).show();
                            taskListProgressbar.setVisibility(ProgressBar.GONE);

                        } else {
                            Toast.makeText(getActivity(), " activity is empty", Toast.LENGTH_SHORT).show();
                            taskListProgressbar.setVisibility(ProgressBar.GONE);
                        }
                    } catch (JSONException e) {
                        Log.e("TAG", "Exception", e);
                        taskListProgressbar.setVisibility(ProgressBar.GONE);
                        Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }});
            }
        });
    }
}