package net.hunme.kidsworld_iptv.contract;

import net.hunme.kidsworld_iptv.mode.CompilationsJsonVo;

import java.util.List;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/30
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public interface QRcodeLoginContract {
    interface View {
        void setQRcodeImg(String imgUrl);

        void setUserHeadImg(String headImg);

        void closeLogin();

        void submitPlayRecord();
    }

    interface Presenter {
        void getQRCode(String pushId, String sign, String requestSource);

        void submitPlayRecord(List<CompilationsJsonVo> compilationsList);
    }
}
