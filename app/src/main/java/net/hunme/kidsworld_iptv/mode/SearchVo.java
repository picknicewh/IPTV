package net.hunme.kidsworld_iptv.mode;

import java.util.List;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/12/7
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class SearchVo {
    private List<ResourceManageVo> resourceManageList;
    private List<CompilationsJsonVo> albumManageList;

    public List<ResourceManageVo> getResourceManageList() {
        return resourceManageList;
    }

    public void setResourceManageList(List<ResourceManageVo> resourceManageList) {
        this.resourceManageList = resourceManageList;
    }

    public List<CompilationsJsonVo> getAlbumManageList() {
        return albumManageList;
    }

    public void setAlbumManageList(List<CompilationsJsonVo> albumManageList) {
        this.albumManageList = albumManageList;
    }
}
