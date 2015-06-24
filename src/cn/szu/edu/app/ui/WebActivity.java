package cn.szu.edu.app.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import cn.szu.edu.app.Constant;
import cn.szu.edu.app.R;


@SuppressLint("JavascriptInterface")
public class WebActivity extends Activity implements Constant {

    public static final String TAG = "WebActivity";

    private WebView webView;

    private String web_url, title, postId;

    private FrameLayout loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Intent intent = getIntent();
        postId = intent.getStringExtra("postId");
        web_url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setTitle(title);

        // 以查询资源的方式查询AdView并加载请求。
//        AdView adView = (AdView)this.findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//
        loading = (FrameLayout) findViewById(R.id.loading);

        webView = (WebView) findViewById(R.id.webView);


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDisplay();
    }

    private void updateDisplay() {
        if(getIntent().hasExtra("notification")){
            webView.loadUrl(web_url);
        } else {
            webView.loadUrl(HTTP_SERVER + web_url);
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);// 让浏览器来存储页面元素的DOM模型，从而使JavaScript可以执行操作了。
        webView.getSettings().setLoadsImagesAutomatically(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.requestFocus();
        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
        // 而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                loading.setVisibility(View.GONE);
//                getActivity().setProgressBarIndeterminateVisibility(false);
//                this.setSupportProgressBarIndeterminateVisibility(false);
            }

        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//				setSupportProgress(newProgress * 100);
                super.onProgressChanged(view, newProgress);
            }
        });
        
    }

    
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        if(getIntent().hasExtra("notification")) {
                            Intent intent = new Intent(WebActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            finish();
                        }
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        switch (item.getItemId()) {
            case android.R.id.home:
                if(getIntent().hasExtra("notification")) {
                    Intent intent = new Intent(WebActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
