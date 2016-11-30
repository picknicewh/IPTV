package net.hunme.kidsworld_iptv.contract;

import net.hunme.kidsworld_iptv.mode.ResourceContentVo;

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
public interface CollectionContract {
    interface View {
        void setKeyBord(List<Map<String, String>> mapList);

        void setColletionContent(List<ResourceContentVo> contentList);
    }

    interface Presenter {
        List<Map<String, String>> getKeyBordDate();

        void getCollectionDate(String type, String name, String pageSize, String pageNumber);

    }
}
