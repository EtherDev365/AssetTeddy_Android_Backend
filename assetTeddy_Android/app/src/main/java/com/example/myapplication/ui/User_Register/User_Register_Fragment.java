package com.example.myapplication.ui.User_Register;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.Sign_in;
import com.example.myapplication.common.HttpCall;
import com.example.myapplication.model.AppParam;
import com.example.myapplication.model.SessionManager;
import com.example.myapplication.ui.Add_New_Asset.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;


public class User_Register_Fragment extends Fragment {
    private HttpCall httpCall = new HttpCall();
    private String commonUrl = httpCall.getCommonUrl();
    public String userRegisterUrl = commonUrl + "/api/reg";
    private ProgressBar progressBar;
    SessionManager sessionManager;
    private Context context;
    EditText name;
    EditText username;
    EditText email;
    EditText contactNumber;
    EditText address;
    EditText password;
    EditText passwordConfirm;
    TextView gender;
    TextView role;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate (R.layout.fragment_user_register, container, false);
        sessionManager = new SessionManager(getContext());
        progressBar = (ProgressBar)root.findViewById(R.id.user_register_progressbar);
        progressBar.setVisibility(View.GONE);

        Spinner genderSpinner  = (Spinner)root.findViewById(R.id.gender_selectBox);
        Spinner roleSpinner  = (Spinner)root.findViewById(R.id.role_selectBox);
        genderSpinner.setOnItemSelectedListener(new genderCategoryHandle());
        roleSpinner.setOnItemSelectedListener(new roleCategoryHandle());
        List<String> genderCategory = new ArrayList<String>();
        List<String> roleCategory = new ArrayList<String>();
        genderCategory.add("Male");
        genderCategory.add("Female");
        roleCategory.add("admin");
        roleCategory.add("technical staff");
        roleCategory.add("compliance team");

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(getActivity (), android.R.layout.simple_spinner_item, genderCategory);
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<String>(getActivity (), android.R.layout.simple_spinner_item, roleCategory);
        // Drop down layout style - list view with radio button
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        genderSpinner.setAdapter(genderAdapter);
        roleSpinner.setAdapter(roleAdapter);

        Button add = (Button)root.findViewById (R.id.signup);
           name =(EditText)root.findViewById (R.id.add_name);
           username =(EditText)root.findViewById (R.id.add_username);
           email =(EditText)root.findViewById (R.id.add_email);
           gender =(TextView) root.findViewById (R.id.add_gender);
           contactNumber = (EditText)root.findViewById (R.id.add_contactNumber);
           address = (EditText)root.findViewById (R.id.add_address);
           password = (EditText)root.findViewById (R.id.add_password);
           passwordConfirm = (EditText)root.findViewById (R.id.add_passwordConfirm);
           role = (TextView) root.findViewById (R.id.add_role);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameValue=name.getText ().toString ();
                String emailValue=email.getText ().toString ();
                String usernameValue=username.getText ().toString ();
                String genderValue=gender.getText ().toString ();
                String contactNumberValue = contactNumber.getText ().toString ();
                String addressValue = address.getText ().toString ();
                String passwordValue=password.getText ().toString ();
                String passwordConfirmValue=passwordConfirm.getText ().toString ();
                String roleValue=role.getText ().toString ();
                boolean formAvailable = registerFormValidation(nameValue, emailValue, usernameValue, genderValue, contactNumberValue, addressValue,passwordValue,passwordConfirmValue, roleValue);
                if(formAvailable) {
                    progressBar.setVisibility(View.VISIBLE);
                    RequestBody formBody = new FormBody.Builder()
                            .add("name",nameValue)
                            .add("username",usernameValue)
                            .add ("email", emailValue)
                            .add ("gender", genderValue)
                            .add ("contact_number", contactNumberValue)
                            .add ("address", addressValue)
                            .add ("password", passwordValue)
                            .add ("confirm_password", passwordValue)
                            .add ("role", roleValue)
                            .build();
                    try {
                        userRegisterPost(formBody,userRegisterUrl);
                    } catch (IOException e) {
                        Log.e("TAG", "Exception", e);
                        e.printStackTrace ();
                    }

                    try {
                        userRegisterPost(formBody,userRegisterUrl);
                    } catch (IOException e) {
                        e.printStackTrace ();
                    }
                }
            }
        });
        return root;
    }

    void userRegisterPost(RequestBody formBody,String url) throws IOException {

        OkHttpClient client = new OkHttpClient();


        final Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + sessionManager.getToken())
                .post (formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) { Log.e("TAG", "Exception", e);
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
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "user registered successfully", Toast.LENGTH_SHORT).show();
                            clearRegisterForm(name, email,contactNumber,address,username, password,passwordConfirm);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), jr.getString("message") , Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("TAG", "Exception", e);
                        Toast.makeText(getActivity(), "ServerError", Toast.LENGTH_SHORT).show();
                    }
                }});
            }
        });
    }

    private class genderCategoryHandle extends Activity implements
            AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)  {
            final TextView type=(TextView)getView ().findViewById (R.id.add_gender);
            type.setText (parent.getItemAtPosition(pos).toString());

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
            final TextView type=(TextView)getView ().findViewById (R.id.add_role);
            type.setText (parent.getItemAtPosition(pos).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    boolean registerFormValidation(String name, String username, String email, String gender, String contact_number,  String address, String password, String confirm_password, String role ){
        if(name.equals("")){
            Toast.makeText(getActivity(), "name is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(username.equals("")){
            Toast.makeText(getActivity(), "username is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(email.equals("")){
            Toast.makeText(getActivity(), "email is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(contact_number.equals("")){
            Toast.makeText(getActivity(), "contact number is empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(address.equals("")){
            Toast.makeText(getActivity(), "address is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(password.equals("")){
            Toast.makeText(getActivity(), "password is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(confirm_password.equals("")){
            Toast.makeText(getActivity(), "confirm password is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!password.equals(confirm_password)){
            Toast.makeText(getActivity(), " confirm password is invalid", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
    private void clearRegisterForm(EditText name, EditText email, EditText contact_number, EditText address, EditText username, EditText password, EditText confirm_password){
        name.getText().clear();
        email.getText().clear();
        contact_number.getText().clear();
        address.getText().clear();
        username.getText().clear();
        password.getText().clear();
        confirm_password.getText().clear();
    }
}