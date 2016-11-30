package net.hunme.kidsworld_iptv.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import net.hunme.baselibrary.image.ImageMatrix;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/17
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class MainImageView extends ImageView {
    public MainImageView(Context context) {
        super(context);
        init();
    }

    public MainImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MainImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        this.setFocusable(true); //设置可以获取焦点
//        this.setBackgroundResource(R.drawable.item_shadow);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if(gainFocus){
            ImageMatrix.getIntence().enlargeView(this);
            getRootView().requestLayout();
            getRootView().invalidate();
        }else {
            ImageMatrix.getIntence().reductionView(this);
        }
    }
}
