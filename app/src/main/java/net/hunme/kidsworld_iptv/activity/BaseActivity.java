package net.hunme.kidsworld_iptv.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.hunme.kidsworld_iptv.R;

import butterknife.ButterKnife;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/20
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        View view=this.getWindow().getDecorView();
        view.setBackgroundResource(R.mipmap.ic_default_bg);
        ButterKnife.bind(this);
        initDate();
        //设置一个退出的 transitions 动画
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            //允许使用transitions 动画
//            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//            //android 提供了三种动画 Explode（爆炸）,Slide（平滑）,Fade（渐显）.
//            //在跳转之前必须用 startActivity(mIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
//            Explode explode=new Explode();
//            explode.setDuration(1000);
//            getWindow().setExitTransition(new Explode());
//        }
    }

    protected abstract void initDate();
}
