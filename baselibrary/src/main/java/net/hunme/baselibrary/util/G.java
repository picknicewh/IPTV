package net.hunme.baselibrary.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import net.hunme.baselibrary.BaseLibrary;
import net.hunme.baselibrary.R;

/**
 * ================================================
 * 作    者： ZLL
 * 时    间： 2016/7/8
 * 描    述：
 * 版    本：
 * 修订历史：
 * 主要接口：
 * ================================================
 */
public class G {
    /**
     * 调试信息
     */
    public static boolean DEBUG = true;
    /**
     * toast提示
     */
    private static Toast toast;

    /**
     * 尺寸
     */
    public static final class size {
        /**
         * 屏幕宽
         */
        public static int W = 480;
        /**
         * 屏幕高
         */
        public static int H = 800;
    }

    /**
     * 分页加载数量
     */
    public static final int PAGESIZE = 20;
    /**
     *  分页加载临界值
     */
    public static final int CRITICALCODE=8;

    public static final float ENLARGE=1.15f;

    /**
     * 初始化屏幕尺寸
     */
    public static void initDisplaySize(Activity activity) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        size.W = mDisplayMetrics.widthPixels;
        size.H = mDisplayMetrics.heightPixels;
    }

    /**
     * 提示
     *
     * @param msg 提示信息
     */
    public static void showToast(Context context, String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, size.H / 4);
        toast.show();

    }

    /**
     * 记录调试信息
     *
     * @param msg 调试信息
     */
    public static void log(Object msg) {
        if (DEBUG) {
            Log.i("TAG", String.valueOf(msg));
        }
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, double dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmteny(String values) {
        if (null == values || "".equals(values) || "null".equals(values)) {
            return true;
        }
        return false;
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static final int SOUND_KEYSTONE_KEY = 1;
    public static final int SOUND_ERROR_KEY = 0;
//    public static void playKeySound(View view, int soundKey) {
//        if (null != view) {
//            if (soundKey == SOUND_KEYSTONE_KEY) {
//                view.playSoundEffect(SoundEffectConstants.NAVIGATION_DOWN);
//            } else if (soundKey == SOUND_ERROR_KEY) {
//                view.playSoundEffect(5);
//            }
//        }
//    }

    public static void setNewTextView(TextView textView) {
        if (textView != null) {
            if (size.W == 1920) {
                textView.setTextSize(22);
            } else if (size.W == 1280) {
                textView.setTextSize(22);
            }
            textView.setTextColor(BaseLibrary.app.getResources().getColor(R.color.white));
        }
    }

    public static void setOldTextView(TextView textView) {
        if (textView != null) {
            if (size.W == 1920) {
                textView.setTextSize(20);
            } else if (size.W == 1280) {
                textView.setTextSize(20);
            }
            textView.setTextColor(BaseLibrary.app.getResources().getColor(R.color.white_50));
        }
    }

    public static String getBigImageUrl(String imageUrl) {
        int index = imageUrl.lastIndexOf("/");
        if (index != -1) {
            imageUrl = imageUrl.substring(0, index - 2) + imageUrl.substring(index, imageUrl.length());
        }
        return imageUrl;
    }
}
