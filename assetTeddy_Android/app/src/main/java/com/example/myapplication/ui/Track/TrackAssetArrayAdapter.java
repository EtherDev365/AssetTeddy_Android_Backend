package com.example.myapplication.ui.Track;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.SessionManager;
import com.example.myapplication.ui.Update_Asset.AssetDetail;

import java.util.ArrayList;

public class TrackAssetArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> barcodeValues;
    private final ArrayList<String> idList;


    public TrackAssetArrayAdapter(Context context, ArrayList<String> barcodeList, ArrayList<String> idList) {
        super(context, R.layout.asset_item, barcodeList);
        this.context = context;
        this.barcodeValues = barcodeList;
        this.idList = idList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.asset_item, parent, false);

        LinearLayout itemButton = (LinearLayout) rowView.findViewById(R.id.item_button);
        TextView textView = (TextView) rowView.findViewById(R.id.asset_tag);
        textView.setText(barcodeValues.get(position));
        itemButton.setTag(position);

        itemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                SessionManager sessionManager = new SessionManager(getContext());
                sessionManager.setAssetItemId(idList.get(position));
                Intent intent = new Intent( context, TrackView.class );
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}