package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.common.HttpCall;
import com.example.myapplication.model.AppParam;
import com.example.myapplication.model.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Sign_in extends AppCompatActivity {

    private HttpCall httpCall = new HttpCall();
    private String commonUrl = httpCall.getCommonUrl();

    private EditText txt_username,txt_password;
    private Button btn_login;
    private ProgressBar progressBar;

    public JSONObject jsonBody = new JSONObject();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        setContentView (R.layout.activity_sign_in);

        progressBar = (ProgressBar)findViewById(R.id.signInProgressbar);

//        progressBar.setVisibility(View.GONE);
        progressBar.setVisibility(ProgressBar.GONE);
//        progressBar.setIndeterminate(false);
        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);
        txt_username = (EditText)findViewById(R.id.loginusername);
        txt_password = (EditText)findViewById(R.id.loginpassword);
        btn_login = (Button)findViewById(R.id.signin);
        final EditText usernameText=(EditText)findViewById (R.id.loginusername) ;
        usernameText.setText("andy", TextView.BufferType.EDITABLE);
        final EditText passwordText=(EditText)findViewById (R.id.loginpassword) ;
        passwordText.setText("123456", TextView.BufferType.EDITABLE);

        btn_login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
            progressBar.setProgress(0);
            final String username= usernameText.getText().toString().trim();

            final String password=passwordText.getText().toString().trim();

            try {
                jsonBody.put("username",username);
                jsonBody.put("password", password);
                switch(username.length ()*password.length ()){
                    case 0:
                        toastMsg("Invalid Username or Password");
                        break;
                    default:
                        try {
                            postRequest( commonUrl + "/api/login", jsonBody);
                        } catch (IOException e) {
                            e.printStackTrace ();

                        }
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            }
        });
    }

void postRequest(String postUrl, JSONObject postBody) throws IOException {

    OkHttpClient client = new OkHttpClient();
       RequestBody body = RequestBody.create(JSON, postBody.toString());
    Request request = new Request.Builder()
             .url(postUrl)
            .post(body)
            .build();

    client.newCall(request).enqueue(new Callback () {
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
//                        AppParam.getInstance().parse(jr);
                        AppParam.getInstance().save(Sign_in.this);
                        onSignInSuccess(jr);
                    } else {
                        progressBar.setVisibility(ProgressBar.GONE);
                        Toast.makeText(Sign_in.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("TAG", "Exception", e);
                    progressBar.setVisibility(ProgressBar.GONE);
                    Toast.makeText(Sign_in.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }});
        }
    });
}

private void onSignInSuccess(JSONObject responseData) throws JSONException {

        SessionManager sessionManager   = new SessionManager(getApplicationContext());
        sessionManager.setId(responseData.getJSONObject("success").getJSONObject("user").getString("id"));
        sessionManager.setUsername(responseData.getJSONObject("success").getJSONObject("user").getString("username"));
        sessionManager.setRole(responseData.getJSONObject("success").getJSONObject("user").getString("role"));
        sessionManager.setToken(responseData.getJSONObject ("success").getString ("token"));
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }
public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.getView ().getBackground ().setColorFilter (Color.DKGRAY, PorterDuff.Mode.SRC_IN);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.WHITE);
        toast.show();
    }
}