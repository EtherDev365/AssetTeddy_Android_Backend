package com.example.myapplication.ui.Home;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.myapplication.R;
import com.example.myapplication.Sign_in;
import com.example.myapplication.common.HttpCall;
import com.example.myapplication.model.AppParam;
import com.example.myapplication.model.SessionManager;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.example.myapplication.Sign_in.JSON;

public class Homepage_Fragment extends Fragment {

    private HttpCall httpCall = new HttpCall();
    private String commonUrl = httpCall.getCommonUrl();
    private  SessionManager sessionManager;
    GridView homeGridView;
    Adapter homeAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        sessionManager = new SessionManager(getContext());

//        homeGridView = (GridView) view.findViewById(R.id.home_grid_view);
//        homeAdapter = new GridAdapter(getActivity(), listService);
//        homeGridView.setAdapter(homeAdapter);

        LinearLayout added=(LinearLayout) view.findViewById (R.id.addnewasset);
        LinearLayout update=(LinearLayout) view.findViewById (R.id.update);
        LinearLayout searchFirst=(LinearLayout) view.findViewById (R.id.searchfirst);
        LinearLayout report=(LinearLayout) view.findViewById (R.id.report);
        LinearLayout register=(LinearLayout) view.findViewById (R.id.register);
        LinearLayout updateUser=(LinearLayout) view.findViewById (R.id.reuser);
        LinearLayout taskFirst=(LinearLayout) view.findViewById (R.id.taskfirst);
        LinearLayout logoutFirst=(LinearLayout) view.findViewById (R.id.logoutfirst);

        added.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController (getActivity (), R.id.nav_host_fragment);
                navController.navigate (R.id.Add_New_Asset);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController (getActivity (), R.id.nav_host_fragment);
                navController.navigate (R.id.Update_Asset);
            }
        });

        searchFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController (getActivity (), R.id.nav_host_fragment);
                navController.navigate (R.id.Track_Asset);
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController (getActivity (), R.id.nav_host_fragment);
                navController.navigate (R.id.Generate_Report);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController (getActivity (), R.id.nav_host_fragment);
                navController.navigate (R.id.User_Register);
            }
        });
        updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController (getActivity (), R.id.nav_host_fragment);
                navController.navigate (R.id.Remove_User);
            }
        });
        taskFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController (getActivity (), R.id.nav_host_fragment);
                navController.navigate (R.id.Tasks);
            }
        });

        logoutFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    logoutHandle();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mainIconHandle(added,update, searchFirst, report, register, updateUser, taskFirst ,view);
        return view;
    }

    private void mainIconHandle(LinearLayout addNew, LinearLayout updateAsset, LinearLayout trackAsset, LinearLayout reportAsset, LinearLayout registerUser, LinearLayout removeUser, LinearLayout task, View view){

        if(sessionManager.getRole().equals("admin")){
//            homeGridView.removeView(addNew);
//            homeGridView.removeView(updateAsset);
//            homeGridView.removeView(trackAsset);
            addNew.setVisibility(View.INVISIBLE);
            updateAsset.setVisibility(View.INVISIBLE);
            trackAsset.setVisibility(View.INVISIBLE);
        }else if(sessionManager.getRole().equals("technical staff")){
            reportAsset.setVisibility(View.INVISIBLE);
            registerUser.setVisibility(View.INVISIBLE);
            removeUser.setVisibility(View.INVISIBLE);
            task.setVisibility(View.INVISIBLE);
        }else if(sessionManager.getRole().equals("compliance team")){
            addNew.setVisibility(View.INVISIBLE);
            registerUser.setVisibility(View.INVISIBLE);
            removeUser.setVisibility(View.INVISIBLE);
            task.setVisibility(View.INVISIBLE);
        }
    }

    private void logoutHandle( ) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(commonUrl + "/api/logout")
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
                final String myResponse = response.body().string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response.code() == 200){
                            Toast.makeText(getActivity(), "logout successfully!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity ().getApplicationContext(),Sign_in.class);
                            startActivity(i);
                        }
                    }
                });
            }
        });
    }

}