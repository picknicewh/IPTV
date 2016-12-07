package net.hunme.kidsworld_iptv.mode;

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
public class CookBookVo {
    private String date;
    private String day;
    private List<DishesVo> dishesList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<DishesVo> getDishesList() {
        return dishesList;
    }

    public void setDishesList(List<DishesVo> dishesList) {
        this.dishesList = dishesList;
    }
}
