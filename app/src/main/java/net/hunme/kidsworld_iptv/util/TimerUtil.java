package net.hunme.kidsworld_iptv.util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * ======================================
 * 作者： Restring
 * 时间： 2016/12/8
 * 名称：计时器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 * ======================================
 */

public class TimerUtil {
    private Timer timer;
    private TimerTask timerTask;
    private int SECOND; //秒数
    private static TimerUtil timerUtil;
    private int timerIntervals; //计时间隔

    public TimerUtil getInstance(int timerIntervals) {
        this.timerIntervals = timerIntervals;
        if (timerUtil != null) {
            timerUtil = new TimerUtil();
            timer = new Timer();
        }
        return timerUtil;
    }

    private void scheduleTask() {
        if (timer != null) {
            timer.cancel();
        }
        timerTask = new TimerTask() {
            @Override
            public void run() {
                SECOND++;
            }
        };
        timer.schedule(timerTask, timerIntervals);
    }

    //开始计时
    public void startTimer() {
        scheduleTask();
    }

    //停止计时
    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    //获取计时
    public int getTimer() {
        return SECOND;
    }

}
