package net.hunme.baselibrary.image;

import android.content.Context;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.hunme.baselibrary.R;

/**
 * ======================================
 * 作者：ZLL
 * 时间：2016/12/30
 * 名称：图片工具类
 * 附加注释：
 * ======================================
 */

public class ImageLoaderUtil {
    private static ImageLoaderUtil loaderUtil;

    public static ImageLoaderUtil getIntences() {
        if (loaderUtil == null) {
            loaderUtil = new ImageLoaderUtil();
        }
        return loaderUtil;
    }

    /**
     * 缓存模式;
     * DiskCacheStrategy.NONE 什么都不缓存
     * DiskCacheStrategy.SOURCE 仅仅只缓存原来的全分辨率的图像
     * DiskCacheStrategy.RESULT 仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
     * DiskCacheStrategy.ALL 缓存所有版本的图像（默认行为）
     * <p>
     * override(600, 200) 重设大小
     * centerCrop 即缩放图像让它填充到 ImageView 界限内并且裁剪额外的部分。ImageView 可能会完全填充，但图像可能不会完整显示。
     * fitCenter 即缩放图像让图像都测量出来等于或小于 ImageView 的边界范围。该图像将会完全显示，但可能不会填满整个 ImageView。
     *
     * @param url
     * @param imageView
     */
    public static void loadImage(String url, ImageView imageView) {
        Glide.with(imageView.getContext()).load(url)
                .crossFade() // 淡入淡出动画
//                .dontAnimate() //取消加载动画
                .placeholder(imageView.getDrawable()) //正在加载显示图片
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)  //设置图片缓存模式
                .error(R.mipmap.ic_img_error) //加载失败显示图片
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param url
     * @param imageView
     * @param round
     */
    public static void loadRoundImage(String url, ImageView imageView, int round) {
        Glide.with(imageView.getContext()).load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(imageView.getDrawable())
                .error(R.mipmap.ic_img_error)
                .crossFade()
                .centerCrop()
//                .bitmapTransform(new RoundedCornersTransformation(imageView.getContext(), round, 0,
//                        RoundedCornersTransformation.CornerType.ALL)).crossFade(1000)
                .transform(new GlideRoundTransform(imageView.getContext(),round))
                .into(imageView);
    }

    /**
     * 清除缓存
     *
     * @param context
     */
    public static void clearMemory(final Context context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Glide.get(context.getApplicationContext()).clearMemory();
        } else {
            Glide.get(context.getApplicationContext()).clearDiskCache();
        }
    }

    /**
     * 设置gif动画
     *
     * @param url
     * @param imageView
     */
    public static void setGifImage(String url, ImageView imageView) {
        Glide.with(imageView.getContext()).load(url).asGif()
//                .crossFade() // 淡入淡出动画
//                .dontAnimate() //取消加载动画
                .placeholder(imageView.getDrawable()) //正在加载显示图片
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)  //设置图片缓存模式
                .error(R.mipmap.ic_img_error) //加载失败显示图片
                .into(imageView);
    }


}
