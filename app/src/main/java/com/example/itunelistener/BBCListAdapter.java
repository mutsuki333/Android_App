package com.example.itunelistener;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class BBCListAdapter extends BaseAdapter {
    ArrayList<String> Titles;
    ArrayList<String> Descriptions;
    Context mContext;

    public BBCListAdapter(Context context, ArrayList<String> titles, ArrayList<String> descriptions){
        this.Titles = titles;
        this.Descriptions = descriptions;
        this.mContext = context;

    }

    @Override
    public int getCount() {
        return Titles.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_item, null);
        try{

            TextView textView = (TextView) itemView.findViewById(R.id.title);
            textView.setText(Titles.get(i));

            TextView textView2 = (TextView) itemView.findViewById(R.id.description);
            textView2.setText(Descriptions.get(i));

        }
        catch (Exception e) {
            e.printStackTrace();
        }


        return itemView;
    }
}
