package com.example.itunelistener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class BBCRecyclerViewAdapter extends RecyclerView.Adapter<BBCRecyclerViewAdapter.ViewHolder> {
    ArrayList<String> Titles;
    ArrayList<String> Descriptions;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView TitleView;
        public TextView DesView;
        private RecyclerViewClickListener listener;

        public ViewHolder (View v, RecyclerViewClickListener l){
            super(v);
            TitleView = v.findViewById(R.id.title);
            DesView = v.findViewById(R.id.description);
            listener = l;
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());
        }

    }

    public BBCRecyclerViewAdapter(ArrayList<String> titles, ArrayList<String> descriptions, RecyclerViewClickListener listener){
        Titles = titles;
        Descriptions = descriptions;
        mListener = listener;
    }

    public interface RecyclerViewClickListener {
        void onItemClick(View view, int position);
    }

    private RecyclerViewClickListener mListener;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.TitleView.setText(Titles.get(position));
        holder.DesView.setText(Descriptions.get(position));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        ViewHolder vh = new ViewHolder(v, mListener);
        v.setOnClickListener(vh);
        return vh;
    }

    @Override
    public int getItemCount() {
        return Titles.size();
    }
}
