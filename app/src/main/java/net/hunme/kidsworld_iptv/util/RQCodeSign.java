package net.hunme.kidsworld_iptv.util;

import net.hunme.baselibrary.util.MD5Utils;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/14
 * 描    述：IPTV扫码登录鉴权
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class RQCodeSign {
    private final String TV_KEY = "tyr897fDSW78e978W49D49Fe894eWdFe6";
    private final String RQ_URL="/app/getQRCode";
    //当前时间毫秒数
    private String curryTime;

    //获取 hashValue
    private String getHashValue() {
        curryTime = String.valueOf(System.currentTimeMillis());
        return RQ_URL+ curryTime + TV_KEY;
    }

    //获取sing
    public String getSign() {
        String MD5HashValue= MD5Utils.encode(getHashValue());
        return curryTime + "-" +MD5HashValue;
    }
}
