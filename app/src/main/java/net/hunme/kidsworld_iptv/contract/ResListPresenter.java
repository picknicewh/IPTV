package net.hunme.kidsworld_iptv.contract;

import com.google.gson.reflect.TypeToken;

import net.hunme.baselibrary.mode.Result;
import net.hunme.baselibrary.network.OkHttpListener;
import net.hunme.baselibrary.network.OkHttps;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.mode.CompilationsJsonVo;
import net.hunme.kidsworld_iptv.mode.ThemeManageVo;
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
public class ResListPresenter implements ResListContract.Presenter, OkHttpListener {
    private ResListContract.View view;
    private int resPageNumber = 1; //资源页数
    private String type;
    private boolean isPagin;//是否分页
    private String url;//访问资源的Uri地址，用于分页加载 记录下来可以直接进行访问
    private int themePageNumber;//主题页数
    private boolean isThemeByAlbum; //是否通过服务端所传专辑ID访问资源

    public ResListPresenter(ResListContract.View view) {
        this.view = view;
    }

    /**
     * 获取最新资源
     *
     * @param type
     */
    @Override
    public void getResReleases(int pageNumber, String type, String url, boolean isThemeByAlbum) {
        Map<String, Object> map = new HashMap<>();
        map.put("tsId", IPTVApp.um.getUserTsId());
        map.put("pageSize", G.PAGESIZE);
        map.put("pageNumber", pageNumber);
        if (isThemeByAlbum)
            map.put("themeId", type);
        else
            map.put("type", type);
        Type mType = new TypeToken<Result<List<CompilationsJsonVo>>>() {
        }.getType();
        OkHttps.sendPost(mType, url, map, this);
        isPagin = pageNumber > 1; //判断是否为分页加载 页数为一就是第一页 不是分页加载
        if (!isPagin) {
            this.resPageNumber = 1; //如果不是分页 还原分页数
            this.type = type;//保存当前type
            this.url = url;//保存当前url；
            this.isThemeByAlbum = isThemeByAlbum;
        }
    }

    /**
     * 分页加载资源
     */
    @Override
    public void getPaginReleases() {
        resPageNumber++;
        getResReleases(resPageNumber, type, url, isThemeByAlbum);
    }

    /**
     * 获取主题
     *
     * @param pageNumber
     * @param type
     */
    @Override
    public void getThemeList(int pageNumber, String type) {
        Map<String, Object> map = new HashMap<>();
        map.put("pageSize", G.PAGESIZE);
        map.put("type", type);
        map.put("pageNumber", pageNumber);
        Type mType = new TypeToken<Result<List<ThemeManageVo>>>() {
        }.getType();
        OkHttps.sendPost(mType, AppUrl.GETTHEMELIST, map, this);
        this.type = type;
    }

    /**
     * 分页加载主题
     */
    @Override
    public void getPaginTheme() {
        themePageNumber++;
        getThemeList(themePageNumber, type);
    }

    @Override
    public void onSuccess(String uri, Object date) {
        if (AppUrl.GETTHEMELIST.equals(uri)) {
            Result<List<ThemeManageVo>> result = (Result<List<ThemeManageVo>>) date;
            if (result.getData().size() > 0)
                view.showThemeList(result.getData());
            else
                themePageNumber = themePageNumber > 1 ? themePageNumber-- : 1;
        } else {
            Result<List<CompilationsJsonVo>> result = (Result<List<CompilationsJsonVo>>) date;
            if (isPagin) {
                if (result.getData().size() < 0)
                    resPageNumber--;
            } else
                view.showCollection(result.getData(), isPagin);

//            if (result.getData().size() > 0)
//                view.showCollection(result.getData(), isPagin);
//            else if (isPagin)
//                resPageNumber--;
        }
    }

    @Override
    public void onError(String uri, String error) {
        G.showToast(IPTVApp.getInstance().getApplicationContext(), error);
        if (AppUrl.GETTHEMELIST.equals(uri)) {
            themePageNumber = themePageNumber > 1 ? themePageNumber-- : 1;
        } else {
            if (isPagin)
                resPageNumber--;
        }
    }
}
