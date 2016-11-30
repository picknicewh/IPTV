package net.hunme.kidsworld_iptv.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
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
    private void init(){
        this.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }
}
