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
public class DynamicInfoJsonVo {
    private String tsId;
    private String tsName;
    private String tsType;
    private String img;
    private String dynamicId;
    private String dynamicType;
    private List<String> imgUrl;
    private String videoUrl;
    private String text;
    private List<String> list;
    private String isAgree;
    private List<DynamidRewVo> dynamidRew;
    private String isCollect;
    private String createTime;
    private String rewCount;
    private String date;
    public String getTsId() {
        return tsId;
    }

    public void setTsId(String tsId) {
        this.tsId = tsId;
    }

    public String getTsName() {
        return tsName;
    }

    public void setTsName(String tsName) {
        this.tsName = tsName;
    }

    public String getTsType() {
        return tsType;
    }

    public void setTsType(String tsType) {
        this.tsType = tsType;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getDynamicType() {
        return dynamicType;
    }

    public void setDynamicType(String dynamicType) {
        this.dynamicType = dynamicType;
    }

    public List<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(String isAgree) {
        this.isAgree = isAgree;
    }

    public List<DynamidRewVo> getDynamidRew() {
        return dynamidRew;
    }

    public void setDynamidRew(List<DynamidRewVo> dynamidRew) {
        this.dynamidRew = dynamidRew;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRewCount() {
        return rewCount;
    }

    public void setRewCount(String rewCount) {
        this.rewCount = rewCount;
    }

    public String getData() {
        return date;
    }

    public void setData(String data) {
        this.date = data;
    }
}
