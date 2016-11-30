package net.hunme.baselibrary.image;

import android.support.annotation.Nullable;
import android.view.View;

import net.hunme.baselibrary.util.G;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/11
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class ImageMatrix {
    private static ImageMatrix iMatrix;

    public static ImageMatrix getIntence() {
        if (iMatrix == null) {
            iMatrix = new ImageMatrix();
        }
        return iMatrix;
    }

    /**
     * 放大图片
     */
    public void enlargeView(@Nullable View view) {
        if (view != null) {
            view.animate().scaleX(G.ENLARGE).scaleY(G.ENLARGE).setDuration(300).start();
            view.bringToFront();
        }
    }

    public void reductionView(@Nullable View view) {
        if (view != null) {
            view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(300).start();
        }
    }

    public void enlargeNoBingView(@Nullable View view) {
        if (view != null) {
            view.animate().scaleX(1.2f).scaleY(1.2f).setDuration(300).start();
//            view.bringToFront();
        }
    }
}
