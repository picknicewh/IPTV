package net.hunme.baselibrary.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * 作者： Restring
 * 时间： 2017/1/17
 * 名称： 图片接口类  提供方法
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */

public interface ImageListener {
    void display(ImageView imageView, String imgUrl, int progressId, int errorId, int imageType);

    void display(ImageView imageView, String imgUrl, int progressId, int errorId);

    void display(ImageView imageView, String imgUrl, int progressId);

    void display(ImageView imageView, String imgUrl);

    void clearMemory(Context context);
}

