package net.hunme.kidsworld_iptv.mode;

import java.util.List;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/26
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class RecommendVo {
    private String name;
    private String type;
    private List<ResourceContentVo> contentJsonList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ResourceContentVo> getContentJsonList() {
        return contentJsonList;
    }

    public void setContentJsonList(List<ResourceContentVo> contentJsonList) {
        this.contentJsonList = contentJsonList;
    }
}
