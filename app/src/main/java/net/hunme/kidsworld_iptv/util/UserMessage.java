package net.hunme.kidsworld_iptv.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/21
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class UserMessage {
    private static UserMessage um;
    private static SharedPreferences spf;
    private static SharedPreferences.Editor editor;

    public static UserMessage getInstance(Context context) {
        if (um == null) {
            um = new UserMessage();
            spf = context.getSharedPreferences("IPTV", Context.MODE_PRIVATE);
            editor = spf.edit();
        }
        return um;
    }

    public void setUserTsId(String tsId) {
        editor.putString("tsId", tsId);
        editor.commit();
    }
//c14dceea93e244b6be7ceed3d65bf037
    //正式服tsId: a83837fe72274191b3ee9e629debbd89    测试tsId: 9fc62f95a4014bb7bfcfaee7578787fc  c9441620ad664bf691b5d0d007488b2b
    public String getUserTsId() {
        return spf.getString("tsId", "c14dceea93e244b6be7ceed3d65bf037");
    }

    public void setUserImgUrl(String imgUrl) {
        editor.putString("imgUrl", imgUrl);
        editor.commit();
    }

    public String getUserImagUrl() {
        return spf.getString("imgUrl", "");
    }

    public void setPushId(String pushId) {
        editor.putString("pushId", pushId);
        editor.commit();
    }

    public String getPushId() {
        return spf.getString("pushId", "");
    }

    ;
}
