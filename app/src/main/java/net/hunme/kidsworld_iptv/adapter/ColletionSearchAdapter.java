package net.hunme.kidsworld_iptv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.hunme.kidsworld_iptv.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/30
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class ColletionSearchAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return 20;
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
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_collection_search_result, viewGroup, false);
            new ViewHolder(view);
        }
        return view;
    }

    class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;

        @Bind(R.id.tv_progress)
        TextView tvProgress;

        @Bind(R.id.tv_album)
        TextView tvAlbum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
}
