package com.example.a2021_12_31;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Fragment1 extends Fragment {
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_1, container, false);
        webView = v.findViewById(R.id.webView);

        String url = "https://www.youtube.com/watch?v=Oc_GqKNpGf0"; // Bach BWV 847

        webView.getSettings().setJavaScriptEnabled(true); // JavaScript allow
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(url);

        //webView 사용 시 권한 줘야함!!
        //INTERNET, https 통신권한!

        return v;
    }
}