package net.hunme.kidsworld_iptv.contract;

import com.google.gson.reflect.TypeToken;

import net.hunme.baselibrary.mode.Result;
import net.hunme.baselibrary.network.OkHttpListener;
import net.hunme.baselibrary.network.OkHttps;
import net.hunme.baselibrary.util.DateUtil;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.mode.DynamicInfoJsonVo;
import net.hunme.kidsworld_iptv.mode.DynamicJsonVo;
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
public class DynamicPresenter implements DynamicContract.Presenter, OkHttpListener {
    private DynamicContract.View view;
    private String createTime = DateUtil.getCurrentDateTime();
    private String groupId;
    private String groupType;
    private int pageNumber = 1;
    private boolean isChange;

    public DynamicPresenter(DynamicContract.View view) {
        this.view = view;
    }

    @Override
    public void getHeadMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("tsId", IPTVApp.um.getUserTsId());
        Type type = new TypeToken<Result<List<DynamicJsonVo>>>() {
        }.getType();
        OkHttps.sendPost(type, AppUrl.GETDYNAMICHEAD, map, this);
    }

    @Override
    public void getDynamicInfo(String groupId, String groupType, int pageNumber,
                               String type, String dynamicId) {
        Map<String, Object> map = new HashMap<>();
        map.put("tsId", IPTVApp.um.getUserTsId());
        map.put("groupId", groupId);
        map.put("groupType", groupType);
        map.put("pageNumber", pageNumber);
        map.put("pageSize", G.PAGESIZE);
        map.put("createTime", createTime);
        map.put("type", type);
        map.put("dynamicId", dynamicId);
        Type mType = new TypeToken<Result<List<DynamicInfoJsonVo>>>() {
        }.getType();
        OkHttps.sendPost(mType, AppUrl.GETDYNAMIC, map, this);
        if (pageNumber == 1) {
            isChange = true;
            this.pageNumber = 1;
            this.groupId = groupId;
            this.groupType = groupType;
        } else {
            isChange = false;
        }
    }

    @Override
    public void getPaginDynamicInfo(String type, String dynamicId) {
        pageNumber++;
        getDynamicInfo(groupId, groupType, pageNumber, type, dynamicId);
    }

    @Override
    public void onSuccess(String uri, Object date) {
        if (AppUrl.GETDYNAMICHEAD.equals(uri)) {
            Result<List<DynamicJsonVo>> result = (Result<List<DynamicJsonVo>>) date;
            view.setHeadMessage(result.getData());
        } else if (AppUrl.GETDYNAMIC.equals(uri)) {
            Result<List<DynamicInfoJsonVo>> result = (Result<List<DynamicInfoJsonVo>>) date;
//            createTime = result.getMsec();
            if (result.getData().size() > 0)
                view.setDynamicInfo(result.getData(), isChange);
            else
                pageNumber--;
        }
    }

    @Override
    public void onError(String uri, String error) {
        if (AppUrl.GETDYNAMIC.equals(uri)) {
            pageNumber--;
        }
    }
}
