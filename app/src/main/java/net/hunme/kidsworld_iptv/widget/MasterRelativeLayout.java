package net.hunme.kidsworld_iptv.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.open.androidtvwidget.view.MainUpView;

import net.hunme.baselibrary.util.G;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/16
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class MasterRelativeLayout extends RelativeLayout implements View.OnFocusChangeListener {
    private Activity activity;
    private MainUpView upView;
    public MasterRelativeLayout(Context context) {
        super(context);
        init(context);
    }

    public MasterRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MasterRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        activity= (Activity) context;
        for(int i=0;i<getChildCount();i++){
            getChildAt(i).setOnFocusChangeListener(this);
        }
    }

    public void setUpView(MainUpView upView){
        this.upView=upView;
    }
    @Override
    public void onFocusChange(View view, boolean b) {
        if(upView==null){
            return;
        }
        if(b){
            upView.setFocusView(view, G.ENLARGE);
        }else {
            upView.setUnFocusView(view);
        }
    }

}
