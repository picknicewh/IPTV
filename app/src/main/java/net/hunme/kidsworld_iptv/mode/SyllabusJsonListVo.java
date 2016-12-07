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
public class SyllabusJsonListVo {
    private String title;
    private List<String> imgs;
    private String creationTime;
    private String syllabusId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getSyllabusId() {
        return syllabusId;
    }

    public void setSyllabusId(String syllabusId) {
        this.syllabusId = syllabusId;
    }
}
