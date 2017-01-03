package net.hunme.kidsworld_iptv.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;

import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.activity.QRcodeLoginActivity;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.mode.PushVo;
import net.hunme.kidsworld_iptv.mode.RQCodeVo;
import net.hunme.kidsworld_iptv.util.PushDb;
import net.hunme.kidsworld_iptv.util.PushDbHelp;

import cn.jpush.android.api.JPushInterface;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/8
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class JpushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)) {
            G.log("[MyJPushReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            String json = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Gson gson = new Gson();
            PushVo push = gson.fromJson(json, PushVo.class);
            if (G.isEmteny(push.getMessagetype())) {
                try {
                    RQCodeVo codeVo = gson.fromJson(json, RQCodeVo.class);
                    if (QRcodeLoginActivity.view != null) {
                        if (G.isEmteny(codeVo.getTs_id())) {
                            //扫码成功 等待移动端确认登录
                            QRcodeLoginActivity.view.setUserHeadImg(codeVo.getImgurl());//显示用户头像
                            G.log("========扫码成功 等待移动端确认登录========");
                        } else {
                            //移动端确认成功
                            IPTVApp.um.setUserTsId(codeVo.getTs_id());//保存用户tsId
                            IPTVApp.um.setAccountId(codeVo.getAccount_id()); //保存用户account_id
                            IPTVApp.um.setUserName(codeVo.getTs_name());//保存用户名
                            IPTVApp.um.setUserImgUrl(codeVo.getImgurl());//保存用户头像
                            QRcodeLoginActivity.view.closeLogin(); //关闭扫码页面
                        }
                    }
                } catch (Exception e) {

                }
            } else if (push.getMessagetype().equals("3")) {
                PushDb pushDb = new PushDb(context);
                PushDbHelp.getInstance().insert(pushDb.getWritableDatabase(),
                        push.getContent(),
                        push.getCreate_time(),
                        push.getTitle());
                G.log("content===" + push.getContent() + "========createTime=====" + push.getCreate_time() + "=====title====" + push.getTitle());
            }
        }
    }
}
