package org.atctech.parent.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.atctech.parent.R;
import org.atctech.parent.utils.ConstantValue;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewBookFragment extends android.app.Fragment {

    private WebView webView;

    public ViewBookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_book, container, false);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("View File");

        webView = view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        Bundle bundle = getArguments();

        String file_name = bundle.getString("FILE_NAME");

        String url = "https://docs.google.com/viewer?url=" + ConstantValue.PDF_URL + file_name;
        String ext = file_name.substring(file_name.lastIndexOf("."));
        if ((ext.contentEquals(".pdf")) || (ext.contentEquals(".docx")))
        {
            webView.loadUrl(url);
        }
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s)
            {
                webView.loadUrl(s);
                return true;
            }
        });
        webView.loadUrl(url);




        return view;
    }

}
