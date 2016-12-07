package net.hunme.kidsworld_iptv.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;

import net.hunme.baselibrary.image.ImageCache;
import net.hunme.baselibrary.mode.Result;
import net.hunme.baselibrary.network.OkHttpListener;
import net.hunme.baselibrary.network.OkHttps;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.mode.SyllabusJsonListVo;
import net.hunme.kidsworld_iptv.util.AppUrl;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 课程表
 */
public class ScheduleFragment extends Fragment implements OkHttpListener {

    @Bind(R.id.iv_schedule)
    ImageView ivSchedule;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, view);
        Map<String, Object> map = new HashMap<>();
        map.put("tsId", IPTVApp.um.getUserTsId());
        map.put("pageNumber", "1");
        map.put("pageSize", G.PAGESIZE);
        Type type=new TypeToken<Result<List<SyllabusJsonListVo>>>(){}.getType();
        OkHttps.sendPost(type, AppUrl.GETSYLLABUSLIST,map,this);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onSuccess(String uri, Object date) {
        Result<List<SyllabusJsonListVo>> result= (Result<List<SyllabusJsonListVo>>) date;
        List<SyllabusJsonListVo> jsonList=result.getData();
        if(jsonList!=null&&jsonList.size()>0){
            if(jsonList.get(0).getImgs()!=null&&jsonList.get(0).getImgs().size()>0){
                ImageCache.imageLoader(jsonList.get(0).getImgs().get(0),ivSchedule);
            }
        }
    }

    @Override
    public void onError(String uri, String error) {

    }
}
