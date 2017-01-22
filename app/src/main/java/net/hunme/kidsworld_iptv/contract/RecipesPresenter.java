package net.hunme.kidsworld_iptv.contract;

import com.google.gson.reflect.TypeToken;

import net.hunme.baselibrary.mode.Result;
import net.hunme.baselibrary.network.OkHttpListener;
import net.hunme.baselibrary.network.OkHttps;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.mode.CookBookVo;
import net.hunme.kidsworld_iptv.util.AppUrl;

import java.lang.reflect.Type;
import java.util.HashMap;
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
public class RecipesPresenter implements RecipesContract.Presenter, OkHttpListener {
    private RecipesContract.View view;

    public RecipesPresenter(RecipesContract.View view) {
        this.view = view;
    }

    @Override
    public void getCookBook(String date) {
        view.goneRecipes();
        Map<String, Object> map = new HashMap<>();
        map.put("tsId", IPTVApp.um.getUserTsId());
        map.put("date", date);
        Type type = new TypeToken<Result<CookBookVo>>() {
        }.getType();
        OkHttps.sendPost(type, AppUrl.GETCOOKBOOK, map, this);

    }

    @Override
    public void onSuccess(String uri, Object date) {
        Result<CookBookVo> result = (Result<CookBookVo>) date;
        view.showCookBook(result.getData().getDishesList());
    }

    @Override
    public void onError(String uri, String error) {

    }
}
