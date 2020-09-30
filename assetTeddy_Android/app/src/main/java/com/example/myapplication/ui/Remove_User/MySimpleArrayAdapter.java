package com.example.myapplication.ui.Remove_User;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.SessionManager;

import java.util.ArrayList;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;
    private final ArrayList<String> roleList;
    private final ArrayList<String> idList;


    public MySimpleArrayAdapter(Context context, ArrayList<String> nameList, ArrayList<String> roleList,  ArrayList<String> idList) {
        super(context, R.layout.user_list, nameList);
        this.context = context;
        this.values = nameList;
        this.roleList = roleList;
        this.idList = idList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.user_list, parent, false);

        LinearLayout itemButton = (LinearLayout) rowView.findViewById(R.id.item_button);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        TextView textView1 = (TextView) rowView.findViewById(R.id.label1);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(values.get(position).toUpperCase());
        textView1.setText(roleList.get(position).toUpperCase());
        imageView.setImageResource(R.drawable.avatar);
        itemButton.setTag(position);

        itemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                SessionManager sessionManager = new SessionManager(getContext());
                sessionManager.setUserId(idList.get(position));
                Intent intent = new Intent( context, UserProfile.class );
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}