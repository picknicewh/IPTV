package net.hunme.kidsworld_iptv.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;

import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.mode.RQCodeVo;

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
            try {
                RQCodeVo codeVo = gson.fromJson(json, RQCodeVo.class);
//                if (UserFragment.userFragmentView != null) {
//                    if (G.isEmteny(codeVo.getTs_id())){
//                        UserMessage.getInstance(context).setUserImgUrl(codeVo.getImgurl());
//                        UserFragment.userFragmentView.showRQCodeSuccess(codeVo.getImgurl());
//                    } else{
//                        UserMessage.getInstance(context).setUserTsId(codeVo.getTs_id());
//                        UserFragment.userFragmentView.closeDialog();
//                    }
//                }
            } catch (Exception e) {

            }

        }
    }
}
