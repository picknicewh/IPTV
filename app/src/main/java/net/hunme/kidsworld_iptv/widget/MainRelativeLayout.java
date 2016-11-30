package net.hunme.kidsworld_iptv.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/8
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class MainRelativeLayout extends RelativeLayout {
    public MainRelativeLayout(Context context) {
        super(context);
        init();
    }

    public MainRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MainRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        this.setFocusable(true);
        this.setClipChildren(true);
        this.setClipToPadding(false);
        this.setWillNotDraw(false);
//        this.setPadding(G.dp2px(getContext(),3),G.dp2px(getContext(),3),G.dp2px(getContext(),3),G.dp2px(getContext(),3));
//        this.setBackgroundResource(R.drawable.select_focuse_bg);
    }

//    @Override
//    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
//        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
//        if(gainFocus){
//            ImageMatrix.getIntence().enlargeView(this);
//            getRootView().requestLayout();
//            getRootView().invalidate();
//        }else {
//            ImageMatrix.getIntence().reductionView(this);
//        }
//    }
}
