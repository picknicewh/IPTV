package net.hunme.kidsworld_iptv.contract;

import com.google.gson.reflect.TypeToken;

import net.hunme.baselibrary.mode.Result;
import net.hunme.baselibrary.network.OkHttpListener;
import net.hunme.baselibrary.network.OkHttps;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.mode.MessageJsonVo;
import net.hunme.kidsworld_iptv.util.AppUrl;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/12/6
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class NoticePresenter implements NoticeContract.presenter, OkHttpListener {
    private NoticeContract.View view;

    public NoticePresenter(NoticeContract.View view) {
        this.view = view;
    }

    @Override
    public void getSchoolNotice() {
        Map<String, Object> map = new HashMap<>();
        map.put("tsId", IPTVApp.um.getUserTsId());
        map.put("dateTime", "2016-08-08 00:00:00");
        Type type = new TypeToken<Result<List<MessageJsonVo>>>() {
        }.getType();
        OkHttps.sendPost(type, AppUrl.GETMESSAGE, map, this);
    }

    @Override
    public void onSuccess(String uri, Object date) {
        if (AppUrl.GETMESSAGE.equals(uri)) {
            Result<List<MessageJsonVo>> result = (Result<List<MessageJsonVo>>) date;
            if (result.getData().size() > 0) {
                view.showNotice(result.getData());
            }
        }
    }

    @Override
    public void onError(String uri, String error) {

    }
}
