package net.hunme.kidsworld_iptv.contract;

import com.google.gson.reflect.TypeToken;

import net.hunme.baselibrary.mode.Result;
import net.hunme.baselibrary.network.OkHttpListener;
import net.hunme.baselibrary.network.OkHttps;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.mode.ResourceManageVo;
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
public class ResDetilsPresenter implements ResDetilsContract.Presenter, OkHttpListener {
    private ResDetilsContract.View view;
    private int pageNumber = 1;
    private String albumId;
    private boolean isClean;
    public ResDetilsPresenter(ResDetilsContract.View view) {
        this.view = view;
    }

    /**
     * 通过专辑ID获取资源
     *
     * @param albumId
     * @param pageNumber
     */
    @Override
    public void getCompilationsAllResource(String albumId, int pageNumber,boolean isClean) {
        Map<String, Object> map = new HashMap<>();
        map.put("tsId", IPTVApp.um.getUserTsId());
        map.put("pageSize", G.PAGESIZE);
        map.put("pageNumber", pageNumber);
        map.put("albumId", albumId);
        map.put("account_id",IPTVApp.um.getAccounId());
        Type type = new TypeToken<Result<List<ResourceManageVo>>>() {
        }.getType();
        OkHttps.sendPost(type, AppUrl.GETCOMPILATIONALLRESOURCE, map, this);
        this.albumId = albumId;
        this.isClean=isClean;
    }

    @Override
    public void getPaginCompialation() {
        pageNumber++;
        getCompilationsAllResource(albumId, pageNumber,false);
    }

    @Override
    public void synDate(String albumId, int pageNumber) {
        this.pageNumber = pageNumber;
        this.albumId=albumId;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public void onSuccess(String uri, Object date) {
        Result<List<ResourceManageVo>> result = (Result<List<ResourceManageVo>>) date;
        if (result.getData().size() > 0)
            view.showCompilationResource(result.getData(),isClean);
        else
            pageNumber = pageNumber > 1 ? pageNumber-- : 1;
    }

    @Override
    public void onError(String uri, String error) {
        pageNumber = pageNumber > 1 ? pageNumber-- : 1;
    }
}
