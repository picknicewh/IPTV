package net.hunme.kidsworld_iptv.contract;

import com.google.gson.reflect.TypeToken;

import net.hunme.baselibrary.mode.Result;
import net.hunme.baselibrary.network.OkHttpListener;
import net.hunme.baselibrary.network.OkHttps;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.mode.CollectionVo;
import net.hunme.kidsworld_iptv.mode.ResourceContentVo;
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
public class CollectionPresenter implements CollectionContract.Presenter, OkHttpListener {
    private CollectionContract.View view;

    public CollectionPresenter(CollectionContract.View view) {
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

    @Override
    public void getCollectionDate(String type, String name, String pageSize, String pageNumber) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("nameAbbreviation", name);
        map.put("pageSize", pageSize);
        map.put("pageNumber", pageNumber);
        Type mtype = new TypeToken<Result<List<CollectionVo>>>() {
        }.getType();
        OkHttps.sendPost(mtype, AppUrl.GETRESOURCElIST, map, this);
    }

    @Override
    public void onSuccess(String uri, Object date) {
        if (AppUrl.GETRESOURCElIST.equals(uri)) {
            Result<List<CollectionVo>> result = (Result<List<CollectionVo>>) date;
            List<CollectionVo> contentVos = result.getData();
            List<ResourceContentVo> contentList = new ArrayList<>();
            for (CollectionVo vo : contentVos) {
                ResourceContentVo contentVo = new ResourceContentVo();
                contentVo.setImgUrl(vo.getImgUrl());
                contentVo.setName(vo.getName());
                contentVo.setId(vo.getId());
                contentList.add(contentVo);
            }
            view.setColletionContent(contentList);
        }
    }

    @Override
    public void onError(String uri, String error) {
        G.showToast(IPTVApp.getInstance().getApplicationContext(), error);
    }
}
