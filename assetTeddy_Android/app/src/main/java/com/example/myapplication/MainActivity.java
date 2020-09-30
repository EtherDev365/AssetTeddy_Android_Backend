package com.example.myapplication;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.common.HttpCall;
import com.example.myapplication.model.SessionManager;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class MainActivity extends AppCompatActivity {
    private HttpCall httpCall = new HttpCall();
    private String commonUrl = httpCall.getCommonUrl();
    public String postUrl = commonUrl + "/api/users";
    private AppBarConfiguration mAppBarConfiguration;
    private  SessionManager sessionManager ;
    private Context context;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);
        toastMsg("Login successfully.");
          sessionManager = new SessionManager(getApplicationContext());
          try {
              getrole(postUrl);
          } catch (IOException e) {
              e.printStackTrace ();
          }
         final DrawerLayout drawer = findViewById (R.id.drawer_layout);
        NavigationView navigationView = findViewById (R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder (R.id.Homepage,
                R.id.Add_New_Asset, R.id.Update_Asset, R.id.Track_Asset,R.id.Generate_Report,R.id.User_Register,R.id.Remove_User,R.id.Tasks,R.id.update_asset)
                .setDrawerLayout (drawer)
                .build ();
        NavController navController = Navigation.findNavController (this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController (this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController (navigationView, navController);
        LinearLayout home= (LinearLayout) findViewById (R.id.header);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController (MainActivity.this, R.id.nav_host_fragment);
                navController.navigate (R.id.Homepage);
                drawer.close();
            }
        });
        LinearLayout logout=(LinearLayout)findViewById (R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    logoutHandle();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup container = (ViewGroup) findViewById(R.id.nav_host_fragment);
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        // display role and username

          TextView usernameTextView = findViewById(R.id.username);
          usernameTextView.setText(sessionManager.getUsername());
          TextView roleTextView = findViewById(R.id.role);
          roleTextView.setText(sessionManager.getRole().toUpperCase());
          handleDrawer();
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController (this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp (navController, mAppBarConfiguration)
                || super.onSupportNavigateUp ();
    }
    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.getView ().getBackground ().setColorFilter (Color.DKGRAY, PorterDuff.Mode.SRC_IN);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.WHITE);
        toast.show();
    }
    public void getrole( String url) throws IOException {

        OkHttpClient client = new OkHttpClient();

        final TextView role=(TextView)findViewById (R.id.role);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + sessionManager.getToken())
                .build();
        client.newCall(request).enqueue(new Callback () {
            @Override
            public void onFailure(Call call, IOException e) { Log.e("TAG", "Exception", e);
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                ResponseBody responseBody = response.body();
                final String resp = responseBody.string();
                try {
                    JSONObject jr = new JSONObject(resp);
                    JSONArray arr = jr.getJSONArray("data");
                    for (int i=0; i < arr.length(); i++)
                    {
                        try {
                            JSONObject oneObject = arr.getJSONObject(i);
                            String username = oneObject.getString("username");
//                           if(username.equals (sm.username)){role.setText (oneObject.getString ("role"));break;}
                        } catch (JSONException e) {
                            // Oops
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace ();
                }
            }
        });
    }

    private void handleDrawer(){
        String roleValue = sessionManager.getRole();
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        Menu menu =navigationView.getMenu();
        Log.e("rolevalue",  roleValue);
        if(roleValue.equals("admin")){
            MenuItem addNewAssetItem = menu.findItem(R.id.Add_New_Asset);
            MenuItem updateAssetItem = menu.findItem(R.id.Update_Asset);
            MenuItem trackAssetItem = menu.findItem(R.id.Track_Asset);
            addNewAssetItem.setVisible(false);
            updateAssetItem.setVisible(false);
            trackAssetItem.setVisible(false);
        }else if(roleValue.equals("technical staff")){
            MenuItem generateReportItem = menu.findItem(R.id.Generate_Report);
            MenuItem registerUserItem = menu.findItem(R.id.User_Register);
            MenuItem removeUserItem = menu.findItem(R.id.Remove_User);
            MenuItem taskItem = menu.findItem(R.id.Tasks);
            generateReportItem.setVisible(false);
            registerUserItem.setVisible(false);
            removeUserItem.setVisible(false);
            taskItem.setVisible(false);
        }else if(roleValue.equals("compliance team")){
            MenuItem addNewAssetItem = menu.findItem(R.id.Add_New_Asset);
            MenuItem registerItem = menu.findItem(R.id.User_Register);
            MenuItem removeItem = menu.findItem(R.id.Remove_User);
            MenuItem taskItem = menu.findItem(R.id.Tasks);
            addNewAssetItem.setVisible(false);
            registerItem.setVisible(false);
            removeItem.setVisible(false);
            taskItem.setVisible(false);
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

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.code() == 200) {
                            Toast.makeText(MainActivity.this, "logout successfully!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this.getApplicationContext(), Sign_in.class);
                            startActivity(i);
                        }
                    }
                });
            }
        });
    }
}