package net.hunme.kidsworld_iptv.mode;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/12/2
 * 描    述：返回资源实体类
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class CompilationsJsonVo implements Serializable {
    private String albumId;
    private String name;
    private String size;
    private String currentProgress;
    private String isEnd;
    private String imageUrl;
    private String createTime;
    private String brief;
    private String favorites;
    private String isFavorites;
    private String isUpdate;
    private String searchCount;
    private String albumName;

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(String currentProgress) {
        this.currentProgress = currentProgress;
    }

    public String getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public String getIsFavorites() {
        return isFavorites;
    }

    public void setIsFavorites(String isFavorites) {
        this.isFavorites = isFavorites;
    }

    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(String searchCount) {
        this.searchCount = searchCount;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
}

