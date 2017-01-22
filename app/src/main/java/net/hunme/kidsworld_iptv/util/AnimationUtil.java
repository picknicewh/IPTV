package net.hunme.kidsworld_iptv.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * ======================================
 * 作者：ZLL
 * 时间：2017/1/5
 * 名称：动画类
 * 附加注释：处理各种动画
 * ======================================
 */

public class AnimationUtil {
    /**
     * 水平位移动画
     *
     * @param view     移动的View
     * @param forX     从该View的X个身位开始
     * @param toX      到该View的X个身位
     * @param Duration 动画持续时间
     */
    public static void tranLevelAnimation(View view, float forX, float toX, int Duration) {
        final TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, forX,
                Animation.RELATIVE_TO_SELF, toX,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f);
        animation.setDuration(Duration);//设置动画持续时间
        animation.setRepeatCount(Integer.MAX_VALUE);//设置重复次数（无限执行）
        animation.setRepeatMode(Animation.RESTART);//设置从头开始执行
        view.setAnimation(animation);
        animation.start();
    }
}
