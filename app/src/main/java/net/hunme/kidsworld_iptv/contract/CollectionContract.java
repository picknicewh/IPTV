package net.hunme.kidsworld_iptv.contract;

import net.hunme.kidsworld_iptv.mode.CompilationsJsonVo;

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
public interface CollectionContract {
    interface View{
        void showCollection(List<CompilationsJsonVo> resFavoritesList,boolean isPagin);
    }

    interface Presenter{
        void getCollectionRes(int pageNumber,int type);
        void getPaginCollectionRes();
    }
}
