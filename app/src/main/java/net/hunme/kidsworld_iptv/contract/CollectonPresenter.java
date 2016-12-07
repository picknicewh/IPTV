package net.hunme.kidsworld_iptv.contract;

import com.google.gson.reflect.TypeToken;

import net.hunme.baselibrary.mode.Result;
import net.hunme.baselibrary.network.OkHttpListener;
import net.hunme.baselibrary.network.OkHttps;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.mode.CompilationsJsonVo;
import net.hunme.kidsworld_iptv.util.AppUrl;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/12/5
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class CollectonPresenter implements CollectionContract.Presenter, OkHttpListener {
    private CollectionContract.View view;
    private int pageNumber=1; //页数
    private int type; //记录当前Type
    private boolean isPagin; //是否分页

    public CollectonPresenter(CollectionContract.View view) {
        this.view = view;
    }

    /**
     * 获取收藏
     *
     * @param pageNumber
     * @param type
     */
    @Override
    public void getCollectionRes(int pageNumber, int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("tsId", IPTVApp.um.getUserTsId());
        map.put("pageSize", G.PAGESIZE);
        map.put("pageNumber", pageNumber);
        if (type > 0)
            map.put("type", type);
        Type mType = new TypeToken<Result<List<CompilationsJsonVo>>>() {
        }.getType();
        OkHttps.sendPost(mType, AppUrl.GETRESOURCEFAVORITES, map, this);
        isPagin = pageNumber > 1; //判断是否为分页加载 页数为一就是第一页 不是分页加载
        if (!isPagin) this.pageNumber = 1; //如果不是分页 还原分页数
        this.type = type;//保存当前type
    }

    /**
     * 获取分页数据
     */
    @Override
    public void getPaginCollectionRes() {
        pageNumber++;
        getCollectionRes(pageNumber, type);
    }

    @Override
    public void onSuccess(String uri, Object date) {
        if (AppUrl.GETRESOURCEFAVORITES.equals(uri)) {
            Result<List<CompilationsJsonVo>> result = (Result<List<CompilationsJsonVo>>) date;
            if (result.getData().size() <= 0 && isPagin)
                pageNumber--;
            else
                view.showCollection(result.getData(), isPagin);
        }
    }

    @Override
    public void onError(String uri, String error) {
        G.showToast(IPTVApp.getInstance().getApplicationContext(), error);
        if (isPagin)
            pageNumber--;
    }
}
