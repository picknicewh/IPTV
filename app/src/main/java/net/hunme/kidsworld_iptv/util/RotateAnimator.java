package net.hunme.kidsworld_iptv.util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * ======================================
 * 作者：ZLL
 * 时间：2017/1/4
 * 名称：旋转动画
 * 附加注释：
 * ======================================
 */

public class RotateAnimator {
    private static RotateAnimator animator;
    private ObjectAnimator mRotateAnimator;
    private final int DURATION = 14400;
    public RotateAnimator(View view) {
        mRotateAnimator = getRotateAnimator(view);
    }

    public static RotateAnimator getInstance(View view) {
            animator = new RotateAnimator(view);
        return animator;
    }

    private ObjectAnimator getRotateAnimator(View view) {
        if (mRotateAnimator == null) {
            mRotateAnimator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
            mRotateAnimator.setDuration(DURATION);
            mRotateAnimator.setInterpolator(new LinearInterpolator());
            mRotateAnimator.setRepeatMode(ValueAnimator.RESTART);
            mRotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
        }
        return mRotateAnimator;
    }

    public void startRotateAnimation() {
        mRotateAnimator.cancel();
        mRotateAnimator.start();
    }

    public void cancelRotateAnimation() {
        mRotateAnimator.cancel();
    }


    public void pauseRotateAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mRotateAnimator.pause();
        }
    }

    public void resumeRotateAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mRotateAnimator.resume();
        }
    }
}
