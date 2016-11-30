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
        view.setBackgroundResource(R.mipmap.ic_main_bg);
        ButterKnife.bind(this);
        initDate();
    }

    protected abstract void initDate();
}
