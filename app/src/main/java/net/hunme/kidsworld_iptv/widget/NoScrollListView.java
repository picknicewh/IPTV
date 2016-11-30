package net.hunme.kidsworld_iptv.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/20
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class NoScrollListView extends ListView {
    public NoScrollListView(Context context) {
        super(context);
    }

    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpce=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpce);
    }
}
