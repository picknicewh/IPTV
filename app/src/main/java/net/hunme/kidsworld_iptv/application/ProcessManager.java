package net.hunme.kidsworld_iptv.application;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * ======================================
 * 作者：ZLL
 * 时间：2016/12/21
 * 名称：进程管理
 * 版本说明：
 * 附加注释：
 * 主要接口：
 * ======================================
 */

public class ProcessManager {
    /**
     *  获取当前进程
     * @param cxt
     * @param pid
     * @return
     */
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
}
