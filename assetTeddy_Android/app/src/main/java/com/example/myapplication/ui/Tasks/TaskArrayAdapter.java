package com.example.myapplication.ui.Tasks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.SessionManager;
import com.example.myapplication.ui.Remove_User.UserProfile;

import java.util.ArrayList;

public class TaskArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> assetTypeList;
    private final ArrayList<String> assetUsernameList;
    private final ArrayList<String> assetDateList;
    private final ArrayList<String> barcodeList;
    private final ArrayList<String> idList;


    public TaskArrayAdapter(Context context, ArrayList<String> assetTypeList, ArrayList<String> assetUsernameList, ArrayList<String> assetDateList,  ArrayList<String> barcodeList,ArrayList<String> idList) {
        super(context, R.layout.user_list, assetUsernameList);
        this.context = context;
        this.assetTypeList = assetTypeList;
        this.assetUsernameList = assetUsernameList;
        this.assetDateList = assetDateList;
        this.barcodeList = barcodeList;
        this.idList = idList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.task_asset_item, parent, false);

        LinearLayout itemButton = (LinearLayout) rowView.findViewById(R.id.task_item_button);
        TextView assetTypeView = (TextView) rowView.findViewById(R.id.asset_type);
        TextView assetUsernameView = (TextView) rowView.findViewById(R.id.asset_update_username);
        TextView assetDateView = (TextView) rowView.findViewById(R.id.asset_update_date);
        assetTypeView.setText(assetTypeList.get(position).toUpperCase());
        assetUsernameView.setText(assetUsernameList.get(position));
        assetDateView.setText(assetDateList.get(position) + "   " +  barcodeList.get(position));
        itemButton.setTag(position);

        itemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            int position = (Integer) view.getTag();
            SessionManager sessionManager = new SessionManager(getContext());
            sessionManager.setAssetItemId(idList.get(position));
            Intent intent = new Intent( context, TaskListDetail.class );
            context.startActivity(intent);
            }
        });

        return rowView;
    }
}