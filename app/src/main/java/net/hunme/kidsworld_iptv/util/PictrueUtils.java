package net.hunme.kidsworld_iptv.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import net.hunme.baselibrary.image.ImageCache;
import net.hunme.baselibrary.image.ImageLoaderManger;
import net.hunme.baselibrary.util.G;

import java.util.List;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/9/13
 * 描    述：多图显示不同位置工具类
 * 版    本：
 * 修订历史：
 * 主要接口：
 * ================================================
 */
public class PictrueUtils implements View.OnClickListener {

    private int MAXIMAGESIZE = 0;
    private int IMAGESIZE = 0;
    private final int VIEW_SPACE = 10;
    private static PictrueUtils utils;

    public static PictrueUtils getUtils() {
        if (utils == null) {
            utils = new PictrueUtils();
        }
        return utils;
    }

    public void setPictrueLoad(Context context, List<String> imageUrl, RelativeLayout rlParams) {
        MAXIMAGESIZE = G.dp2px(context, 360);
        IMAGESIZE = G.dp2px(context, 180);
        if (rlParams != null) {
            rlParams.removeAllViews();
        } else {
            return;
        }
        if (imageUrl.size() == 1) {
            //单张图片
            ImageView imageView = new ImageView(context);
//            ImageLoaderManger.getManger().display(imageView,imageUrl.get(0));
            //这里需要强制设置其ImageView的宽高  如果不设置会显示很小很小的图片
            //后期需要修复
            Glide.with(context).load(imageUrl.get(0))
                    .override(800, 800)
                    .into(imageView);
            imageView.setPadding(VIEW_SPACE, VIEW_SPACE, VIEW_SPACE, VIEW_SPACE);
            imageView.setOnClickListener(this);
            imageView.setTag(0);
            rlParams.addView(imageView);
        } else if (imageUrl.size() == 3 || imageUrl.size() == 6) {
            //3张或者6张图片
            for (int i = 0; i < imageUrl.size(); i++) {
                ImageView imageView = initImageView(context, imageUrl.get(i), i);
                RelativeLayout.LayoutParams pl;
                if (i == 0) {
                    pl = new RelativeLayout.LayoutParams(MAXIMAGESIZE, MAXIMAGESIZE);
                    pl.setMargins(VIEW_SPACE, VIEW_SPACE, VIEW_SPACE, VIEW_SPACE);
                } else if (i <= 2) {
                    pl = new RelativeLayout.LayoutParams(IMAGESIZE, IMAGESIZE);
                    if (i == 2) {
                        pl.addRule(RelativeLayout.BELOW, i + 100 - 1);
                        pl.addRule(RelativeLayout.ALIGN_LEFT, i + 100 - 1);
                        pl.addRule(RelativeLayout.ALIGN_BOTTOM, i + 100 - 2);
                        pl.setMargins(0, VIEW_SPACE, 0, 0);
                    } else if (i == 1) {
                        pl.addRule(RelativeLayout.RIGHT_OF, i + 100 - 1);
                        pl.addRule(RelativeLayout.ALIGN_TOP, i + 100 - 1);
                    }
                } else {
                    pl = new RelativeLayout.LayoutParams(IMAGESIZE, IMAGESIZE);
                    if (i == 3) {
                        pl.addRule(RelativeLayout.BELOW, i + 100 - 3);
                        pl.addRule(RelativeLayout.ALIGN_LEFT, i + 100 - 3);
                        pl.setMargins(0, 0, 0, VIEW_SPACE);
                    } else {
                        pl.setMargins(VIEW_SPACE, 0, VIEW_SPACE, 0);
                        pl.addRule(RelativeLayout.RIGHT_OF, i + 100 - 1);
                        pl.addRule(RelativeLayout.ALIGN_TOP, i + 100 - 1);
                    }
                }
                rlParams.addView(imageView, pl);
            }
        } else if (imageUrl.size() == 4) {
            //4张图片
            for (int i = 0; i < imageUrl.size(); i++) {
                ImageView imageView = initImageView(context, imageUrl.get(i), i);
                RelativeLayout.LayoutParams pl = new RelativeLayout.LayoutParams(IMAGESIZE, IMAGESIZE);
                if (i % 2 == 0) {
                    pl.setMargins(VIEW_SPACE, VIEW_SPACE, VIEW_SPACE, VIEW_SPACE);
                    pl.addRule(RelativeLayout.BELOW, i + 100 - 1);
                } else {
                    pl.setMargins(0, 0, VIEW_SPACE, 0);
                    pl.addRule(RelativeLayout.RIGHT_OF, i + 100 - 1);
                    pl.addRule(RelativeLayout.ALIGN_TOP, i + 100 - 1);
                }
                rlParams.addView(imageView, pl);
            }
        } else if (imageUrl.size() == 2) {
            //2张图片
            for (int i = 0; i < imageUrl.size(); i++) {
                ImageView imageView = initImageView(context, imageUrl.get(i), i);
                RelativeLayout.LayoutParams pl = new RelativeLayout.LayoutParams(G.dp2px(context, 250), G.dp2px(context, 250));
                pl.setMargins(0, 0, VIEW_SPACE, 0);
                pl.addRule(RelativeLayout.RIGHT_OF, i + 100 - 1);
                pl.addRule(RelativeLayout.ALIGN_TOP, i + 100 - 1);
                rlParams.addView(imageView, pl);
            }
        } else {
            //去除上面的情况
            for (int i = 0; i < imageUrl.size(); i++) {
                ImageView imageView = initImageView(context, imageUrl.get(i), i);
                RelativeLayout.LayoutParams pl = new RelativeLayout.LayoutParams(IMAGESIZE, IMAGESIZE);
                if (i % 3 == 0) {
                    pl.setMargins(VIEW_SPACE, VIEW_SPACE, VIEW_SPACE, VIEW_SPACE);
                    pl.addRule(RelativeLayout.BELOW, i + 100 - 1);
                } else {
                    pl.setMargins(0, 0, VIEW_SPACE, 0);
                    pl.addRule(RelativeLayout.RIGHT_OF, i + 100 - 1);
                    pl.addRule(RelativeLayout.ALIGN_TOP, i + 100 - 1);
                }
                rlParams.addView(imageView, pl);
            }
        }
    }

