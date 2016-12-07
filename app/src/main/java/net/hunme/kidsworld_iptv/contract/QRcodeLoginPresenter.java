package net.hunme.kidsworld_iptv.contract;

import com.google.gson.reflect.TypeToken;

import net.hunme.baselibrary.mode.Result;
import net.hunme.baselibrary.network.OkHttpListener;
import net.hunme.baselibrary.network.OkHttps;
import net.hunme.kidsworld_iptv.util.AppUrl;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/30
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class QRcodeLoginPresenter implements QRcodeLoginContract.Presenter, OkHttpListener {
    private QRcodeLoginContract.View view;

    public QRcodeLoginPresenter(QRcodeLoginContract.View view) {
        this.view = view;
    }

    @Override
    public void getQRCode(String pushId, String sign, String requestSource) {
        Map<String,Object> map=new HashMap<>();
        map.put("pushId",pushId);
        map.put("sign",sign);
        map.put("requestSource",requestSource);
        Type type=new TypeToken<Result<String>>(){}.getType();
        OkHttps.sendPost(type, AppUrl.GETQRCODE,map,this);
    }

    @Override
    public void onSuccess(String uri, Object date) {
        if(AppUrl.GETQRCODE.equals(uri)){
            view.setQRcodeImg(((Result<String>)date).getData());
        }
    }

    @Override
    public void onError(String uri, String error) {

    }
}
