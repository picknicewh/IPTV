package net.hunme.kidsworld_iptv.contract;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/12/1
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public interface MovePlayContract {
    interface View {
        void setPlaySpeedHint(String speed);

        void setplaySpeed(String speed);

        void setDurationHint(String duration);

        void setDuration(String duration);

    }

    interface Presenter {
        void savePlayTheRecording(String resourceid, String broadcastPace, int type);
    }
}
