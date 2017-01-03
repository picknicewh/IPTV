package net.hunme.kidsworld_iptv.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * ======================================
 * 作者：ZLL
 * 时间：2016/12/28
 * 名称：
 * 附加注释：
 * ======================================
 */

public class ViewPageAdapter extends PagerAdapter {
    private List<View> viewlist;

    public ViewPageAdapter(List<View> viewlist) {
        this.viewlist = viewlist;
    }

    @Override
    public int getCount() {
        return viewlist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = viewlist.get(position);
        container.addView(view);
        return viewlist.get(position);
    }

    /**
     *  解决list数据清空之后 视图不销毁的bug
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        return null != viewlist && viewlist.size() == 0 ? POSITION_NONE : super.getItemPosition(object);
    }
}
