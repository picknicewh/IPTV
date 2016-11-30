package net.hunme.kidsworld_iptv.util;

import android.app.Activity;

import com.open.androidtvwidget.view.MainUpView;

import net.hunme.kidsworld_iptv.R;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/16
 * 描    述：焦点移动移动框
 * 无论的菜单还是系统默认  一个页面必须只有一个Upview 不然就无法做到一个页面框
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class MasterUpView {
    private MainUpView upView;
    private MainUpView getMainUpView(Activity activity) {
        if(upView==null){
            upView = new MainUpView(activity);
            upView.attach2Window(activity);
        }
//        mainUpView.setUpRectResource(R.drawable.select_frame);
//        mainUpView.setShadowResource(R.drawable.ic_item_shadow);
//        mainUpView.setUpRectResource(R.drawable.select_frame);
        return upView;
    }

    /**
     *  默认选中框
     * @param activity
     * @return
     */
    public MainUpView getDefaulFrame(Activity activity) {
        upView=getMainUpView(activity);
        upView.setUpRectResource(R.drawable.select_frame);
        return upView;
    }

    /**
     *  默认选中框
     * @param activity
     * @return
     */
    public MainUpView getDefaulFrame(Activity activity,int resouceId) {
        upView=getMainUpView(activity);
        upView.setUpRectResource(resouceId);
        return upView;
    }

    /**
     * 针对菜单选择框
     * @return
     */
    public MainUpView getMenuFrame(Activity activity){
        upView=getMainUpView(activity);
        upView.setUpRectResource(R.mipmap.ic_menu_select_bg);
        return upView;
    }

    /**
     *  针对菜单选择框
     * @return
     */
    public MainUpView getMenuFrame(Activity activity,int resouceId){
        upView=getMainUpView(activity);
        upView.setUpRectResource(resouceId);
        return upView;
    }
}
