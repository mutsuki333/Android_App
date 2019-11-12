package com.example.itunelistener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BBCRecyclerViewAdapter.RecyclerViewClickListener {
    ArrayList<String> titles;
    ArrayList<String> descriptions;
    ArrayList<String> previewURLs;
    BBCRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe);

        titles = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        previewURLs = new ArrayList();

        final List<String> data = new ArrayList<String>();
        adapter = new BBCRecyclerViewAdapter(titles, descriptions, this);

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
        Toast.makeText(this, titles.get(position), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, WebviewActivity.class);
//        intent.putExtra("title", titles.get(position));
//        intent.putExtra("cover", covers.get(position));
        intent.putExtra("url", previewURLs.get(position));
        startActivity(intent);
    }
}
