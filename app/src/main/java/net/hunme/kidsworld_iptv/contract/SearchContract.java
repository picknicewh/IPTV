package net.hunme.kidsworld_iptv.contract;

import net.hunme.kidsworld_iptv.mode.CompilationsJsonVo;
import net.hunme.kidsworld_iptv.mode.FootPrintVo;
import net.hunme.kidsworld_iptv.mode.ResourceManageVo;

import java.util.List;
import java.util.Map;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/25
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public interface SearchContract {
    interface View {
        void setKeyBord(List<Map<String, String>> mapList);

        void setMoveHotSearch(List<CompilationsJsonVo> compilationsList);

        void setMusicHotSearch(List<CompilationsJsonVo> compilationsList);

        void setSearchDate(List<CompilationsJsonVo> compilationsList, List<ResourceManageVo> manageList, boolean isPagin);

        void setSearchFootPrint(List<FootPrintVo> footPrintList);

        void intentFromFootPrint(ResourceManageVo manageLis);

        void showDialog();

        void dismissDialog();
    }

    interface Presenter {
        List<Map<String, String>> getKeyBordDate();

        void getHotSearch(int pageNumber, int type);

        void getSearchDate(int type, int pageNumber, String tag);

        void getPaginSearch();

        void getSearchFootPrint(String tag);

        void getFootPrintDetails(String resourceId);
    }
}
