package net.hunme.kidsworld_iptv.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/25
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class MyListView extends ListView {
    private int lastSelectItem;

    public MyListView(Context context) {
        super(context);
        init();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    /**
     * 重写 onFocusChanged 方法 解决就近选择
     *
     * @param gainFocus
     * @param direction
     * @param previouslyFocusedRect
     */
    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        lastSelectItem = getSelectedItemPosition();//记录下最后一次获取焦点Item的位置
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            View other = getChildAt(lastSelectItem);
            int top = (other== null) ? 0 : other.getTop();
            setSelectionFromTop(lastSelectItem, top);
        }
    }
}
