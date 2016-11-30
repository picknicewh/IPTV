package net.hunme.kidsworld_iptv.application;

import android.app.Application;

import net.hunme.baselibrary.BaseLibrary;
import net.hunme.baselibrary.util.G;
import net.hunme.baselibrary.util.MD5Utils;
import net.hunme.kidsworld_iptv.util.JPushUtil;
import net.hunme.kidsworld_iptv.util.UserMessage;

import cn.jpush.android.api.JPushInterface;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/9/29
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class IPTVApp extends Application {
    private static IPTVApp instance;
    public static UserMessage um;
    public static String pushId;
    public static IPTVApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        um = UserMessage.getInstance(this);
        BaseLibrary.initializer(this);
        //极光初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        setJpushAilas();
    }

    public void setJpushAilas(){
        if(G.isEmteny(um.getPushId())){
            pushId= MD5Utils.encode(String.valueOf(System.currentTimeMillis()+Math.random()*100));
            new JPushUtil().setJpushAlias(pushId);
        }
        G.log("======================"+um.getPushId());
    }
}