    /**
     * @param context
     * @param imgUrl
     * @param position
     * @return
     */
    private ImageView initImageView(Context context, String imgUrl, int position) {
        ImageView imageView = new ImageView(context);
        ImageLoaderManger.getManger().display(imageView, imgUrl);
        imageView.setId(position + 100);
        imageView.setOnClickListener(this);
        imageView.setTag(position);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }


    /**
     * 设置图片布局参数,根据不同长宽的图片不同的布局
     *
     * @param url      图片的url
     * @param iv_image 图片控件
     */
    private RelativeLayout.LayoutParams setParam(Context context, String url, ImageView iv_image) {
        Bitmap bitmap = ImageCache.getBitmap(url);
        if (bitmap != null) {
            int imageWidth = bitmap.getWidth();
            int imageHeight = bitmap.getHeight();
            RelativeLayout.LayoutParams lp;
            int viewWidth = G.dp2px(context, 180);
            int viewHeight = G.dp2px(context, 180);
            if (imageHeight > imageWidth) {
                lp = new RelativeLayout.LayoutParams(viewWidth, viewHeight);
                iv_image.setScaleType(ImageView.ScaleType.FIT_START);
            } else if (imageHeight == imageWidth) {
                lp = new RelativeLayout.LayoutParams(viewWidth, viewWidth);
                iv_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                lp = new RelativeLayout.LayoutParams(viewWidth, viewHeight * 2 / 3);
                iv_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            return lp;
        }
        return null;
    }

    @Override
    public void onClick(View view) {

    }
}
