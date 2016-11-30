package net.hunme.kidsworld_iptv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.hunme.kidsworld_iptv.R;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/25
 * 描    述：资源信息详情列表适配器
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class GridResourceInfoAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 40;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHold hold;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_resource_info, viewGroup, false);
            new ViewHold(view);
        }
        hold = (ViewHold) view.getTag();

        return view;
    }

    class ViewHold {
        ImageView ivGird;
        TextView tvName;

        public ViewHold(View view) {
            ivGird = (ImageView) view.findViewById(R.id.iv_gird);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(this);
        }
    }
}
