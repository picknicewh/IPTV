package net.hunme.kidsworld_iptv.util;

import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.application.IPTVApp;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 作者： wh
 * 时间： 2016-6-27
 * 名称： 极光推送的基本设置
 * 版本说明：代码规范整改
 * 附加注释：setAliasAndTags方法中设置自己想要自定义的值
 * 主要接口：暂无
 */
public class JPushUtil implements TagAliasCallback {
    /**
     * 设置将获取的tag字符串，转换成 Set<String>,以逗号分隔
     */
    private static Set<String> setTag(String tag) {
        // 检查 tag 的有效性
        if (JPushUtilHelp.isEmpty(tag)) {
            G.log("tag不能为空！");
            return null;
        }
        // ","隔开的多个 转换成 Set
        String[] sArray = tag.split(",");
        Set<String> tagSet = new LinkedHashSet<String>();
        for (String sTagItme : sArray) {
            if (!JPushUtilHelp.isValidTagAndAlias(sTagItme)) {
                G.log("设置tag格式不对！");
                return null;
            }
            tagSet.add(sTagItme);
        }
        return tagSet;
    }

    /**
     * 设置将获取的alias字符串,进行过滤判断
     */
    private static String setAlias(String alias) {
        if (JPushUtilHelp.isEmpty(alias)) {
            G.log("alias不能为空！");
            return null;
        }
        if (!JPushUtilHelp.isValidTagAndAlias(alias)) {
            G.log("设置alias格式不对！");
            return null;
        } else {
            G.log("alias格式通过");
        }
        return alias;
    }

    public void setJpushTag(String tag) {
        JPushInterface.setTags(IPTVApp.getInstance().getApplicationContext(), setTag(tag), this);
    }

    public void setJpushAlias(String alias) {
        JPushInterface.setAlias(IPTVApp.getInstance().getApplicationContext(), setAlias(alias), this);
    }

    /**
     * 设置别名,标签
     */
    public void setAliasAndTags(String alias, String tags) {
        JPushInterface.setAliasAndTags(IPTVApp.getInstance().getApplicationContext(), setAlias(alias), setTag(tags), this);
    }

    @Override
    public void gotResult(int i, String s, Set<String> set) {
        switch (i) {
            case 0:
                G.log("设置成功！");
                //将别名保存起了作为扫码时候的pushid
                G.log("==========保存的pushId======"+IPTVApp.pushId);
                IPTVApp.um.setPushId(IPTVApp.pushId);
                break;
            case 6002:
                G.log("设置超时！");
                IPTVApp.getInstance().setJpushAilas();
                break;
        }
    }

//    /**
//     * 设置推送日期,周一至周日的每天0-24小时
//     */
//    public void setPushTime() {
//        Set<Integer> days = new HashSet<>();
//        for (int i = 0; i < 7; i++) {
//            days.add(i);
//        }
//        JPushInterface.setPushTime(IPTVApp.getInstance().getApplicationContext(), days, 0, 24);
//        //设置静音时间段晚上10点半到早上八点半
//        JPushInterface.setSilenceTime(IPTVApp.getInstance().getApplicationContext(), 22, 30, 8, 30);
//    }
}
