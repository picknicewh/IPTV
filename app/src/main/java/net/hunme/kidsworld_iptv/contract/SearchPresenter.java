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
    private int type; //记录当前请求的类型  1 表示动画坊  2 表示音悦台

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
    public void getSearchDate() {

    }

    @Override
    public void getPaginSearch() {

    }


    @Override
    public void onSuccess(String uri, Object date) {
        if (AppUrl.GETHOTSEARCH.equals(uri)) {
            Result<List<CompilationsJsonVo>> result = (Result<List<CompilationsJsonVo>>) date;
            if (result.getData().size() > 0)
                view.setMoveHotSearch(result.getData());
        }
    }

    @Override
    public void onError(String uri, String error) {
        G.showToast(IPTVApp.getInstance().getApplicationContext(), error);
    }
}
