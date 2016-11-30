package net.hunme.kidsworld_iptv.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import net.hunme.baselibrary.util.G;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/24
 * 描    述：Fragment 创建封装类
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class FragmentUtil {
    private FragmentTransaction fragmentTransaction;
    private Fragment mContent;
    /**
     *  设置fragment
     * @param fragment 当前fragmentActivity
     * @param toFragment 设置的fragment
     * @param fragmentlayoutId fragment所处xml的id
     */
    public void setFragment(FragmentActivity fragment, Fragment toFragment,int fragmentlayoutId){
        if (mContent != toFragment) {
            fragmentTransaction = fragment.getSupportFragmentManager().beginTransaction();

            if (mContent == null) {
                fragmentTransaction.replace(fragmentlayoutId, toFragment).commit();
                mContent = toFragment;
                return;
            }
//            if ((int) (newView.getTag()) > (int) (oldView.getTag())) {
//                fragmentTransaction = fragmentTransaction.setCustomAnimations(R.anim.slide_top_in, R.anim.slide_top_out).hide(mContent);
//            } else {
//                fragmentTransaction = fragmentTransaction.setCustomAnimations(R.anim.slide_top_in_s, R.anim.slide_top_out_s).hide(mContent);
//            }
            G.log("================="+!toFragment.isAdded());
            fragmentTransaction.hide(mContent); //将之前页面表现在最前面
            if (!toFragment.isAdded()) // 先判断是否被add过
                fragmentTransaction.add(fragmentlayoutId, toFragment).commit(); // 隐藏当前的fragment，add下一个到Activity中
            else
                fragmentTransaction.show(toFragment).commit(); // 隐藏当前的fragment，显示下一个

            mContent = toFragment; //重新赋值
        }
    }
}
