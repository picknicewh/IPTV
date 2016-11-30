package net.hunme.kidsworld_iptv.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.open.androidtvwidget.view.GridViewTV;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/19
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class NoScrollGridView extends GridViewTV {

    public NoScrollGridView(Context context) {
        super(context);
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpce=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpce);
    }
}
