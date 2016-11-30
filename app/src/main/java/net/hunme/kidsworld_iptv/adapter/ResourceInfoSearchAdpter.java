package net.hunme.kidsworld_iptv.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.hunme.kidsworld_iptv.R;

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
public class ResourceInfoSearchAdpter extends RecyclerView.Adapter<ResourceInfoSearchAdpter.ViewHold>{
    private List<String>menuList;

    public ResourceInfoSearchAdpter(List<String> menuList) {
        this.menuList = menuList;
    }


    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resource_info_search,parent,false);
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


    class ViewHold extends RecyclerView.ViewHolder{
        TextView tvSearchMenu;
        public ViewHold(View itemView) {
            super(itemView);
            tvSearchMenu= (TextView) itemView.findViewById(R.id.tv_search_menu);
            itemView.setTag(this);
        }
    }
}
