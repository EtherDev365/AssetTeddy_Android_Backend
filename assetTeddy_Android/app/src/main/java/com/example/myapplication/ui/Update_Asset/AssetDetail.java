package com.example.myapplication.ui.Update_Asset;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.common.HttpCall;
import com.example.myapplication.model.SessionManager;
import com.example.myapplication.ui.Add_New_Asset.BarcodeScan;
import com.example.myapplication.ui.Add_New_Asset.HomeFragment;
import com.example.myapplication.ui.Remove_User.UserProfile;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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

public class AssetDetail extends AppCompatActivity {

    private HttpCall httpCall = new HttpCall();
    private String commonUrl = httpCall.getCommonUrl();
    public String updateAssetUrl = commonUrl + "/api/updateAsset";
    public String getAssetUrl = commonUrl + "/api/getAsset";
    public String removeAssetUrl = commonUrl + "/api/deleteAsset";

    Spinner updateTypeSpinner,updateStatusSpinner, addLocationSpinner, addDepartmentSpinner;
    public TextView type;
    private boolean isBackFromB;
    SessionManager sessionManager;
    Context context;
    private ProgressBar updateAssetProgressBar;

    EditText remark;
    EditText serial;
    TextView tag;
    EditText des;
    TextView status;
    TextView updateLocationView;
    TextView updateDepartmentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_detail);


        updateAssetProgressBar = (ProgressBar)findViewById(R.id.update_asset_progressbar);
        updateAssetProgressBar.setVisibility(ProgressBar.GONE);
        isBackFromB=false;
        sessionManager = new SessionManager(getApplicationContext());
        updateTypeSpinner  = (Spinner)findViewById(R.id.update_type_spinner);
        updateStatusSpinner  = (Spinner)findViewById(R.id.update_status_spinner);
        addLocationSpinner  = (Spinner)findViewById(R.id.update_location_spinner);
        addDepartmentSpinner  = (Spinner)findViewById(R.id.update_department_spinner);
        type=(TextView)findViewById (R.id.update_type);
        updateTypeSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener1());
        status = (TextView) findViewById(R.id.update_status);
        updateStatusSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener2());
        updateLocationView = (TextView) findViewById(R.id.update_location);
        addLocationSpinner.setOnItemSelectedListener(new updateLocationOnclickListener());
        updateDepartmentView = (TextView)findViewById(R.id.update_department);
        addDepartmentSpinner.setOnItemSelectedListener(new updateDepartmentOnclickListener());
        // Spinner Drop down elements
        List<String> categories1 = new ArrayList<String>();
        categories1.add("Monitor");
        categories1.add("CPU");
        categories1.add("Mouse");
        categories1.add("Keyboard");
        categories1.add("Phone");
        categories1.add("Headset");
        categories1.add("Projector");
        categories1.add("TV");
        categories1.add("Modem");
        categories1.add("Printer");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories1);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        updateTypeSpinner.setAdapter(dataAdapter);
        List<String> categories2 = new ArrayList<String> ();
        categories2.add("Assigned");
        categories2.add("Unassign");
        categories2.add("Faulty");
        categories2.add("Dispose");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        updateStatusSpinner.setAdapter(dataAdapter2);

        List<String> locationCategory = new ArrayList<String> ();
        locationCategory.add("Axiata Tower 9th Floor");
        locationCategory.add("1 Sentral Level 6");
        locationCategory.add("1 Sentral Level 22");
        locationCategory.add("Nu 1 Level 6");
        locationCategory.add("Nu 1 Level 22");
        locationCategory.add("Nu 2 Level 19");
        locationCategory.add("Nu 2 Level 22");
        locationCategory.add("Bank Rakyat Level 21");
        locationCategory.add("Bank Rakyat Level 26");
        locationCategory.add("Bank Rakyat Level 27");

        ArrayAdapter<String> addLocationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locationCategory);
        // Drop down layout style - list view with radio button
        addLocationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        addLocationSpinner.setAdapter(addLocationAdapter);

        List<String> departmentCategory = new ArrayList<String> ();
        departmentCategory.add("HRD");
        departmentCategory.add("L&D");
        departmentCategory.add("QD");
        departmentCategory.add("TND");
        departmentCategory.add("Airbnb");
        departmentCategory.add("Stripe");
        departmentCategory.add("Kaspersky");
        departmentCategory.add("Nespresso");
        departmentCategory.add("Clubmed");
        departmentCategory.add("BMW");
        departmentCategory.add("SGA");
        departmentCategory.add("Nestle");
        departmentCategory.add("Google");
        departmentCategory.add("Facebook");
        departmentCategory.add("LOL");

        ArrayAdapter<String> addDepartmentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, departmentCategory);
        // Drop down layout style - list view with radio button
        addDepartmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        addDepartmentSpinner.setAdapter(addDepartmentAdapter);

        ImageView scanButton = (ImageView)findViewById (R.id.input_scan);
        Button updateAssetButton = (Button)findViewById (R.id.update_asset_button);
        Button removeAssetButton=(Button)findViewById (R.id.remove_asset_button);
        Button backButton=(Button)findViewById (R.id.back_button);

        scanButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( AssetDetail.this, BarcodeScan.class );
                AssetDetail.this.startActivity(intent);
            }
        });

        TextView username=(TextView)findViewById (R.id.update_username);
        username.setText (sessionManager.getUsername());

        remark=(EditText)findViewById (R.id.update_remark);
        serial=(EditText)findViewById (R.id.update_serial_number);
        tag=(TextView) findViewById (R.id.update_asset_tag);
        des=(EditText)findViewById (R.id.update_description);
        status=(TextView)findViewById (R.id.update_status);
        updateAssetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ser=serial.getText ().toString ();
                String ta=tag.getText ().toString ();
                String typ=type.getText ().toString ();
                String de=des.getText ().toString ();
                String loc=updateLocationView.getText ().toString ();
                String dep=updateDepartmentView.getText ().toString ();
                String sta= status.getText ().toString ();
                String mark=remark.getText ().toString ();
                boolean formAvailable = updateAssetFormValidation(ser,de,sta,mark);
                if(formAvailable){
                    updateAssetProgressBar.setVisibility(ProgressBar.VISIBLE);
                    RequestBody formBody = new FormBody.Builder()
                            .add("serial_number",ser)
                            .add ("barcode", ta)
                            .add ("asset_type", typ)
                            .add ("description", de)
                            .add ("location", loc)
                            .add ("department", dep)
                            .add ("status", sta)
                            .add ("updated_by_user",sessionManager.getUsername())
                            .add ("remark", mark)
                            .build();
                    try {
                        updateAssetFormRequest(formBody,updateAssetUrl + "/" +  sessionManager.getAssetItemId());
                    } catch (IOException e) {
                        Log.e("TAG", "Exception", e);
                        e.printStackTrace ();
                    }
                }

            }
        });

        removeAssetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAssetData();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getAsset();
    }

    void updateAssetFormRequest( RequestBody formBody,String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
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
                final String resp = responseBody.string();
                runOnUiThread(new Runnable() { public void run() {
                    try {
                        JSONObject jr = new JSONObject(resp);
                        if (response.code() == 200) {
                            updateAssetProgressBar.setVisibility(ProgressBar.GONE);
                            Toast.makeText(AssetDetail.this, jr.getString("message"), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            updateAssetProgressBar.setVisibility(ProgressBar.GONE);
                            Toast.makeText(AssetDetail.this, jr.getString("message") , Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.e("TAG", "Exception", e);
                        updateAssetProgressBar.setVisibility(ProgressBar.GONE);
                        Toast.makeText( AssetDetail.this, "ServerError", Toast.LENGTH_SHORT).show();
                    }
                }});
            }

        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.e("Scan*******", "Cancelled scan");

            } else {
                Log.e("Scan", "Scanned");

                EditText scanOutputWindow = (EditText)findViewById(R.id.scan_output);
                scanOutputWindow.setText (result.getContents ());
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public class CustomOnItemSelectedListener1 extends Activity implements
            AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)  {
//            final TextView type=(TextView)findViewById (R.id.update_type);
            type.setText (parent.getItemAtPosition(pos).toString());

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    }
    public class updateLocationOnclickListener extends Activity implements
            AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)  {
//            final TextView location= (TextView)findViewById (R.id.update_location);
            updateLocationView.setText (parent.getItemAtPosition(pos).toString());

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    }
    public class updateDepartmentOnclickListener extends Activity implements
            AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)  {
//            final TextView department = (TextView)findViewById (R.id.update_department);
            updateDepartmentView.setText (parent.getItemAtPosition(pos).toString());

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    }

    public class CustomOnItemSelectedListener2 implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)  {
//            final TextView status=(TextView)findViewById (R.id.update_status);
            status.setText (parent.getItemAtPosition(pos).toString());

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    }



    boolean updateAssetFormValidation(String serialNumber, String assetTag, String description, String remark ){
        if(serialNumber.equals("")){
            Toast.makeText(AssetDetail.this, "serial number is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(assetTag.equals("")){
            Toast.makeText(AssetDetail.this, "asset tag is empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(description.equals("")){
            Toast.makeText(AssetDetail.this, "description number is empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(remark.equals("")){
            Toast.makeText(AssetDetail.this, "remark password is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    public void onPause() {
        super.onPause();
        isBackFromB = true;
    }

    public void onResume() {
        super.onResume();
        if (isBackFromB){
            tag.setText(sessionManager.getBarcode());
        }

    }

    public void getAsset(){
        updateAssetProgressBar.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        Log.e("sdfsdf",sessionManager.getAssetItemId() );
        Request request = new Request.Builder()
                .url(getAssetUrl + "/" + sessionManager.getAssetItemId() )
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
                Log.e("asdfasfdas", resp);
                runOnUiThread(new Runnable() { public void run() {
                    try {
                        JSONObject jr = new JSONObject(resp);
                        if (response.code() == 200) {
                            setAssetData(serial, tag, type, des, updateLocationView, updateDepartmentView, status, remark, jr.getJSONObject("data"));
                            updateAssetProgressBar.setVisibility(View.GONE);
                            Toast.makeText(AssetDetail.this, "get asset successfully ", Toast.LENGTH_SHORT).show();                        } else {
                        }
                    } catch (JSONException e) {
                        Log.e("TAG", "Exception", e);
                        updateAssetProgressBar.setVisibility(View.GONE);
                        Toast.makeText(AssetDetail.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }});
            }
        });
    }

    private void setAssetData(EditText serial_number, TextView asset_tag, TextView asset_type, EditText description, TextView location, TextView department, TextView status, EditText remark, JSONObject userData) throws JSONException {
        String serial_number_temp = userData.getString("serial_number");
        String asset_tag_temp = userData.getString("barcode");
        String asset_type_temp = userData.getString("asset_type");
        String description_temp = userData.getString("description");
        String location_temp = userData.getString("location");
        String departmentTemp = userData.getString("department");
        String status_temp = userData.getString("status");
        String remark_temp = userData.getString("remark");
        serial_number.setText(serial_number_temp);
        asset_tag.setText(asset_tag_temp);
        asset_type.setText(asset_type_temp);
        description.setText(description_temp);
        location.setText(location_temp);
        department.setText(departmentTemp);
        status.setText(status_temp);
        remark.setText(remark_temp);
    }

    private void deleteAssetData(){
        updateAssetProgressBar.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(removeAssetUrl + "/" +  sessionManager.getAssetItemId() )
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
                            Toast.makeText(AssetDetail.this, jr.getString("message"), Toast.LENGTH_SHORT).show();
                            updateAssetProgressBar.setVisibility(View.GONE);
                            onBackPressed();
                        } else {
                        }
                    } catch (JSONException e) {
                        Log.e("TAG", "Exception", e);
                        updateAssetProgressBar.setVisibility(View.GONE);
                        Toast.makeText(AssetDetail.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }});
            }
        });
    }
}