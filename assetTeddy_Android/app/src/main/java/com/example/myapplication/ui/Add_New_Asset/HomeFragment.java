package com.example.myapplication.ui.Add_New_Asset;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.example.myapplication.common.HttpCall;
import com.example.myapplication.model.SessionManager;
import com.example.myapplication.ui.Remove_User.UserProfile;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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


public class HomeFragment extends Fragment {

    private HttpCall httpCall = new HttpCall();
    private String commonUrl = httpCall.getCommonUrl();
    public String addNewAssetUrl = commonUrl + "/api/addNewAsset";
    public String [] stype = {"Monitor", "CPU", "Phone", "Modem", "Keyboard", "printer", "headset", "TV"};

    public   String [] sstatus = {"Assigned", "Unassigned", "Faulty", "Dispose"};
    Spinner spinner1,spinner2, addLocationSpinner, addDepartmentSpinner;
    public TextView type;
    private boolean isBackFromB;
    SessionManager sessionManager;
    Context context;
    private ProgressBar addAssetProgressBar;

    EditText remark;
    EditText serial;
    TextView tag;
    EditText des;
    TextView status;
    TextView addLocationView;
    TextView addDepartmentView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new, container, false);
        addAssetProgressBar = (ProgressBar)view.findViewById(R.id.add_new_asset_progressbar);
        addAssetProgressBar.setVisibility(ProgressBar.GONE);
        isBackFromB=false;
        sessionManager = new SessionManager(getContext());
        spinner1  = (Spinner)view.findViewById(R.id.spinner1);
        spinner2  = (Spinner)view.findViewById(R.id.spinner2);
        addLocationSpinner  = (Spinner)view.findViewById(R.id.add_location_spinner);
        addDepartmentSpinner  = (Spinner)view.findViewById(R.id.add_department_spinner);
        type=(TextView)view.findViewById (R.id.addtype);
        spinner1.setOnItemSelectedListener(new  CustomOnItemSelectedListener1 ());
        status = (TextView) view.findViewById(R.id.addstatus);
        spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener2 ());
        addLocationView = (TextView) view.findViewById(R.id.addlocation);
        addLocationSpinner.setOnItemSelectedListener(new addLocationOnclickListener ());
        addDepartmentView = (TextView)view.findViewById(R.id.adddepartment);
        addDepartmentSpinner.setOnItemSelectedListener(new addDepartmentOnclickListener());
        // Spinner Drop down elements
        List<String> categories1 = new ArrayList<String> ();
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity (), android.R.layout.simple_spinner_item, categories1);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter);
        List<String> categories2 = new ArrayList<String> ();
        categories2.add("Assigned");
        categories2.add("Unassign");
        categories2.add("Faulty");
        categories2.add("Dispose");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity (), android.R.layout.simple_spinner_item, categories2);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter2);

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

        ArrayAdapter<String> addLocationAdapter = new ArrayAdapter<String>(getActivity (), android.R.layout.simple_spinner_item, locationCategory);
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

        ArrayAdapter<String> addDepartmentAdapter = new ArrayAdapter<String>(getActivity (), android.R.layout.simple_spinner_item, departmentCategory);
        // Drop down layout style - list view with radio button
        addDepartmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        addDepartmentSpinner.setAdapter(addDepartmentAdapter);

        ImageView scanButton = (ImageView)view.findViewById (R.id.input_scan);
        Button add=(Button)view.findViewById (R.id.add);
        Button cancel=(Button)view.findViewById (R.id.cancel);

        scanButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity(), BarcodeScan.class );
                getActivity().startActivity(intent);
            }
        });

        TextView username=(TextView)view.findViewById (R.id.addusername);
        username.setText (sessionManager.getUsername());

        remark=(EditText)view.findViewById (R.id.addremark);
        serial=(EditText)view.findViewById (R.id.addserial);
        tag=(TextView) view.findViewById (R.id.scan_output);
        des=(EditText)view.findViewById (R.id.adddescription);
        status=(TextView)view.findViewById (R.id.addstatus);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            String ser=serial.getText ().toString ();
            String ta=tag.getText ().toString ();
            String typ=type.getText ().toString ();
            String de=des.getText ().toString ();
            String loc=addLocationView.getText ().toString ();
            String dep=addDepartmentView.getText ().toString ();
            String sta= status.getText ().toString ();
            String mark=remark.getText ().toString ();
            boolean formAvailable = addNewAssetFormValidation(ser,ta,typ,de,loc,dep,sta,mark);
            if(formAvailable){
                addAssetProgressBar.setVisibility(ProgressBar.VISIBLE);
                RequestBody formBody = new FormBody.Builder()
                    .add("serial_number",ser)
                    .add ("barcode", ta)
                    .add ("asset_type", typ)
                    .add ("description", de)
                    .add ("location", loc)
                    .add ("department", dep)
                    .add ("status", sta)
                    .add ("created_by_user",sessionManager.getUsername())
                    .add ("remark", mark)
                    .build();
                try {
                    newAssetFormRequest(formBody,addNewAssetUrl);
                } catch (IOException e) {
                    Log.e("TAG", "Exception", e);
                    e.printStackTrace ();
                }
            }
            NavController navController = Navigation.findNavController (getActivity (), R.id.nav_host_fragment);
            navController.navigate (R.id.Add_New_Asset);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController (getActivity (), R.id.nav_host_fragment);
                navController.navigate (R.id.Add_New_Asset);
            }
        });
        return view;
    }

     void newAssetFormRequest( RequestBody formBody,String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
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
                            addAssetProgressBar.setVisibility(ProgressBar.GONE);
                            Toast.makeText(getActivity(), "new asset added successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            addAssetProgressBar.setVisibility(ProgressBar.GONE);
                            Toast.makeText(getActivity(), jr.getString("message") , Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.e("TAG", "Exception", e);
                        addAssetProgressBar.setVisibility(ProgressBar.GONE);
                        Toast.makeText(getActivity(), "ServerError", Toast.LENGTH_SHORT).show();
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

                EditText scanOutputWindow = (EditText)getActivity ().findViewById(R.id.scan_output);
            scanOutputWindow.setText (result.getContents ());
                Toast.makeText(getActivity (), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
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
            final TextView type=(TextView)getView ().findViewById (R.id.addtype);
            type.setText (parent.getItemAtPosition(pos).toString());

         }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
      }
    public class addLocationOnclickListener extends Activity implements
            AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)  {
            final TextView location=(TextView)getView ().findViewById (R.id.addlocation);
            location.setText (parent.getItemAtPosition(pos).toString());

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    }
    public class addDepartmentOnclickListener extends Activity implements
            AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)  {
            final TextView department = (TextView)getView ().findViewById (R.id.adddepartment);
            department.setText (parent.getItemAtPosition(pos).toString());

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    }

    public class CustomOnItemSelectedListener2 implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)  {
            final TextView status=(TextView)getView ().findViewById (R.id.addstatus);
            status.setText (parent.getItemAtPosition(pos).toString());

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    }


    public static class CustomOnItemSelectedListener3 implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    boolean addNewAssetFormValidation(String serialNumber, String assetTag, String type, String description, String location,  String department, String status, String remark ){
        if(serialNumber.equals("")){
            Toast.makeText(getActivity(), "serial number is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(assetTag.equals("")){
            Toast.makeText(getActivity(), "asset tag is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(type.equals("")){
            Toast.makeText(getActivity(), "type is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(description.equals("")){
            Toast.makeText(getActivity(), "description number is empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(location.equals("")){
            Toast.makeText(getActivity(), "location is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(department.equals("")){
            Toast.makeText(getActivity(), "department is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(status.equals("")){
            Toast.makeText(getActivity(), "status password is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(remark.equals("")){
            Toast.makeText(getActivity(), "remark password is empty", Toast.LENGTH_SHORT).show();
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
}


