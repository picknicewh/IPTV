package net.hunme.kidsworld_iptv.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/17
 * 描    述：适配 bringToFront 导致焦点错位
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class MainLinearLayout extends LinearLayout {

    private int position;
    public MainLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public MainLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MainLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.setChildrenDrawingOrderEnabled(true);
        this.setClipChildren(false);
        this.setClipToPadding(false);
    }

    @Override
    public void bringChildToFront(View child) {
//        super.bringChildToFront(child);
        position = indexOfChild(child);
        if (position != -1) {
            postInvalidate();
        }
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (position != -1) {
            if (i == childCount - 1)
                return position;

            if (i == position)
                return childCount - 1;
        }
        return i;
    }

//    WidgetTvViewBring mWidgetTvViewBring;
//
//    private void init(Context context) {
//        this.setChildrenDrawingOrderEnabled(true);
//        mWidgetTvViewBring = new WidgetTvViewBring(this);
//    }
//
//    @Override
//    public void bringChildToFront(View child) {
//        mWidgetTvViewBring.bringChildToFront(this, child);
//    }
//
//    @Override
//    protected int getChildDrawingOrder(int childCount, int i) {
//        return mWidgetTvViewBring.getChildDrawingOrder(childCount, i);
//    }
}
