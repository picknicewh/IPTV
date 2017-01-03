package net.hunme.kidsworld_iptv.contract;

import com.google.gson.reflect.TypeToken;

import net.hunme.baselibrary.mode.Result;
import net.hunme.baselibrary.network.OkHttpListener;
import net.hunme.baselibrary.network.OkHttps;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.mode.FootPrintVo;
import net.hunme.kidsworld_iptv.util.AppUrl;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/12/7
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class FootPrintPresenter implements FootPrintContract.Presenter, OkHttpListener {
    private FootPrintContract.View view;
    private int pageNumber = 1;
    private int type = 1;

    public FootPrintPresenter(FootPrintContract.View view) {
        this.view = view;
    }

    @Override
    public void getFootPrint(int pageNumber, int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("tsId", IPTVApp.um.getUserTsId());
        map.put("pageSize", G.PAGESIZE);
        map.put("type", type);
        map.put("pageNumber", pageNumber);
        map.put("account_id",IPTVApp.um.getAccounId());
        Type mType = new TypeToken<Result<List<FootPrintVo>>>() {
        }.getType();
        OkHttps.sendPost(mType, AppUrl.GETFOOTPRINT, map, this);
        if (this.type != type) {
            this.type = type;
            this.pageNumber = 1;
        }
    }

    @Override
    public void getPaginFootPrint() {
        pageNumber++;
        getFootPrint(pageNumber, type);
    }

    @Override
    public void onSuccess(String uri, Object date) {
        if (AppUrl.GETFOOTPRINT.equals(uri)) {
            Result<List<FootPrintVo>> result = (Result<List<FootPrintVo>>) date;
            if (result.getData().size() > 0)
                view.showFootPrint(result.getData());
            else
                pageNumber = pageNumber > 1 ? pageNumber-- : 1;
        }
    }

    @Override
    public void onError(String uri, String error) {
        pageNumber = pageNumber > 1 ? pageNumber-- : 1;
    }
}
