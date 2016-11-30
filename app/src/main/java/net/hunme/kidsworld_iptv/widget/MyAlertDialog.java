package net.hunme.kidsworld_iptv.widget;


import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import net.hunme.kidsworld_iptv.R;

/**
 * 作    者：Restring
 * 时    间： 2016/7/24
 * 描    述：自定义一个弹框
 * 版    本：
 * 修订历史：
 * 主要接口：
 */
public class MyAlertDialog {
    private static double heightFlag = 0.3;
    private static double widthFlag = 0.5;

    public static AlertDialog getDialog(View view, Activity activity, double heigh) {
        heightFlag = heigh;
        return getDialog(view, activity);
    }

    public static AlertDialog getDialog(View view, Activity activity, double widthFlag, double heightFlag) {
        heightFlag = heightFlag;
        widthFlag = widthFlag;
        return getDialog(view, activity);
    }

    public static AlertDialog getDialog(View view, Activity activity) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.show();
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams params =
                alertDialog.getWindow().getAttributes();
        params.width = (int) (display.getWidth() * widthFlag);
        params.height = (int) (display.getHeight() * heightFlag);
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        alertDialog.getWindow().setAttributes(params);
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.fillet_pop);
        alertDialog.getWindow().setContentView(view);
        //内置初始化宽高比
        heightFlag = 0.3;
        widthFlag = 0.5;
        return alertDialog;
    }


}
