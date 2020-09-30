package com.example.myapplication.ui.Track;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.model.SessionManager;
import com.example.myapplication.ui.Add_New_Asset.BarcodeScan;
import com.example.myapplication.ui.Update_Asset.AssetDetail;
import com.example.myapplication.ui.Update_Asset.AssetList;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    SessionManager sessionManager;
    Spinner trackByTypeSpinner, trackByLocationSpinner, trackByDepartmentSpinner;
    TextView trackTypeView, trackLocationView, trackDepartmentView, trackBarcodeView;
    private boolean isBackFromB;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate (R.layout.fragment_track_asset, container, false);
        sessionManager = new SessionManager(getContext());
        isBackFromB=false;
//        trackByTypeSpinner  = (Spinner)root.findViewById(R.id.track_by_type_spinner);
//        trackByLocationSpinner  = (Spinner)root.findViewById(R.id.track_by_location_spinner);
//        trackByDepartmentSpinner  = (Spinner)root.findViewById(R.id.track_by_department_spinner);
//
//        trackTypeView = (TextView) root.findViewById(R.id.track_by_type);
//        trackLocationView = (TextView) root.findViewById(R.id.track_by_location);
//        trackDepartmentView = (TextView) root.findViewById(R.id.track_by_department);
        trackBarcodeView = (TextView) root.findViewById(R.id.track_by_asset_tag);
//
//        trackByTypeSpinner.setOnItemSelectedListener(new trackTypeOnItemSelectedListener());
//        trackByLocationSpinner.setOnItemSelectedListener(new trackLocationOnItemSelectedListener());
//        trackByDepartmentSpinner.setOnItemSelectedListener(new trackDepartmentOnItemSelectedListener());
//
//        List<String> typeCategory = new ArrayList<String>();
//        typeCategory.add("");
//        typeCategory.add("Monitor");
//        typeCategory.add("CPU");
//        typeCategory.add("Mouse");
//        typeCategory.add("Keyboard");
//        typeCategory.add("Phone");
//        typeCategory.add("Headset");
//        typeCategory.add("Projector");
//        typeCategory.add("TV");
//        typeCategory.add("Modem");
//        typeCategory.add("Printer");
//
//        List<String> locationCategory = new ArrayList<String> ();
//        locationCategory.add("");
//        locationCategory.add("Axiata Tower 9th Floor");
//        locationCategory.add("1 Sentral Level 6");
//        locationCategory.add("1 Sentral Level 22");
//        locationCategory.add("Nu 1 Level 6");
//        locationCategory.add("Nu 1 Level 22");
//        locationCategory.add("Nu 2 Level 19");
//        locationCategory.add("Nu 2 Level 22");
//        locationCategory.add("Bank Rakyat Level 21");
//        locationCategory.add("Bank Rakyat Level 26");
//        locationCategory.add("Bank Rakyat Level 27");
//
//        List<String> departmentCategory = new ArrayList<String> ();
//        departmentCategory.add("");
//        departmentCategory.add("HRD");
//        departmentCategory.add("L&D");
//        departmentCategory.add("QD");
//        departmentCategory.add("TND");
//        departmentCategory.add("Airbnb");
//        departmentCategory.add("Stripe");
//        departmentCategory.add("Kaspersky");
//        departmentCategory.add("Nespresso");
//        departmentCategory.add("Clubmed");
//        departmentCategory.add("BMW");
//        departmentCategory.add("SGA");
//        departmentCategory.add("Nestle");
//        departmentCategory.add("Google");
//        departmentCategory.add("Facebook");
//        departmentCategory.add("LOL");
//
//        ArrayAdapter<String> trackTypeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, typeCategory);
//        // Drop down layout style - list view with radio button
//        trackTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // attaching data adapter to spinner
//        trackByTypeSpinner.setAdapter(trackTypeAdapter);
//
//        ArrayAdapter<String> trackLocationAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, locationCategory);
//        // Drop down layout style - list view with radio button
//        trackLocationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // attaching data adapter to spinner
//        trackByLocationSpinner.setAdapter(trackLocationAdapter);
//
//        ArrayAdapter<String> trackDepartmentAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, departmentCategory);
//        // Drop down layout style - list view with radio button
//        trackDepartmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // attaching data adapter to spinner
//        trackByDepartmentSpinner.setAdapter(trackDepartmentAdapter);

        Button searchButton = (Button)root.findViewById(R.id.search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sessionManager.setTrackAssetLocation(trackLocationView.getText().toString());
//                sessionManager.setTrackAssetDepartment(trackLocationView.getText().toString());
//                sessionManager.setTrackType(trackTypeView.getText().toString());
                if(trackBarcodeView.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Input the barcode.", Toast.LENGTH_SHORT).show();
                }else{
                    sessionManager.setTrackBarcode(trackBarcodeView.getText().toString());
                    Intent intent = new Intent( getActivity(), TrackView.class );
                    getActivity().startActivity(intent);
                }
            }
        });

        ImageView barcodeScanButton = (ImageView) root.findViewById(R.id.track_by_asset_tag_icon);

        barcodeScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent( getActivity(), BarcodeScan.class );
                getActivity().startActivity(intent);
            }
        });

        return root;
    }

    public class trackTypeOnItemSelectedListener extends Activity implements
            AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)  {
            trackTypeView.setText (parent.getItemAtPosition(pos).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    public class trackLocationOnItemSelectedListener extends Activity implements
            AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)  {
            trackLocationView.setText (parent.getItemAtPosition(pos).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    public class trackDepartmentOnItemSelectedListener extends Activity implements
            AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)  {
            trackDepartmentView.setText (parent.getItemAtPosition(pos).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    public void onPause() {
        super.onPause();
        isBackFromB = true;
    }

    public void onResume() {
        super.onResume();
        if (isBackFromB){
            trackBarcodeView.setText(sessionManager.getBarcode());
        }
    }
}