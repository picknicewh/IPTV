package net.hunme.kidsworld_iptv.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import net.hunme.kidsworld_iptv.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/25
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class MyKeybordPopwindow extends PopupWindow implements View.OnClickListener, View.OnFocusChangeListener {
    @Bind(R.id.tv_01)
    TextView tv01;
    @Bind(R.id.tv_02)
    TextView tv02;
    @Bind(R.id.tv_05)
    TextView tv05;
    @Bind(R.id.tv_04)
    TextView tv04;
    @Bind(R.id.tv_03)
    TextView tv03;

    private List<TextView> tvList;
    private EditText editText;

    public MyKeybordPopwindow(final Activity activity, EditText editText) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_keybrod, null);
        setContentView(view);
        this.editText = editText;
        tv01 = (TextView) view.findViewById(R.id.tv_01);
        tv02 = (TextView) view.findViewById(R.id.tv_02);
        tv03 = (TextView) view.findViewById(R.id.tv_03);
        tv04 = (TextView) view.findViewById(R.id.tv_04);
        tv05 = (TextView) view.findViewById(R.id.tv_05);

        tvList = new ArrayList<>();
        tvList.add(tv01);
        tvList.add(tv02);
        tvList.add(tv05);
        tvList.add(tv04);
        tvList.add(tv03);

        tv01.setOnClickListener(this);
        tv02.setOnClickListener(this);
        tv03.setOnClickListener(this);
        tv04.setOnClickListener(this);
        tv05.setOnClickListener(this);

        tv01.setOnFocusChangeListener(this);
        tv02.setOnFocusChangeListener(this);
        tv03.setOnFocusChangeListener(this);
        tv04.setOnFocusChangeListener(this);
        tv05.setOnFocusChangeListener(this);

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
        this.setFocusable(true);
        this.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
            }
        });
    }


    public void setTextViewContent(List<String> menuDate) {
        String letter = menuDate.get(0);//字母
        String number = menuDate.get(1);//数字
        int i;
        for (i = 0; i < letter.length(); i++) {
            tvList.get(i).setText(letter.substring(i, i + 1));
            tvList.get(i).setVisibility(View.VISIBLE);
        }
        tvList.get(i).setText(number);
        tvList.get(i).setVisibility(View.VISIBLE);
    }

//    //获取按键的值
//    public String getKeyBordText(int position) {
//        return tvList.get(position).getText().toString();
//    }


    @Override
    public void onFocusChange(View view, boolean b) {
        if (b)
            view.setBackgroundResource(R.mipmap.ic_keybord_select_on);
        else
            view.setBackgroundResource(R.mipmap.ic_keybord_select_off);
    }

    public void onClick(View view) {
        String text = null;
        switch (view.getId()) {
            case R.id.tv_01:
                text = tv01.getText().toString();
                break;
            case R.id.tv_02:
                text = tv02.getText().toString();
                break;
            case R.id.tv_05:
                text = tv05.getText().toString();
                break;
            case R.id.tv_04:
                text = tv04.getText().toString();
                break;
            case R.id.tv_03:
                text = tv03.getText().toString();
                break;
        }
        if (editText != null)
            editText.setText(editText.getText() + text);
        this.dismiss();
    }
}
