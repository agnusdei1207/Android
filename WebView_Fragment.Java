package com.example.a2021_12_31;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Fragment1 extends Fragment {
    WebView webView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_1, container, false);

        webView = v.findViewById(R.id.webView);

        String url = getContext().getSharedPreferences("mySPF", Context.MODE_PRIVATE)
                .getString("url", "www.smhrd.or.kr");

        webView.getSettings().setJavaScriptEnabled(true); // JavaScript 허용
        webView.setWebViewClient(new WebViewClient()); // 요청객체 설정!
        webView.loadUrl(url);

        // webView 쓰려면 권한줘야함!!!
        // INTERNET, https 통신권한!

        return v;
    }
}






