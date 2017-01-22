package net.hunme.kidsworld_iptv.contract;

import net.hunme.kidsworld_iptv.mode.DynamicInfoJsonVo;
import net.hunme.kidsworld_iptv.mode.DynamicJsonVo;

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
public interface DynamicContract {
    interface View {
        void setHeadMessage(List<DynamicJsonVo> dynamicHeadList);

        void setDynamicInfo(List<DynamicInfoJsonVo> dynamicInfoList, boolean isChange);

        void goneDynamicList();
    }

    interface Presenter {
        void getHeadMessage();

        void getDynamicInfo(String groupId, String groupType, int pageNumber, String type, String dynamicId);

        void getPaginDynamicInfo(String type, String dynamicId);

        void refreshDate();
    }
}
