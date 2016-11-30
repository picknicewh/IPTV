package net.hunme.kidsworld_iptv.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import net.hunme.baselibrary.image.ImageMatrix;
import net.hunme.kidsworld_iptv.R;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/17
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class MainTextView extends TextView {
    public MainTextView(Context context) {
        super(context);
        init();
    }

    public MainTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MainTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        this.setFocusable(true);
        this.setBackgroundResource(R.drawable.select_focuse_bg);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if(focused){
            ImageMatrix.getIntence().enlargeView(this);
            getRootView().requestLayout();
            getRootView().invalidate();
        }else{
            ImageMatrix.getIntence().reductionView(this);
        }
    }
}
