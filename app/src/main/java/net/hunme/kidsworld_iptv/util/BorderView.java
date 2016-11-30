package net.hunme.kidsworld_iptv.util;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;

import com.open.androidtvwidget.view.GridViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/2
 * 描    述：放大加边框
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class BorderView implements AdapterView.OnItemSelectedListener, View.OnFocusChangeListener {
    private MainUpView defaultFrame;
    private Activity activity;
    private View oldView;
    private GirdViewItemSelect itemSelect;
    private GridViewTV viewTV;
    public BorderView(Activity activity) {
        this.activity = activity;
//        init();
    }

    public static BorderView getInstance(Activity activity) {
        return new BorderView(activity);
    }

//    /**
//     * 初始化边框
//     */
//    private void init() {
//        mainUpView = MasterUpView.getMainUpView(activity);
////        mainUpView = new MainUpView(activity);
////        mainUpView.attach2Window(activity); //初始化边框xml
////        mainUpView.setVisibility(View.GONE);
////        EffectNoDrawBridge drawBridge = new EffectNoDrawBridge();
////        mainUpView.setEffectBridge(drawBridge);
////        drawBridge.setShadowResource(R.drawable.ic_item_shadow);
////        drawBridge.setUpRectResource(R.drawable.select_frame); // 设置移动边框图片.
////        drawBridge.setDrawUpRectPadding(new Rect(20, 20, 23, -35)); //设置边框的大小 不适配
////        if (G.size.W == 1920) {
////            drawBridge.setDrawUpRectPadding(new Rect(18, 35, 18, -70)); //设置边框的大小 不适配
////        } else if (G.size.W == 1280) {
////            drawBridge.setDrawUpRectPadding(new Rect(20, 20, 23, -35)); //设置边框的大小 不适配
////        }
////        if (G.size.W == 1920) {
////            drawBridge.setDrawUpRectPadding(new Rect(0, 1, 0, 0)); //设置边框的大小 不适配
////        } else if (G.size.W == 1280) {
////            drawBridge.setDrawUpRectPadding(new Rect(-15, 0, -17, -75)); //设置边框的大小 不适配
////        }
//    }

    public void setGirdItemSelect(GirdViewItemSelect itemSelect) {
        this.itemSelect = itemSelect;
    }

    /**
     * 设置GiradView
     *
     * @param viewTV
     */
    public void setGirdViewTV(final GridViewTV viewTV, final MainUpView defaultFrame) {
        this.defaultFrame = defaultFrame;
        this.viewTV=viewTV;
        this.viewTV.setSelector(new ColorDrawable(Color.TRANSPARENT)); //去除系统默认选中框
        this.viewTV.setOnItemSelectedListener(this); //设置GirdView的选择每个子View监听事件
        this.viewTV.setOnFocusChangeListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (view != null) {
            defaultFrame.setUpRectResource(R.drawable.select_frame);
            defaultFrame.setUnFocusView(oldView);
            defaultFrame.setFocusView(view,G.ENLARGE);
            oldView = view;
        }
        if (itemSelect != null) {
            itemSelect.onItemSelect(view, i);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        defaultFrame.setUpRectResource(R.drawable.select_frame);
        if(b){
            defaultFrame.setFocusView(oldView,G.ENLARGE);
        }else {
//            viewTV.clearChoices();
//            viewTV.clearDisappearingChildren();
//            viewTV.setOnItemSelectedListener(this);
            viewTV.clearChoices();
            defaultFrame.setUnFocusView(oldView);
        }
//        defaultFrame.setUpRectResource(R.drawable.select_frame);
//        if (b){
//            defaultFrame.setFocusView(viewTV.getChildAt(0), G.ENLARGE);
//            oldView=viewTV.getChildAt(0);
//        }
////        viewTV.setDefualtSelect(0);
//        else
//            defaultFrame.setUnFocusView(oldView);
    }
}
