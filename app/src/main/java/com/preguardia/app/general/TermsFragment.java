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

import com.afollestad.materialdialogs.MaterialDialog;
import com.preguardia.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author amouly on 2/26/16.
 */
public class TermsFragment extends Fragment {

    @Bind(R.id.terms_webview)
    WebView webView;

    private MaterialDialog progressDialog;

    public static TermsFragment newInstance() {
        return new TermsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms, container, false);

        ButterKnife.bind(this, view);

        // Init Progress dialog
        MaterialDialog.Builder progressBuilder = new MaterialDialog.Builder(getActivity())
                .title(R.string.drawer_consultation_history)
                .content(R.string.user_login_loading)
                .cancelable(false)
                .progress(true, 0);

        progressDialog = progressBuilder.build();

        webView.setBackgroundColor(Color.TRANSPARENT);

        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        webView.setWebViewClient(new WebViewClient());

        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        webView.loadUrl("http://graciasdoc.com/tyc.html");
        progressDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
