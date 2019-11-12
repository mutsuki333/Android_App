package com.example.itunelistener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BBCRecyclerViewAdapter extends RecyclerView.Adapter<BBCRecyclerViewAdapter.ViewHolder> {
    ArrayList<String> Titles;
    ArrayList<String> Descriptions;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView TitleView;
        public TextView DesView;

        public ViewHolder (View v){
            super(v);
            TitleView = v.findViewById(R.id.title);
            DesView = v.findViewById(R.id.description);
        }

    }

    public BBCRecyclerViewAdapter(ArrayList<String> titles, ArrayList<String> descriptions){
        Titles = titles;
        Descriptions = descriptions;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.TitleView.setText(Titles.get(position));
        holder.DesView.setText(Descriptions.get(position));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public int getItemCount() {
        return Titles.size();
    }
}
