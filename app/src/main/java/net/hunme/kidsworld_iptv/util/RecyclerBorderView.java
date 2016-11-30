package net.hunme.kidsworld_iptv.util;

import android.app.Activity;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.open.androidtvwidget.bridge.RecyclerViewBridge;
import com.open.androidtvwidget.leanback.recycle.LinearLayoutManagerTV;
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/4
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class RecyclerBorderView implements RecyclerViewTV.OnItemListener {
    private MainUpView mainUpView;
    private Activity activity;
    private RecyclerViewTV recyclerView;
    private View oldView;
    private GirdViewItemSelect itemSelect;
    private RecyclerViewBridge dafalutFrame;
    public RecyclerBorderView(Activity activity) {
        this.activity = activity;
    }

    public static RecyclerBorderView getInstance(Activity activity) {
        return new RecyclerBorderView(activity);
    }

    private void init() {
//        mainUpView = new MainUpView(activity);
//        mainUpView.attach2Window(activity);
//        mainUpView.setVisibility(View.GONE);
//        mainUpView.setEffectBridge(new RecyclerViewBridge());
//        // 注意这里，需要使用 RecyclerViewBridge 的移动边框 Bridge.
//        mRecyclerViewBridge = (RecyclerViewBridge) mainUpView.getEffectBridge();
////        mRecyclerViewBridge.setUpRectResource(R.drawable.ic_girad_bg);
//        mRecyclerViewBridge.setUpRectResource(R.drawable.select_frame); // 设置移动边框图片.
////        if (G.size.W == 1920) {
////            mRecyclerViewBridge.setDrawUpRectPadding(new Rect(35, 35, 35, -70));
////        } else if (G.size.W == 1280) {
////            mRecyclerViewBridge.setDrawUpRectPadding(new Rect(28, 20, 23, -35));
////        }
//        if (G.size.W == 1920) {
//            mRecyclerViewBridge.setDrawUpRectPadding(new Rect(0, 1, 0, 0)); //设置边框的大小 不适配
//        } else if (G.size.W == 1280) {
//            mRecyclerViewBridge.setDrawUpRectPadding(new Rect(0, -20, 0, -75)); //设置边框的大小 不适配
//        }
        LinearLayoutManagerTV layoutManager = new LinearLayoutManagerTV(activity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setSelectedItemAtCentered(true);
        recyclerView.setOnItemListener(this);
    }

    public void setRecyclerView(RecyclerViewTV recyclerView,MainUpView upView) {
        upView.setEffectBridge(new RecyclerViewBridge());
        this.recyclerView = recyclerView;
        this.dafalutFrame = (RecyclerViewBridge) upView.getEffectBridge();
        this.dafalutFrame.setDrawUpRectPadding(new Rect(20,10,20,20));
        init();
    }

    public void setItemSelect(GirdViewItemSelect itemSelect){
        this.itemSelect=itemSelect;
    }

    @Override
    public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {
        dafalutFrame.setUnFocusView(itemView);
    }

    @Override
    public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {

        dafalutFrame.setUpRectResource(R.drawable.ic_read);
//        dafalutFrame.setShadowResource(R.drawable.ic_girad_bg);
        dafalutFrame.setFocusView(itemView, G.ENLARGE);
        oldView = itemView;
        if(itemSelect!=null){
            itemSelect.onItemSelect(itemView,position);
        }
    }

    @Override
    public void onReviseFocusFollow(RecyclerViewTV parent, View itemView, int position) {
//        dafalutFrame.setShadowResource(R.drawable.ic_girad_bg);
        dafalutFrame.setUpRectResource(R.drawable.ic_read);
        dafalutFrame.setFocusView(itemView, G.ENLARGE);
        oldView = itemView;
    }
}
