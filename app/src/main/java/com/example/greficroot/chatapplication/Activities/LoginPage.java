
package com.example.greficroot.chatapplication.Activities;

import android.content.Intent;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.greficroot.chatapplication.R;

import java.util.regex.*;

public class LoginPage extends AppCompatActivity {
    private String api = "https://id.twitch.tv/oauth2/authorize?response_type=token&client_id=j8izzvzi8t8mpw2bl7oi8ks22sq2mx&redirect_uri=http://localhost&scope=chat:read chat:edit";
    private WebView web;
    private myClient client;
    //private WebViewClient client;
    private Pattern p;
    private Matcher m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new myClient();
        web = findViewById(R.id.login);
        web.setWebViewClient(client);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(api);
    }
    private void destroyActivity(){
        finish();
    }
    private void createActivity(String Token){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("TOKEN", Token);
        startActivity(intent);
    }
    private String getToken(String urlToken){
        p = Pattern.compile("=[0-9a-z]+&");
        m = p.matcher(urlToken);
        String Token;
        if(m.find()) {
            Token = m.group(0).substring(1, m.group(0).length() - 1);
            Log.d("TOKEN", Token);
            return Token;
        }
        return "";
    }
    public class myClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView web, String url){
            if(url.endsWith("bearer")) { createActivity( getToken(url));destroyActivity();}
            return false;
        }
    }
}

