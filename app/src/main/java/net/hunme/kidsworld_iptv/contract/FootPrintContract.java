package net.hunme.kidsworld_iptv.contract;

import net.hunme.kidsworld_iptv.mode.FootPrintVo;

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
public interface FootPrintContract {
    interface View {
        void showFootPrint(List<FootPrintVo> footPrintList);
    }

    interface Presenter {
        void getFootPrint(int pageNumber,int type);
        void getPaginFootPrint();
    }
}
