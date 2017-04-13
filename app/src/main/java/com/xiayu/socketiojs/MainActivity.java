package com.xiayu.socketiojs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView contentWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentWebView = new WebView(this);
        // 启用javascript
        contentWebView.getSettings().setJavaScriptEnabled(true);
        // 从assets目录下面的加载html
        contentWebView.loadUrl("file:///android_asset/socket.html");
        contentWebView.addJavascriptInterface(MainActivity.this, "android");


        //Button按钮 无参调用HTML js方法
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = "'http://192.168.0.13:3301'";
////                url = "'https://pushmobile.gcall.com'";
//                String event = "'{\"ssid\":\"a556440b75b2dcab0147d3091e\",\"lgid\":0,\"type\":21,\"id\":\"213978905664344064\",\"ver\":2,\"did\":\"05de8010-2880-39d5-9693-0e6347cdb870\"}'";
//                event = "'{\"ssid\":\"0f3365ed200dd8dccff2b4211d\",\"lgid\":0,\"type\":21,\"id\":\"213978905664344064\",\"ver\":2,\"did\":\"05de8010-2880-39d5-9693-0e6347cdb870\"}'";
//
//                // 无参数调用 JS的方法
//                contentWebView.loadUrl("javascript:connect("+url+","+event+")");
                Intent intent = new Intent(MainActivity.this, ChatSocketService.class);
                intent.setAction(ChatSocketService.ACTION_REQUEST_CONNECT);
                startService(intent);
            }
        });
        //Button按钮 有参调用HTML js方法
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // 传递参数调用JS的方法
//                contentWebView.loadUrl("javascript:disconnect()");
                Intent intent = new Intent(MainActivity.this, ChatSocketService.class);
                intent.setAction(ChatSocketService.ACTION_REQUEST_CONNECT);
                startService(intent);
            }
        });
        //Button按钮 有参调用HTML js方法
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // 传递参数调用JS的方法
//                contentWebView.loadUrl("javascript:close()");
            }
        });


    }

    //由于安全原因 targetSdkVersion>=17需要加 @JavascriptInterface
    //JS调用Android JAVA方法名和HTML中的按钮 onclick后的别名后面的名字对应
    @JavascriptInterface
    public void startFunction() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "show", Toast.LENGTH_LONG).show();
            }
        });
    }

    @JavascriptInterface
    public void startFunction(final String text) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(MainActivity.this).setMessage(text).show();
            }
        });


    }
}
