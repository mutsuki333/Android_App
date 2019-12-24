package com.example.itunelistener;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

public class WebviewFragment extends Fragment {
    String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        url = getArguments().getString("URL");

        return inflater.inflate(R.layout.web_preview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        WebView webView = (WebView) view.findViewById(R.id.webview);

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

        super.onViewCreated(view, savedInstanceState);
    }
}
