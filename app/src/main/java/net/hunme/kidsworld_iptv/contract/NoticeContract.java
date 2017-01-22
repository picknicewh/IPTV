package net.hunme.kidsworld_iptv.contract;

import net.hunme.kidsworld_iptv.mode.MessageJsonVo;
import net.hunme.kidsworld_iptv.util.PushDb;

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
public interface NoticeContract {
    interface View {
        void showNotice(List<MessageJsonVo> messageList);
        void goneNoticeList();
    }

    interface presenter {
        void getSchoolNotice();
        void getSystemNotice(PushDb db);
    }
}
