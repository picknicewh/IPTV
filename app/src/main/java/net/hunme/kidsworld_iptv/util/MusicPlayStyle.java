package net.hunme.kidsworld_iptv.util;

import android.view.View;
import android.widget.TextView;

import net.hunme.kidsworld_iptv.R;

/**
 * 作者： Restring
 * 时间： 2017/1/18
 * 名称： 音乐播放风格
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */

public class MusicPlayStyle {
    private final static String[] NAME = new String[]{
            "KIKIBA儿童资源库", "刘璐璐", "方素素",
            "小天天", "李明东", "毛毛",
            "小石头", "山林",
            "小宝姐姐", "王老师",
    };

    /**
     * 设置音乐播放背景
     *
     * @param view      界面背景
     * @param imgCircle 播放专辑背景
     * @param tvUpload  资源上传版权背景
     */
    public static void setMusicPlayStyle(View view,  View imgCircle, TextView tvUpload, String uploadName) {
        if (NAME[0].equals(uploadName) || NAME[1].equals(uploadName) || NAME[2].equals(uploadName)) {
            view.setBackgroundResource(R.mipmap.ic_music_one_bg);
            imgCircle.setBackgroundResource(R.mipmap.ic_circle_one_bg);
            tvUpload.setBackgroundResource(R.mipmap.ic_upload_one_bg);
        } else if (NAME[3].equals(uploadName) || NAME[4].equals(uploadName) || NAME[5].equals(uploadName)) {
            view.setBackgroundResource(R.mipmap.ic_music_tow_bg);
            imgCircle.setBackgroundResource(R.mipmap.ic_circle_tow_bg);
            tvUpload.setBackgroundResource(R.mipmap.ic_upload_tow_bg);
        } else if (NAME[6].equals(uploadName) || NAME[7].equals(uploadName)) {
            view.setBackgroundResource(R.mipmap.ic_music_three_bg);
            imgCircle.setBackgroundResource(R.mipmap.ic_circle_three_bg);
            tvUpload.setBackgroundResource(R.mipmap.ic_upload_three_bg);
        } else {
            view.setBackgroundResource(R.mipmap.ic_music_four_bg);
            imgCircle.setBackgroundResource(R.mipmap.ic_circle_four_bg);
            tvUpload.setBackgroundResource(R.mipmap.ic_upload_four_bg);
        }

//        } else {
//            view.setBackgroundResource(R.mipmap.ic_music_one_bg);
//            imgCircle.setImageResource(R.mipmap.ic_circle_one_bg);
//            tvUpload.setBackgroundResource(R.mipmap.ic_upload_one_bg);
//        }

//        switch (type) {
//            case 0:
//                view.setBackgroundResource(R.mipmap.ic_music_one_bg);
//                imgCircle.setImageResource(R.mipmap.ic_circle_one_bg);
//                tvUpload.setBackgroundResource(R.mipmap.ic_upload_one_bg);
//                break;
//            case 1:
//                view.setBackgroundResource(R.mipmap.ic_music_tow_bg);
//                imgCircle.setImageResource(R.mipmap.ic_circle_tow_bg);
//                tvUpload.setBackgroundResource(R.mipmap.ic_upload_tow_bg);
//                break;
//            case 2:
//                view.setBackgroundResource(R.mipmap.ic_music_three_bg);
//                imgCircle.setImageResource(R.mipmap.ic_circle_three_bg);
//                tvUpload.setBackgroundResource(R.mipmap.ic_upload_three_bg);
//                break;
//            case 3:
//                view.setBackgroundResource(R.mipmap.ic_music_four_bg);
//                imgCircle.setImageResource(R.mipmap.ic_circle_four_bg);
//                tvUpload.setBackgroundResource(R.mipmap.ic_upload_four_bg);
//                break;
//        }
    }
}
