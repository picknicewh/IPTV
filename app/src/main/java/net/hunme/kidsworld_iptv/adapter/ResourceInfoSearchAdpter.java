package net.hunme.kidsworld_iptv.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.util.OnPaginSelectViewListen;

import java.util.List;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/25
 * 描    述：资源详情集数选择
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class ResourceInfoSearchAdpter extends RecyclerView.Adapter<ResourceInfoSearchAdpter.ViewHold> implements RecyclerViewTV.OnItemListener {
    private List<String> menuList;
    private MainUpView upView;
    private RecyclerViewTV recyclerViewTV;
    private OnPaginSelectViewListen onPaginSelectViewListen;

    public ResourceInfoSearchAdpter(RecyclerViewTV recyclerViewTV, MainUpView upView, List<String> menuList) {
        this.recyclerViewTV = recyclerViewTV;
        this.upView = upView;
        this.menuList = menuList;
        this.upView.setUpRectResource(R.drawable.select_frame);
        this.recyclerViewTV.setOnItemListener(this);

    }

    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resource_info_search, parent, false);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(ViewHold holder, int position) {
        holder.tvSearchMenu.setText(menuList.get(position));
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }


    @Override
    public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {
        upView.setUnFocusView(itemView);
    }

    @Override
    public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {
        upView.setFocusView(itemView, 1.0f);
        onPaginSelectViewListen.onPaginListen(itemView,position);
    }

    @Override
    public void onReviseFocusFollow(RecyclerViewTV parent, View itemView, int position) {
        upView.setFocusView(itemView, 1.0f);
        onPaginSelectViewListen.onPaginListen(itemView,position);
    }


    class ViewHold extends RecyclerView.ViewHolder {
        TextView tvSearchMenu;

        public ViewHold(View itemView) {
            super(itemView);
            tvSearchMenu = (TextView) itemView.findViewById(R.id.tv_search_menu);
            itemView.setTag(this);
        }
    }

    public void setOnPaginSelectViewListen(OnPaginSelectViewListen onPaginSelectViewListen) {
        this.onPaginSelectViewListen = onPaginSelectViewListen;
    }
}
