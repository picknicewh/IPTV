package net.hunme.kidsworld_iptv.contract;

import net.hunme.kidsworld_iptv.mode.CompilationsJsonVo;
import net.hunme.kidsworld_iptv.mode.ThemeManageVo;

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

        void setSearchDate(List<CompilationsJsonVo> compilationsList, List<ThemeManageVo> manageList);
    }

    interface Presenter {
        List<Map<String, String>> getKeyBordDate();

        void getHotSearch(int pageNumber,int type);

        void getSearchDate();

        void getPaginSearch();
    }
}
