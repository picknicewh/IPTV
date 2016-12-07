package net.hunme.kidsworld_iptv.contract;

import net.hunme.kidsworld_iptv.mode.ResourceManageVo;

import java.util.List;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/12/5
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public interface ResDetilsContract {
    interface View {
        void showCompilationResource(List<ResourceManageVo> manageList);
    }

    interface Presenter {
        void getCompilationsAllResource(String albumId,int pageNumber);
        void getPaginCompialation();
        void synDate(String albumId,int pageNumber);
        int getPageNumber();
    }
}

