package net.hunme.kidsworld_iptv.contract;

import com.google.gson.reflect.TypeToken;

import net.hunme.baselibrary.mode.Result;
import net.hunme.baselibrary.network.OkHttpListener;
import net.hunme.baselibrary.network.OkHttps;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.util.AppUrl;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * ======================================
 * 作者：ZLL
 * 时间：2016/12/8
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 * ======================================
 */

public class MovePlayPresenter implements MovePlayContract.Presenter, OkHttpListener {

    @Override
    public void savePlayTheRecording(String resourceid, String broadcastPace, int type) {
        Map<String, Object> map = new HashMap<>();
        if (G.isEmteny(IPTVApp.um.getUserTsId())) {
            return;
        }
        map.put("tsId", IPTVApp.um.getUserTsId());
        map.put("resourceid", resourceid);
        map.put("type", type);
        if (type == 2) {
            //表示结束  需要传播放进度
            map.put("broadcastPace", broadcastPace);
        }
        Type mType = new TypeToken<Result<String>>() {
        }.getType();
        OkHttps.sendPost(mType, AppUrl.SAVEPALYTHERESDING, map, this);
    }

    @Override
    public void onSuccess(String uri, Object date) {

    }

    @Override
    public void onError(String uri, String error) {

    }
}
