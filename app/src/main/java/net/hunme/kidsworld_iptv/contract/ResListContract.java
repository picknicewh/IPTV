package net.hunme.kidsworld_iptv.contract;

import net.hunme.kidsworld_iptv.mode.CompilationsJsonVo;
import net.hunme.kidsworld_iptv.mode.ThemeManageVo;

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
public interface ResListContract {
    interface View {
        void showCollection(List<CompilationsJsonVo> resFavoritesList, boolean isPagin);

        void showThemeList(List<ThemeManageVo> themeManageList);
    }

    interface Presenter {
        void getResReleases(int pageNumber, String type, String url,boolean isThemeByAlbum);

        void getPaginReleases();

        void getThemeList(int pageNumber, String type);

        void getPaginTheme();
    }
}
