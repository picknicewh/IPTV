package net.hunme.kidsworld_iptv.util;

import net.hunme.baselibrary.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/12/6
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class DateWeekListUtil {
    private static List<String> list;
    private static String s1, s2, s3, s4, s5;

    public static List<String> getWeekList() {
        list=new ArrayList<>();
        String week = DateUtil.getWeekOfDate(new Date());
        if (week.equals("星期一")) {
            s1 = DateUtil.getNDaysLaterDate(0);
            s2 = DateUtil.getNDaysLaterDate(1);
            s3 = DateUtil.getNDaysLaterDate(2);
            s4 = DateUtil.getNDaysLaterDate(3);
            s5 = DateUtil.getNDaysLaterDate(4);
        } else if (week.equals("星期二")) {
            s1 = DateUtil.getNDaysLaterDate(-1);
            s2 = DateUtil.getNDaysLaterDate(0);
            s3 = DateUtil.getNDaysLaterDate(1);
            s4 = DateUtil.getNDaysLaterDate(2);
            s5 = DateUtil.getNDaysLaterDate(3);
        } else if (week.equals("星期三")) {
            s1 = DateUtil.getNDaysLaterDate(-2);
            s2 = DateUtil.getNDaysLaterDate(-1);
            s3 = DateUtil.getNDaysLaterDate(0);
            s4 = DateUtil.getNDaysLaterDate(1);
            s5 = DateUtil.getNDaysLaterDate(2);
        } else if (week.equals("星期四")) {
            s1 = DateUtil.getNDaysLaterDate(-3);
            s2 = DateUtil.getNDaysLaterDate(-2);
            s3 = DateUtil.getNDaysLaterDate(-1);
            s4 = DateUtil.getNDaysLaterDate(0);
            s5 = DateUtil.getNDaysLaterDate(1);
        } else if (week.equals("星期五")) {
            s1 = DateUtil.getNDaysLaterDate(-4);
            s2 = DateUtil.getNDaysLaterDate(-3);
            s3 = DateUtil.getNDaysLaterDate(-2);
            s4 = DateUtil.getNDaysLaterDate(-1);
            s5 = DateUtil.getNDaysLaterDate(0);
        } else if (week.equals("星期六")) {
            s1 = DateUtil.getNDaysLaterDate(-5);
            s2 = DateUtil.getNDaysLaterDate(-4);
            s3 = DateUtil.getNDaysLaterDate(-3);
            s4 = DateUtil.getNDaysLaterDate(-2);
            s5 = DateUtil.getNDaysLaterDate(-1);
        } else if (week.equals("星期日")) {
            s1 = DateUtil.getNDaysLaterDate(-6);
            s2 = DateUtil.getNDaysLaterDate(-5);
            s3 = DateUtil.getNDaysLaterDate(-4);
            s4 = DateUtil.getNDaysLaterDate(-3);
            s5 = DateUtil.getNDaysLaterDate(-2);
        }
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        list.add(s5);
        return list;
    }
}
