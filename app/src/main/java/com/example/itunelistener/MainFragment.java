package com.example.itunelistener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements BBCRecyclerViewAdapter.RecyclerViewClickListener {

    ArrayList<String> titles;
    ArrayList<String> descriptions;
    ArrayList<String> previewURLs;
    BBCRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        titles = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        previewURLs = new ArrayList();

        final List<String> data = new ArrayList<String>();
        adapter = new BBCRecyclerViewAdapter(titles, descriptions, this);

        return inflater.inflate(R.layout.swipe, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                titles.clear();
                descriptions.clear();
                Download_news();
            }
        });

        Download_news();
    }

    protected void Download_news(){
        try {
            URL url = new URL("http://feeds.bbci.co.uk/zhongwen/trad/rss.xml");
            new BBCSAX(url, new ParserListener() {
                @Override
                public void start(){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(true);
                        }
                    });

                }

                @Override
                public void finish(){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }

                @Override
                public void setTitle(final String s) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            titles.add(s);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
                public void setDescription(final String s) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            descriptions.add(s);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
                @Override
                public void setUrl(final String url) {
                    previewURLs.add(url);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), titles.get(position), Toast.LENGTH_LONG).show();
        WebviewFragment webview = new WebviewFragment();
        Bundle args = new Bundle();
        args.putString("URL", previewURLs.get(position));
        webview.setArguments(args);
        View detailsFrame = getActivity().findViewById(R.id.detail_container);
        if(detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE){
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, webview, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        }
        else{
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, webview, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        }

    }

}
