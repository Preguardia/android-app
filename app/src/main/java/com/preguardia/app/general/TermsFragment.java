package com.preguardia.app.general;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.preguardia.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author amouly on 2/26/16.
 */
public class TermsFragment extends Fragment {

    @Bind(R.id.terms_webview)
    WebView webView;

    public static TermsFragment newInstance() {
        return new TermsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms, container, false);

        ButterKnife.bind(this, view);

        webView.setBackgroundColor(Color.TRANSPARENT);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView.loadUrl("http://graciasdoc.com/tyc.html");

        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        webView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
