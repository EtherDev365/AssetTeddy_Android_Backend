package com.example.myapplication.ui.Update_Asset;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.common.HttpCall;
import com.example.myapplication.model.SessionManager;
import com.example.myapplication.ui.Remove_User.UserProfile;
import com.example.myapplication.ui.User_Register.User_Register_Fragment;

import java.util.ArrayList;
import java.util.List;


public class GalleryFragment extends Fragment {

    Button monitorButton;
    Button cpuButton;
    Button mouseButton;
    Button keyboardButton;
    Button phoneButton;
    Button headsetButton;
    Button projectorButton;
    Button tvButton;
    Button modemButton;
    Button printerButton;
    private String trackType;
    List<String> typeCategory = new ArrayList<String> ();

    private SessionManager sessionManager;
    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        sessionManager = new SessionManager(getContext());
        monitorButton = (Button)view.findViewById(R.id.monitor_track);
        cpuButton = (Button)view.findViewById(R.id.cpu_track);
        mouseButton = (Button)view.findViewById(R.id.mouse_track);
        keyboardButton = (Button)view.findViewById(R.id.keyboard_track);
        phoneButton = (Button)view.findViewById(R.id.phone_track);
        headsetButton = (Button)view.findViewById(R.id.headset_track);
        projectorButton = (Button)view.findViewById(R.id.projector_track);
        tvButton = (Button)view.findViewById(R.id.tv_track);
        modemButton = (Button)view.findViewById(R.id.modem_track);
        printerButton = (Button)view.findViewById(R.id.printer_track);

        typeCategory.add("Monitor");
        typeCategory.add("CPU");
        typeCategory.add("Mouse");
        typeCategory.add("Keyboard");
        typeCategory.add("Phone");
        typeCategory.add("Headset");
        typeCategory.add("Projector");
        typeCategory.add("TV");
        typeCategory.add("Modem");
        typeCategory.add("Printer");

        Spinner locationSpinner  = (Spinner)view.findViewById(R.id.location_spinner);
        locationSpinner.setOnItemSelectedListener(new GalleryFragment.locationCategoryHandle());
        List<String> locationCategory = new ArrayList<String>();
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
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(getActivity (), android.R.layout.simple_spinner_item, locationCategory);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        locationSpinner.setAdapter(locationAdapter);
        typeTrackHandle(monitorButton, cpuButton, mouseButton, keyboardButton, phoneButton, headsetButton, projectorButton, tvButton, modemButton, printerButton);
        Button confirmButton = (Button)view.findViewById (R.id.track_confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView locationValue = (TextView) view.findViewById(R.id.track_location_view) ;
                sessionManager.setTrackLocation(locationValue.getText().toString());
                sessionManager.setDeviceType(trackType);
                Toast.makeText(getActivity(), "confirm button selected", Toast.LENGTH_SHORT).show();
                Intent assetTrackActivity = new Intent( getActivity(), AssetList.class );
                getActivity().startActivity(assetTrackActivity);
            }
        });
        return view;
    }
    private class locationCategoryHandle extends Activity implements
            AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)  {
            final TextView locationView=(TextView)getView ().findViewById (R.id.track_location_view);
            locationView.setText (parent.getItemAtPosition(pos).toString());

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    }

    private void typeTrackHandle(Button monitorButton, Button cpuButton, Button mouseButton, Button keyboardButton, Button phoneButton, Button headsetButton, Button projectorButton, Button tvButton, Button modemButton, Button printerButton){
        monitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackType = typeCategory.get(0);
                Toast.makeText(getActivity(), trackType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
        cpuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackType = typeCategory.get(1);
                Toast.makeText(getActivity(), trackType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
        mouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackType = typeCategory.get(2);
                Toast.makeText(getActivity(), trackType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
        keyboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackType = typeCategory.get(3);
                Toast.makeText(getActivity(), trackType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackType = typeCategory.get(4);
                Toast.makeText(getActivity(), trackType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
        headsetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackType = typeCategory.get(5);
                Toast.makeText(getActivity(), trackType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
        projectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackType = typeCategory.get(6);
                Toast.makeText(getActivity(), trackType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
        tvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackType = typeCategory.get(7);
                Toast.makeText(getActivity(), trackType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
        modemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackType = typeCategory.get(8);
                Toast.makeText(getActivity(), trackType +" selected", Toast.LENGTH_SHORT).show();
            }
        });
        printerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackType = typeCategory.get(9);
                Toast.makeText(getActivity(), trackType +" selected", Toast.LENGTH_SHORT).show();
            }
        });


    }

}