package net.hunme.kidsworld_iptv.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import net.hunme.kidsworld_iptv.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/25
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class MyKeybordPopwindow extends PopupWindow {
    private TextView view1,view2,view3,view4,view5;
    private List<TextView>tvList;
    public MyKeybordPopwindow(final Activity activity) {
        View view= LayoutInflater.from(activity).inflate(R.layout.dialog_keybrod,null);
        setContentView(view);
        view1= (TextView) view.findViewById(R.id.tv_01);
        view2= (TextView) view.findViewById(R.id.tv_02);
        view3= (TextView) view.findViewById(R.id.tv_03);
        view4= (TextView) view.findViewById(R.id.tv_04);
        view5= (TextView) view.findViewById(R.id.tv_05);
        tvList=new ArrayList<>();
        tvList.add(view1);
        tvList.add(view2);
        tvList.add(view3);
        tvList.add(view4);
        tvList.add(view5);
//        G.initDisplaySize(activity);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SignPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SignPopupWindow弹出窗体可点击
        this.setFocusable(false);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(55000000);
        //设置SignPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //弹出设置窗体背景透明度0.7f
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.7f;
        activity.getWindow().setAttributes(lp);
        //监听popwindow消失时恢复原来的透明度
        this.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
            }
        });
    }


    public void setTextViewContent(List<String> menuDate){
        String letter=menuDate.get(0);//字母
        String number=menuDate.get(1);//数字
        int i;
        for (i=0;i<letter.length();i++){
            tvList.get(i).setText(letter.substring(i,i+1));
        }
        tvList.get(i).setText(number);
    }

    //获取按键的值
    public String getKeyBordText(int position){
       return tvList.get(position).getText().toString();
    }
}
