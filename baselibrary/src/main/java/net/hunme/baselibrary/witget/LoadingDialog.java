package net.hunme.baselibrary.witget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.hunme.baselibrary.R;

/**
 * 作者： wh
 * 时间： 2016/8/5
 * 名称：加载
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class LoadingDialog extends Dialog {

    private TextView tv_prompt;
    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_loading);
        LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.LinearLayout);
        tv_prompt= (TextView) this.findViewById(R.id.tv_prompt);
        linearLayout.getBackground().setAlpha(210);
    }

    public void setLoadingText(String text){
        tv_prompt.setText(text);
    }

    public void setLoadingTextVis(boolean isVis){
        if(tv_prompt!=null)
        tv_prompt.setVisibility(isVis? View.VISIBLE:View.GONE);
    }
}
