package com.xiayu.socketiojs;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * 创建者     罗夏雨
 * 创建时间   2017/4/13 13:56
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class ChatSocketService extends Service {
    public static final String ACTION_REQUEST_CONNECT    = "action_request_connect";
    public static final String ACTION_REQUEST_DISCONNECT = "action_request_disconnect";
    private WebView contentWebView;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("onCreate");
        contentWebView = new WebView(getApplicationContext());
        contentWebView.getSettings().setJavaScriptEnabled(true);
        contentWebView.addJavascriptInterface(ChatSocketService.this, "android");
        contentWebView.loadUrl("file:///android_asset/socket.html");
    }
    @JavascriptInterface
    public void startFunction() {

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand");
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_REQUEST_CONNECT.equals(action)) {
                reconnectScoketIO();
            } else if (ACTION_REQUEST_DISCONNECT.equals(action)) {
                selfDisconnect();
            }
        }
        return START_STICKY; //不需要粘性启动
    }

    private void selfDisconnect() {
        System.out.println("selfDisconnect");
        contentWebView.loadUrl("javascript:disconnect()");
    }

    private void reconnectScoketIO() {
        System.out.println("reconnectScoketIO");
        String url = "'http://192.168.0.13:3301'";
        String event;
        event = "'{\"ssid\":\"4cd6760b3cf7acedc9b35140c9\",\"lgid\":0,\"type\":21,\"id\":\"213987951310065664\",\"ver\":2,\"did\":\"05de8010-2880-39d5-9693-0e6347cdb870\"}'";
        event = "'{\"ssid\":\"0f3365ed200dd8dccff2b4211d\",\"lgid\":0,\"type\":21,\"id\":\"213978905664344064\",\"ver\":2,\"did\":\"05de8010-2880-39d5-9693-0e6347cdb870\"}'";
        contentWebView.loadUrl("javascript:connect(" + url + "," + event + ")");
    }
}
