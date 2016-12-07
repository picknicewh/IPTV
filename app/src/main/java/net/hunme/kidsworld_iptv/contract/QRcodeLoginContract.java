package net.hunme.kidsworld_iptv.contract;

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
    }

    interface Presenter {
        void getQRCode(String pushId, String sign, String requestSource);
    }
}
