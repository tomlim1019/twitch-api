package com.example.twitchapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Activity2 extends AppCompatActivity {
    String name;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        if (savedInstanceState == null) {
            Bundle extras = Activity2.this.getIntent().getExtras();
            if(extras != null) {
                name= extras.getString("name");
//                Log.d("ASD", ""+name);
                title= extras.getString("title");
//                Log.d("ASD", ""+title);
            }
        }
        String frameVideo = "<html><body><b>"+title+"</b> from "+name+"<br><iframe width=\"345\" height=\"250\" src=\"https://player.twitch.tv/?channel="+name+"\" scrolling=\"no\" frameborder=\"0\" allowfullscreen=\"false\"></iframe></body></html>";
        String frameChat = "<html><iframe width=\"345\" height=\"250\" id=\""+name+"\" src=\"https://www.twitch.tv/embed/"+name+"/chat\" scrolling=\"no\" frameborder=\"0\"></iframe></html>";
        WebView webView = (WebView) findViewById(R.id.videoview);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadData(frameVideo, "text/html", "utf-8");

        WebView chatView = (WebView) findViewById(R.id.chatview);
        chatView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings chatSettings = chatView.getSettings();
        chatSettings.setJavaScriptEnabled(true);
        chatView.loadData(frameChat, "text/html", "utf-8");
    }
}
