package com.example.itunelistener;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebviewActivity extends AppCompatActivity {
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_preview);
        WebView webView = (WebView) findViewById(R.id.webview);


        Intent intent = getIntent();
        url = intent.getStringExtra("url");
//        webView.setWebViewClient(new WebViewClient()
//        {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url)
//            {
//                view.loadUrl(url);
//                System.out.println("hello");
//                return true;
//            }
//        });
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
