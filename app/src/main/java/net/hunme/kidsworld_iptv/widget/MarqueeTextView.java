package net.hunme.kidsworld_iptv.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/22
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class MarqueeTextView extends TextView{
    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
