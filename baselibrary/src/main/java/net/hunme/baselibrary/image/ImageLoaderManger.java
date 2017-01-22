package net.hunme.baselibrary.image;

import android.content.Context;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.hunme.baselibrary.R;
import net.hunme.baselibrary.util.G;

/**
 * 作者： Restring
 * 时间： 2017/1/17
 * 名称：Glide 图片加载实现类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */

public class ImageLoaderManger implements ImageListener {
    private final int DEFAULT_PROGRESSID = -1;
    private final int DEFAULT_ERRORID = -1;
    private final int DEFAULT_TYPE = 0;
    public final static int IMAGE_R0UND = 1;
    public final static int IMAGE_GIFE = 1;
    private static ImageLoaderManger loaderManger;

    public static ImageLoaderManger getManger() {
        if (loaderManger == null) {
            loaderManger = new ImageLoaderManger();
        }
        return loaderManger;
    }

    /**
     * Glid 图片加载
     *
     * @param imageView  图片
     * @param imgUrl     图片地址
     * @param progressId 加载中图片
     * @param errorId    加载错误图片
     * @param imageType  加载类型
     */
    @Override
    public void display(ImageView imageView, String imgUrl, int progressId, int errorId, int imageType) {
        DrawableTypeRequest<String> imageLoad = Glide.with(imageView.getContext()).load(imgUrl);
        if (DEFAULT_PROGRESSID == progressId)
            imageLoad.placeholder(imageView.getDrawable());
        else
            imageLoad.placeholder(progressId).centerCrop();

        if (DEFAULT_ERRORID == errorId)
            imageLoad.error(R.mipmap.ic_img_error);
        else
            imageLoad.error(errorId);

        if (imageType == IMAGE_R0UND)
            imageLoad.transform(new GlideRoundTransform(imageView.getContext(), imageType));
        if (imageType == IMAGE_GIFE)
            imageLoad.asGif();

        imageLoad.diskCacheStrategy(DiskCacheStrategy.SOURCE)  //设置图片缓存模式
                .crossFade() //设置淡入淡出动画
//                .centerCrop() //设置图片居中显示
                .into(imageView);
        G.log(this,"==== Glide imgURL====="+imgUrl);
    }

    @Override
    public void display(ImageView imageView, String imgUrl, int progressId, int errorId) {
        display(imageView, imgUrl, progressId, errorId, DEFAULT_TYPE);
    }

    @Override
    public void display(ImageView imageView, String imgUrl, int progressId) {
        display(imageView, imgUrl, progressId, DEFAULT_ERRORID, DEFAULT_TYPE);
    }

    @Override
    public void display(ImageView imageView, String imgUrl) {
        display(imageView, imgUrl, DEFAULT_PROGRESSID, DEFAULT_ERRORID, DEFAULT_TYPE);
    }

    @Override
    public void clearMemory(Context context) {
        if (Looper.myLooper() == Looper.getMainLooper())
            Glide.get(context.getApplicationContext()).clearMemory();
        else
            Glide.get(context.getApplicationContext()).clearDiskCache();
    }
}
