package net.hunme.kidsworld_iptv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.open.androidtvwidget.bridge.OpenEffectBridge;
import com.open.androidtvwidget.leanback.recycle.LinearLayoutManagerTV;
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.baselibrary.mode.Result;
import net.hunme.baselibrary.network.OkHttpListener;
import net.hunme.baselibrary.network.OkHttps;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.adapter.SuperActivityRecyclerAdapter;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.mode.CompilationsJsonVo;
import net.hunme.kidsworld_iptv.util.AnimationUtil;
import net.hunme.kidsworld_iptv.util.AppUrl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements View.OnFocusChangeListener, OkHttpListener, RecyclerViewTV.OnItemClickListener {

    @Bind(R.id.rv_record)
    RecyclerViewTV rvRecord;

    @Bind(R.id.rl_menu)
    LinearLayout rlMenu;

    @Bind(R.id.ll_movie)
    LinearLayout llMovie;

    @Bind(R.id.tv_notice)
    TextView tvNotice;

    @Bind(R.id.rl_notice)
    RelativeLayout rlNotice;

    @Bind(R.id.ib_setting)
    ImageButton ibSetting;

    private MainUpView upView;   //焦点移动框
    private SuperActivityRecyclerAdapter adapterRecord;
    private List<CompilationsJsonVo> compilationsList;
    private Timer timer;
    private boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //乐园请求焦点  默认选中
        llMovie.requestFocus();
        llMovie.findFocus();
    }

    @Override
    protected void initDate() {
        upView = new MainUpView(this);
        upView.attach2Window(this);
        compilationsList = new ArrayList<>();
        rvRecord.setLayoutManager(new LinearLayoutManagerTV(this, LinearLayoutManager.HORIZONTAL, false));
        adapterRecord = new SuperActivityRecyclerAdapter(compilationsList, upView, rvRecord);
        rvRecord.setAdapter(adapterRecord);
        rvRecord.setOnItemClickListener(this);
        for (int i = 0; i < rlMenu.getChildCount(); i++) {
            rlMenu.getChildAt(i).setOnFocusChangeListener(this);
        }
        ibSetting.setOnFocusChangeListener(this);
        AnimationUtil.tranLevelAnimation(rlNotice, -1.0f, 2.4f, 20000); //设置火车位移
        getRecommendList();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        ((OpenEffectBridge) upView.getEffectBridge()).setVisibleWidget(true); //隐藏焦点框
        if (b) {
            float enlargeSpace;
            if (view.getId() == R.id.ll_rank || view.getId() == R.id.ll_magic||view.getId()==R.id.ib_setting)
                enlargeSpace = 1.3f;
            else
                enlargeSpace = 1.5f;
            upView.setFocusView(view, enlargeSpace);
            adapterRecord.setFouceForTheme(true);
        } else {
            upView.setUnFocusView(view);
        }
    }

    @OnClick({R.id.ll_rank, R.id.ll_home, R.id.ll_movie, R.id.ll_music, R.id.ll_magic, R.id.ib_setting})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_rank:
                G.showToast(this, "暂未开放");
                break;
            case R.id.ll_home:
                if (G.isEmteny(IPTVApp.um.getUserTsId()))
                    intent = new Intent(this, QRcodeLoginActivity.class);
                else
                    intent = new Intent(this, HomeActivity.class);
                break;
            case R.id.ll_movie:
                intent = new Intent(this, ResourcesListActivity.class);
                intent.putExtra("actionType", String.valueOf(G.IPTV_TYPE.MOVE));
                break;
            case R.id.ll_music:
                intent = new Intent(this, ResourcesListActivity.class);
                intent.putExtra("actionType", String.valueOf(G.IPTV_TYPE.MUSIC));
                break;
            case R.id.ll_magic:
                G.showToast(this, "暂未开放");
                break;
            case R.id.ib_setting:
                intent = new Intent(this, SettingDetlisActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    /**
     * 获取推荐
     */
    private void getRecommendList() {
        Map<String, Object> map = new HashMap<>();
        map.put("tsId", IPTVApp.um.getUserTsId());
        map.put("pageSize", 15);
        map.put("type", "4");
        map.put("pageNumber", 1);
        Type type = new TypeToken<Result<List<CompilationsJsonVo>>>() {
        }.getType();
        OkHttps.sendPost(type, AppUrl.GETGUESSTYOULIKE, map, this);
    }

    @Override
    public void onSuccess(String uri, Object date) {
        Result<List<CompilationsJsonVo>> result = (Result<List<CompilationsJsonVo>>) date;
        if (result.getData() != null && result.getData().size() > 0) {
            compilationsList.clear();
            compilationsList.addAll(result.getData());
            adapterRecord.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String uri, String error) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (isExit) {
                finish();
            } else {
                G.showToast(this, "再按一次退出程序");
            }
            if (timer != null) timer.cancel();
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            };
            isExit = true;
            timer.schedule(timerTask, 1000);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemClick(RecyclerViewTV parent, View itemView, int position) {
        Intent intent = new Intent(MainActivity.this, ResourceDetlisActivity.class);
        intent.putExtra("compilation", compilationsList.get(position));
        startActivity(intent);
    }
}