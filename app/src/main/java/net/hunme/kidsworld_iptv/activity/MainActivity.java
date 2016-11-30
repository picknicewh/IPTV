package net.hunme.kidsworld_iptv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.adapter.SuperActivityRecyclerAdapter;
import net.hunme.kidsworld_iptv.util.RecyclerBorderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements View.OnFocusChangeListener {

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

    private MainUpView upView;
    private SuperActivityRecyclerAdapter adapterRecord;
    private List<String> imgUrlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //乐园请求焦点  默认选中
        llMovie.requestFocus();
        llMovie.setFocusableInTouchMode(true);
    }

    @Override
    protected void initDate() {
        upView = new MainUpView(this);
        upView.attach2Window(this);
        imgUrlList = new ArrayList<>();
        RecyclerBorderView borderView = RecyclerBorderView.getInstance(this);
        borderView.setRecyclerView(rvRecord, upView);
        adapterRecord = new SuperActivityRecyclerAdapter(imgUrlList);
        rvRecord.setAdapter(adapterRecord);

        for (int i = 0; i < rlMenu.getChildCount(); i++) {
            rlMenu.getChildAt(i).setOnFocusChangeListener(this);
        }

        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 2.4f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f);
        animation.setDuration(20000);//设置动画持续时间
        animation.setRepeatCount(Integer.MAX_VALUE);//设置重复次数
        animation.setRepeatMode(Animation.RESTART);//设置从头开始执行
        rlNotice.setAnimation(animation);
        animation.start();

        setTestDate();
    }

    private void setTestDate() {
        for (int i = 0; i < 10; i++) {
            imgUrlList.add("");
        }
        adapterRecord.notifyDataSetChanged();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            upView.setUpRectResource(R.drawable.dr);
            upView.setFocusView(view, 1.2f);
        } else
            upView.setUnFocusView(view);
    }

    @OnClick({R.id.ll_rank, R.id.ll_home, R.id.ll_movie, R.id.ll_music, R.id.ll_magic, R.id.ib_setting})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_rank:
                G.showToast(this, "暂未开放");
                break;
            case R.id.ll_home:
                intent = new Intent(this, HomeActivity.class);
                break;
            case R.id.ll_movie:
                intent=new Intent(this,ResourcesListActivity.class);
                break;
            case R.id.ll_music:
                intent=new Intent(this,ResourcesListActivity.class);
                break;
            case R.id.ll_magic:
                G.showToast(this, "暂未开放");
                break;
            case R.id.ib_setting:
                intent=new Intent(this,SettingDetlisActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}