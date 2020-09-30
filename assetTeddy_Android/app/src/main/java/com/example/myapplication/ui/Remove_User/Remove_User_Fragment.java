package com.example.myapplication.ui.Remove_User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Sign_in;
import com.example.myapplication.common.HttpCall;
import com.example.myapplication.model.AppParam;
import com.example.myapplication.model.SessionManager;
import com.example.myapplication.ui.User_Register.User_Register_Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Remove_User_Fragment extends Fragment {

    private HttpCall httpCall = new HttpCall();
    private String commonUrl = httpCall.getCommonUrl();
    public String getUsersUrl = commonUrl + "/api/users";
    private  SessionManager sessionManager;
    private boolean isBackFromB;
    JSONArray userArray = new JSONArray();
    ArrayList<String> nameList = new ArrayList<String >();
    ArrayList<String> roleList = new ArrayList<String >();
    ArrayList<String> idList = new ArrayList<String >();
    ListView listview;
    Context context;
    private ProgressBar getUserProgressbar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate (R.layout.fragment_remove_user, container, false);
        sessionManager = new SessionManager(getContext());
        getUserProgressbar = (ProgressBar)root.findViewById(R.id.get_user_progressbar);
        getUserProgressbar.setVisibility(ProgressBar.GONE);

        listview = (ListView) root.findViewById(R.id.listview);
        isBackFromB=false;
        getUsers();
        return root;
    }

    public void onPause() {
        super.onPause();
        isBackFromB = true;
    }

    public void onResume() {
        super.onResume();
        if (isBackFromB){
            nameList.clear();
            roleList.clear();
            idList.clear();
            getUsers();
        }

    }

    private void getUsers() {
        getUserProgressbar.setVisibility(ProgressBar.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getUsersUrl)
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
                            userArray = jr.getJSONArray("data");
                            for (int i = 0; i < userArray.length(); i++) {
                                JSONObject  attachment = (JSONObject) userArray.get(i);
                                String name = attachment.getString("name");
                                String role = attachment.getString("role");
                                String id = attachment.getString("id");
                                nameList.add(name);
                                roleList.add(role);
                                idList.add(id);
                            }
                            MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getActivity(), nameList,roleList, idList);
                            listview.setAdapter(adapter);

                            Toast.makeText(getActivity(), "get users successfully ", Toast.LENGTH_SHORT).show();
                            getUserProgressbar.setVisibility(ProgressBar.GONE);

                        } else {
                            Toast.makeText(getActivity(), " users empty", Toast.LENGTH_SHORT).show();
                            getUserProgressbar.setVisibility(ProgressBar.GONE);
                        }
                    } catch (JSONException e) {
                        Log.e("TAG", "Exception", e);
                        getUserProgressbar.setVisibility(ProgressBar.GONE);
                        Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }});
            }
        });
    }
}
