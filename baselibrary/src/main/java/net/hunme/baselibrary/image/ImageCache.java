package net.hunme.baselibrary.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import net.hunme.baselibrary.R;
import net.hunme.baselibrary.util.G;

/**
 * ================================================
 * 作    者： ZLL
 * 时    间： 2016/7/8
 * 描    述：图片工具类
 * 版    本：
 * 修订历史：
 * 主要接口：
 * ================================================
 */
public class ImageCache {
    private static DisplayImageOptions options;
    private static ImageLoader imageLoader;

    /**
     * 实例化设置获取图片的时候一些参数
     *
     * @return
     */
    private static ImageLoader ImageCache() {
        if (imageLoader == null) {
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.ic_img_error) // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.ic_img_error) // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.mipmap.ic_img_error) // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                    .considerExifParams(true) // 启用EXIF和JPEG图像格式
                    // .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                    .bitmapConfig(Bitmap.Config.ARGB_8888).build(); // 构建完成
            imageLoader = ImageLoader.getInstance();
        }
        return imageLoader;
    }

    /**
     * 显示普通图片
     *
     * @param uri       图片地址
     * @param imageView
     */
    public static void imageLoader(String uri, ImageView imageView) {
        if (imageView == null) {
            return;
        }
        ImageCache().displayImage(transFromImagUrl(uri), imageView, options);
    }

    /**
     * 获取图片下载进度监听
     *
     * @param uri
     * @param imageView
     * @param listener  图片下载进度监听
     */
    public static void imageLoader(String uri, ImageView imageView, SimpleImageLoadingListener listener) {
        ImageCache().displayImage(transFromImagUrl(uri), imageView, listener);
    }

    /**
     * 获取一个uri的bitmap对象
     *
     * @param uri
     * @return
     */
    public static Bitmap getBitmap(String uri) {
        return ImageCache().loadImageSync(transFromImagUrl(uri));
    }


    private static String transFromImagUrl(String imgUrl) {
        if (G.isEmteny(imgUrl)) {
            return "";
        }
        imgUrl = G.changUrlFor8082(imgUrl);
//        if (imgUrl.contentEquals("\\")) {
        imgUrl = imgUrl.replace("\\", "/");
//        }
        return imgUrl;
    }
}
