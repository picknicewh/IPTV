package net.hunme.kidsworld_iptv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import net.hunme.kidsworld_iptv.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/28
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class DynamicAdapter extends BaseAdapter {
    private List<String> urlList;
    private List<View>viewList;
    public DynamicAdapter( List<String> urlList) {
        this.urlList = urlList;
        viewList=new ArrayList<>();
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHold hold;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dynamic, viewGroup, false);
            new ViewHold(view);
//            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, G.size.H);
//            view.setLayoutParams(layoutParams);
        }
        hold = (ViewHold) view.getTag();
//        ImageCache.imageLoader(G.getBigImageUrl(urlList.get(i)), hold.ivDynamic);
//        viewList.add(view);
        return view;
    }

    class ViewHold {
        ImageView ivDynamic;

        public ViewHold(View view) {
            ivDynamic = (ImageView) view.findViewById(R.id.iv_dynamic);
            view.setTag(this);
        }
    }


}
