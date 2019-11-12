package com.example.itunelistener;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> titles;
    ArrayList<String> descriptions;
    BBCRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe);

        titles = new ArrayList<String>();
        descriptions = new ArrayList<String>();

        final List<String> data = new ArrayList<String>();
        adapter = new BBCRecyclerViewAdapter(titles, descriptions);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(true);
                        }
                    });

                }

                @Override
                public void finish(){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }

                @Override
                public void setTitle(final String s) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            titles.add(s);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
                public void setDescription(final String s) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            descriptions.add(s);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }
}
