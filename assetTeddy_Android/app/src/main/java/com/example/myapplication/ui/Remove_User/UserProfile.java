package com.example.myapplication.ui.Remove_User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.MainActivity;
import com.example.myapplication.Sign_in;
import com.example.myapplication.common.HttpCall;
import com.example.myapplication.model.AppParam;
import com.example.myapplication.model.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class UserProfile extends AppCompatActivity {
    private HttpCall httpCall = new HttpCall();
    private String commonUrl = httpCall.getCommonUrl();
    public String userUpdateUrl = commonUrl + "/api/updateUser/";
    public String getUserUrl = commonUrl + "/api/users/";
    public String removeUserUrl = commonUrl + "/api/removeUser/";
    private SessionManager sessionManager ;
    JSONArray userArray = new JSONArray();

    EditText name;
    TextView gender;
    EditText email;
    EditText contactNumber;
    TextView role;
    EditText address;
    EditText username;
    EditText password;

    ArrayList<String> nameList = new ArrayList<String >();
    ArrayList<String> roleList = new ArrayList<String >();
    ArrayList<String> idList = new ArrayList<String >();
    ListView listview;
    Context context;

    private ProgressBar userHandleProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        sessionManager = new SessionManager(getApplicationContext());
        userHandleProgressbar = (ProgressBar)findViewById(R.id.user_handle_progressbar);
        userHandleProgressbar.setVisibility(View.GONE);

        listview = (ListView) findViewById(R.id.listview);

        getUser();

        Spinner genderSpinner  = (Spinner)findViewById(R.id.gender_selectBox);
        Spinner roleSpinner  = (Spinner)findViewById(R.id.role_selectBox);
        genderSpinner.setOnItemSelectedListener(new genderCategoryHandle());
        roleSpinner.setOnItemSelectedListener(new roleCategoryHandle());
        List<String> genderCategory = new ArrayList<String>();
        List<String> roleCategory = new ArrayList<String>();
        genderCategory.add("Male");
        genderCategory.add("Female");
        roleCategory.add("admin");
        roleCategory.add("technical staff");
        roleCategory.add("compliance team");

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(UserProfile.this, android.R.layout.simple_spinner_item, genderCategory);
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<String>(UserProfile.this, android.R.layout.simple_spinner_item, roleCategory);
        // Drop down layout style - list view with radio button
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        genderSpinner.setAdapter(genderAdapter);
        roleSpinner.setAdapter(roleAdapter);

        Button updateUserButton= (Button)findViewById (R.id.profile_userUpdate);
        Button removeButton = (Button)findViewById (R.id.profile_userRemove);

        name =(EditText)findViewById (R.id.profile_name);
        username =(EditText)findViewById (R.id.profile_username);
        email =(EditText)findViewById (R.id.profile_email);
        gender =(TextView) findViewById (R.id.profile_gender);
        contactNumber = (EditText)findViewById (R.id.profile_contactNumber);
        address = (EditText)findViewById (R.id.profile_address);
        password = (EditText)findViewById (R.id.profile_password);
        role=(TextView) findViewById (R.id.profile_role);

        updateUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameValue=name.getText ().toString ();
                String emailValue=email.getText ().toString ();
                String usernameValue=username.getText ().toString ();
                String genderValue=gender.getText ().toString ();
                String contactNumberValue = contactNumber.getText ().toString ();
                String addressValue = address.getText ().toString ();
                String passwordValue=password.getText ().toString ();
                String roleValue=role.getText ().toString ();
                boolean formAvailable = registerFormValidation(nameValue, emailValue, usernameValue, genderValue, contactNumberValue, addressValue,passwordValue, roleValue);
                if(formAvailable) {
                    userHandleProgressbar.setVisibility(View.VISIBLE);
                    RequestBody formBody = new FormBody.Builder()
                            .add("name",nameValue)
                            .add("username",usernameValue)
                            .add ("email", emailValue)
                            .add ("gender", genderValue)
                            .add ("contact_number", contactNumberValue)
                            .add ("address", addressValue)
                            .add ("password", passwordValue)
                            .add ("role", roleValue)
                            .build();
                    try {
                        userUpdatePost(formBody,userUpdateUrl+ sessionManager.getUserId());
                    } catch (IOException e) {
                        Log.e("TAG", "Exception", e);
                        e.printStackTrace ();
                    }

                }
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });
    }

    void userUpdatePost(RequestBody formBody,String url) throws IOException {

        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + sessionManager.getToken())
                .put (formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) { Log.e("TAG", "Exception", e);
                call.cancel();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String respRef = "";
                if(responseBody != null){
                    respRef = responseBody.string();
                }
                final String resp = respRef;

                runOnUiThread(new Runnable() { public void run() {
                    try {
                        JSONObject jr = new JSONObject(resp);
                        if (response.code() == 200) {
                            Toast.makeText(UserProfile.this, jr.getString("message"), Toast.LENGTH_SHORT).show();
                            userHandleProgressbar.setVisibility(View.GONE);
//                            Intent intent = new Intent(UserProfile.this, Remove_User_Fragment.class);
//
//                            startActivity(intent);
                            onBackPressed();
                        } else {
                            Toast.makeText(UserProfile.this, "data error" , Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.e("TAG", "Exception", e);
                        userHandleProgressbar.setVisibility(View.GONE);
                        Toast.makeText(UserProfile.this, "ServerError", Toast.LENGTH_SHORT).show();
                    }
                }});
            }
        });
    }

    private class genderCategoryHandle extends Activity implements
            AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)  {
//            final TextView type=(TextView)findViewById (R.id.profile_gender);
//            type.setText (parent.getItemAtPosition(pos).toString());
            gender.setText (parent.getItemAtPosition(pos).toString());

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    }

    private class roleCategoryHandle extends Activity implements
            AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)  {
//            final TextView type=(TextView)findViewById (R.id.profile_role);
//            type.setText (parent.getItemAtPosition(pos).toString());
            role.setText (parent.getItemAtPosition(pos).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    }

    boolean registerFormValidation(String name, String username, String email, String gender, String contact_number,  String address, String password,  String role ){
        if(name.equals("")){
            Toast.makeText(UserProfile.this, "name is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(username.equals("")){
            Toast.makeText(UserProfile.this, "username is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(email.equals("")){
            Toast.makeText(UserProfile.this, "email is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(contact_number.equals("")){
            Toast.makeText(UserProfile.this, "contact number is empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(address.equals("")){
            Toast.makeText(UserProfile.this, "address is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(role.equals("")){
            Toast.makeText(UserProfile.this, "role is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(gender.equals("")){
            Toast.makeText(UserProfile.this, "gender is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(password.equals("")){
            Toast.makeText(UserProfile.this, "password is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    private void getUser() {
        userHandleProgressbar.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getUserUrl + sessionManager.getUserId() )
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
                        JSONArray userData = jr.getJSONArray("data");
                        JSONObject  attachment = (JSONObject) userData.get(0);
                        if (response.code() == 200) {
                            setProfile(name, gender, email, contactNumber, role, address, username, password, attachment);
                            userHandleProgressbar.setVisibility(View.GONE);
                            Toast.makeText(UserProfile.this, "get users successfully ", Toast.LENGTH_SHORT).show();                        } else {
                        }
                    } catch (JSONException e) {
                        Log.e("TAG", "Exception", e);
                        userHandleProgressbar.setVisibility(View.GONE);
                        Toast.makeText(UserProfile.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }});
            }
        });
    }

    private void setProfile(EditText name, TextView gender, EditText email, EditText contactNumber, TextView role, EditText address, EditText username, EditText password, JSONObject userData) throws JSONException {
        String nameTemp = userData.getString("name");
        String genderTemp = userData.getString("gender");
        String emailTemp = userData.getString("email");
        String contactNumberTemp = userData.getString("contact_number");
        String roleTemp = userData.getString("role");
        String addressTemp = userData.getString("address");
        String usernameTemp = userData.getString("username");
        String passwordTemp = userData.getString("confirm_password");
        name.setText(nameTemp);
        gender.setText(genderTemp);
        contactNumber.setText(contactNumberTemp);
        email.setText(emailTemp);
        role.setText(roleTemp);
        address.setText(addressTemp);
        username.setText(usernameTemp);
        password.setText(passwordTemp);
    }

    private void deleteUser() {
        userHandleProgressbar.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(removeUserUrl + sessionManager.getUserId() )
                .addHeader("Authorization", "Bearer " + sessionManager.getToken())
                .method("DELETE", null)
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
                            Toast.makeText(UserProfile.this, jr.getString("message"), Toast.LENGTH_SHORT).show();
                            userHandleProgressbar.setVisibility(View.GONE);
                            onBackPressed();
                        } else {
                        }
                    } catch (JSONException e) {
                        Log.e("TAG", "Exception", e);
                        userHandleProgressbar.setVisibility(View.GONE);
                        Toast.makeText(UserProfile.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }});
            }
        });
    }
}