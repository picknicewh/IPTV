package net.hunme.kidsworld_iptv.contract;

import com.google.gson.reflect.TypeToken;

import net.hunme.baselibrary.mode.Result;
import net.hunme.baselibrary.network.OkHttpListener;
import net.hunme.baselibrary.network.OkHttps;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.mode.CompilationsJsonVo;
import net.hunme.kidsworld_iptv.mode.FootPrintVo;
import net.hunme.kidsworld_iptv.mode.ResourceManageVo;
import net.hunme.kidsworld_iptv.mode.SearchVo;
import net.hunme.kidsworld_iptv.util.AppUrl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/25
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class SearchPresenter implements SearchContract.Presenter, OkHttpListener {
    private SearchContract.View view;
    private int type;
    private boolean isPagin;
    private int pageNumber = 1;
    private String tag;

    public SearchPresenter(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public List<Map<String, String>> getKeyBordDate() {
        List<Map<String, String>> maps = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("number", "1");
        maps.add(map);

        map = new HashMap<>();
        map.put("letter", "ABC");
        map.put("number", "2");
        maps.add(map);

        map = new HashMap<>();
        map.put("letter", "DEF");
        map.put("number", "3");
        maps.add(map);

        map = new HashMap<>();
        map.put("letter", "GHI");
        map.put("number", "4");
        maps.add(map);

        map = new HashMap<>();
        map.put("letter", "JKL");
        map.put("number", "5");
        maps.add(map);

        map = new HashMap<>();
        map.put("letter", "MNO");
        map.put("number", "6");
        maps.add(map);

        map = new HashMap<>();
        map.put("letter", "PQRS");
        map.put("number", "7");
        maps.add(map);

        map = new HashMap<>();
        map.put("letter", "TUS");
        map.put("number", "8");
        maps.add(map);

        map = new HashMap<>();
        map.put("letter", "WXYZ");
        map.put("number", "9");
        maps.add(map);

        map = new HashMap<>();
        map.put("letter", "清空");
        maps.add(map);

        map = new HashMap<>();
        map.put("number", "0");
        maps.add(map);

        map = new HashMap<>();
        map.put("letter", "回删");
        maps.add(map);
        view.setKeyBord(maps);
        return maps;
    }

    /**
     * 获取热搜榜单
     *
     * @param pageNumber
     * @param type
     */
    @Override
    public void getHotSearch(int pageNumber, int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("tsId", IPTVApp.um.getUserTsId());
        map.put("pageSize", G.PAGESIZE);
        map.put("type", type);
        map.put("pageNumber", pageNumber);
        Type mType = new TypeToken<Result<List<CompilationsJsonVo>>>() {
        }.getType();
        if (type == G.IPTV_TYPE.MOVE) {
            OkHttps.sendPost(mType, AppUrl.GETHOTSEARCH, map, this);
        } else if (type == G.IPTV_TYPE.MUSIC) {
            OkHttps.sendPost(mType, AppUrl.GETHOTSEARCH, map, new OkHttpListener() {
                @Override
                public void onSuccess(String uri, Object date) {
                    Result<List<CompilationsJsonVo>> result = (Result<List<CompilationsJsonVo>>) date;
                    if (result.getData().size() > 0)
                        view.setMusicHotSearch(result.getData());
                }

                @Override
                public void onError(String uri, String error) {

                }
            });
        }
    }


    @Override
    public void getSearchDate(int type, int pageNumber, String tag) {
        Map<String, Object> map = new HashMap<>();
        map.put("tsId", IPTVApp.um.getUserTsId());
        map.put("pageSize", G.PAGESIZE);
        map.put("pageNumber", pageNumber);
        map.put("type", type); //1=视屏，2=音频，3=资讯,4=视频加音频，5=全部
        map.put("tag", tag);
        map.put("account_id", IPTVApp.um.getAccounId());
        Type mType = new TypeToken<Result<SearchVo>>() {
        }.getType();
        OkHttps.sendPost(mType, AppUrl.GETRESOURCESEARCH, map, this);
        if (pageNumber == 1) {
            this.isPagin = false;
            this.pageNumber = 1;
            this.type = type;
            this.tag = tag;
            view.showDialog();
        } else
            this.isPagin = true;
    }

    @Override
    public void getPaginSearch() {
        pageNumber++;
        getSearchDate(type, pageNumber, tag);
    }

    /**
     * 足迹搜索
     *
     * @param tag
     */
    @Override
    public void getSearchFootPrint(String tag) {
        Map<String, Object> map = new HashMap<>();
        map.put("tsId", IPTVApp.um.getUserTsId());
        map.put("tag", tag);
        map.put("account_id", IPTVApp.um.getAccounId());
        Type mType = new TypeToken<Result<List<FootPrintVo>>>() {
        }.getType();
        OkHttps.sendPost(mType, AppUrl.SEARCHFOOTPRINT, map, this);
    }

    /**
     * 足迹资源详情搜索
     *
     * @param resourceId
     */
    @Override
    public void getFootPrintDetails(String resourceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("tsId", IPTVApp.um.getUserTsId());
        map.put("resourceId", resourceId);
        map.put("account_id", IPTVApp.um.getAccounId());
        Type mType = new TypeToken<Result<ResourceManageVo>>() {
        }.getType();
        OkHttps.sendPost(mType, AppUrl.GETFOOTPRINTDATAILS, map, this);
    }

    @Override
    public void onSuccess(String uri, Object date) {
        if (AppUrl.GETHOTSEARCH.equals(uri)) {
            Result<List<CompilationsJsonVo>> result = (Result<List<CompilationsJsonVo>>) date;
            if (result.getData().size() > 0)
                view.setMoveHotSearch(result.getData());
        } else if (AppUrl.GETRESOURCESEARCH.equals(uri)) {
            view.dismissDialog();
            Result<SearchVo> result = (Result<SearchVo>) date;
            if (result.getData().getAlbumManageList().size() > 0 || result.getData().getResourceManageList().size() > 0) {
                view.setSearchDate(result.getData().getAlbumManageList(), result.getData().getResourceManageList(), isPagin);
            } else {
                if (isPagin) {
                    pageNumber--;
                    //分页加载不进行提示
                    return;
                }
                G.showToast(IPTVApp.getInstance().getApplicationContext(), "暂未搜索到该资源");
            }
        } else if (AppUrl.SEARCHFOOTPRINT.equals(uri)) {
            view.dismissDialog();
            Result<List<FootPrintVo>> result = (Result<List<FootPrintVo>>) date;
            if (result.getData().size() > 0)
                view.setSearchFootPrint(result.getData());
            else
                G.showToast(IPTVApp.getInstance().getApplicationContext(), "暂未搜索到该资源");
        } else if (AppUrl.GETFOOTPRINTDATAILS.equals(uri)) {
            view.intentFromFootPrint(((Result<ResourceManageVo>) date).getData());
        }
    }

    @Override
    public void onError(String uri, String error) {
        G.showToast(IPTVApp.getInstance().getApplicationContext(), error);
        if (AppUrl.GETRESOURCESEARCH.equals(uri)) {
            if (isPagin)
                pageNumber--;
        }
        view.dismissDialog();
    }
}
