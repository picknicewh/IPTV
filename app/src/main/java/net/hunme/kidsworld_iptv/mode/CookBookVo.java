package net.hunme.kidsworld_iptv.mode;

import java.util.List;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/25
 * 描    述：食谱实体类
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class CookBookVo {
    private String date;
    private String day;
    private List<Dishes> dishesList;

    public class Dishes {
        private String dinnerTime;
        private List<String> cookUrl;
        private String cookName;

        public String getDinnerTime() {
            return dinnerTime;
        }

        public void setDinnerTime(String dinnerTime) {
            this.dinnerTime = dinnerTime;
        }

        public List<String> getCookUrl() {
            return cookUrl;
        }

        public void setCookUrl(List<String> cookUrl) {
            this.cookUrl = cookUrl;
        }

        public String getCookName() {
            return cookName;
        }

        public void setCookName(String cookName) {
            this.cookName = cookName;
        }
    }

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

    public List<Dishes> getDishesList() {
        return dishesList;
    }

    public void setDishesList(List<Dishes> dishesList) {
        this.dishesList = dishesList;
    }
}
