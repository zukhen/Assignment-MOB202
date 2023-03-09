package com.example.myapplication.fragment.OtherFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebViewFragment extends Fragment{
    private WebView webView;
    private ImageView btn_backToProfile;
    public WebViewFragment() {
        // Required empty public constructor
    }

    public static WebViewFragment newInstance() {

        return new WebViewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_webview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        getDataFromBundle();

//        set on click
        btn_backToProfile.setOnClickListener(view1 -> getActivity().getSupportFragmentManager().popBackStack());
    }


    private void initUi(View view) {
        webView = view.findViewById(R.id.webView);
        btn_backToProfile = view.findViewById(R.id.btn_backToProfile);
    }

    private void getDataFromBundle(){
        Bundle bundle = this.getArguments();
        String url = bundle.getString("link");
        fillWebViewHelp(url);

    }

    @SuppressLint("SetJavaScriptEnabled")
    public void fillWebViewHelp(String url) {
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
    }

}